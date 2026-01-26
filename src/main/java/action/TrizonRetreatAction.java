package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonRetreatAction extends AbstractTrizonAction {
    public TrizonRetreatAction(int times) {
        this.times = times;
    }

    @Override
    public void actionRepeat() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.discardPile.size() > 0 && p.hand.size() < 10) {
            AbstractCard c = p.discardPile.getTopCard();
            p.hand.addToHand(c);
            p.discardPile.removeCard(c);
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        this.isDone = true;
    }
}
