package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonWiseDrawAction extends AbstractTrizonAction {
    public TrizonWiseDrawAction() {
    }

    @Override
    public void update() {
        ArrayList<Integer> cost_list = new ArrayList<>();
        ArrayList<AbstractCard> to_draw = new ArrayList<>();

        for (int i = AbstractDungeon.player.drawPile.size() - 1; i >= 0; i--) {
            AbstractCard c = AbstractDungeon.player.drawPile.group.get(i);
            if (!cost_list.contains(c.cost)) {
                cost_list.add(c.cost);
                to_draw.add(c);
            }
        }

        for (int i = to_draw.size() - 1; i >= 0; i--) {
            AbstractCard c = to_draw.get(i);
            AbstractDungeon.player.drawPile.removeCard(c);
            AbstractDungeon.player.drawPile.addToTop(c);
        }

        this.addToTop(new DrawCardAction(cost_list.size()));
        this.isDone = true;
    }
}
