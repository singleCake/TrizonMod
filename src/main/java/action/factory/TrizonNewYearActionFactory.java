package action.factory;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class TrizonNewYearActionFactory extends AbstractTrizonFactory {
    private static final String DESCRIPTION = AbstractTrizonFactory.getDescription(TrizonNewYearActionFactory.class);

    public TrizonNewYearActionFactory() {
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonNewYearAction();
    }

    @Override
    public AbstractTrizonFactory clone() {
        return new TrizonNewYearActionFactory();
    }

    @Override
    public String rawDescription() {
        return DESCRIPTION;
    }

    private static class TrizonNewYearAction extends AbstractGameAction {
        @Override
        public void update() {
            this.addToTop(new PressEndTurnButtonAction());
            this.addToTop(new SkipEnemiesTurnAction());
            this.addToTop(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
            this.isDone = true;
        }
    }
}
