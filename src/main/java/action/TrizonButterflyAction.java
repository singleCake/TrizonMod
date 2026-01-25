package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

public class TrizonButterflyAction extends AbstractTrizonAction {
    private AbstractCard cardToModify;
    private AbstractCard cardPlayed;

    public TrizonButterflyAction(AbstractCard cardToModify, AbstractCard card, int amount) {
        this.cardToModify = cardToModify;
        this.cardPlayed = card;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (cardPlayed.type != CardType.ATTACK) {
            this.addToTop(new TrizonModifyDamageAction(cardToModify, amount));
        }
        this.isDone = true;
    }
}
