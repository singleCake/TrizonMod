package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

import action.TrizonCheckCardAction;
import action.TrizonModifyDamageAction;

public class TrizonLycorisActionFactory extends AbstractTrizonFactory {
    private AbstractCard cardExhausted;

    public TrizonLycorisActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public void receiveCard(AbstractCard card) {
        this.cardExhausted = card;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonCheckCardAction(cardExhausted, 
            c -> c.type == CardType.ATTACK, 
            new TrizonModifyDamageAction(this_card, amount));
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonLycorisActionFactory) {
            this.amount += other.amount;
            return true;
        }

        return false;
    }
}
