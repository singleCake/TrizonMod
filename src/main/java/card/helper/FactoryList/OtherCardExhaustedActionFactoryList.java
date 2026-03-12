package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import action.factory.AbstractTrizonFactory;
import card.helper.Tip.TimingTip;

public class OtherCardExhaustedActionFactoryList extends AbstractFactoryList {
    public OtherCardExhaustedActionFactoryList() {
    }

    public void behave(AbstractCard card) {
        for (AbstractTrizonFactory factory : factorys) {
            factory.receiveCard(card);
            this.addToBot(factory.create());
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
