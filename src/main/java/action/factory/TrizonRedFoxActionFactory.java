package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonRedFoxAction;

public class TrizonRedFoxActionFactory extends AbstractTrizonFactory {
    public TrizonRedFoxActionFactory() {
        this.amount = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonRedFoxAction(amount);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonRedFoxActionFactory) {
            this.amount += ((TrizonRedFoxActionFactory) other).amount;
        }
    }
}
