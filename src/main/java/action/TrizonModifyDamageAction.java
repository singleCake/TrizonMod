package action;

import com.megacrit.cardcrawl.cards.AbstractCard;

import card.TrizonCard;

public class TrizonModifyDamageAction extends AbstractTrizonAction {
    private AbstractCard cardToModify;

    public TrizonModifyDamageAction(AbstractCard card, int amount) {
        this.cardToModify = card;
        this.amount = amount;
    }

    @Override
    public void update() {
        cardToModify.baseDamage += amount;
        if (cardToModify.baseDamage < 0) {
            cardToModify.baseDamage = 0;
        }
        if (cardToModify instanceof TrizonCard)
            ((TrizonCard)cardToModify).modifier.addDamage(amount);
        this.isDone = true;
    }

}
