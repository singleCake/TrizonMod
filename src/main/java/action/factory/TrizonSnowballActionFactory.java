package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.TrizonFreezeCardAction;
import power.TrizonFrozenPower;

public class TrizonSnowballActionFactory extends AbstractTrizonFactory {
    AbstractCard targetCard;
    AbstractCreature targetCreature;

    public TrizonSnowballActionFactory() {
    }

    @Override
    public void receiveCard(AbstractCard card) {
        this.targetCard = card;
    }

    @Override
    public void receiveTarget(AbstractCreature creature) {
        this.targetCreature = creature;
    }

    @Override
    public AbstractGameAction create() {
        if (targetCreature != null) {
            return new ApplyPowerAction(targetCreature, AbstractDungeon.player, new TrizonFrozenPower(targetCreature));
        }
        if (targetCard != null) {
            return new TrizonFreezeCardAction(targetCard);
        }
        return null;
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
    }
}
