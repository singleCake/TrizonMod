package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.core.AbstractCreature;

import action.TrizonAttackAction;
import card.TrizonCard;

public class TrizonAttackActionFactory extends AbstractTrizonFactory {
    public int damage;
    public int times;
    public AttackEffect attackEffect;

    public TrizonAttackActionFactory(TrizonCard cardPlayed, int damage, AttackEffect attackEffect) {
        this(cardPlayed, null, damage, 1, attackEffect);
    }

    public TrizonAttackActionFactory(TrizonCard cardPlayed, int damage, int times, AttackEffect attackEffect) {
        this(cardPlayed, null, damage, times, attackEffect);
    }

    public TrizonAttackActionFactory(TrizonCard cardPlayed, AbstractCreature target, int damage, int times, AttackEffect attackEffect) {
        this.cardPlayed = cardPlayed;
        this.target = target;
        this.damage = damage;
        this.times = times;
        this.attackEffect = attackEffect;
    }
    
    @Override
    public AbstractGameAction create() {
        return new TrizonAttackAction(cardPlayed, target, damage, times, attackEffect);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonAttackActionFactory) {
            TrizonAttackActionFactory o = (TrizonAttackActionFactory) other;
            this.damage += o.damage;
            this.times = Math.max(this.times,  o.times);
        }
    }
}
