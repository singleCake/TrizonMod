package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonHunterAction;

public class TrizonHunterActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonHunterActionFactory.class);

    public TrizonHunterActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonHunterAction(target, this_card);
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonHunterActionFactory();
    }
}
