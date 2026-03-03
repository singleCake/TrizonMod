package action;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import card.uncommon.Bamboo;

public class TrizonBambooAction extends AbstractTrizonAction {
    public TrizonBambooAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractCard tmp = new Bamboo();
        tmp.setCostForTurn(0);
        tmp.exhaust = true;
        this.addToTop(new MakeTempCardInHandAction(tmp, amount));
        this.isDone = true;
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch {
        @SpireInsertPatch(rloc = 6, localvars = {"card"})
        public static void Insert(AbstractCard __instance, @ByRef AbstractCard[] card) {
            card[0].exhaust = __instance.exhaust;
        }
    }
}
