package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patch.FreezeCardPatch;

public class TrizonUnFreezeAllCardAtStartOfTurnAction extends AbstractTrizonAction{

    public TrizonUnFreezeAllCardAtStartOfTurnAction() {
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            FreezeCardPatch.Unfreeze(c);
        }
        this.isDone = true;
    }
}
