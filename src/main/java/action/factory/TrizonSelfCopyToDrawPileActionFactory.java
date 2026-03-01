package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;

public class TrizonSelfCopyToDrawPileActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonSelfCopyToDrawPileActionFactory.class);

    public TrizonSelfCopyToDrawPileActionFactory() {
        this.amount = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new MakeTempCardInDrawPileAction(this_card, this.amount, true, true);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        TrizonSelfCopyToDrawPileActionFactory copy = new TrizonSelfCopyToDrawPileActionFactory();
        copy.amount = this.amount;
        return copy;
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSelfCopyToDrawPileActionFactory) {
            this.amount += other.amount;
            return true;
        }

        return false;
    }
}
