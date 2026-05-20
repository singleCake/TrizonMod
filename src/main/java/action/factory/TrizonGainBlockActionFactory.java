package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonGainBlockAction;
import card.helper.DynamicVariable.FuseDV.BlockFuseDV;
import card.helper.DynamicVariable.FuseDV.FuseDV;

public class TrizonGainBlockActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonGainBlockActionFactory.class);
    private static final String DESCRIPTION_FOR_CARD = AbstractTrizonFactory
            .getDescriptionForCard(TrizonGainBlockActionFactory.class);

    public TrizonGainBlockActionFactory(int blockAmount) {
        this.amount = blockAmount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonGainBlockAction(this.amount);
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
        return new TrizonGainBlockActionFactory(amount);
    }
    
    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonGainBlockActionFactory) {
            this.amount += ((TrizonGainBlockActionFactory) other).amount;
            return true;
        }

        return false;
    }

    @Override
    public FuseDV getFuseDV() {
        return new BlockFuseDV(this.amount);
    }
}
