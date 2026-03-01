package power.factory;

import com.megacrit.cardcrawl.powers.AbstractPower;

public class TrizonScavengerPowerFactory extends AbstractTrizonPowerFactory {
    private static final String DESCRIPTION = AbstractTrizonPowerFactory.getDescription(TrizonScavengerPowerFactory.class); 

    public TrizonScavengerPowerFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractPower create() {
        return new power.TrizonScavengerPower(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonPowerFactory clone() {
        return new TrizonScavengerPowerFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonPowerFactory other) {
        if (other instanceof TrizonScavengerPowerFactory) {
            this.amount += other.amount;
            return true;
        }
        return false;
    }
    
}
