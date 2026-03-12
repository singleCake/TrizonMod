package card.helper.FactoryList;

import card.helper.TimingTip;

public class EndOfTurnActionFactoryList extends AbstractFactoryList {
    public EndOfTurnActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return "";

        return FUSED_CARD_TIMING[END] + " NL " + description;
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[END], description);
    }
}
