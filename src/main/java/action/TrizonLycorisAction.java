package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

public class TrizonLycorisAction extends AbstractTrizonAction {
    private AbstractCard cardToModify;
    private AbstractCard cardExhausted;

    public TrizonLycorisAction(AbstractCard cardToModify, AbstractCard card, int amount) {
        this.cardToModify = cardToModify;
        this.cardExhausted = card;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (cardExhausted.type == CardType.ATTACK) {
            this.addToTop(new TrizonModifyDamageAction(cardToModify, amount));
        }
        this.isDone = true;
    }
}
