package action.factory;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.AbstractTrizonAction;
import patch.FreezeCardPatch;

public class TrizonHermitActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonHermitActionFactory.class);

    public TrizonHermitActionFactory(int amount) {
        this.amount = amount;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonHermitAction(amount);
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonHermitActionFactory(this.amount);
    }
    
    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonHermitActionFactory) {
            TrizonHermitActionFactory o = (TrizonHermitActionFactory) other;
            this.amount += o.amount;
            return true;
        }
        return false;
    }

    private static class TrizonHermitAction extends AbstractTrizonAction {
        public TrizonHermitAction(int amount) {
            this.amount = amount;
        }

        @Override
        public void update() {
            ArrayList<AbstractCard> cardsToFreeze = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!FreezeCardPatch.isFrozen(c)) {
                    cardsToFreeze.add(c);
                }
            }

            if (cardsToFreeze.size() < this.amount) {
                for (AbstractCard c : cardsToFreeze) {
                    FreezeCardPatch.Freeze(c);
                }
            } else {
                for (int i = 0; i < this.amount; i++) {
                    int index = AbstractDungeon.cardRandomRng.random(cardsToFreeze.size() - 1);
                    FreezeCardPatch.Freeze(cardsToFreeze.get(index));
                    cardsToFreeze.remove(index);
                }
            }

            this.isDone = true;
        }
    }
}
