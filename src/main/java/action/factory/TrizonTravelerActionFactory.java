package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonTravelerAction;

public class TrizonTravelerActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonTravelerActionFactory.class);

    public TrizonTravelerActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonTravelerAction();
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonTravelerActionFactory();
    }    
}
