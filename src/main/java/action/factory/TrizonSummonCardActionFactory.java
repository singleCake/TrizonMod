package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonSummonCardAction;

public class TrizonSummonCardActionFactory extends AbstractTrizonFactory {
    private boolean freeThisTurn;

    public TrizonSummonCardActionFactory(int times, boolean freeThisTurn) {
        this.times = times;
        this.freeThisTurn = freeThisTurn;
    }

    public TrizonSummonCardActionFactory(int times) {
        this(times, false);
    }

    public TrizonSummonCardActionFactory(boolean freeThisTurn) {
        this(1, freeThisTurn);
    }

    public TrizonSummonCardActionFactory() {
        this(1, false);
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonSummonCardAction(this.times, this.freeThisTurn);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSummonCardActionFactory) {
            if (this.freeThisTurn == ((TrizonSummonCardActionFactory)other).freeThisTurn) {
                this.times += other.times;
                return true;
            }
        }

        return false;
    }
}
