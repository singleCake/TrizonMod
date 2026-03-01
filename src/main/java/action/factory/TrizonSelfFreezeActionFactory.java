package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonFreezeCardAction;

public class TrizonSelfFreezeActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonFreezeCardAction.class);

    public TrizonSelfFreezeActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonFreezeCardAction(this_card);
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSelfFreezeActionFactory();
    }
}
