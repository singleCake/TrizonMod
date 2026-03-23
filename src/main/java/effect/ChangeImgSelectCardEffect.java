package effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import card.TrizonCard;
import card.TrizonFusedCard;
import patch.ChangeImgPatch.GridCardSelectScreenField;

public class ChangeImgSelectCardEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack
            .getUIString("Trizon:ChangeImgSelectCardEffect");

    public static final String[] TEXT = uiStrings.TEXT;

    private Color screenColor = AbstractDungeon.fadeColor.cpy();

    private TrizonFusedCard card;

    private boolean imgSelected = false;

    public ChangeImgSelectCardEffect(TrizonFusedCard card) {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        this.card = card;
        this.duration = 1.0F;
        this.screenColor.a = 0.0F;

        CardGroup imgCardGroup = getImgCardGroup();
        AbstractDungeon.gridSelectScreen.open(imgCardGroup, 1, TEXT[0], true, false, false, false);
        GridCardSelectScreenField.forChangeImg.set(AbstractDungeon.gridSelectScreen, true);
    }

    public void update() {
        if (!imgSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            imgSelected = true;
            AbstractCard selectedCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            card.textureImg = ((TrizonCard) selectedCard).textureImg;
            card.loadCardImage(card.textureImg);
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndDontObtainEffect(card.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }
    }

    private CardGroup getImgCardGroup() {
        CardGroup imgCardGroup = new CardGroup(CardGroupType.UNSPECIFIED);
        for (String key : card.fusionData.keySet()) {
            AbstractCard c = CardLibrary.getCard(key);
            if (c.type == card.type && c instanceof TrizonCard) {
                imgCardGroup.addToBottom(c);
            }
        }
        return imgCardGroup;
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
