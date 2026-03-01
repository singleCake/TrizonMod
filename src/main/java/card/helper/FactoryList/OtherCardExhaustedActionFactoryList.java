package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import action.factory.AbstractTrizonFactory;

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
        if (factorys.isEmpty())
            return "";

        String otherCardExhaustedDescription = FUSED_CARD_TIMING[IN_HAND] + " NL ";
        
        for (AbstractTrizonFactory factory : factorys)
            otherCardExhaustedDescription += factory.rawDescription() + " NL ";
        
        return otherCardExhaustedDescription;
    }
}
