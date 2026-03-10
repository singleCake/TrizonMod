package action;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonFreezeAllEnemyAction extends AbstractTrizonAction {
    public TrizonFreezeAllEnemyAction() {
    }

    @Override
    public void update() {
        for (int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i--) {
            if (!((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isDying &&
                    ((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).currentHealth > 0 &&
                    !((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isEscaping) {
                this.addToTop(new TrizonFreezeEnemyAction((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)));
            }
        }

        this.isDone = true;
    }
    
}
