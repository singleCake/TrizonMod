package ui.singleCardView;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ChangeImgButton {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Trizon:ChangeImgButton");

    public static final String[] TEXT = uiStrings.TEXT;

    private float current_x;

    private float current_y;

    private Color textColor = Color.WHITE.cpy();

    private Color btnColor = Color.WHITE.cpy();

    public boolean screenDisabled = false;

    private static final float HITBOX_W = 260.0F * Settings.scale;

    private static final float HITBOX_H = 80.0F * Settings.scale;

    public Hitbox hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);

    private float controllerImgTextWidth = 0.0F;

    public ChangeImgButton(float x, float y) {
        this.hb.move(x, y);
        this.current_x = x;
        this.current_y = y;
    }

    public void update() {
        this.hb.update();
        if (this.hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if (this.hb.clicked && !this.screenDisabled) {
            this.hb.clicked = false;
        }
        this.screenDisabled = false;
        this.textColor.a = MathHelper.fadeLerpSnap(this.textColor.a, 1.0F);
        this.btnColor.a = this.textColor.a;
    }

    public void render(SpriteBatch sb) {
        renderButton(sb);
        if (FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT[0], 9999.0F, 0.0F) > 200.0F * Settings.scale) {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[0], this.current_x, this.current_y,
                    this.textColor, 0.8F);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[0], this.current_x, this.current_y,
                    this.textColor);
        }
    }

    private void renderButton(SpriteBatch sb) {
        sb.setColor(this.btnColor);
        sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, this.current_x - 256.0F, this.current_y - 128.0F, 256.0F, 128.0F,
                512.0F,
                256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        if (this.hb.hovered && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, this.current_x - 256.0F, this.current_y - 128.0F, 256.0F,
                    128.0F,
                    512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
        if (Settings.isControllerMode) {
            if (this.controllerImgTextWidth == 0.0F)
                this.controllerImgTextWidth = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT[0], 99999.0F,
                        0.0F) / 2.0F;
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.cancel
                    .getKeyImg(), this.current_x - 32.0F - this.controllerImgTextWidth - 38.0F * Settings.scale,
                    this.current_y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64,
                    64,
                    false, false);
        }
        this.hb.render(sb);
    }
}
