package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonGainEnergyAction;

public class TrizonGainEnergyActionFactory extends AbstractTrizonFactory {
    public TrizonGainEnergyActionFactory(int energyAmount) {
        this.amount = energyAmount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonGainEnergyAction(this.amount);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonGainEnergyActionFactory) {
            this.amount += ((TrizonGainEnergyActionFactory) other).amount;
        }
    }
}
