package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonWorkaholicFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonWorkaholicFactory.class);

    public TrizonWorkaholicFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new action.TrizonPlayCardAction(this_card, AbstractDungeon.player.exhaustPile);
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        TrizonWorkaholicFactory copy = new TrizonWorkaholicFactory();
        return copy;
    }    
}
