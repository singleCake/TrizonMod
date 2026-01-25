package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonHandToDrawPileAction extends AbstractTrizonAction {
    AbstractCard card;

    public TrizonHandToDrawPileAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        AbstractDungeon.player.hand.moveToDeck(card, true);
        this.isDone = true;
    }
}
