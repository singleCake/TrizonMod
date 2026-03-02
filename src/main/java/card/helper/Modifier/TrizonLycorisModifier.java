package card.helper.Modifier;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

public class TrizonLycorisModifier extends AbstractCardModifier {
    private static final String DESCRIPTION = AbstractCardModifier.getDescription(TrizonLycorisModifier.class);

    int additionalDamage = 0;

    public TrizonLycorisModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public int modifyDamage(int damage) {
        return damage + additionalDamage;
    }

    @Override
    public void triggerOnOtherCardExhausted(AbstractCard card) {
        if (card.type == CardType.ATTACK) {
            this.additionalDamage += this.amount;
        }
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractCardModifier clone() {
        return new TrizonLycorisModifier(amount);
    }

    @Override
    public boolean fuse(AbstractCardModifier other) {
        if (other instanceof TrizonLycorisModifier) {
            this.amount += other.amount;
            return true;
        }

        return false;
    }
}
