package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TrizonTempCardInHandActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonTempCardInHandActionFactory.class);
    String cardID;
    transient AbstractCard card;

    public TrizonTempCardInHandActionFactory(AbstractCard card) {
        this(card, 1);
    }

    public TrizonTempCardInHandActionFactory(AbstractCard card, int amount) {
        this.card = card;
        this.cardID = card.cardID;
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        if (this.card == null) {
            this.card = resolveCardById(this.cardID);
        }
        return new MakeTempCardInHandAction(card, amount);
    }

    @Override
    public String rawDescription() {
        if (this.card == null) {
            this.card = resolveCardById(this.cardID);
        }
        String name = this.card == null ? this.cardID : this.card.name;
        return String.format(DESCRIPTION, amount, name);
    }

    @Override
    public AbstractTrizonFactory clone() {
        if (this.card == null) {
            this.card = resolveCardById(this.cardID);
        }
        return new TrizonTempCardInHandActionFactory(this.card, amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonTempCardInHandActionFactory) {
            if (this.cardID != null && this.cardID.equals(((TrizonTempCardInHandActionFactory) other).cardID)) {
                this.amount += other.amount;
                return true;
            }
        }
        return false;
    }
}
