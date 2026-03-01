package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class FrozenActionFactoryList extends AbstractFactoryList {
    public FrozenActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String frozenDescription = FUSED_CARD_TIMING[FROZEN] + " NL ";

        for (AbstractTrizonFactory factory : factorys)
            frozenDescription += factory.rawDescription() + " NL ";

        return frozenDescription;
    }
}
