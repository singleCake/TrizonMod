package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonDebuffAllEnemyAction;
import power.TrizonFrozenPower;

public class TrizonFreezeAllEnemyActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonFreezeAllEnemyActionFactory.class);

    public TrizonFreezeAllEnemyActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonDebuffAllEnemyAction(TrizonFrozenPower::new);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonFreezeAllEnemyActionFactory();
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }
    
}
