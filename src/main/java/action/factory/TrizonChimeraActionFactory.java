package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonChimeraAction;

public class TrizonChimeraActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonChimeraActionFactory.class);

    public TrizonChimeraActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonChimeraAction(this_card);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonChimeraActionFactory();
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION);
    }
    
}
