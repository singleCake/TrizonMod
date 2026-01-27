package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

import action.TrizonCheckCardAction;
import action.TrizonModifyDamageAction;

public class TrizonButterflyActionFactory extends AbstractTrizonFactory {
    private AbstractCard cardPlayed;

    public TrizonButterflyActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public void receiveCard(AbstractCard card) {
        this.cardPlayed = card;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonCheckCardAction(cardPlayed, 
            c -> c.type != CardType.ATTACK, 
            new TrizonModifyDamageAction(this_card, amount));
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonButterflyActionFactory) {
            this.amount += other.amount;
            return true;
        }

        return false;
    }
}
