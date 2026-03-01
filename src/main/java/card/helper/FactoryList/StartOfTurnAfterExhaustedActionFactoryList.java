package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class StartOfTurnAfterExhaustedActionFactoryList extends AbstractFactoryList {
    public StartOfTurnAfterExhaustedActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String startOfTurnDescription = FUSED_CARD_TIMING[IN_EXHAUST] + " NL ";
        
        for (AbstractTrizonFactory factory : factorys)
            startOfTurnDescription += factory.rawDescription() + " NL ";
        
        return startOfTurnDescription;
    }
}
