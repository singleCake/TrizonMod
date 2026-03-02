package card.helper.Modifier;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import card.TrizonCard;
import fusable.Fusable;

public class CardModifierList implements Fusable<CardModifierList> {
    private ArrayList<AbstractCardModifier> modifiers = new ArrayList<>();

    public CardModifierList() {
    }

    public void addModifier(AbstractCardModifier modifier) {
        this.modifiers.add(modifier);
    }

    public void clear() {
        modifiers = new ArrayList<>();
    }

    public int modifyDamage(int damage) {
        int modifiedDamage = damage;
        for (AbstractCardModifier modifier : modifiers) {
            modifiedDamage = modifier.modifyDamage(modifiedDamage);
        }
        return modifiedDamage;
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        for (AbstractCardModifier modifier : modifiers) {
            modifier.triggerOnOtherCardPlayed(c);
        }
    }

    public void triggerOnOtherCardExhausted(AbstractCard c) {
        for (AbstractCardModifier modifier : modifiers) {
            modifier.triggerOnOtherCardExhausted(c);
        }
    }

    public String rawDescription() {
        String modifierDescription = "";
        for (AbstractCardModifier modifier : modifiers) {
            modifierDescription += modifier.rawDescription();
        }

        return modifierDescription;
    }

    public CardModifierList clone() {
        CardModifierList modifierList = new CardModifierList();
        for (AbstractCardModifier modifier : modifiers) {
            modifierList.addModifier(modifier.clone());
        }
        return modifierList;
    }

    @Override
    public boolean fuse(CardModifierList other) {
        for (AbstractCardModifier modifier1 : modifiers) {
            for (AbstractCardModifier modifier2 : other.modifiers) {
                if (modifier1.getClass().equals(modifier2.getClass())) {
                    if (!modifier1.fuse(modifier2)) {
                        this.addModifier(modifier2);
                    }
                    break;
                }
            }
        }

        for (AbstractCardModifier modifier2 : other.modifiers) {
            boolean foundMatch = false;
            for (AbstractCardModifier modifier1 : modifiers) {
                if (modifier1.getClass().equals(modifier2.getClass())) {
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {
                this.addModifier(modifier2);
            }
        }

        return true;
    }

    @SpirePatch(clz = AbstractCard.class, method = "applyPowers")
    public static class ApplyPowersPatch {
        @SpireInsertPatch(rloc = 9, localvars = {"tmp"})
        public static void Insert(AbstractCard __instance, @ByRef float[] tmp) {
            if (__instance instanceof TrizonCard) {
                tmp[0] = ((TrizonCard) __instance).modifier.modifyDamage((int)tmp[0]);
            }
        }   
    }
}
