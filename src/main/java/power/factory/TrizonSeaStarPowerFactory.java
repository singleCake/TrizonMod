package power.factory;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TrizonSeaStarPowerFactory extends AbstractTrizonPowerFactory {
    public TrizonSeaStarPowerFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractPower create() {
        return new power.TrizonSeaStarPower(AbstractDungeon.player, this.amount);
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
