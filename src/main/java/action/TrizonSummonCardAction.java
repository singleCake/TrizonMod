package action;

import java.util.Map;
import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import card.TrizonCard;

public class TrizonSummonCardAction extends AbstractTrizonAction {
    private boolean freeThisTurn;
    private final Predicate<TrizonCard> cardFilter;

    public TrizonSummonCardAction(int times) {
        this(times, false, null);
    }
    
    public TrizonSummonCardAction(int times, boolean freeThisTurn) {
        this(times, freeThisTurn, null);
    }

    public TrizonSummonCardAction(int times, boolean freeThisTurn, Predicate<TrizonCard> cardFilter) {
        this.times = times;
        this.freeThisTurn = freeThisTurn;
        this.cardFilter = cardFilter == null ? c -> true : cardFilter;
    }

    @Override
    public void actionRepeat() {
        TrizonCard c = getRandomTrizonCard();
        if (this.freeThisTurn) {
            c.setCostForTurn(0);
        }
        this.addToTop(new MakeTempCardInHandAction(c));
    }

    private TrizonCard getRandomTrizonCard() {
        CardGroup trizonCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if (c.getValue() instanceof TrizonCard 
            && (c.getValue().rarity == CardRarity.COMMON || c.getValue().rarity == CardRarity.UNCOMMON)) {
                if (this.cardFilter.test((TrizonCard) c.getValue())) {
                    trizonCards.addToBottom(c.getValue());
                }
            }
        }
        return (TrizonCard) trizonCards.getRandomCard(true);
    }
}
