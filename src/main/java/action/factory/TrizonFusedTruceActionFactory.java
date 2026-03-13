package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.WeakPower;

import action.TrizonDebuffAllEnemyAction;

public class TrizonFusedTruceActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonFusedTruceActionFactory.class);

    public TrizonFusedTruceActionFactory(int amount) {
        this.amount = amount;   
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonDebuffAllEnemyAction(mo -> new WeakPower(mo, this.amount, false));
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonFusedTruceActionFactory(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonFusedTruceActionFactory) {
            this.amount += ((TrizonFusedTruceActionFactory) other).amount;
            return true;
        }
        return false;
    }
    
}
