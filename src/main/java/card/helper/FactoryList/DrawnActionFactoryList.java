package card.helper.FactoryList;

import action.factory.AbstractTrizonFactory;

public class DrawnActionFactoryList extends AbstractFactoryList {
    public DrawnActionFactoryList() {
    }

    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String drawnDescription = FUSED_CARD_TIMING[DRAWN] + " NL ";

        for (AbstractTrizonFactory factory : factorys)
            drawnDescription += factory.rawDescription() + " NL ";

        return drawnDescription;
    }
}  
