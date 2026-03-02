package card.helper.Modifier;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

public class TrizonButterflyModifier extends AbstractCardModifier {
    private static final String DESCRIPTION = AbstractCardModifier.getDescription(TrizonButterflyModifier.class);

    int additionalDamage = 0;

    public TrizonButterflyModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public int modifyDamage(int damage) {
        return damage + additionalDamage;
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard card) {
        if (card.type != CardType.ATTACK) {
            this.additionalDamage += this.amount;
        }
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractCardModifier clone() {
        return new TrizonButterflyModifier(amount);
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
