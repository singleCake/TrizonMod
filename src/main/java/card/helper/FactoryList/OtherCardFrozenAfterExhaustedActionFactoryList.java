package card.helper.FactoryList;

import card.helper.TimingTip;

public class OtherCardFrozenAfterExhaustedActionFactoryList extends AbstractFactoryList {
    public OtherCardFrozenAfterExhaustedActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return "";

        return FUSED_CARD_TIMING[IN_EXHAUST] + " NL " + description;
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[IN_EXHAUST], description);
    }
}
