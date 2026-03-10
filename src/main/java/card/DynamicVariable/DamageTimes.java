package card.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;
import card.TrizonCard;

public class DamageTimes extends DynamicVariable {
    @Override
    public String key() {
        return "T"; 
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card instanceof TrizonCard && ((TrizonCard)card).isDamageTimesModified;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof TrizonCard) {
            ((TrizonCard)card).isDamageTimesModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        return card instanceof TrizonCard ? ((TrizonCard)card).damageTimes : 1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card instanceof TrizonCard ? ((TrizonCard)card).baseDamageTimes : 1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card instanceof TrizonCard && ((TrizonCard)card).upgradedDamageTimes;
    }
}
