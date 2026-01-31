package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TrizonRandomPlayAttackActionFactory extends AbstractTrizonFactory {
    public TrizonRandomPlayAttackActionFactory() {
        this.amount = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new action.TrizonRandomPlayAttackAction(this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonRandomPlayAttackActionFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
