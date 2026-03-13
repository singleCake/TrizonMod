package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.TrizonSpellAction;

public class TrizonTortureActionFactory extends AbstractTrizonFactory {
    protected int damage;
    protected AttackEffect attackEffect;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonTortureActionFactory.class);

    public TrizonTortureActionFactory(int damage, AttackEffect attackEffect) {
        this(damage, 1, attackEffect);
    }

    public TrizonTortureActionFactory(int damage, int times, AttackEffect attackEffect) {
        this.damage = damage;
        this.times = times;
        this.attackEffect = attackEffect;
    }
    
    @Override
    public AbstractGameAction create() {
        return new TrizonSpellAction(this_card, AbstractDungeon.player, damage, times, attackEffect);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, damage, times);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonTortureActionFactory(damage, times, attackEffect);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonTortureActionFactory) {
            TrizonTortureActionFactory o = (TrizonTortureActionFactory) other;
            this.damage += o.damage;
            this.times = Math.max(this.times,  o.times);
            return true;
        }
        return false;
    }
}
