package patch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

import card.AbstractTrizonCard;

public class WeightedShufflePatch {
    private static class WeightedCard {
        public final AbstractCard card;
        public final float weight;

        public WeightedCard(AbstractCard card, float weight) {
            this.card = card;
            this.weight = weight;
        }
    }

    public static void applyWeightedOrder(CardGroup group) {
        List<WeightedCard> weightedCards = new ArrayList<>();
        for (AbstractCard card : group.group) {
            float weight = AbstractDungeon.cardRandomRng.random(0.0F, 1.0F);
            if (card instanceof AbstractTrizonCard) {
                if (((AbstractTrizonCard<?>) card).isGoldFish()) {
                    weight *= 0.5F;
                }
            }

            weightedCards.add(new WeightedCard(card, weight));
        }

        weightedCards.sort(Comparator.comparingDouble(c -> c.weight));
        group.group.clear();
        for (WeightedCard wc : weightedCards) {
            group.group.add(wc.card);
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "shuffle", paramtypez = {})
    public static class ShufflePatch {
        @SpirePostfixPatch
        public static void Postfix(CardGroup __instance) {
            if (__instance.type == CardGroup.CardGroupType.DRAW_PILE) {
                applyWeightedOrder(__instance);
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "shuffle", paramtypez = {Random.class})
    public static class ShufflePatch2 {
        @SpirePostfixPatch
        public static void Postfix(CardGroup __instance, Random rng) {
            if (__instance.type == CardGroup.CardGroupType.DRAW_PILE) {
                applyWeightedOrder(__instance);
            }
        }
    }
}

