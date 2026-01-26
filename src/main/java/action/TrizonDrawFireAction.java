package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.TrizonCard;

public class TrizonDrawFireAction extends AbstractTrizonAction {
    public TrizonDrawFireAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.drawPile.isEmpty() && p.discardPile.isEmpty()) {
            this.isDone = true;
            return;
        }

        ArrayList<TrizonCard> cardsToDraw = new ArrayList<>();
        int draw_count = 0;
        for (AbstractCard c : p.drawPile.group) {
            if (c instanceof TrizonCard) {
                if (((TrizonCard)c).isFire()) {
                    cardsToDraw.add((TrizonCard)c);
                    draw_count++;
                    if (draw_count >= this.amount) {
                        break;
                    }
                }
            }
        }

        for (TrizonCard c : cardsToDraw) {
            p.drawPile.removeCard(c);
            p.drawPile.addToTop(c);
        }

        if (draw_count < this.amount && !p.discardPile.isEmpty()) {
            this.addToTop(new TrizonDrawFireAction(this.amount - draw_count));
            this.addToTop(new ShuffleAction(p.drawPile));
            this.addToTop(new EmptyDeckShuffleAction());
        } 

        this.addToTop(new DrawCardAction(draw_count));
    }
}
