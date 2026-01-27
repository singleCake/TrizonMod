package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patch.RoadBlockPatch;

public class TrizonRoadblockAction extends AbstractTrizonAction {
    private int num;

    public TrizonRoadblockAction(int num) {
        this.num = num;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            RoadBlockPatch.CanUseThisTurnField.canUseThisTurn.set(c, false);
            num--;
            if (num <= 0) {
                break;
            }
        }
        this.isDone = true;
    }
}
