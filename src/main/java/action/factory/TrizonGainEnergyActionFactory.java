package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonGainEnergyAction;

public class TrizonGainEnergyActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonGainEnergyActionFactory.class);

    public TrizonGainEnergyActionFactory(int energyAmount) {
        this.amount = energyAmount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonGainEnergyAction(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonGainEnergyActionFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonGainEnergyActionFactory) {
            this.amount += ((TrizonGainEnergyActionFactory) other).amount;
            return true;
        }

        return false;
    }
}
