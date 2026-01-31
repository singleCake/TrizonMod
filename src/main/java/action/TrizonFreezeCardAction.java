package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patch.FreezeCardPatch;

public class TrizonFreezeCardAction extends AbstractTrizonAction {
    AbstractCard card;
    public TrizonFreezeCardAction(AbstractCard card) {
        System.out.println("TrizonFreezeCardAction Freezing card " + card.name);
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
