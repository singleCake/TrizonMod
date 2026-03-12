package power.factory;

import java.util.ArrayList;

import com.megacrit.cardcrawl.powers.AbstractPower;

import fusable.Fusable;
import localization.TrizonFactoryStrings;

public abstract class AbstractTrizonPowerFactory implements Fusable<AbstractTrizonPowerFactory> {
    protected int amount;
    
    public abstract AbstractPower create();

    public abstract String rawDescription();

    public abstract AbstractTrizonPowerFactory clone();

    protected static String getDescription(Class<? extends AbstractTrizonPowerFactory> clazz) {
        return TrizonFactoryStrings.getDescription(clazz);
    }

    protected static String[] getExtendedDescription(Class<? extends AbstractTrizonPowerFactory> clazz) {
        return TrizonFactoryStrings.getExtendedDescription(clazz);
    }

    public static ArrayList<AbstractTrizonPowerFactory> fuseFactories(ArrayList<AbstractTrizonPowerFactory> factories1, ArrayList<AbstractTrizonPowerFactory> factories2) {
        ArrayList<AbstractTrizonPowerFactory> fusedFactories = new ArrayList<>();
        for (AbstractTrizonPowerFactory factory1 : factories1) {
            fusedFactories.add(factory1.clone());
        }

        for (AbstractTrizonPowerFactory factory2 : factories2) {
            boolean fused = false;
            for (AbstractTrizonPowerFactory factory1 : fusedFactories) {
                if (factory1.fuse(factory2)) {
                    fused = true;
                    break;
                }
            }
            if (!fused) {
                fusedFactories.add(factory2.clone());
            }
        }

        return fusedFactories;
    }
}
