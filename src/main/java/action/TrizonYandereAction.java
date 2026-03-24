package action;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StasisPower;

import card.AbstractTrizonCard;

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

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.hasPower(StasisPower.POWER_ID)) {
                try {
                    Field stasisCardField = StasisPower.class.getDeclaredField("card");
                    stasisCardField.setAccessible(true);
                    AbstractCard stasisCard = (AbstractCard) stasisCardField.get(mo.getPower(StasisPower.POWER_ID));
                    if (isYandere(stasisCard)) {
                        AbstractDungeon.actionManager.addToTop(new LoseHPAction(mo, mo, 99999));
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
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
        if (c instanceof AbstractTrizonCard) {
            return ((AbstractTrizonCard<?>) c).isYandere();
        }
        return false;
    }
}
