package card.helper.DynamicVariable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.abstracts.DynamicVariable;
import card.TrizonFusedCard;

public class FuseDynamicVariable extends DynamicVariable {
    @Override
    public String key() {
        return "FDV";
    }

    private static int index = -1;
    private static int base_index = -1;
    private static int modified_index = -1;

    @Override
    public int value(AbstractCard card) {
        return card instanceof TrizonFusedCard ? ((TrizonFusedCard) card).fuseDVs.get(index).value : -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card instanceof TrizonFusedCard ? ((TrizonFusedCard) card).fuseDVs.get(base_index).baseValue : -1;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        index++;
        base_index++;
        modified_index++;
        return card instanceof TrizonFusedCard ? ((TrizonFusedCard) card).fuseDVs.get(modified_index).isModified : false;
    }

    private static void reset() {
        index = -1;
        base_index = -1;
        modified_index = -1;
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDescriptionCN")
    public static class RenderDescriptionCNPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (__instance instanceof TrizonFusedCard) {
                FuseDynamicVariable.reset();
            }
        }

        // @SpireInsertPatch(rloc = 32, localvars = {"tmp"})
        // public static void Insert(AbstractCard __instance, SpriteBatch sb, String tmp) {
        //     if (__instance instanceof TrizonFusedCard) {
        //         System.out.println("tmp: " + tmp);
        //     }
        // }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescriptionCN")
    public static class RenderDescriptionCNPatch2 {
        @SpirePrefixPatch
        public static void Prefix(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (___card instanceof TrizonFusedCard) {
                FuseDynamicVariable.reset();
            }
        }
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
