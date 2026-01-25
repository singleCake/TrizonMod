package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonFreezeCardAction;

public class TrizonSelfFreezeActionFactory extends AbstractTrizonFactory {
    public TrizonSelfFreezeActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonFreezeCardAction(this_card);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
    }
}
