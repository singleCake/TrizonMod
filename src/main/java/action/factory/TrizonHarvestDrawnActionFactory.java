package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;

import action.TrizonCheckAction;

public class TrizonHarvestDrawnActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonHarvestDrawnActionFactory.class);
    DamageInfo info;

    public TrizonHarvestDrawnActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public void receiveDamageInfo(DamageInfo info) {
        this.info = info;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonCheckAction<DamageInfo>(info, (info) -> info.type == DamageType.THORNS, new DrawCardAction(amount));
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonHarvestDrawnActionFactory(this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonHarvestDrawnActionFactory) {
            TrizonHarvestDrawnActionFactory o = (TrizonHarvestDrawnActionFactory) other;
            this.amount += o.amount;
            return true;
        }

        return false;
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }
    
}
