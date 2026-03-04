package action;

import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class TrizonCheckAction<T> extends AbstractTrizonAction {
    private final Predicate<T> filter;
    private AbstractGameAction followupAction;
    private T toCheck;

    public TrizonCheckAction(T toCheck, Predicate<T> filter, AbstractGameAction followupAction) {
        this.toCheck = toCheck;
        this.filter = filter;
        this.followupAction = followupAction;
    }

    @Override
    public void update() {
        if (this.filter.test(this.toCheck)) {
            this.addToTop(followupAction);
        }
        this.isDone = true;
    }
}
