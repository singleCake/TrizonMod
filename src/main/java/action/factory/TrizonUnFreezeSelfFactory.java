package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonUnFreezeCardAction;

public class TrizonUnFreezeSelfFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonUnFreezeSelfFactory.class);

    public TrizonUnFreezeSelfFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonUnFreezeCardAction(this_card);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonUnFreezeSelfFactory();
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }
}
