package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TrizonRandomReviveActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonRandomReviveActionFactory.class);

    public TrizonRandomReviveActionFactory(int amount) {
        this.amount = amount;
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
        return new TrizonRandomReviveActionFactory(this.amount);
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
