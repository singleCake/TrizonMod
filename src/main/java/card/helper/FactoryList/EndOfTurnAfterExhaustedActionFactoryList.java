package card.helper.FactoryList;

import card.helper.Tip.TimingTip;

public class EndOfTurnAfterExhaustedActionFactoryList extends AbstractFactoryList {
    public EndOfTurnAfterExhaustedActionFactoryList() {
    }

    @Override
    public String generateRawDescription() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return "";

        return FUSED_CARD_TIMING[END_IN_EXHAUST] + " NL " + description + " NL ";
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[END_IN_EXHAUST], description);
    }
}
