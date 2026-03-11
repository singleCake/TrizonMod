package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class StartOfCombatFactoryList extends AbstractFactoryList {
    public StartOfCombatFactoryList() {
    }

    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String startOfCombatDescription = FUSED_CARD_TIMING[START_OF_COMBAT] + " NL ";
        
        for (AbstractTrizonFactory factory : factorys)
            startOfCombatDescription += factory.rawDescription() + " NL ";
        
        return startOfCombatDescription;
    }
    
}
