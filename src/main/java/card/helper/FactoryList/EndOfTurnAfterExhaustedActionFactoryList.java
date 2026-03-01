package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class EndOfTurnAfterExhaustedActionFactoryList extends AbstractFactoryList {
    public EndOfTurnAfterExhaustedActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String endOfTurnDescription = FUSED_CARD_TIMING[IN_EXHAUST] + " NL ";
        
        for (AbstractTrizonFactory factory : factorys)
            endOfTurnDescription += factory.rawDescription() + " NL ";
        
        return endOfTurnDescription;
    }
}
