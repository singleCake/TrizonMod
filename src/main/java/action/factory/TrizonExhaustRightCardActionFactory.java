package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonExhaustRightCardAction;

public class TrizonExhaustRightCardActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonExhaustRightCardActionFactory.class);

    public TrizonExhaustRightCardActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonExhaustRightCardAction(amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonExhaustRightCardActionFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonExhaustRightCardActionFactory) {
            this.amount = ((TrizonExhaustRightCardActionFactory) other).amount;
            return true;
        }
        
        return false;
    }
}
