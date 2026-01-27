package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonSummonCardAction;

public class TrizonSummonFireActionFactory extends AbstractTrizonFactory {
    private boolean freeThisTurn;

    public TrizonSummonFireActionFactory(int times, boolean freeThisTurn) {
        this.times = times;
        this.freeThisTurn = freeThisTurn;
    }

    public TrizonSummonFireActionFactory(int times) {
        this(times, false);
    }

    public TrizonSummonFireActionFactory(boolean freeThisTurn) {
        this(1, freeThisTurn);
    }

    public TrizonSummonFireActionFactory() {
        this(1, false);
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonSummonCardAction(this.times, this.freeThisTurn, c -> c.isFire());
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSummonFireActionFactory) {
            if (this.freeThisTurn == ((TrizonSummonFireActionFactory)other).freeThisTurn) {
                this.times += other.times;
            }

            return true;
        }

        return false;
    }
}
