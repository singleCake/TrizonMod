package card.helper.DynamicVariable.FuseDV;

import card.TrizonFusedCard;

public abstract class FuseDV {
    public int value;
    public int baseValue;
    public boolean isModified;

    public FuseDV(int baseValue) {
        this.baseValue = baseValue;
        this.value = baseValue;
        this.isModified = false;
    }

    public abstract void applyPower(TrizonFusedCard this_card);
}
