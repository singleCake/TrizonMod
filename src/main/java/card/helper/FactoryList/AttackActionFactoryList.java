package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import action.factory.AbstractTrizonFactory;
import card.helper.TimingTip;

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
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return "";

        return FUSED_CARD_TIMING[ATTACK] + " NL " + description;
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[ATTACK], description);
    }
}
