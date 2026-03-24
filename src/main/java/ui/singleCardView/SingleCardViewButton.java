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

public class SingleCardViewButton {
    private static final String CONFIRM_TEXT = CardCrawlGame.languagePack.getUIString("Trizon:SingleCardViewButton").TEXT[3];

    private float current_x;

    private float current_y;

    private Color textColor = Color.WHITE.cpy();

    private Color btnColor = Color.WHITE.cpy();

    public boolean screenDisabled = false;

    private static final float HITBOX_W = 260.0F * Settings.scale;

    private static final float HITBOX_H = 80.0F * Settings.scale;

    public Hitbox hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);

    private float controllerImgTextWidth = 0.0F;

    private String label;

    private boolean needConfirm = false;

    public boolean confirming = false;

    public SingleCardViewButton(float x, float y, String label) {
        this(x, y, label, false);
    }

    public SingleCardViewButton(float x, float y, String label, boolean needConfirm) {
        this.hb.move(x, y);
        this.current_x = x;
        this.current_y = y;
        this.label = label;
        this.needConfirm = needConfirm;
    }

    public void update() {
        this.hb.update();
        if (this.hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if (this.hb.clicked && !this.screenDisabled) {
            this.hb.clicked = false;
            if (this.needConfirm) {
                if (!this.confirming) {
                    this.confirming = true;
                    this.textColor = Color.RED.cpy();
                    this.label = CONFIRM_TEXT;
                    return;
                }
            }
        }
        this.screenDisabled = false;
        this.textColor.a = MathHelper.fadeLerpSnap(this.textColor.a, 1.0F);
        this.btnColor.a = this.textColor.a;
    }

    public void render(SpriteBatch sb) {
        renderButton(sb);
        if (FontHelper.getSmartWidth(FontHelper.buttonLabelFont, this.label, 9999.0F, 0.0F) > 200.0F * Settings.scale) {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.label, this.current_x, this.current_y,
                    this.textColor, 0.8F);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.label, this.current_x, this.current_y,
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
                this.controllerImgTextWidth = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, this.label, 99999.0F,
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
