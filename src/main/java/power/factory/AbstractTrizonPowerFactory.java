package power.factory;

import com.megacrit.cardcrawl.powers.AbstractPower;

import fusable.Fusable;

public abstract class AbstractTrizonPowerFactory implements Fusable<AbstractTrizonPowerFactory> {
    public abstract AbstractPower create();
}
