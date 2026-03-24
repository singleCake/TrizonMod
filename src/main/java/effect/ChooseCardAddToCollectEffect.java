package effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import patch.CollectPatch;
import ui.collect.ChooseCollectScreen;

public class ChooseCardAddToCollectEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack
            .getUIString("Trizon:Collect");

    public static final String[] TEXT = uiStrings.TEXT;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    private boolean imgSelected = false;

    public ChooseCardAddToCollectEffect() {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        this.duration = 1.0F;
        this.screenColor.a = 0.0F;

        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 1, TEXT[0], false, false, true, true);
    }

    public void update() {
        if (!imgSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            imgSelected = true;
            AbstractCard selectedCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            ChooseCollectScreen.Inst.addToCollection(selectedCard.makeStatEquivalentCopy());
            CollectPatch.hasChosenCollect = true;
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(selectedCard.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID)
            AbstractDungeon.gridSelectScreen.render(sb);
    }

    public void dispose() {
    }
}
