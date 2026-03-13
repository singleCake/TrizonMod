package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TrizonCaptureActionFactory extends AbstractTrizonFactory {
    transient AbstractCard card;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonCaptureActionFactory.class);

    public TrizonCaptureActionFactory() {
    }

    @Override
    public void receiveCard(AbstractCard card) {
        this.card = card;
    }

    @Override
    public AbstractGameAction create() {
        return new action.TrizonCaptureAction(this.card);
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonCaptureActionFactory();
    }    
}
