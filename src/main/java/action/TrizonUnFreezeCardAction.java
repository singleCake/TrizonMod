package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patch.FreezeCardPatch;

public class TrizonUnFreezeCardAction extends AbstractTrizonAction {
    AbstractCard card;
    AbstractGameAction followUpAction = null;

    public TrizonUnFreezeCardAction(AbstractCard card) {
        this(card, null);
    }

    public TrizonUnFreezeCardAction(AbstractCard card, AbstractGameAction followUpAction) {
        this.card = card;
        this.followUpAction = followUpAction;
        if (card == null) {
            ArrayList<AbstractCard> frozenCards = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (FreezeCardPatch.isFrozen(c)) {
                    frozenCards.add(c);
                }
            }
            if (frozenCards.size() > 0) {
                this.card = frozenCards.get(AbstractDungeon.cardRandomRng.random(frozenCards.size() - 1));
            }
        }
    }

    @Override
    public void update() {
        if (this.card == null) {
            this.isDone = true;
            return ;
        }
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
