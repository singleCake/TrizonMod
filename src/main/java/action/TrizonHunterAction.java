package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TrizonHunterAction extends AbstractTrizonAction {
    private AbstractCreature target;
    private AbstractCard cardToModify;

    public TrizonHunterAction(AbstractCreature target, AbstractCard card) {
        this.target = target;
        this.cardToModify = card;
    }

    @Override
    public void update() {
        cardToModify.returnToHand = false;
        if (((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) {
            cardToModify.returnToHand = true;
            cardToModify.setCostForTurn(0);
        }
    }
}
