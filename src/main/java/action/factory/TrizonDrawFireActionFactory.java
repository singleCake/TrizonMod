package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonDrawFireAction;

public class TrizonDrawFireActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonDrawFireActionFactory.class);
    
    public TrizonDrawFireActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonDrawFireAction(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonDrawFireActionFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonDrawFireActionFactory) {
            this.amount += ((TrizonDrawFireActionFactory) other).amount;
            return true;
        }

        return false;
    }
}
