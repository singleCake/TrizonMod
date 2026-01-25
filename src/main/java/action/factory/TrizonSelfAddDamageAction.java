package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonModifyDamageAction;

public class TrizonSelfAddDamageAction extends AbstractTrizonFactory {
    public TrizonSelfAddDamageAction(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonModifyDamageAction(this_card, amount);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSelfAddDamageAction) {
            this.amount += other.amount;
        }
    }
}
