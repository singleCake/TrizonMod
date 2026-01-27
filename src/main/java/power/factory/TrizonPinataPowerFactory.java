package power.factory;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TrizonPinataPowerFactory extends AbstractTrizonPowerFactory {
    public TrizonPinataPowerFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractPower create() {
        return new power.TrizonPinataPower(AbstractDungeon.player, this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonPowerFactory other) {
        if (other instanceof TrizonPinataPowerFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
