package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonSummonCardAction;

public class TrizonGoldFishSummonCardActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonGoldFishSummonCardActionFactory.class);

    public TrizonGoldFishSummonCardActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonSummonCardAction(this.amount, false, c -> c.cost == 0);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonGoldFishSummonCardActionFactory(this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonGoldFishSummonCardActionFactory) {
            this.amount += other.amount;
            return true;
        }

        return false;
    }
}
