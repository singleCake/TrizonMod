package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonAttackRightAction;

public class TrizonAttackRightActionFactory extends AbstractTrizonFactory {
    private int damage;

    public TrizonAttackRightActionFactory(int damage, int times) {
        this.damage = damage;
        this.times = times;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonAttackRightAction(this_card, damage, times);
    }
    
    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonAttackRightActionFactory) {
            TrizonAttackRightActionFactory o = (TrizonAttackRightActionFactory) other;
            this.damage += o.damage;
            this.times = Math.max(this.times, o.times);
            return true;
        }

        return false;
    }
}
