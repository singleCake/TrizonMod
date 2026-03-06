package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonIceCreamAction;

public class TrizonIceCreamActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonIceCreamActionFactory.class);

    public TrizonIceCreamActionFactory(int times) {
        this.times = times;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonIceCreamAction(this.times);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.times);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonIceCreamActionFactory(this.times);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonIceCreamActionFactory) {
            this.times += other.times;
            return true;
        }

        return false;
    }
    
}
