package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.StrengthPower;

import action.TrizonDebuffAllEnemyAction;

public class TrizonFusedBlizzardActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonFusedBlizzardActionFactory.class);

    public TrizonFusedBlizzardActionFactory(int amount) {
        this.amount = amount;   
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonDebuffAllEnemyAction(mo -> new StrengthPower(mo, -this.amount));
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonFusedBlizzardActionFactory(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonFusedBlizzardActionFactory) {
            this.amount += ((TrizonFusedBlizzardActionFactory) other).amount;
            return true;
        }
        return false;
    }
    
}
