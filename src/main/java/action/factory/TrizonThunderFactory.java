package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;

import action.TrizonCheckAction;
import action.TrizonSpellAction;

public class TrizonThunderFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonThunderFactory.class);
    transient DamageInfo info;

    public TrizonThunderFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public void receiveDamageInfo(DamageInfo damageInfo) {
        this.info = damageInfo;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonCheckAction<DamageInfo>(info, (info) -> {
            return info.type == DamageType.NORMAL;
        }, new TrizonSpellAction(this_card, null, amount, 1, AttackEffect.LIGHTNING));
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonThunderFactory(amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonThunderFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
