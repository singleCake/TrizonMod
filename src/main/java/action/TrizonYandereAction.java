package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.TrizonCard;

public class TrizonYandereAction extends AbstractTrizonAction {
    private ArrayList<AbstractCard> yanderes = new ArrayList<>();

    public TrizonYandereAction() {
    }

    @Override
    public void update() {
        searchYandere(AbstractDungeon.player.drawPile);
        searchYandere(AbstractDungeon.player.discardPile);
        searchYandere(AbstractDungeon.player.exhaustPile);
        searchYandere(AbstractDungeon.player.limbo);

        for (int i = yanderes.size() - 1; i >= 0; i--) {
            AbstractCard c = yanderes.get(i);
            this.addToTop(new MakeTempCardInHandAction(c, true, true));
        }

        this.isDone = true;
    }

    private void searchYandere(CardGroup group) {
        ArrayList<AbstractCard> yanderes_t = new ArrayList<>();
        for (AbstractCard c : group.group) {
            if (isYandere(c)) {
                yanderes.add(c);
                yanderes_t.add(c);
            }
        }
        for (AbstractCard c : yanderes_t) {
            group.removeCard(c);
        }
    }

    private boolean isYandere(AbstractCard c) {
        if (c instanceof TrizonCard) {
            return ((TrizonCard)c).isYandere();
        }
        return false;
    }
}
