package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import action.TrizonLycorisAction;

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
        return new TrizonLycorisAction(this_card, cardExhausted, amount);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonLycorisActionFactory) {
            this.amount += other.amount;
        }
    }
}
