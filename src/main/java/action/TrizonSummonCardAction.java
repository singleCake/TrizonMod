package action;

import java.util.Map;
import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import card.AbstractTrizonCard;

public class TrizonSummonCardAction extends AbstractTrizonAction {
    private boolean freeThisTurn;
    private boolean isEthereal;
    private final Predicate<AbstractTrizonCard<?>> cardFilter;

    public TrizonSummonCardAction(int times) {
        this(times, false, false, null);
    }
    
    public TrizonSummonCardAction(int times, boolean freeThisTurn) {
        this(times, freeThisTurn, false, null);
    }

    public TrizonSummonCardAction(int times, boolean freeThisTurn, boolean isEthereal) {
        this(times, freeThisTurn, isEthereal, null);
    }

    public TrizonSummonCardAction(int times, boolean freeThisTurn, Predicate<AbstractTrizonCard<?>> cardFilter) {
        this(times, freeThisTurn, false, cardFilter);
    }

    public TrizonSummonCardAction(int times, boolean freeThisTurn, boolean isEthereal, Predicate<AbstractTrizonCard<?>> cardFilter) {
        this.times = times;
        this.freeThisTurn = freeThisTurn;
        this.isEthereal = isEthereal;
        this.cardFilter = cardFilter == null ? c -> c.rarity == CardRarity.COMMON || c.rarity == CardRarity.UNCOMMON : cardFilter;
    }

    @Override
    public void actionRepeat() {
        AbstractTrizonCard<?> c = getRandomTrizonCard();
        if (this.freeThisTurn) {
            c.setCostForTurn(0);
        }
        if (this.isEthereal) {
            c.isEthereal = true;
        }
        this.addToTop(new MakeTempCardInHandAction(c));
    }

    private AbstractTrizonCard<?> getRandomTrizonCard() {
        CardGroup trizonCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if (c.getValue() instanceof AbstractTrizonCard && !c.getValue().hasTag(CardTags.HEALING)) {
                if (this.cardFilter.test((AbstractTrizonCard<?>) c.getValue())) {
                    trizonCards.addToBottom(c.getValue());
                }
            }
        }
        return (AbstractTrizonCard<?>) trizonCards.getRandomCard(true);
    }
}
