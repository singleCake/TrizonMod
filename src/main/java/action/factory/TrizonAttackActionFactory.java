package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.core.AbstractCreature;

import action.TrizonAttackAction;

public class TrizonAttackActionFactory extends AbstractTrizonFactory {
    private int damage;
    private AttackEffect attackEffect;

    public TrizonAttackActionFactory(int damage, AttackEffect attackEffect) {
        this(null, damage, 1, attackEffect);
    }

    public TrizonAttackActionFactory(int damage, int times, AttackEffect attackEffect) {
        this(null, damage, times, attackEffect);
    }

    public TrizonAttackActionFactory(AbstractCreature target, int damage, int times, AttackEffect attackEffect) {
        this.target = target;
        this.damage = damage;
        this.times = times;
        this.attackEffect = attackEffect;
    }
    
    @Override
    public AbstractGameAction create() {
        return new TrizonAttackAction(this_card, target, damage, times, attackEffect);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonAttackActionFactory) {
            TrizonAttackActionFactory o = (TrizonAttackActionFactory) other;
            this.damage += o.damage;
            this.times = Math.max(this.times,  o.times);
            return true;
        }

        return false;
    }
}
