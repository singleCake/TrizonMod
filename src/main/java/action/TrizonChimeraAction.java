package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import card.TrizonCard;
import card.TrizonFusedCard;

public class TrizonChimeraAction extends AbstractTrizonAction {
    ArrayList<AbstractCard> cardsToPlay = new ArrayList<>();
    
    public TrizonChimeraAction(TrizonCard this_card, int times) {
        this.this_card = this_card;
        this.times = times;

        if (this_card instanceof TrizonFusedCard) {
            for (String key : ((TrizonFusedCard)this_card).fusionData.keySet()) {
                int amount = ((TrizonFusedCard)this_card).fusionData.get(key);
                for (int i = 0; i < amount; i++) {
                    cardsToPlay.add(CardLibrary.getCard(key).makeCopy());
                }
            }
        } else {
            this.isDone = true;
        }
    }

    @Override
    public void actionRepeat() {
        for (AbstractCard c : cardsToPlay) {
            this.addToTop(new TrizonPlayCardAction(c, true));
        }
    }
}
