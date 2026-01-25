package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonSummonCardAction extends AbstractTrizonAction {
    private boolean freeThisTurn;
    
    public TrizonSummonCardAction(int times, boolean freeThisTurn) {
        this.times = times;
        this.freeThisTurn = freeThisTurn;
    }

    @Override
    public void actionRepeat() {
        AbstractCard c = getRandomTrizonCard();
        if (this.freeThisTurn) {
            c.setCostForTurn(0);
        }
        this.addToTop(new MakeTempCardInHandAction(c));
    }

    private AbstractCard getRandomTrizonCard() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (!c.hasTag(AbstractCard.CardTags.HEALING))
            list.add(c);
        } 
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (!c.hasTag(AbstractCard.CardTags.HEALING))
            list.add(c); 
        }
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }
}
