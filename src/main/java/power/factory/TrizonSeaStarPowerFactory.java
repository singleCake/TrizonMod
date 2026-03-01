package power.factory;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TrizonSeaStarPowerFactory extends AbstractTrizonPowerFactory {
    private static final String DESCRIPTION = AbstractTrizonPowerFactory.getDescription(TrizonSeaStarPowerFactory.class);

    public TrizonSeaStarPowerFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractPower create() {
        return new power.TrizonSeaStarPower(AbstractDungeon.player, this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonPowerFactory clone() {
        return new TrizonSeaStarPowerFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonPowerFactory other) {
        if (other instanceof TrizonSeaStarPowerFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
