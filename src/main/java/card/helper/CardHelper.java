package card.helper;

import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

import card.TrizonCard;
import card.TrizonFusedCard;

public class CardHelper {
    public static String makeID(Class<? extends TrizonCard> cardClass) {
        return "Trizon:" + cardClass.getSimpleName();
    }

    public static String getFusedCardRawDescription(TrizonFusedCard fusedCard) {
        String rawDescription = "";
        for (String name : fusedCard.fusionData.keySet()) {
            int count = fusedCard.fusionData.get(name);
            if (count == 1) {
                rawDescription += name;
            } else {
                rawDescription += name + '(' + count + ')';
            }
        }
        return rawDescription;
    }

    public static CardType getFusedCardType(TrizonCard card1, TrizonCard card2) {
        if (card1.type == CardType.ATTACK || card2.type == CardType.ATTACK) {
            return CardType.ATTACK;
        } else if (card1.type == CardType.SKILL && card2.type == CardType.SKILL) {
            return CardType.SKILL;
        } else if (card1.type == CardType.POWER && card2.type == CardType.POWER) {
            return CardType.POWER;
        } else {
            return CardType.STATUS; // not likely to reach here
        }
    }

    public static CardRarity getFusedCardRarity(TrizonCard card1, TrizonCard card2) {
        if (card1.rarity.ordinal() > card2.rarity.ordinal()) {
            return card1.rarity;
        } else {
            return card2.rarity;
        }
    }

    public static CardTarget getFusedCardTarget(TrizonCard card1, TrizonCard card2) {
        if (card1.target == CardTarget.SELF_AND_ENEMY || card2.target == CardTarget.SELF_AND_ENEMY || card1.target == CardTarget.ENEMY || card2.target == CardTarget.ENEMY) {
            return CardTarget.ENEMY; // must aim a target
        } else {
            return CardTarget.NONE; // no target needed
        }
    }
}
