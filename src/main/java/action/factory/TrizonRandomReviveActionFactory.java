package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonRandomReviveAction;

public class TrizonRandomReviveActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonRandomReviveAction.class);

    public TrizonRandomReviveActionFactory() {
        this.amount = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new action.TrizonRandomReviveAction(amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        TrizonRandomReviveActionFactory copy = new TrizonRandomReviveActionFactory();
        copy.amount = this.amount;
        return copy;
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonRandomReviveActionFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
