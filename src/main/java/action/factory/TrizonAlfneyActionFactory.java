package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonAlfneyAction;

public class TrizonAlfneyActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonAlfneyActionFactory.class);

    public TrizonAlfneyActionFactory(int blockAmount) {
        this.amount = blockAmount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonAlfneyAction(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonAlfneyActionFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonAlfneyActionFactory) {
            this.amount += ((TrizonAlfneyActionFactory) other).amount;
            return true;
        }

        return false;
    }
}
