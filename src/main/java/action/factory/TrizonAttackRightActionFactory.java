package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;

import action.TrizonAttackRightAction;
import card.helper.DynamicVariable.FuseDV.DamageFuseDV;
import card.helper.DynamicVariable.FuseDV.FuseDV;

public class TrizonAttackRightActionFactory extends AbstractTrizonFactory {
    private int damage;
    private static final String DESCRIPTION = AbstractTrizonFactory
            .getDescription(TrizonAttackRightActionFactory.class);
    private static final String DESCRIPTION_FOR_CARD = AbstractTrizonFactory
            .getDescriptionForCard(TrizonAttackActionFactory.class);

    public TrizonAttackRightActionFactory(int damage, int times) {
        this.damage = damage;
        this.times = times;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonAttackRightAction(this_card, damage, times);
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
        return new TrizonAttackRightActionFactory(damage, times);
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

    @Override
    public FuseDV getFuseDV() {
        return new DamageFuseDV(damage, DamageType.NORMAL, true);
    }
}
