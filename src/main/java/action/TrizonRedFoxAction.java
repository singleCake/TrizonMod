package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.common.Fireball;

public class TrizonRedFoxAction extends AbstractTrizonAction {
    public TrizonRedFoxAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> fireballs = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof Fireball) {
                fireballs.add(c);
                this.amount--;
                if (this.amount <= 0) {
                    break;
                }
            }
        }

        this.addToTop(new MakeTempCardInHandAction(new Fireball(), fireballs.size()));

        for (AbstractCard c : fireballs) {
            AbstractDungeon.player.exhaustPile.removeCard(c);
        }

        this.isDone = true;
    }
}
