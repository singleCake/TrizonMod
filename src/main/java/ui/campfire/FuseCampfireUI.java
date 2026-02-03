package ui.campfire;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import card.basic.Meat;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class FuseCampfireUI {
    public CancelButton cancelButton = new CancelButton();

    private ArrayList<AbstractCard> displayCards = new ArrayList<>();

    private int selectedCardIndex = -1;

    private static final float CARD_START_X = Settings.WIDTH / 2.0F;

    private static final float CARD_START_Y = Settings.HEIGHT / 7.0F * 3.8F;

    private static final float CARD_SPACING_X = 300.0F * Settings.xScale;

    public FuseCampfireUI() {
        super();
        setDisplayCards();
    }

    private void updateCardsMode() {
        for (int i = 0; i < this.displayCards.size(); i++) {
            AbstractCard card = this.displayCards.get(i);
            card.update();
            
            if (card.hb.hovered) {
                this.selectedCardIndex = i;
            }
        }
    }

    private void renderCardsMode(SpriteBatch sb) {
        for (int i = 0; i < this.displayCards.size(); i++) {
            AbstractCard card = this.displayCards.get(i);
            
            float cardX = CARD_START_X + (i - 1) * CARD_SPACING_X;
            card.current_x = cardX;
            card.current_y = CARD_START_Y;
            
            card.render(sb);
        }
    }

    public void setDisplayCards() {
        this.displayCards = new ArrayList<>();
        displayCards.add(new Meat());
        displayCards.add(new Meat());
        displayCards.add(new Meat());
        this.selectedCardIndex = -1;
    }

    public AbstractCard getSelectedCard() {
        if (this.selectedCardIndex >= 0 && this.selectedCardIndex < this.displayCards.size()) {
            return this.displayCards.get(this.selectedCardIndex);
        }
        return null;
    }

    public void clearDisplayCards() {
        this.displayCards.clear();
        this.selectedCardIndex = -1;
    }

    @SpirePatch(clz = CampfireUI.class, method = SpirePatch.CLASS)
    public static class FuseCampfireUIFieldPatch {
        public static SpireField<FuseCampfireUI> fuseUI = new SpireField<>(() -> null);

        public static SpireField<Boolean> fuseMode = new SpireField<>(() -> false);
    }

    public static boolean isInFuseMode(CampfireUI instance) {
        return FuseCampfireUIFieldPatch.fuseMode.get(instance);
    }

    public static FuseCampfireUI getFuseUI(CampfireUI instance) {
        return FuseCampfireUIFieldPatch.fuseUI.get(instance);
    }

    public static void initFuseUI(CampfireUI instance) {
        FuseCampfireUIFieldPatch.fuseUI.set(instance, new FuseCampfireUI());
    }

    public static void switchMode(boolean toFuseMode) {
        CampfireUI instance = ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI;

        if (toFuseMode) {
            FuseCampfireUIFieldPatch.fuseMode.set(instance, true);
            FuseCampfireUIFieldPatch.fuseUI.get(instance).cancelButton.show("取消");
        } else {
            FuseCampfireUIFieldPatch.fuseMode.set(instance, false);
            FuseCampfireUIFieldPatch.fuseUI.get(instance).cancelButton.hide();
        }
    }

    @SpirePatch(clz = CampfireUI.class, method = "render")
    public static class RenderPatch {
        @SpirePostfixPatch
        public static void Insert(CampfireUI __instance, SpriteBatch sb) {
            if (isInFuseMode(__instance)) {
                FuseCampfireUI fuseUI = getFuseUI(__instance);
                fuseUI.renderCardsMode(sb);
                fuseUI.cancelButton.render(sb);
            }
        }
    }

    @SpirePatch(clz = CampfireUI.class, method = "renderCampfireButtons")
    public static class RenderCampfireButtonsPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CampfireUI __instance, SpriteBatch sb) {
            if (isInFuseMode(__instance)) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CampfireUI.class, method = "update")
    public static class UpdatePatch {
        @SpireInsertPatch(rloc = 11)
        public static void Insert(CampfireUI __instance) {
            if (isInFuseMode(__instance)) {
                FuseCampfireUI fuseUI = getFuseUI(__instance);
                fuseUI.updateCardsMode();
                fuseUI.cancelButton.update();
            }
        }
    }

    @SpirePatch(clz = AbstractCampfireOption.class, method = "update")
    public static class AbstractCampfireOptionUpdatePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCampfireOption __instance) {
            CampfireUI campfireUI = ((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI;
            if (isInFuseMode(campfireUI)) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        @SpireInsertPatch(rloc = 25)
        public static SpireReturn<Void> Insert(AbstractCampfireOption __instance) {
            if (__instance instanceof FuseOption) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
