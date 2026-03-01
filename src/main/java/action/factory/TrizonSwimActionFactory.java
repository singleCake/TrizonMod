package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import action.TrizonUnFreezeCardAction;

public class TrizonSwimActionFactory extends AbstractTrizonFactory {
    AbstractCard card = null;
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonUnFreezeCardAction.class);

    public TrizonSwimActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public void receiveCard(AbstractCard card) {
        this.card = card;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonUnFreezeCardAction(card, 
            new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, amount), amount));
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonSwimActionFactory(amount);
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonSwimActionFactory) {
            this.amount += ((TrizonSwimActionFactory) other).amount;
            return true;
        }
        return false;
    }
}
