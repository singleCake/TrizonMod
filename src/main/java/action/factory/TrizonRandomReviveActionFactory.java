package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TrizonRandomReviveActionFactory extends AbstractTrizonFactory {
    public TrizonRandomReviveActionFactory() {
        this.amount = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new action.TrizonRandomReviveAction(amount);
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
