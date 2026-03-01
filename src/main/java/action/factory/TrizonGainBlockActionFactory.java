package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonGainBlockAction;

public class TrizonGainBlockActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonGainBlockActionFactory.class);

    public TrizonGainBlockActionFactory(int blockAmount) {
        this.amount = blockAmount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonGainBlockAction(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonGainBlockActionFactory(amount);
    }
    
    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonGainBlockActionFactory) {
            this.amount += ((TrizonGainBlockActionFactory) other).amount;
            return true;
        }

        return false;
    }
}
