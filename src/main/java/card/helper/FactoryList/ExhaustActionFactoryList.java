package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;
import card.helper.TimingTip;

public class ExhaustActionFactoryList extends AbstractFactoryList {
    public ExhaustActionFactoryList() {
    }

    @Override
    public void behave() {
        for (int i = factorys.size() - 1; i >= 0; i--) {
            AbstractTrizonFactory factory = factorys.get(i);
            this.addToTop(factory.create());
        }
    }

    @Override
    public String generateRawDescription() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return "";

        return FUSED_CARD_TIMING[EXHAUST] + " NL " + description;
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[EXHAUST], description);
    }
}
