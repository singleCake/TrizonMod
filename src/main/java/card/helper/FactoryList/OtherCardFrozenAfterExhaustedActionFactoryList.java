package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class OtherCardFrozenAfterExhaustedActionFactoryList extends AbstractFactoryList {
    public OtherCardFrozenAfterExhaustedActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String description = FUSED_CARD_TIMING[IN_EXHAUST] + " NL ";

        for (AbstractTrizonFactory factory : factorys)
            description += factory.rawDescription() + " NL ";

        return description;
    }
}
