package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonExhaustThisCardActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonExhaustThisCardActionFactory.class);

    public TrizonExhaustThisCardActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new ExhaustSpecificCardAction(this_card, AbstractDungeon.player.drawPile);
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonExhaustThisCardActionFactory();
    }
}
