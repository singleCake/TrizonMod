package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patch.FreezeCardPatch;

public class TrizonUnFreezeCardAction extends AbstractTrizonAction{
    AbstractCard card;
    AbstractGameAction followUpAction = null;

    public TrizonUnFreezeCardAction(AbstractCard card) {
        this(card, null);
    }

    public TrizonUnFreezeCardAction(AbstractCard card, AbstractGameAction followUpAction) {
        this.card = card;
        this.followUpAction = followUpAction;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.uuid.equals(card.uuid)) {
                if (FreezeCardPatch.isFrozen(c) && followUpAction != null) {
                    this.addToTop(followUpAction);
                }
                FreezeCardPatch.Unfreeze(c);
                break;
            }
        }
        this.isDone = true;
    }
}
