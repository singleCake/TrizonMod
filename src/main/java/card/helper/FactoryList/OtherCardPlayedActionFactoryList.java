package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import action.factory.AbstractTrizonFactory;

public class OtherCardPlayedActionFactoryList extends AbstractFactoryList {
    public OtherCardPlayedActionFactoryList() {
    }
    
    public void behave(AbstractCard card) {
        for (AbstractTrizonFactory factory : factorys) {
            factory.receiveCard(card);
            this.addToBot(factory.create());
        }
    }

    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String otherCardPlayedDescription = FUSED_CARD_TIMING[IN_HAND] + " NL ";
        
        for (AbstractTrizonFactory factory : factorys)
            otherCardPlayedDescription += factory.rawDescription() + " NL ";
        
        return otherCardPlayedDescription;
    }
}
