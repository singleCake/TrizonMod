package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonHarvestAction;

public class TrizonHarvestActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonHarvestActionFactory.class);

    public TrizonHarvestActionFactory(int amount) {
        this.amount = amount;    
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonHarvestAction(this_card, this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonHarvestActionFactory(this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonHarvestActionFactory) {
            TrizonHarvestActionFactory o = (TrizonHarvestActionFactory) other;
            this.amount += o.amount;
            return true;
        }

        return false;
    }
}
