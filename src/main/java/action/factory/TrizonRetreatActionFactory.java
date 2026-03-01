package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TrizonRetreatActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonRetreatActionFactory.class);

    public TrizonRetreatActionFactory() {
        this.times = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new action.TrizonRetreatAction(this.times);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.times);
    }

    @Override
    public AbstractTrizonFactory clone() {
        TrizonRetreatActionFactory copy = new TrizonRetreatActionFactory();
        copy.times = this.times;
        return copy;
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonRetreatActionFactory) {
            this.times += ((TrizonRetreatActionFactory) other).times;
            return true;
        }
        return false;
    }
}
