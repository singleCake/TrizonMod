package patch;

import static modcore.TrizonMod.PlayerColorEnum.Trizon;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;

import basemod.ReflectionHacks;
import character.Shan;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import ui.collect.ChooseCollectScreen;
import ui.collect.CollectButton;
import ui.singleCardView.SingleCardViewButton;

public class CollectPatch {
    private static final String[] COLLECT_TEXT = CardCrawlGame.languagePack.getUIString("Trizon:Collect").TEXT;
    private static final String[] SINGLE_CARD_VIEW_TEXT = CardCrawlGame.languagePack
            .getUIString("Trizon:SingleCardViewButton").TEXT;
    private static AbstractCard cardInst = null;

    private static boolean isShan() {
        return (CardCrawlGame.chosenCharacter == Trizon && ((Boolean) ReflectionHacks
                .getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected"))
                .booleanValue());
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenUpdatePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CharacterSelectScreen __instance) {
            if (isShan()) {
                ChooseCollectScreen.Inst.update();
                if (ChooseCollectScreen.Inst.selecting()) {
                    return SpireReturn.Return(null);
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class CharacterSelectScreenRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance, SpriteBatch sb) {
            if (isShan()) {
                ChooseCollectScreen.Inst.render(sb);
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = SpirePatch.CLASS)
    public static class ButtonField {
        public static SpireField<SingleCardViewButton> obtainButton = new SpireField<>(() -> null);

        public static SpireField<SingleCardViewButton> deleteButton = new SpireField<>(() -> null);
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class, CardGroup.class })
    public static class SingleCardViewOpenPatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance, AbstractCard card, CardGroup group) {
            if (group.type == TrizonEnum.COLLECT) {
                ButtonField.obtainButton.set(__instance, new SingleCardViewButton(300.0F * Settings.scale,
                        Settings.HEIGHT / 5.0F * 4.0F - 100.0F * Settings.scale, SINGLE_CARD_VIEW_TEXT[1]));
                ButtonField.deleteButton.set(__instance, new SingleCardViewButton(300.0F * Settings.scale,
                        Settings.HEIGHT / 5.0F * 4.0F - 200.0F * Settings.scale, SINGLE_CARD_VIEW_TEXT[2], true));
                cardInst = card.makeSameInstanceOf();
            } else {
                ButtonField.obtainButton.set(__instance, null);
                ButtonField.deleteButton.set(__instance, null);
                cardInst = null;
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "update")
    public static class SingleCardViewUpdatePatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance) {
            SingleCardViewButton obtainButton = ButtonField.obtainButton.get(__instance);
            SingleCardViewButton deleteButton = ButtonField.deleteButton.get(__instance);
            if (obtainButton != null) {
                obtainButton.update();
            }
            if (deleteButton != null) {
                deleteButton.update();
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "updateInput")
    public static class SingleCardViewUpdateInputPatch {
        @SpireInsertPatch(rloc = 15)
        public static SpireReturn<Void> Insert(SingleCardViewPopup __instance) {
            SingleCardViewButton obtainButton = ButtonField.obtainButton.get(__instance);
            SingleCardViewButton deleteButton = ButtonField.deleteButton.get(__instance);
            if (obtainButton != null) {
                if (obtainButton.hb.hovered) {
                    obtainButton.hb.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    ChooseCollectScreen.Inst.setCardToObtain(cardInst);
                    ChooseCollectScreen.Inst.gridCardSelectScreen.close();
                }
            }
            if (deleteButton != null) {
                if (deleteButton.hb.hovered) {
                    deleteButton.hb.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    if (deleteButton.confirming) {
                        ChooseCollectScreen.Inst.removeFromCollection(cardInst);
                    } else {
                        // 防止SingleCardViewPopup直接关闭
                        return SpireReturn.Return();
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "render")
    public static class SingleCardViewRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            SingleCardViewButton obtainButton = ButtonField.obtainButton.get(__instance);
            SingleCardViewButton deleteButton = ButtonField.deleteButton.get(__instance);
            if (obtainButton != null) {
                obtainButton.render(sb);
            }
            if (deleteButton != null) {
                deleteButton.render(sb);
            }
        }
    }

    @SpirePatch(clz = ReturnToMenuButton.class, method = SpirePatch.CLASS)
    public static class EndRestartButtonField {
        public static SpireField<CollectButton> restartField = new SpireField<>(() -> null);
    }

    public static boolean hasChosenCollect = false;

    @SpirePatch(clz = ReturnToMenuButton.class, method = SpirePatch.CONSTRUCTOR)
    public static class RButtonAdder {
        public static void Postfix(ReturnToMenuButton __instance) {
            if (AbstractDungeon.player instanceof Shan && !hasChosenCollect) {
                EndRestartButtonField.restartField.set(__instance, new CollectButton());
            }
        }
    }

    @SpirePatch(clz = ReturnToMenuButton.class, method = "appear")
    public static class AppearInjecter {
        public static void Postfix(ReturnToMenuButton __instance, float x, float y, String label) {
            if (AbstractDungeon.player instanceof Shan && !hasChosenCollect) {
                ((CollectButton) EndRestartButtonField.restartField.get(__instance)).appear(x - 300.0F * Settings.scale,
                        y, COLLECT_TEXT[0]);
            }
        }
    }

    @SpirePatch(clz = ReturnToMenuButton.class, method = "hide")
    public static class HideInjecter {
        public static void Postfix(ReturnToMenuButton __instance) {
            if (AbstractDungeon.player instanceof Shan && !hasChosenCollect) {
                ((CollectButton) EndRestartButtonField.restartField.get(__instance)).hide();
            }
        }
    }

    @SpirePatch(clz = ReturnToMenuButton.class, method = "update")
    public static class OptionsUpdateInjecter {
        public static void Postfix(ReturnToMenuButton __instance) {
            if (AbstractDungeon.player instanceof Shan && !hasChosenCollect) {
                ((CollectButton) EndRestartButtonField.restartField.get(__instance)).update();
            }
        }
    }

    @SpirePatch(clz = ReturnToMenuButton.class, method = "render")
    public static class OptionsRenderInjecter {
        public static void Postfix(ReturnToMenuButton __instance, SpriteBatch sb) {
            if (AbstractDungeon.player instanceof Shan && !hasChosenCollect) {
                ((CollectButton) EndRestartButtonField.restartField.get(__instance)).render(sb);
            }
        }
    }

    @SpirePatch(clz = VictoryScreen.class, method = "updateStatsScreen")
    public static class DynamicSliderVictory {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(VictoryScreen __instance, ReturnToMenuButton ___returnButton, float ___statsTimer) {
            if (AbstractDungeon.player instanceof Shan && !hasChosenCollect) {
                ((CollectButton) EndRestartButtonField.restartField.get(___returnButton)).y = Interpolation.pow3In
                        .apply(Settings.HEIGHT * 0.1F, Settings.HEIGHT * 0.15F, ___statsTimer * 1.0F / 0.5F);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(Interpolation.PowIn.class,
                        "apply");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), (Matcher) methodCallMatcher);
            }
        }
    }

    @SpirePatch(clz = DeathScreen.class, method = "updateStatsScreen")
    public static class DynamicSliderDeath {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(DeathScreen __instance, ReturnToMenuButton ___returnButton, float ___statsTimer) {
            if (AbstractDungeon.player instanceof Shan && !hasChosenCollect) {
                ((CollectButton) EndRestartButtonField.restartField.get(___returnButton)).y = Interpolation.pow3In
                        .apply(Settings.HEIGHT * 0.1F, Settings.HEIGHT * 0.15F, ___statsTimer * 1.0F / 0.5F);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(Interpolation.PowIn.class,
                        "apply");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), (Matcher) methodCallMatcher);
            }
        }
    }
}
