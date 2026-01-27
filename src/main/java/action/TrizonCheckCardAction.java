package action;

import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TrizonCheckCardAction extends AbstractTrizonAction {
    private final Predicate<AbstractCard> cardFilter;
    private AbstractGameAction followupAction;
    private AbstractCard cardToCheck;

    public TrizonCheckCardAction(AbstractCard cardToCheck, Predicate<AbstractCard> cardFilter, AbstractGameAction followupAction) {
        this.cardToCheck = cardToCheck;
        this.cardFilter = cardFilter;
        this.followupAction = followupAction;
    }

    @Override
    public void update() {
        if (this.cardFilter.test(this.cardToCheck)) {
            this.addToTop(followupAction);
        }
        this.isDone = true;
    }
}
