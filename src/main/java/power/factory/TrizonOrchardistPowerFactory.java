package power.factory;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TrizonOrchardistPowerFactory extends AbstractTrizonPowerFactory {
    private static final String DESCRIPTION = AbstractTrizonPowerFactory.getDescription(TrizonOrchardistPowerFactory.class); 

    public TrizonOrchardistPowerFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractPower create() {
        return new power.TrizonOrchardistPower(AbstractDungeon.player, this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonPowerFactory clone() {
        return new TrizonOrchardistPowerFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonPowerFactory other) {
        if (other instanceof TrizonOrchardistPowerFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
