package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patch.FreezeCardPatch;

public class TrizonFreezeCardAction extends AbstractTrizonAction {
    AbstractCard card;
    AbstractGameAction followUpAction;

    public TrizonFreezeCardAction(AbstractCard card) {
        this(card, null);   
    }

    public TrizonFreezeCardAction(AbstractCard card, AbstractGameAction followUpAction) {
        this.card = card;
        this.followUpAction = followUpAction;

    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.uuid.equals(card.uuid)) {
                FreezeCardPatch.Freeze(c);
                break;
            }
        }
        if (followUpAction != null) {
            this.addToTop(followUpAction);
        }
        this.isDone = true;
    }
}
