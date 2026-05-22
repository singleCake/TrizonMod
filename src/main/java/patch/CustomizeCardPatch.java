package patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import card.TrizonFusedCard;
import effect.ChangeImgSelectCardEffect;
import ui.panel.CardRenamePanel;
import ui.singleCardView.SingleCardViewButton;

public class CustomizeCardPatch {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Trizon:SingleCardViewButton").TEXT;

    private static TrizonFusedCard card = null;

    @SpirePatch(clz = SingleCardViewPopup.class, method = SpirePatch.CLASS)
    public static class CustomizeCardPatchField {
        public static SpireField<SingleCardViewButton> changeImgButton = new SpireField<>(() -> null);

        public static SpireField<SingleCardViewButton> renameButton = new SpireField<>(() -> null);
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class, CardGroup.class })
    public static class OpenPatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance, AbstractCard card, CardGroup group) {
            CustomizeCardPatchField.changeImgButton.set(__instance, null);
            CustomizeCardPatchField.renameButton.set(__instance, null);
            CustomizeCardPatch.card = null;

            if (AbstractDungeon.player == null) {
                return;
            }
            if (card instanceof TrizonFusedCard && group.equals(AbstractDungeon.player.masterDeck)) {
                CustomizeCardPatchField.changeImgButton.set(__instance,
                        new SingleCardViewButton(300.0F * Settings.scale, Settings.HEIGHT / 5.0F * 4.0F, TEXT[0]));
                CustomizeCardPatchField.renameButton.set(__instance, new SingleCardViewButton(300.0F * Settings.scale,
                        Settings.HEIGHT / 5.0F * 4.0F - 100.0F * Settings.scale, TEXT[4]));
                CustomizeCardPatch.card = (TrizonFusedCard) card;
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class })
    public static class OpenNoGroupPatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance, AbstractCard card) {
            CustomizeCardPatchField.changeImgButton.set(__instance, null);
            CustomizeCardPatchField.renameButton.set(__instance, null);
            CustomizeCardPatch.card = null;
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "update")
    public static class UpdatePatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance) {
            SingleCardViewButton changeImgButton = CustomizeCardPatchField.changeImgButton.get(__instance);
            SingleCardViewButton renameButton = CustomizeCardPatchField.renameButton.get(__instance);
            if (changeImgButton != null) {
                changeImgButton.update();
            }
            if (renameButton != null) {
                renameButton.update();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class UpdateInputPatch {
        @SpireInsertPatch(rloc = 15)
        public static SpireReturn<Void> Insert(SingleCardViewPopup __instance) {
            SingleCardViewButton changeImgButton = CustomizeCardPatchField.changeImgButton.get(__instance);
            SingleCardViewButton renameButton = CustomizeCardPatchField.renameButton.get(__instance);
            if (changeImgButton != null) {
                if (changeImgButton.hb.hovered) {
                    changeImgButton.hb.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.topLevelEffectsQueue.add(new ChangeImgSelectCardEffect(card));
                }
            }
            if (renameButton != null) {
                if (renameButton.hb.hovered) {
                    renameButton.hb.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    CardRenamePanel.CardRenamePanelField.openRenamePanel(__instance, card,
                            AbstractDungeon.player.masterDeck);
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "render")
    public static class RenderPatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            SingleCardViewButton changeImgButton = CustomizeCardPatchField.changeImgButton.get(__instance);
            SingleCardViewButton renameButton = CustomizeCardPatchField.renameButton.get(__instance);
            if (changeImgButton != null) {
                changeImgButton.render(sb);
            }
            if (renameButton != null) {
                renameButton.render(sb);
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = SpirePatch.CLASS)
    public static class GridCardSelectScreenField {
        public static SpireField<Boolean> forChangeImg = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "open", paramtypez = { CardGroup.class, int.class,
            String.class, boolean.class, boolean.class, boolean.class, boolean.class })
    public static class GridCardSelectScreenOpenPatch {
        @SpirePostfixPatch
        public static void Postfix(GridCardSelectScreen __instance, CardGroup group, int numCards, String tipMsg,
                boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge) {
            GridCardSelectScreenField.forChangeImg.set(AbstractDungeon.gridSelectScreen, false);
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class GridCardSelectScreenUpdatePatch {
        private static AbstractCard hoveredCard = null;

        @SpireInsertPatch(rloc = 96)
        public static void Insert1(GridCardSelectScreen __instance) {
            if (GridCardSelectScreenField.forChangeImg.get(__instance)) {
                CustomCard hoveredCard = (CustomCard) ReflectionHacks.getPrivate(
                        __instance, GridCardSelectScreen.class, "hoveredCard");
                if (hoveredCard == null) {
                    return;
                }
                GridCardSelectScreenUpdatePatch.hoveredCard = hoveredCard;

                __instance.upgradePreviewCard = card.makeStatEquivalentCopy();
                ((TrizonFusedCard) __instance.upgradePreviewCard).textureImg = hoveredCard.textureImg;
                ((TrizonFusedCard) __instance.upgradePreviewCard).loadCardImage(hoveredCard.textureImg);

                ReflectionHacks.setPrivate(__instance, GridCardSelectScreen.class, "hoveredCard", card);
            }
        }

        @SpireInsertPatch(rloc = 214)
        public static void Insert2(GridCardSelectScreen __instance) {
            if (GridCardSelectScreenField.forChangeImg.get(__instance)) {
                __instance.selectedCards.clear();
                __instance.selectedCards.add(GridCardSelectScreenUpdatePatch.hoveredCard);
                GridCardSelectScreenUpdatePatch.hoveredCard = null;
            }
        }
    }
}
