package card.helper;

import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import card.TrizonCard;
import card.TrizonFusedCard;

import static card.helper.SnowballTargeting.CARD_OR_ENEMY;
import static card.helper.CardTargeting.CARD;

public class CardHelper {
    public static String makeID(Class<? extends TrizonCard> cardClass) {
        System.out.println("Making ID for class: " + cardClass.getSimpleName());
        return "Trizon:" + cardClass.getSimpleName();
    }

    public static String getFusedCardName(TrizonFusedCard fusedCard) {
        String cardName = "";
        for (String name : fusedCard.fusionData.keySet()) {
            int count = fusedCard.fusionData.get(name);
            if (count == 1) {
                cardName += CardLibrary.getCard(name).name;
            } else {
                cardName += CardLibrary.getCard(name).name + '(' + count + ')';
            }
        }
        return cardName;
    }

    public static String getFusedCardRawDescription(TrizonFusedCard fusedCard) {
        String rawDescription = "";
        for (String name : fusedCard.fusionData.keySet()) {
            int count = fusedCard.fusionData.get(name);
            if (count == 1) {
                rawDescription += CardLibrary.getCard(name).name;
            } else {
                rawDescription += CardLibrary.getCard(name).name + '(' + count + ')';
            }
        }
        return rawDescription;
    }

    public static String getFusedCardImg(TrizonCard card1, TrizonCard card2) {
        if (card2.type == CardType.ATTACK && card1.type == CardType.SKILL) {
            return card2.textureImg;
        }
        return card1.textureImg;
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
        if (card1.target == CardTarget.SELF_AND_ENEMY || card2.target == CardTarget.SELF_AND_ENEMY || 
            card1.target == CardTarget.ENEMY || card2.target == CardTarget.ENEMY) {
            return CardTarget.ENEMY; // 必须指定一个敌人
        } else if (card1.target == CARD_OR_ENEMY || card2.target == CARD_OR_ENEMY) {
            return CARD_OR_ENEMY; // 需要指定卡牌或敌人
        } else if (card1.target == CARD || card2.target == CARD) {
            return CARD; // 需要指定一个卡牌
        } else {
            return CardTarget.NONE; // 不需要指定目标
        }
    }
}
