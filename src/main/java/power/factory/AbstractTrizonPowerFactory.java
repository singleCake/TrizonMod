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

        // 相同的工厂进行融合，不同的工厂直接加入
        for (AbstractTrizonPowerFactory factory1 : fusedFactories) {
            for (AbstractTrizonPowerFactory factory2 : factories2) {
                if (factory1.getClass().equals(factory2.getClass())) {
                    if (!factory1.fuse(factory2)) {
                        fusedFactories.add(factory2.clone());
                    }
                    break;
                }
            }
        }

        return fusedFactories;
    }
}
