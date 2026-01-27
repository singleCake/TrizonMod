package patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RoadBlockPatch {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class CanUseThisTurnField {
        public static SpireField<Boolean> canUseThisTurn = new SpireField<>(() -> true);
    }

    @SpirePatch(clz = AbstractCard.class, method = "canUse")
    public static class CanUsePatch {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(AbstractCard __instance) {
            if (!CanUseThisTurnField.canUseThisTurn.get(__instance)) {
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "resetAttributes")
    public static class ResetAttributesPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance) {
            CanUseThisTurnField.canUseThisTurn.set(__instance, true);
        }
    }
}
