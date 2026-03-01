package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonSummonCardAction;

public class TrizonSummonCardActionFactory extends AbstractTrizonFactory {
    private boolean freeThisTurn;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonSummonCardActionFactory.class);
    private static final String[] EXTENDED_DESCRIPTION = AbstractTrizonFactory.getExtendedDescription(TrizonSummonCardActionFactory.class);

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
    public String rawDescription() {
        String text = String.format(DESCRIPTION, times);
        if (freeThisTurn) {
            text += EXTENDED_DESCRIPTION[0];
        }
        return text;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSummonCardActionFactory(times, freeThisTurn);
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
