package power.helper;

import com.megacrit.cardcrawl.powers.AbstractPower;

public class PowerHelper {
    public static String makeID(Class<? extends AbstractPower> powerClass) {
        return "Trizon:" + powerClass.getSimpleName();
    }
}
