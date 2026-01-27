package action;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonRandomReviveAction extends AbstractTrizonAction {
    public TrizonRandomReviveAction(int amount) {
        this.times = amount;
    }

    @Override
    public void actionRepeat() {
        CardGroup exhaustPile = AbstractDungeon.player.exhaustPile;
        int index = AbstractDungeon.cardRandomRng.random(exhaustPile.size() - 1);
        AbstractCard card = exhaustPile.group.get(index);
        AbstractDungeon.player.exhaustPile.removeCard(card);
        this.addToTop(new MakeTempCardInHandAction(card));

        this.isDone = true;
    }
}
