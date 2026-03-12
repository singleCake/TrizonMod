package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TrizonTempCardInDrawPileActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonTempCardInDrawPileActionFactory.class);

    String cardID;
    transient AbstractCard card;

    public TrizonTempCardInDrawPileActionFactory(AbstractCard card, int amount) {
        this.card = card;
        this.cardID = card.cardID;
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        if (this.card == null) {
            this.card = resolveCardById(this.cardID);
        }
        return new MakeTempCardInDrawPileAction(card, amount, true, true);
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
        return new TrizonTempCardInDrawPileActionFactory(this.card, amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonTempCardInDrawPileActionFactory) {
            if (this.cardID != null && this.cardID.equals(((TrizonTempCardInDrawPileActionFactory) other).cardID)) {
                this.amount += other.amount;
                return true;
            }
        }
        return false;
    }
}
