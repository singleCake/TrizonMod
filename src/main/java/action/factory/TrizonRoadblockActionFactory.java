package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonRoadblockActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonRoadblockActionFactory.class);
    public TrizonRoadblockActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        int num = 0;
        boolean in_hand = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.uuid.equals(this_card.uuid)) {
                in_hand = true;
                break;
            }
            num++;
        }
        return new action.TrizonRoadblockAction(in_hand ? num : 0);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonRoadblockActionFactory();
    }
}
