package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonBambooAction;

public class TrizonBambooActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonBambooActionFactory.class);

    public TrizonBambooActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonBambooAction(amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonBambooActionFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonBambooActionFactory) {
            TrizonBambooActionFactory o = (TrizonBambooActionFactory) other;
            this.amount += o.amount;
            return true;
        }

        return false;
    }
    
}
