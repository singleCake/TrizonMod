package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

import action.TrizonVampireAction;

public class TrizonVampireActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonVampireActionFactory.class);
    transient DamageInfo info;

    public TrizonVampireActionFactory() {
    }

    @Override
    public void receiveDamageInfo(DamageInfo info) {
        this.info = info;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonVampireAction(target, info);
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonVampireActionFactory();
    }
}
