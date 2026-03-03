package power.factory;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TrizonMaidPowerFactory extends AbstractTrizonPowerFactory {
    private static final String DESCRIPTION = AbstractTrizonPowerFactory.getDescription(TrizonMaidPowerFactory.class);

    public TrizonMaidPowerFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractPower create() {
        return new power.TrizonMaidPower(AbstractDungeon.player, this.amount);
    }
    
    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonPowerFactory clone() {
        return new TrizonMaidPowerFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonPowerFactory other) {
        if (other instanceof TrizonMaidPowerFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
