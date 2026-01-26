package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonModifyDamageAction;

public class TrizonSelfAddDamageActionFactory extends AbstractTrizonFactory {
    public TrizonSelfAddDamageActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonModifyDamageAction(this_card, amount);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSelfAddDamageActionFactory) {
            this.amount += other.amount;
        }
    }
}
