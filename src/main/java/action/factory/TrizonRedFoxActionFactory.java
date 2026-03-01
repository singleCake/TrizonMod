package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonRedFoxAction;

public class TrizonRedFoxActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonRedFoxAction.class);

    public TrizonRedFoxActionFactory() {
        this.amount = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonRedFoxAction(amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        TrizonRedFoxActionFactory copy = new TrizonRedFoxActionFactory();
        copy.amount = this.amount;
        return copy;
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonRedFoxActionFactory) {
            this.amount += ((TrizonRedFoxActionFactory) other).amount;
            return true;
        }

        return false;
    }
}
