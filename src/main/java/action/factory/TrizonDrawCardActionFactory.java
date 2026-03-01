package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class TrizonDrawCardActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonDrawCardActionFactory.class);

    public TrizonDrawCardActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new DrawCardAction(amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonDrawCardActionFactory(amount);
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
