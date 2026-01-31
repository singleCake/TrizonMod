package patch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.TrizonUnFreezeAllCardAtStartOfTurnAction;
import card.TrizonCard;

public class FreezeCardPatch {
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
        FrozenField.frozen.set(card, true);
    }

    public static void Unfreeze(AbstractCard card) {
        FrozenField.frozen.set(card, false);
    }

    public static boolean isFrozen(AbstractCard card) {
        return FrozenField.frozen.get(card);
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
