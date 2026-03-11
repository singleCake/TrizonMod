package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonSummonCardAction;

public class TrizonSummonCardActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonSummonCardActionFactory.class);
    private static final String[] EXTENDED_DESCRIPTION = AbstractTrizonFactory.getExtendedDescription(TrizonSummonCardActionFactory.class);
    private boolean freeThisTurn;
    private boolean isEthereal;

    public TrizonSummonCardActionFactory(int times, boolean freeThisTurn, boolean isEthereal) {
        this.times = times;
        this.freeThisTurn = freeThisTurn;
        this.isEthereal = isEthereal;
    }

    public TrizonSummonCardActionFactory(int times) {
        this(times, false, false);
    }

    public TrizonSummonCardActionFactory(boolean freeThisTurn) {
        this(1, freeThisTurn, false);
    }

    public TrizonSummonCardActionFactory() {
        this(1, false, false);
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonSummonCardAction(this.times, this.freeThisTurn, this.isEthereal);
    }

    @Override
    public String rawDescription() {
        String text = String.format(DESCRIPTION, times);
        if (freeThisTurn) {
            text += EXTENDED_DESCRIPTION[0];
        }
        if (isEthereal) {
            text += EXTENDED_DESCRIPTION[1];
        }
        return text;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSummonCardActionFactory(times, freeThisTurn, isEthereal);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSummonCardActionFactory) {
            if (this.freeThisTurn == ((TrizonSummonCardActionFactory)other).freeThisTurn && this.isEthereal == ((TrizonSummonCardActionFactory)other).isEthereal) {
                this.times += other.times;
                return true;
            }
        }

        return false;
    }
}
