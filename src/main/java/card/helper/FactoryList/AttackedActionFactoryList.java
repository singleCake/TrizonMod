package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.DamageInfo;

import action.factory.AbstractTrizonFactory;

public class AttackedActionFactoryList extends AbstractFactoryList {
    public AttackedActionFactoryList() {
    }

    public void behave(DamageInfo info) {
        for (AbstractTrizonFactory factory : factorys) {
            factory.receiveDamageInfo(info);
            this.addToBot(factory.create());
        }
    }

    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String attackedDescription = FUSED_CARD_TIMING[IN_HAND] + " NL ";
        
        for (AbstractTrizonFactory factory : factorys)
            attackedDescription += factory.rawDescription() + " NL ";
        
        return attackedDescription;
    }
    
}
