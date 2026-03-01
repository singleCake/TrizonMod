package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonHandToDrawPileAction;

public class TrizonSelfBackToDrawPileActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonSelfBackToDrawPileActionFactory.class);
    
    public TrizonSelfBackToDrawPileActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonHandToDrawPileAction(this_card);
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSelfBackToDrawPileActionFactory();
    }
}