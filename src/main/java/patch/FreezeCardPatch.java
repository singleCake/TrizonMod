package patch;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import action.TrizonUnFreezeAllCardAtStartOfTurnAction;
import card.TrizonCard;
import card.helper.Modifier.TrizonPenguinModifier.FrozenNumFieldPatch;

public class FreezeCardPatch {
    private static final String FREEZE_CARD_MESSAGE = CardCrawlGame.languagePack.getUIString("Trizon:FreezeCardMessage").TEXT[0];
    private static final String FREEZE_TEXTURE_PATH = "TrizonResources/img/512/frost.png";
    private static Texture freezeTexture;

    private static Texture getFreezeTexture() {
        if (freezeTexture == null) {
            freezeTexture = ImageMaster.loadImage(FREEZE_TEXTURE_PATH);
        }
        return freezeTexture;
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class FrozenField {
        public static SpireField<Boolean> frozen = new SpireField<>(() -> false);
    }

    public static void Freeze(AbstractCard card) {
        if (card instanceof TrizonCard) {
            ((TrizonCard) card).triggerOnFrozen();
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof TrizonCard) {
                ((TrizonCard) c).triggerOnOtherCardFrozenAfterExhausted();
            }
        }
        FrozenNumFieldPatch.addFrozenNum();
        FrozenField.frozen.set(card, true);
    }

    public static void Unfreeze(AbstractCard card) {
        FrozenField.frozen.set(card, false);
    }

    public static boolean isFrozen(AbstractCard card) {
        return FrozenField.frozen.get(card);
    }

    // 渲染冻结贴图
    @SpirePatch(clz = AbstractCard.class, method = "renderCard")
    public static class RenderCardPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            if (isFrozen(__instance)) {
                Texture freezeTexture = getFreezeTexture();
                float drawX = __instance.current_x;
                float drawY = __instance.current_y;
                sb.draw(freezeTexture, drawX - 256.0F, drawY - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, __instance.drawScale * Settings.scale, __instance.drawScale * Settings.scale, __instance.angle, 0, 0, 512, 512, false, false);
            }
        }
    }

    // 冻结的牌回合结束时保留
    @SpirePatch(clz = DiscardAtEndOfTurnAction.class, method = "update")
    public static class DiscardAtEndOfTurnActionPatch {
        @SpireInsertPatch(rloc = 5, localvars = {"e"})
        public static void Insert(DiscardAtEndOfTurnAction __instance, @ByRef AbstractCard[] e) {
            if (isFrozen(e[0]) && !e[0].retain) {
                e[0].retain = true;
            }
        }
    }

    //冻结的牌无法被打出
    @SpirePatch(clz = AbstractCard.class, method = "canUse")
    public static class AbstractCardCanUsePatch {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            if (FrozenField.frozen.get(__instance)) {
                __instance.cantUseMessage = FREEZE_CARD_MESSAGE;
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

    // 回合开始时所有牌解冻
    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnCards")
    public static class GameActionManagerGetNextActionPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance) {
            AbstractDungeon.actionManager.addToBottom(new TrizonUnFreezeAllCardAtStartOfTurnAction());
        }
    }
}
