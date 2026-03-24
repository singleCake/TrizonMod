package ui.collect;

import java.io.IOException;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import modcore.TrizonMod;
import patch.TrizonEnum;

public class ChooseCollectScreen implements ISubscriber {
    private static final String CONFIG_MOD_ID = "TrizonMod";
    private static final String CONFIG_FILE_NAME = "CollectConfig";
    private static final String CONFIG_KEY_COLLECTION_CARDS = "collectionCards";
    private static final String CONFIG_KEY_COLLECTION_CARD_IDS = "collectionCardIds";

    private static class CollectCardSnapshot {
        CardSave cardSave;
        String customDataType;
        String customDataJson;
    }

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Trizon:ChooseCollectScreen").TEXT;

    public static ChooseCollectScreen Inst = new ChooseCollectScreen();

    public MyGridCardSelectScreen gridCardSelectScreen = new MyGridCardSelectScreen();

    private CheckCollectButton checkCollectButton = new CheckCollectButton(350.0F * Settings.scale,
            350.0F * Settings.scale, TEXT[0]);

    private CheckCollectButton cancelButton = new CheckCollectButton(1600.0F * Settings.scale, 350.0F * Settings.scale,
            TEXT[3]);

    public CardGroup collection = new CardGroup(TrizonEnum.COLLECT);

    public AbstractCard cardToObtain = null;

    private ChooseCollectScreen() {
        BaseMod.subscribe(this);
        loadCollectionFromConfig();
    }

    private void initDefaultCollection() {
        this.collection.clear();
    }

    public void addToCollection(AbstractCard card) {
        this.collection.addToTop(card);
        saveCollectionToConfig();
    }

    public void removeFromCollection(AbstractCard card) {
        if (this.collection.group.size() <= 1) {
            gridCardSelectScreen.close();
            this.collection.clear();
            saveCollectionToConfig();
            return;
        }
        for (AbstractCard c : this.collection.group) {
            if (c.cardID.equals(card.cardID)) {
                this.collection.removeCard(c);
                break;
            }
        }
        saveCollectionToConfig();
    }

    public void saveCollectionToConfig() {
        try {
            SpireConfig config = new SpireConfig(CONFIG_MOD_ID, CONFIG_FILE_NAME);
            CollectCardSnapshot[] snapshots = this.collection.group.stream()
                    .map(c -> {
                        CollectCardSnapshot snapshot = new CollectCardSnapshot();
                        snapshot.cardSave = new CardSave(c.cardID, c.timesUpgraded, c.misc);

                        if (c instanceof CustomSavable<?>) {
                            Object customData = ((CustomSavable<?>) c).onSave();
                            if (customData != null) {
                                snapshot.customDataType = customData.getClass().getName();
                                snapshot.customDataJson = BaseMod.gson.toJson(customData);
                            }
                        }

                        return snapshot;
                    })
                    .toArray(CollectCardSnapshot[]::new);
            config.setString(CONFIG_KEY_COLLECTION_CARDS, BaseMod.gson.toJson(snapshots));
            config.save();
        } catch (IOException e) {
            TrizonMod.logger.error("Failed to save collect cards.", e);
        }
    }

    private void loadCollectionFromConfig() {
        try {
            SpireConfig config = new SpireConfig(CONFIG_MOD_ID, CONFIG_FILE_NAME);
            String snapshotsJson = config.getString(CONFIG_KEY_COLLECTION_CARDS);
            if (snapshotsJson != null && !snapshotsJson.isEmpty()) {
                CollectCardSnapshot[] snapshots = BaseMod.gson.fromJson(snapshotsJson, CollectCardSnapshot[].class);
                this.collection.clear();
                if (snapshots != null) {
                    for (CollectCardSnapshot snapshot : snapshots) {
                        if (snapshot == null) {
                            continue;
                        }

                        if (snapshot.cardSave != null) {
                            AbstractCard card = createCardFromSave(snapshot.cardSave);
                            if (card != null) {
                                loadCustomSavableData(card, snapshot.customDataType, snapshot.customDataJson);
                                this.collection.addToTop(card);
                            }
                            continue;
                        }
                    }
                }
                return;
            }

            String cardIdsJson = config.getString(CONFIG_KEY_COLLECTION_CARD_IDS);
            if (cardIdsJson == null || cardIdsJson.isEmpty()) {
                initDefaultCollection();
                saveCollectionToConfig();
                return;
            }

            String[] cardIds = BaseMod.gson.fromJson(cardIdsJson, String[].class);
            this.collection.clear();
            if (cardIds != null) {
                for (String cardId : cardIds) {
                    AbstractCard template = CardLibrary.getCard(cardId);
                    if (template != null) {
                        this.collection.addToTop(template.makeCopy());
                    }
                }
            }
        } catch (Exception e) {
            TrizonMod.logger.error("Failed to load collect cards, fallback to default.", e);
            initDefaultCollection();
            saveCollectionToConfig();
        }
    }

    private AbstractCard createCardFromSave(CardSave cardSave) {
        try {
            AbstractCard card = CardLibrary.getCopy(cardSave.id, cardSave.upgrades, cardSave.misc);
            if (card != null) {
                return card;
            }
        } catch (Exception e) {
            TrizonMod.logger.warn("CardLibrary.getCopy failed, fallback to manual restore: " + cardSave.id, e);
        }

        AbstractCard template = CardLibrary.getCard(cardSave.id);
        if (template == null) {
            return null;
        }

        AbstractCard card = template.makeCopy();
        int upgrades = Math.max(0, cardSave.upgrades);
        for (int i = 0; i < upgrades && card.canUpgrade(); i++) {
            card.upgrade();
        }
        card.misc = cardSave.misc;
        card.applyPowers();
        card.initializeDescription();
        return card;
    }

    @SuppressWarnings("unchecked")
    private void loadCustomSavableData(AbstractCard card, String customDataType, String customDataJson) {
        if (!(card instanceof CustomSavable<?>)) {
            return;
        }
        if (customDataType == null || customDataType.isEmpty()) {
            return;
        }

        try {
            Class<?> dataClass = Class.forName(customDataType);
            Object customData = BaseMod.gson.fromJson(customDataJson, dataClass);
            ((CustomSavable<Object>) card).onLoad(customData);
        } catch (Exception e) {
            TrizonMod.logger.error("Failed to load custom card data for: " + card.cardID, e);
        }
    }

    public void setCardToObtain(AbstractCard card) {
        this.cardToObtain = card;
    }

    public void update() {
        this.gridCardSelectScreen.update();
        if (this.selecting()) {
            return;
        }
        if (!collection.group.isEmpty()) {
            this.checkCollectButton.update();
        }
        if (cardToObtain != null) {
            cardToObtain.update();
            this.cancelButton.update();
        }
        this.updateInput();
    }

    public void updateInput() {
        if (InputHelper.justClickedLeft && this.checkCollectButton.hb.hovered && !collection.group.isEmpty()) {
            this.gridCardSelectScreen.open(this.collection, 1, false, TEXT[1]);
        }
        if (cardToObtain != null && InputHelper.justClickedLeft && this.cancelButton.hb.hovered) {
            this.cardToObtain = null;
        }
    }

    public void render(SpriteBatch sb) {
        if (!collection.group.isEmpty()) {
            this.checkCollectButton.render(sb);
        }

        if (this.cardToObtain != null) {
            this.cardToObtain.current_x = 1600.0F * Settings.scale;
            this.cardToObtain.current_y = 550.0F * Settings.scale;
            this.cardToObtain.render(sb);
            FontHelper.renderFontLeft(sb, FontHelper.panelNameFont, TEXT[2],
                    this.cardToObtain.current_x - 100.0F * Settings.scale,
                    this.cardToObtain.current_y + 180.0F * Settings.scale, Settings.GOLD_COLOR);
            this.cancelButton.render(sb);
        }

        this.gridCardSelectScreen.render(sb);
    }

    public boolean selecting() {
        return this.gridCardSelectScreen.isOpen();
    }
}
