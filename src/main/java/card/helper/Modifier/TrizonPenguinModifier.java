package card.helper.Modifier;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.StaticSpireField;
import com.megacrit.cardcrawl.actions.GameActionManager;

import card.helper.Tip.TimingTip;

public class TrizonPenguinModifier extends AbstractCardModifier {
    private static final String DESCRIPTION = AbstractCardModifier.getDescription(TrizonPenguinModifier.class);

    public TrizonPenguinModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public int modifyDamage(int damage) {
        return damage + FrozenNumFieldPatch.frozenNum.get() * amount;
    }

    @Override
    public String rawDescription() {
        return String.format(DESCRIPTION, amount);
    }

    @Override
    public TimingTip getTimingTip() {
        return new TimingTip("企鹅", rawDescription());
    }

    @Override
    public AbstractCardModifier clone() {
        return new TrizonPenguinModifier(amount);
    }

    @Override
    public boolean fuse(AbstractCardModifier other) {
        if (other instanceof TrizonLycorisModifier) {
            this.amount += other.amount;
            return true;
        }

        return false;
    }

    @SpirePatch(clz = GameActionManager.class, method = SpirePatch.CLASS)
    public static class FrozenNumFieldPatch {
        public static StaticSpireField<Integer> frozenNum = new StaticSpireField<>(() -> 0);

        public static void addFrozenNum() {
            addFrozenNum(1);
        }

        public static void addFrozenNum(int num) {
            frozenNum.set(frozenNum.get() + num);
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "clear")
    public static class DungeonClearPatch {
        @SpirePrefixPatch
        public static void Prefix() {
            FrozenNumFieldPatch.frozenNum.set(0);
        }
    }
}
