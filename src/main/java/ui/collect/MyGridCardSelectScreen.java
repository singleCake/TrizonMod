package ui.collect;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;

import basemod.ReflectionHacks;

public class MyGridCardSelectScreen implements ScrollBarListener {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("GridCardSelectScreen");

    public static final String[] TEXT = uiStrings.TEXT;

	public final ArrayList<AbstractCard> selectedCards = new ArrayList<>();

	private static final int CARDS_PER_LINE = 5;

	private static final float SPACE_X = 270.0F * Settings.scale;

	private static final float SPACE_Y = 380.0F * Settings.scale;

	private static final float CARD_SCALE = 0.75F;

	private static final float HOVERED_CARD_SCALE = 1.2F;

	private static final float HOVERED_CARD_LIFT_Y = 30.0F * Settings.scale;

	private static final float TOP_PAD_Y = 300.0F * Settings.scale;

	private static final float SCREEN_PAD_Y = 220.0F * Settings.scale;

	private CardGroup sourceGroup = null;

	private AbstractCard hoveredCard;

	private String tipMsg = "";

	private boolean isOpen = false;

	private float currentDiffY = 0.0F;

	private float targetDiffY = 0.0F;

	private final CheckCollectButton backButton = new CheckCollectButton(150.0F * Settings.scale, 300.0F * Settings.scale,
			TEXT[1]);

	public void open(CardGroup group, int numCards, boolean forUpgrade, String tipMsg) {
		this.selectedCards.clear();
		this.hoveredCard = null;
		this.tipMsg = tipMsg;
		this.currentDiffY = 0.0F;
		this.targetDiffY = 0.0F;
		this.sourceGroup = group;
		this.isOpen = true;
		resetCardTransform();
	}

	public void update() {
		if (!this.isOpen) {
			return;
		}
		if (isCardPopupOpen()) {
			this.hoveredCard = null;
			placeCards();
			for (AbstractCard c : this.sourceGroup.group) {
				c.targetDrawScale = CARD_SCALE;
				c.drawScale = MathHelper.cardScaleLerpSnap(c.drawScale, c.targetDrawScale);
			}
			return;
		}
		this.backButton.update();
		updateScroll();
		updateCards();
		updateInput();
	}

	public void render(SpriteBatch sb) {
		if (!this.isOpen) {
			return;
		}

		sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
		sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);

		for (AbstractCard c : this.sourceGroup.group) {
			c.render(sb);
		}

		if (this.hoveredCard != null) {
			this.hoveredCard.renderCardTip(sb);
		}

		FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, this.tipMsg, Settings.WIDTH / 2.0F,
				Settings.HEIGHT - 80.0F * Settings.scale, Settings.GOLD_COLOR);
		this.backButton.render(sb);
	}

	private void updateCards() {
		this.hoveredCard = null;
		placeCards();
		for (AbstractCard c : this.sourceGroup.group) {
			c.update();
            c.updateHoverLogic();
			if (c.hb.hovered) {
				this.hoveredCard = c;
			}
		}
		if (this.hoveredCard != null) {
			this.hoveredCard.targetDrawScale = HOVERED_CARD_SCALE;
			this.hoveredCard.target_y += HOVERED_CARD_LIFT_Y;
		}
		for (AbstractCard c : this.sourceGroup.group) {
			c.drawScale = MathHelper.cardScaleLerpSnap(c.drawScale, c.targetDrawScale);
		}
	}

	private void updateInput() {
		if (InputHelper.pressedEscape) {
			InputHelper.pressedEscape = false;
			close();
			return;
		}
		if (CInputActionSet.cancel.isJustPressed()) {
			CardCrawlGame.sound.play("UI_CLICK_1");
			close();
			return;
		}
		if (InputHelper.justClickedLeft && this.backButton.hb.hovered) {
			CardCrawlGame.sound.play("UI_CLICK_1");
			close();
			return;
		}
		if (InputHelper.justClickedLeft && this.hoveredCard != null) {
			CardCrawlGame.sound.play("CARD_SELECT");
			CardCrawlGame.cardPopup.open(this.hoveredCard, this.sourceGroup);
			return;
		}
	}

	private void updateScroll() {
		if (InputHelper.scrolledDown) {
			this.targetDiffY += SPACE_Y / 2.0F;
		} else if (InputHelper.scrolledUp) {
			this.targetDiffY -= SPACE_Y / 2.0F;
		}
		float upperBound = getScrollUpperBound();
		if (this.targetDiffY < 0.0F) {
			this.targetDiffY = 0.0F;
		} else if (this.targetDiffY > upperBound) {
			this.targetDiffY = upperBound;
		}
		this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.targetDiffY);
	}

	private void placeCards() {
		for (int i = 0; i < this.sourceGroup.size(); i++) {
			AbstractCard c = this.sourceGroup.group.get(i);
			int col = i % CARDS_PER_LINE;
			int row = i / CARDS_PER_LINE;
			float lineWidth = (CARDS_PER_LINE - 1) * SPACE_X;
			c.target_x = Settings.WIDTH / 2.0F - lineWidth / 2.0F + col * SPACE_X;
			c.target_y = Settings.HEIGHT - TOP_PAD_Y - row * SPACE_Y + this.currentDiffY;
			c.targetDrawScale = CARD_SCALE;
		}
	}

	private float getScrollUpperBound() {
		int lines = (int) Math.ceil((float) this.sourceGroup.size() / CARDS_PER_LINE);
		if (lines <= 1) {
			return 0.0F;
		}
		float firstRowCenterY = Settings.HEIGHT - TOP_PAD_Y;
		float lastRowCenterYNoScroll = firstRowCenterY - (lines - 1) * SPACE_Y;
		float halfCardHeight = AbstractCard.IMG_HEIGHT * Settings.scale * CARD_SCALE / 2.0F;
		float minVisibleLastRowCenterY = SCREEN_PAD_Y + halfCardHeight;
		return Math.max(0.0F, minVisibleLastRowCenterY - lastRowCenterYNoScroll);
	}

	private void resetCardTransform() {
		for (AbstractCard c : this.sourceGroup.group) {
			c.drawScale = CARD_SCALE;
			c.targetDrawScale = CARD_SCALE;
			c.current_x = Settings.WIDTH / 2.0F;
			c.current_y = Settings.HEIGHT / 2.0F;
		}
	}

	public void close() {
		this.isOpen = false;
		this.hoveredCard = null;
	}

	public boolean isOpen() {
		return this.isOpen;
	}

	private boolean isCardPopupOpen() {
		try {
			return (Boolean) ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, CardCrawlGame.cardPopup.getClass(),
					"isOpen");
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void scrolledUsingBar(float newPercent) {
	}

}
