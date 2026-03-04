package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import power.TrizonPhoenixPower;

public class TrizonPhoenixFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonPhoenixFactory.class);

    public TrizonPhoenixFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, 
            new TrizonPhoenixPower(AbstractDungeon.player, this_card, amount)
        );
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonPhoenixFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonPhoenixFactory) {
            TrizonPhoenixFactory o = (TrizonPhoenixFactory) other;
            this.amount += o.amount;
            return true;
        }

        return false;
    }
}
