package card.helper.FactoryList;

import card.helper.Tip.TimingTip;

public class EnemyFrozenAfterExhaustedActionFactoryList extends AbstractFactoryList {
    public EnemyFrozenAfterExhaustedActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return "";

        return FUSED_CARD_TIMING[ENEMY_FROZEN_IN_EXHAUST] + " NL " + description + " NL ";
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[ENEMY_FROZEN_IN_EXHAUST], description);
    }
}
