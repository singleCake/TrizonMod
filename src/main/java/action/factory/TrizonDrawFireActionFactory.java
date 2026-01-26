package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonDrawFireAction;

public class TrizonDrawFireActionFactory extends AbstractTrizonFactory {
    
    public TrizonDrawFireActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonDrawFireAction(this.amount);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonDrawFireActionFactory) {
            this.amount += ((TrizonDrawFireActionFactory) other).amount;
        }
    }
}
