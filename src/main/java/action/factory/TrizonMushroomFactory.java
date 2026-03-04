package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;

import card.uncommon.Mushroom;

public class TrizonMushroomFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonMushroomFactory.class);

    public TrizonMushroomFactory() {
        this.amount = 1;
    }

    public TrizonMushroomFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new MakeTempCardInHandAction(new Mushroom(), amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonMushroomFactory(this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonMushroomFactory) {
            this.amount += ((TrizonMushroomFactory) other).amount;
            return true;
        }
        return false;
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }
    
}
