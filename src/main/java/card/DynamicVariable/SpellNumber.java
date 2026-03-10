package card.DynamicVariable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;
import card.TrizonCard;

public class SpellNumber extends DynamicVariable {
    @Override
    public String key() {
        return "S"; 
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card instanceof TrizonCard && ((TrizonCard)card).isSpellNumberModified;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof TrizonCard) {
            ((TrizonCard)card).isSpellNumberModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        return card instanceof TrizonCard ? ((TrizonCard)card).spellNumber : 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card instanceof TrizonCard ? ((TrizonCard)card).baseSpellNumber : 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card instanceof TrizonCard && ((TrizonCard)card).upgradedSpellNumber;
    }
}
