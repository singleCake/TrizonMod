package patch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import action.TrizonUnFreezeAllCardAtStartOfTurnAction;

public class FreezeCardPatch {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class FrozenField {
        public static SpireField<Boolean> frozen = new SpireField<>(() -> false);
    }

    public static void Freeze(AbstractCard card) {
        FrozenField.frozen.set(card, true);
    }

    public static void Unfreeze(AbstractCard card) {
        FrozenField.frozen.set(card, false);
    }

    // 冻结的牌回合结束时保留
    @SpirePatch(clz = RestoreRetainedCardsAction.class, method = "update")
    public static class RestoreRetainedCardsActionPatch {
        @SpireInsertPatch(rloc = 4, localvars = {"e"})
        public static void Insert(RestoreRetainedCardsAction __instance, @ByRef AbstractCard[] e) {
            if (FrozenField.frozen.get(e[0]) && !e[0].retain) {
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
    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class GameActionManagerGetNextActionPatch {
        @SpireInsertPatch(rloc = 236)
        public static void Insert(GameActionManager __instance) {
            __instance.addToBottom(new TrizonUnFreezeAllCardAtStartOfTurnAction());
        }
    }
}
