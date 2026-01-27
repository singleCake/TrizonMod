package power.factory;

import com.megacrit.cardcrawl.powers.AbstractPower;

import fusable.Fusable;

public abstract class AbstractTrizonPowerFactory implements Fusable<AbstractTrizonPowerFactory> {
    protected int amount;
    
    public abstract AbstractPower create();
}
