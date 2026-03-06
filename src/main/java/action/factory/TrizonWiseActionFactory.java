package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.AbstractTrizonAction;
import action.TrizonWiseDrawAction;

public class TrizonWiseActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonWiseActionFactory.class);

    public TrizonWiseActionFactory(int times) {
        this.times = times;
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonWiseActionFactory(this.times);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, times);
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonWiseAction(times);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonWiseActionFactory) {
            this.times += ((TrizonWiseActionFactory) other).times;
            return true;
        }
        return false;
    }

    private static class TrizonWiseAction extends AbstractTrizonAction {
        public TrizonWiseAction(int times) {
            this.times = times;
        }

        @Override
        public void actionRepeat() {
            this.addToTop(new TrizonWiseDrawAction());
        }
    }
}
