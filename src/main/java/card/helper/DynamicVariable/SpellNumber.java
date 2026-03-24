package card.helper.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;
import card.AbstractTrizonCard;

public class SpellNumber extends DynamicVariable {
    @Override
    public String key() {
        return "S"; 
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card instanceof AbstractTrizonCard && ((AbstractTrizonCard<?>)card).isSpellNumberModified;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractTrizonCard) {
            ((AbstractTrizonCard<?>)card).isSpellNumberModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        return card instanceof AbstractTrizonCard ? ((AbstractTrizonCard<?>)card).spellNumber : 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card instanceof AbstractTrizonCard ? ((AbstractTrizonCard<?>)card).baseSpellNumber : 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card instanceof AbstractTrizonCard && ((AbstractTrizonCard<?>)card).upgradedSpellNumber;
    }
}
