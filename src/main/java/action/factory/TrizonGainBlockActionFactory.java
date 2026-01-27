package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonGainBlockAction;

public class TrizonGainBlockActionFactory extends AbstractTrizonFactory {
    public TrizonGainBlockActionFactory(int blockAmount) {
        this.amount = blockAmount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonGainBlockAction(this.amount);
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
