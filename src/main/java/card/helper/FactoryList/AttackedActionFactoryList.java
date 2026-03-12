package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.DamageInfo;

import action.factory.AbstractTrizonFactory;
import card.helper.Tip.TimingTip;

public class AttackedActionFactoryList extends AbstractFactoryList {
    public AttackedActionFactoryList() {
    }

    public void behave(DamageInfo info) {
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

        return FUSED_CARD_TIMING[IN_HAND] + " NL " + description + " NL ";
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[IN_HAND], description);
    }
    
}
