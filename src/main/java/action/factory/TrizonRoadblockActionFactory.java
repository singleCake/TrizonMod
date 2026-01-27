package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonRoadblockActionFactory extends AbstractTrizonFactory {
    public TrizonRoadblockActionFactory() {
    }

    public AbstractGameAction create() {
        int num = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.uuid.equals(this_card.uuid)) {
                break;
            }
            num++;
        }
        return new action.TrizonRoadblockAction(num);
    }
}
