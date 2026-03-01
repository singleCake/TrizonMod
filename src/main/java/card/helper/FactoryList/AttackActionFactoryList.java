package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import action.factory.AbstractTrizonFactory;

public class AttackActionFactoryList extends AbstractFactoryList {
    public AttackActionFactoryList() {
    }
    
    public void behave(AbstractCreature monster, DamageInfo info) {
        for (int i = factorys.size() - 1; i >= 0; i--) {
            AbstractTrizonFactory factory = factorys.get(i);
            factory.receiveTarget(monster);
            factory.receiveDamageInfo(info);
            this.addToTop(factory.create());
        }
    }

    @Override
    public String generateRawDescription() {
        if (factorys.isEmpty())
            return "";

        String attackDescription = FUSED_CARD_TIMING[ATTACK] + " NL ";
        
        for (AbstractTrizonFactory factory : factorys)
            attackDescription += factory.rawDescription() + " NL ";
        
        return attackDescription;
    }
}
