package card.helper.FactoryList;

import card.helper.Tip.TimingTip;

public class DrawnActionFactoryList extends AbstractFactoryList {
    public DrawnActionFactoryList() {
    }

    @Override
    public String generateRawDescription() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return "";

        return FUSED_CARD_TIMING[DRAWN] + " NL " + description + " NL ";
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[DRAWN], description);
    }
}  
