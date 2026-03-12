package card.helper.Modifier;

import com.megacrit.cardcrawl.cards.AbstractCard;

import card.helper.Tip.TimingTip;
import fusable.Fusable;
import localization.TrizonFactoryStrings;

public abstract class AbstractCardModifier implements Fusable<AbstractCardModifier> {
    int amount;

    public AbstractCardModifier() {
    }

    public int modifyDamage(int damage) {
        return damage;
    }

    public int modifyCost() {
        return 0;
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        
    }

    public void triggerOnOtherCardExhausted(AbstractCard c) {

    }

    public abstract String rawDescription();

    public abstract TimingTip getTimingTip();

    public abstract AbstractCardModifier clone();

    protected static String getDescription(Class<? extends AbstractCardModifier> clazz) {
        return TrizonFactoryStrings.getDescription(clazz);
    }
}
