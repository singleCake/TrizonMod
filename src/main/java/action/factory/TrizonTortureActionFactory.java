package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.TrizonSpellAction;
import card.helper.DynamicVariable.FuseDV.DamageFuseDV;
import card.helper.DynamicVariable.FuseDV.FuseDV;

public class TrizonTortureActionFactory extends AbstractTrizonFactory {
    protected int damage;
    protected AttackEffect attackEffect;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonTortureActionFactory.class);
    private static final String DESCRIPTION_FOR_CARD = AbstractTrizonFactory.getDescriptionForCard(TrizonTortureActionFactory.class);

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
    public String rawDescriptionForCard() {
        return String.format(DESCRIPTION_FOR_CARD, times);
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

    @Override
    public FuseDV getFuseDV() {
        return new DamageFuseDV(this.damage, DamageType.THORNS);
    }
}
