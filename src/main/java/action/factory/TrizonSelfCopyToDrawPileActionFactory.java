package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;

public class TrizonSelfCopyToDrawPileActionFactory extends AbstractTrizonFactory {
    public TrizonSelfCopyToDrawPileActionFactory() {
        this.amount = 1;
    }

    @Override
    public AbstractGameAction create() {
        return new MakeTempCardInDrawPileAction(this_card, this.amount, true, true);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSelfCopyToDrawPileActionFactory) {
            this.amount += other.amount;
        }
    }
}
