package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class EnemyFrozenAfterExhaustedActionFactoryList extends AbstractFactoryList {
    public EnemyFrozenAfterExhaustedActionFactoryList() {
    }
    
    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String frozenDescription = FUSED_CARD_TIMING[IN_EXHAUST] + " NL ";

        for (AbstractTrizonFactory factory : factorys)
            frozenDescription += factory.rawDescription() + " NL ";

        return frozenDescription;
    }
}
