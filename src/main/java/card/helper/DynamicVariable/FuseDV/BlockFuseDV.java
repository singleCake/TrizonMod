package card.helper.DynamicVariable.FuseDV;

import action.helper.ApplyPowerHelper;
import card.TrizonFusedCard;

public class BlockFuseDV extends FuseDV {
    public BlockFuseDV(int baseValue) {
        super(baseValue);
    }

    @Override
    public void applyPower(TrizonFusedCard this_card) {
        this.value = ApplyPowerHelper.applyPowerToBlock(baseValue, this_card);
        this.isModified = this.value != this.baseValue;
    }
}
