package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonHandToDrawPileAction;

public class TrizonSelfBackToDrawPileActionFactory extends AbstractTrizonFactory {
    
    public TrizonSelfBackToDrawPileActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonHandToDrawPileAction(this_card);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
    }
}