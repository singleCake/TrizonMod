package effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class CampfireFuseEffect extends AbstractGameEffect {

  private boolean hasRecalled = false;

  private Color screenColor = AbstractDungeon.fadeColor.cpy();

  private AbstractCard card1, card2, result;

  public CampfireFuseEffect(AbstractCard card1, AbstractCard card2, AbstractCard result) {
    this.duration = 2.0F;
    this.screenColor.a = 0.0F;
    this.card1 = card1;
    this.card2 = card2;
    this.result = result;
    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
  }

  public void update() {
    this.duration -= Gdx.graphics.getDeltaTime();
    updateBlackScreenColor();
    if (this.duration < 1.0F && !this.hasRecalled) {
      this.hasRecalled = true;
      (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
      AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
      AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(result.makeStatEquivalentCopy()));
      AbstractDungeon.player.masterDeck.addToTop(result);
    }
    if (this.duration < 0.0F) {
      this.isDone = true;
      ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
      (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
    }
  }

  private void updateBlackScreenColor() {
    if (this.duration > 1.5F) {
      this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.5F) * 2.0F);
    } else if (this.duration < 1.0F) {
      this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
    } else {
      this.screenColor.a = 1.0F;
    }
  }

  public void render(SpriteBatch sb) {
    sb.setColor(this.screenColor);
    sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
  }

  public void dispose() {
  }
}
