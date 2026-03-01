package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TrizonRandomPlayAttackActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonRandomPlayAttackActionFactory.class);

    public TrizonRandomPlayAttackActionFactory() {
        this.amount = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new action.TrizonRandomPlayAttackAction(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        TrizonRandomPlayAttackActionFactory copy = new TrizonRandomPlayAttackActionFactory();
        copy.amount = this.amount;
        return copy;
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
