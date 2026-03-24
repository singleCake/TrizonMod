package card.helper.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;
import card.AbstractTrizonCard;

public class DamageTimes extends DynamicVariable {
    @Override
    public String key() {
        return "T"; 
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card instanceof AbstractTrizonCard && ((AbstractTrizonCard<?>)card).isDamageTimesModified;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractTrizonCard) {
            ((AbstractTrizonCard<?>)card).isDamageTimesModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        return card instanceof AbstractTrizonCard ? ((AbstractTrizonCard<?>)card).damageTimes : 1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card instanceof AbstractTrizonCard ? ((AbstractTrizonCard<?>)card).baseDamageTimes : 1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card instanceof AbstractTrizonCard && ((AbstractTrizonCard<?>)card).upgradedDamageTimes;
    }
}
