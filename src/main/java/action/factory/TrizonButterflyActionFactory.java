package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import action.TrizonButterflyAction;

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
        return new TrizonButterflyAction(this_card, cardPlayed, amount);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonButterflyActionFactory) {
            this.amount += other.amount;
        }
    }
}
