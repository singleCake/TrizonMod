package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonAlfneyAction;
import card.helper.DynamicVariable.FuseDV.BlockFuseDV;
import card.helper.DynamicVariable.FuseDV.FuseDV;

public class TrizonAlfneyActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonAlfneyActionFactory.class);
    private static final String DESCRIPTION_FOR_CARD = AbstractTrizonFactory.getDescriptionForCard(TrizonAlfneyActionFactory.class);

    public TrizonAlfneyActionFactory(int blockAmount) {
        this.amount = blockAmount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonAlfneyAction(this.amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, this.amount);
    }

    @Override
    public String rawDescriptionForCard() {
        return String.format(DESCRIPTION_FOR_CARD);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonAlfneyActionFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonAlfneyActionFactory) {
            this.amount += ((TrizonAlfneyActionFactory) other).amount;
            return true;
        }

        return false;
    }

    @Override
    public FuseDV getFuseDV() {
        return new BlockFuseDV(this.amount);
    }
}
