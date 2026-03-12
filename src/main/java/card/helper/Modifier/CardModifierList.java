package card.helper.Modifier;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import card.TrizonCard;
import card.helper.Tip.TimingTip;
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

    public void updateCost(TrizonCard this_card) {
        if (this_card.cost < 0) {
            return ;
        }
        int modifiedCost = 0;
        for (AbstractCardModifier modifier : modifiers) {
            modifiedCost += modifier.modifyCost();
        }
        if (modifiedCost != 0) {
            int newCost = Math.max(0, this_card.cost + modifiedCost);
            this_card.costForTurn = Math.min(this_card.costForTurn, newCost);
            this_card.isCostModifiedForTurn = true;
        }
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

    public ArrayList<TimingTip> getTips() {
        ArrayList<TimingTip> tips = new ArrayList<>();
        for (AbstractCardModifier modifier : modifiers) {
            tips.add(modifier.getTimingTip());
        }
        return tips;
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

    public ModifierListData exportData() {
        ModifierListData data = new ModifierListData();
        for (AbstractCardModifier modifier : modifiers) {
            ModifierData modifierData = new ModifierData();
            modifierData.className = modifier.getClass().getName();
            modifierData.amount = modifier.amount;
            data.modifiers.add(modifierData);
        }
        return data;
    }

    public static CardModifierList fromData(ModifierListData data) {
        CardModifierList list = new CardModifierList();
        if (data == null || data.modifiers == null) {
            return list;
        }

        for (ModifierData modifierData : data.modifiers) {
            if (modifierData == null || modifierData.className == null) {
                continue;
            }
            AbstractCardModifier modifier = createModifier(modifierData.className, modifierData.amount);
            if (modifier != null) {
                list.addModifier(modifier);
            }
        }
        return list;
    }

    private static AbstractCardModifier createModifier(String className, int amount) {
        try {
            Class<?> clazz = Class.forName(className);
            if (!AbstractCardModifier.class.isAssignableFrom(clazz)) {
                return null;
            }
            @SuppressWarnings("unchecked")
            Class<? extends AbstractCardModifier> modifierClass = (Class<? extends AbstractCardModifier>) clazz;
            return modifierClass.getDeclaredConstructor(int.class).newInstance(amount);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static class ModifierListData {
        public ArrayList<ModifierData> modifiers = new ArrayList<>();
    }

    public static class ModifierData {
        public String className;
        public int amount;
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

    @SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class CalculateCardDamagePatch {
        @SpireInsertPatch(rloc = 9, localvars = {"tmp"})
        public static void Insert(AbstractCard __instance, AbstractMonster mo, @ByRef float[] tmp) {
            if (__instance instanceof TrizonCard) {
                tmp[0] = ((TrizonCard) __instance).modifier.modifyDamage((int)tmp[0]);
            }
        }
    }
}
