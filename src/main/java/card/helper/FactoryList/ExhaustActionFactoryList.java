package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class ExhaustActionFactoryList extends AbstractFactoryList {
    public ExhaustActionFactoryList() {
    }

    @Override
    public void behave() {
        for (int i = factorys.size() - 1; i >= 0; i--) {
            AbstractTrizonFactory factory = factorys.get(i);
            this.addToTop(factory.create());
        }
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
