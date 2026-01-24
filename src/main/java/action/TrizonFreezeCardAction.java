package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patch.FreezeCardPatch;

public class TrizonFreezeCardAction extends TrizonAction {
    AbstractCard card;
    public TrizonFreezeCardAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.uuid.equals(card.uuid)) {
                FreezeCardPatch.Freeze(c);
                break;
            }
        }
        this.isDone = true;
    }
}
