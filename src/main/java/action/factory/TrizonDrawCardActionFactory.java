package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class TrizonDrawCardActionFactory extends AbstractTrizonFactory {
    public TrizonDrawCardActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new DrawCardAction(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonDrawCardActionFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
