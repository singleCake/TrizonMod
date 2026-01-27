package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import patch.FreezeCardPatch;

public class TrizonSwimActionFactory extends AbstractTrizonFactory {
    AbstractCard card = null;

    public TrizonSwimActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public void receiveCard(AbstractCard card) {
        this.card = card;
    }

    @Override
    public AbstractGameAction create() {
        if (card == null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (FreezeCardPatch.isFrozen(c)) {
                    card = c;
                    break;
                }
            }
        }
        if (FreezeCardPatch.isFrozen(card)) {
            FreezeCardPatch.Unfreeze(card);
            return new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, 
                new StrengthPower(AbstractDungeon.player, amount));
        } else {
            return null;
        }
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
