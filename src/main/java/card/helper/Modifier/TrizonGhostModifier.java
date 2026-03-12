package card.helper.Modifier;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.StaticSpireField;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.CardGroup;

import card.helper.Tip.TimingTip;

public class TrizonGhostModifier extends AbstractCardModifier {
    private static final String DESCRIPTION = AbstractCardModifier.getDescription(TrizonGhostModifier.class);

    public TrizonGhostModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public int modifyCost() {
        return -amount * ExhaustNumFieldPatch.exhaustNum.get();
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public TimingTip getTimingTip() {
        return new TimingTip("幽灵", rawDescription());
    }

    @Override
    public AbstractCardModifier clone() {
        return new TrizonGhostModifier(amount);
    }

    @Override
    public boolean fuse(AbstractCardModifier other) {
        if (other instanceof TrizonGhostModifier) {
            this.amount += ((TrizonGhostModifier) other).amount;
            return true;
        }
        return false;
    }


    @SpirePatch(clz = GameActionManager.class, method = SpirePatch.CLASS)
    public static class ExhaustNumFieldPatch {
        public static StaticSpireField<Integer> exhaustNum = new StaticSpireField<>(() -> 0);

        public static void addExhaustNum() {
            addExhaustNum(1);
        }

        public static void addExhaustNum(int num) {
            exhaustNum.set(exhaustNum.get() + num);
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class MoveToExhaustPatch {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance) {
            ExhaustNumFieldPatch.addExhaustNum();
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "clear")
    public static class DungeonClearPatch {
        @SpirePrefixPatch
        public static void Prefix() {
            ExhaustNumFieldPatch.exhaustNum.set(0);
        }
    }
}
