package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import card.AbstractTrizonCard;
import card.TrizonFusedCard;

public class TrizonChimeraAction extends AbstractTrizonAction {
    ArrayList<AbstractCard> cardsToPlay = new ArrayList<>();
    
    public TrizonChimeraAction(AbstractTrizonCard<?> this_card) {
        this.this_card = this_card;

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
    public void update() {
        for (AbstractCard c : cardsToPlay) {
            this.addToTop(new TrizonPlayCardAction(c, true));
        }
        this.isDone = true;
    }
}
