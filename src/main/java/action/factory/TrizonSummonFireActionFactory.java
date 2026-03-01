package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonSummonCardAction;

public class TrizonSummonFireActionFactory extends AbstractTrizonFactory {
    private boolean freeThisTurn;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonSummonCardAction.class);
    private static final String[] EXTENDED_DESCRIPTION = AbstractTrizonFactory.getExtendedDescription(TrizonSummonFireActionFactory.class);

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
    public String rawDescription() {
        String text = String.format(DESCRIPTION, times);
        if (freeThisTurn) {
            text += EXTENDED_DESCRIPTION[0];
        }
        return text;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSummonFireActionFactory(times, freeThisTurn);
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
