package action.factory;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import card.uncommon.Bamboo;

public class TrizonBambooActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonBambooActionFactory.class);

    public TrizonBambooActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        AbstractCard tmp = new Bamboo();
        tmp.setCostForTurn(0);
        tmp.exhaust = true;
        return new MakeTempCardInHandAction(tmp, amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonBambooActionFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonBambooActionFactory) {
            TrizonBambooActionFactory o = (TrizonBambooActionFactory) other;
            this.amount += o.amount;
            return true;
        }

        return false;
    }
}
