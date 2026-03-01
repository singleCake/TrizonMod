package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class ExhaustActionFactoryList extends AbstractFactoryList {
    public ExhaustActionFactoryList() {
    }

    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String exhaustDescription = FUSED_CARD_TIMING[EXHAUST] + " NL ";
        
        for (AbstractTrizonFactory factory : factorys)
            exhaustDescription += factory.rawDescription() + " NL ";
        
        return exhaustDescription;
    }
}
