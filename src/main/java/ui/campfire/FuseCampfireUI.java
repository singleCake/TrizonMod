package ui.campfire;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import card.TrizonCard;
import card.TrizonFusedCard;
import card.special.Empty;
import card.special.EmptyFuseCard;
import effect.CampfireFuseEffect;
import effect.CampfireSelectCardEffect;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class FuseCampfireUI {
    public CancelButton cancelButton = new CancelButton();

    public GridSelectConfirmButton confirmButton = new GridSelectConfirmButton("确认");

    public AbstractCard card1 = null;

    public AbstractCard card2 = null;

    private AbstractCard fuseCard = null;

    private AbstractCard clickStartedCard = null;

    private AbstractCard hoveredCard = null;

    public boolean selectingCard = false;

    private static final float CARD_START_X = Settings.WIDTH / 2.0F;

    private static final float CARD_START_Y = Settings.HEIGHT / 7.0F * 2.1F;

    private static final float CARD_SPACING_X = 400.0F * Settings.xScale;

    public FuseCampfireUI() {
        super();
        initDisplayCards();
    }

    private void update() {
        updateCardsMode();
        updateClicking();

        if (fuseCard.cardID != EmptyFuseCard.ID) {
            confirmButton.show();
            confirmButton.isDisabled = false;
            if (confirmButton.hb.hovered) {
                if (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) {
                    // AbstractDungeon.effectList.add(new CampfireSelectCardEffect(2, fuseCard));
                    // selectingCard = true;
                    AbstractDungeon.topLevelEffects
                            .add(new PurgeCardEffect(card1, Settings.WIDTH / 3.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.group.removeIf(c -> c.uuid.equals(card1.uuid));
                    AbstractDungeon.topLevelEffects
                            .add(new PurgeCardEffect(card2, Settings.WIDTH / 3.0F * 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.group.removeIf(c -> c.uuid.equals(card2.uuid));
                    AbstractDungeon.effectList.add(new CampfireFuseEffect(card1, card2, fuseCard));
                    ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.somethingSelected = true;
                }
            }
        } else {
            confirmButton.hide();
            confirmButton.isDisabled = true;
        }

        this.cancelButton.update();
        this.confirmButton.update();
    }

    private void updateCardsMode() {
        this.hoveredCard = null;
        updateEachCard(card1);
        updateEachCard(card2);
        updateEachCard(fuseCard);
    }

    private void updateEachCard(AbstractCard card) {
        if (card == null)
            return;
        card.update();
        card.updateHoverLogic();
        if (card.hb.hovered) {
            this.hoveredCard = card;
        }
    }

    private void updateClicking() {
        if (selectingCard)
            return;
        if (this.hoveredCard != null) {
            // 左键选择卡牌
            if (InputHelper.justClickedLeft)
                this.clickStartedCard = this.hoveredCard;
            if (((InputHelper.justReleasedClickLeft && this.hoveredCard == this.clickStartedCard)
                    || CInputActionSet.select
                            .isJustPressed())) {
                InputHelper.justReleasedClickLeft = false;
                int index = -1;
                if (this.hoveredCard == card1) {
                    index = 0;
                } else if (this.hoveredCard == card2) {
                    index = 1;
                }
                if (index >= 0) {
                    AbstractDungeon.effectList.add(new CampfireSelectCardEffect(index, getCardGroup(index)));
                    selectingCard = true;
                }
                this.clickStartedCard = null;
            }
            // 右键打开卡牌详情
            if (InputHelper.justClickedRight)
                this.clickStartedCard = this.hoveredCard;
            if (((InputHelper.justReleasedClickRight && this.hoveredCard == this.clickStartedCard)
                    || CInputActionSet.select
                            .isJustPressed())) {
                InputHelper.justReleasedClickRight = false;
                if (this.hoveredCard.cardID != Empty.ID && this.hoveredCard.cardID != EmptyFuseCard.ID)
                    CardCrawlGame.cardPopup.open(this.hoveredCard);
                this.clickStartedCard = null;
            }
        } else {
            this.clickStartedCard = null;
        }
    }

    private CardGroup getCardGroup(int index) {
        CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        Iterator<AbstractCard> var2 = AbstractDungeon.player.masterDeck.group.iterator();
        while (var2.hasNext()) {
            AbstractCard c = var2.next();
            if (c instanceof TrizonCard) {
                TrizonCard trizonCard = (TrizonCard) c;
                if (trizonCard.canFuse()) {
                    cardGroup.addToTop(c);
                }
            }
        }

        if (index == 0) {
            cardGroup.group.removeIf(c -> c.uuid.equals(card2.uuid));
        } else if (index == 1) {
            cardGroup.group.removeIf(c -> c.uuid.equals(card1.uuid));
        }

        return cardGroup;
    }

    private void renderCards(SpriteBatch sb) {
        float cardX = CARD_START_X - CARD_SPACING_X / 2.0F;

        card1.current_x = cardX;
        card1.target_x = cardX;
        card1.current_y = CARD_START_Y;
        card1.target_y = CARD_START_Y;
        card1.drawScale *= 0.65F / 0.75F;
        card1.render(sb);
        card1.drawScale *= 0.75F / 0.65F;

        cardX += CARD_SPACING_X;
        card2.current_x = cardX;
        card2.target_x = cardX;
        card2.current_y = CARD_START_Y;
        card2.target_y = CARD_START_Y;
        card2.drawScale *= 0.65F / 0.75F;
        card2.render(sb);
        card2.drawScale *= 0.75F / 0.65F;

        fuseCard.current_x = Settings.WIDTH / 2.0F;
        fuseCard.target_x = Settings.WIDTH / 2.0F;
        fuseCard.current_y = Settings.HEIGHT / 7.0F * 5.0F;
        fuseCard.target_y = Settings.HEIGHT / 7.0F * 5.0F;
        fuseCard.drawScale *= 0.9F / 0.75F;
        fuseCard.render(sb);
        fuseCard.drawScale *= 0.75F / 0.9F;
    }

    public void initDisplayCards() {
        card1 = new Empty();
        card2 = new Empty();
        fuseCard = new EmptyFuseCard();
    }

    public void setDisplayCards(int index, AbstractCard card) {
        if (index == 0) {
            card1 = card;
        } else if (index == 1) {
            card2 = card;
        }
        if (card1.cardID != Empty.ID && card2.cardID != Empty.ID) {
            if (TrizonFusedCard.canFuse((TrizonCard) card1, (TrizonCard) card2)) {
                fuseCard = new TrizonFusedCard((TrizonCard) card1, (TrizonCard) card2);
            } else {
                fuseCard = new EmptyFuseCard();
            }
        } else {
            fuseCard = new EmptyFuseCard();
        }
    }

    @SpirePatch(clz = CampfireUI.class, method = SpirePatch.CLASS)
    public static class FuseCampfireUIFieldPatch {
        public static SpireField<FuseCampfireUI> fuseUI = new SpireField<>(() -> null);

        public static SpireField<Boolean> fuseMode = new SpireField<>(() -> false);
    }

    public static boolean isInFuseMode(CampfireUI instance) {
        return FuseCampfireUIFieldPatch.fuseMode.get(instance);
    }

    public static FuseCampfireUI getFuseUI(CampfireUI instance) {
        return FuseCampfireUIFieldPatch.fuseUI.get(instance);
    }

    public static void initFuseUI(CampfireUI instance) {
        FuseCampfireUIFieldPatch.fuseUI.set(instance, new FuseCampfireUI());
    }

    public static void switchMode(boolean toFuseMode) {
        CampfireUI instance = ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI;

        if (toFuseMode) {
            FuseCampfireUIFieldPatch.fuseMode.set(instance, true);
            FuseCampfireUIFieldPatch.fuseUI.get(instance).cancelButton.show("取消");
        } else {
            FuseCampfireUIFieldPatch.fuseMode.set(instance, false);
            FuseCampfireUIFieldPatch.fuseUI.get(instance).cancelButton.hide();
        }
    }

    @SpirePatch(clz = CampfireUI.class, method = "render")
    public static class RenderPatch {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(CampfireUI __instance, SpriteBatch sb) {
            if (isInFuseMode(__instance)) {
                FuseCampfireUI fuseUI = getFuseUI(__instance);
                fuseUI.renderArrows(sb);
                fuseUI.renderCards(sb);
                fuseUI.cancelButton.render(sb);
                fuseUI.confirmButton.render(sb);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    private float arrowScale1 = 1.0F, arrowScale2 = 1.0F, arrowScale3 = 1.0F, arrowTimer = 0.0F;

    private void renderArrows(SpriteBatch sb) {
        float x = Settings.WIDTH / 2.0F - 128.0F * Settings.scale - 32.0F;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F,
                this.arrowScale1 * Settings.scale, this.arrowScale1 * Settings.scale, 90.0F, 0, 0, 64, 64, false,
                false);
        x += 128.0F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F,
                this.arrowScale2 * Settings.scale, this.arrowScale2 * Settings.scale, 90.0F, 0, 0, 64, 64, false,
                false);
        x += 128.0F * Settings.scale;
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F,
                this.arrowScale3 * Settings.scale, this.arrowScale3 * Settings.scale, 90.0F, 0, 0, 64, 64, false,
                false);
        this.arrowTimer += Gdx.graphics.getDeltaTime() * 2.0F;
        this.arrowScale1 = 0.8F + (MathUtils.cos(this.arrowTimer) + 1.0F) / 8.0F;
        this.arrowScale2 = 0.8F + (MathUtils.cos(this.arrowTimer - 0.8F) + 1.0F) / 8.0F;
        this.arrowScale3 = 0.8F + (MathUtils.cos(this.arrowTimer - 1.6F) + 1.0F) / 8.0F;
    }

    // @SpirePatch(clz = CampfireUI.class, method = "renderCampfireButtons")
    // public static class RenderCampfireButtonsPatch {
    // @SpirePrefixPatch
    // public static SpireReturn<Void> Prefix(CampfireUI __instance, SpriteBatch sb)
    // {
    // if (isInFuseMode(__instance)) {
    // return SpireReturn.Return();
    // }
    // return SpireReturn.Continue();
    // }
    // }

    @SpirePatch(clz = CampfireUI.class, method = "update")
    public static class UpdatePatch {
        @SpireInsertPatch(rloc = 11)
        public static void Insert(CampfireUI __instance) {
            if (isInFuseMode(__instance)) {
                FuseCampfireUI fuseUI = getFuseUI(__instance);
                fuseUI.update();
            }
        }
    }

    @SpirePatch(clz = AbstractCampfireOption.class, method = "update")
    public static class AbstractCampfireOptionUpdatePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCampfireOption __instance) {
            CampfireUI campfireUI = ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI;
            if (isInFuseMode(campfireUI)) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        @SpireInsertPatch(rloc = 25)
        public static SpireReturn<Void> Insert(AbstractCampfireOption __instance) {
            if (__instance instanceof FuseOption) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
