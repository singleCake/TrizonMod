package action;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonExhaustRightCardAction extends AbstractTrizonAction {
    public TrizonExhaustRightCardAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        CardGroup hand = AbstractDungeon.player.hand;
        if (hand.size() < amount) {
            for (AbstractCard c : hand.group) {
                this.addToTop(new ExhaustSpecificCardAction(c, hand));
            }
        } else {
            for (int i = 0; i < amount; i++) {
                this.addToTop(new ExhaustSpecificCardAction(hand.getTopCard(), hand));
            }
        }
        this.isDone = true;
    }
}
