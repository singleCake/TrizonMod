package power.factory;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TrizonCremationPowerFactory extends AbstractTrizonPowerFactory {
    private static final String DESCRIPTION = AbstractTrizonPowerFactory.getDescription(TrizonCremationPowerFactory.class);

    public TrizonCremationPowerFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractPower create() {
        return new power.TrizonCremationPower(AbstractDungeon.player, this.amount);
    }
    
    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonPowerFactory clone() {
        return new TrizonCremationPowerFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonPowerFactory other) {
        if (other instanceof TrizonCremationPowerFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
}
