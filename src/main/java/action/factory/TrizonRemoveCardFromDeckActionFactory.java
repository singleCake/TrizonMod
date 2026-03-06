package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonRemoveCardFromDeckAction;

public class TrizonRemoveCardFromDeckActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonRemoveCardFromDeckActionFactory.class);

    public TrizonRemoveCardFromDeckActionFactory() {
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonRemoveCardFromDeckActionFactory();
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }
    
    @Override
    public AbstractGameAction create() {
        return new TrizonRemoveCardFromDeckAction(this_card);
    }
}
