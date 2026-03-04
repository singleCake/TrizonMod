package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class EndOfTurnActionFactoryList extends AbstractFactoryList {
    public EndOfTurnActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String frozenDescription = FUSED_CARD_TIMING[END] + " NL ";

        for (AbstractTrizonFactory factory : factorys)
            frozenDescription += factory.rawDescription() + " NL ";

        return frozenDescription;
    }
}
