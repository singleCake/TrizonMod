package patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import basemod.ReflectionHacks;
import card.TrizonCard;
import card.TrizonFusedCard;
import effect.ChangeImgSelectCardEffect;
import ui.singleCardView.ChangeImgButton;

public class ChangeImgPatch {
    private static TrizonFusedCard card = null;

    @SpirePatch(clz = SingleCardViewPopup.class, method = SpirePatch.CLASS)
    public static class ChangeImgButtonField {
        public static SpireField<ChangeImgButton> changeImgButton = new SpireField<>(() -> null);
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class, CardGroup.class })
    public static class OpenPatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance, AbstractCard card, CardGroup group) {
            if (AbstractDungeon.player == null) {
                return;
            }
            if (card instanceof TrizonFusedCard && group.equals(AbstractDungeon.player.masterDeck)) {
                ChangeImgButtonField.changeImgButton.set(__instance,
                        new ChangeImgButton(300.0F * Settings.scale, Settings.HEIGHT / 5.0F * 4.0F));
                ChangeImgPatch.card = (TrizonFusedCard) card;
            } else {
                ChangeImgButtonField.changeImgButton.set(__instance, null);
                ChangeImgPatch.card = null;
            }

            GridCardSelectScreenField.forChangeImg.set(AbstractDungeon.gridSelectScreen, false);
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "update")
    public static class UpdatePatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance) {
            ChangeImgButton button = ChangeImgButtonField.changeImgButton.get(__instance);
            if (button != null) {
                button.update();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class UpdateInputPatch {
        @SpireInsertPatch(rloc = 16)
        public static void Insert(SingleCardViewPopup __instance) {
            ChangeImgButton button = ChangeImgButtonField.changeImgButton.get(__instance);
            if (button != null) {
                if (button.hb.hovered) {
                    button.hb.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    AbstractDungeon.closeCurrentScreen();
                    AbstractDungeon.topLevelEffectsQueue.add(new ChangeImgSelectCardEffect(card));
                }
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "render")
    public static class RenderPatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            ChangeImgButton button = ChangeImgButtonField.changeImgButton.get(__instance);
            if (button != null) {
                button.render(sb);
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = SpirePatch.CLASS)
    public static class GridCardSelectScreenField {
        public static SpireField<Boolean> forChangeImg = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class GridCardSelectScreenUpdatePatch {
        private static TrizonCard hoveredCard = null;

        @SpireInsertPatch(rloc = 96)
        public static void Insert1(GridCardSelectScreen __instance) {
            if (GridCardSelectScreenField.forChangeImg.get(__instance)) {
                TrizonCard hoveredCard = (TrizonCard) ReflectionHacks.getPrivate(
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
