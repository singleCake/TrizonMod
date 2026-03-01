package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.core.AbstractCreature;

import action.TrizonSpellAction;

public class TrizonSpellActionFactory extends AbstractTrizonFactory {
    private int damage;
    private AttackEffect attackEffect;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonSpellAction.class);

    public TrizonSpellActionFactory(int damage, AttackEffect attackEffect) {
        this(null, damage, 1, attackEffect);
    }

    public TrizonSpellActionFactory(int damage, int times, AttackEffect attackEffect) {
        this(null, damage, times, attackEffect);
    }

    public TrizonSpellActionFactory(AbstractCreature target, int damage, int times, AttackEffect attackEffect) {
        this.target = target;
        this.damage = damage;
        this.times = times;
        this.attackEffect = attackEffect;
    }
    
    @Override
    public AbstractGameAction create() {
        return new TrizonSpellAction(this_card, target, damage, times, attackEffect);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, damage, times);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSpellActionFactory(target, damage, times, attackEffect);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSpellActionFactory) {
            TrizonSpellActionFactory o = (TrizonSpellActionFactory) other;
            this.damage += o.damage;
            this.times = Math.max(this.times,  o.times);
            return true;
        }
        return false;
    }
}
