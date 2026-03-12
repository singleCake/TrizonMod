package power.factory;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import localization.TrizonFactoryStrings;

public class TrizonSimpleTemplatePowerFactory extends AbstractTrizonPowerFactory {
    private String ID;

    public TrizonSimpleTemplatePowerFactory(Class<? extends AbstractPower> powerClass, int amount) {
        this.ID = powerClass.getSimpleName() + "Factory";
        this.amount = amount;
    }

    private TrizonSimpleTemplatePowerFactory(String ID, int amount) {
        this.ID = ID;
        this.amount = amount;
    }

    @Override
    public AbstractPower create() {
        if ("TrizonCremationPowerFactory".equals(this.ID)) {
            return new power.TrizonCremationPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonMaidPowerFactory".equals(this.ID)) {
            return new power.TrizonMaidPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonOrchardistPowerFactory".equals(this.ID)) {
            return new power.TrizonOrchardistPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonPinataPowerFactory".equals(this.ID)) {
            return new power.TrizonPinataPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonScavengerPowerFactory".equals(this.ID)) {
            return new power.TrizonScavengerPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonSeaStarPowerFactory".equals(this.ID)) {
            return new power.TrizonSeaStarPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonRecyclePowerFactory".equals(this.ID)) {
            return new power.TrizonRecyclePower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonFighterPowerFactory".equals(this.ID)) {
            return new power.TrizonFighterPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonMeatWallPowerFactory".equals(this.ID)) {
            return new power.TrizonMeatWallPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonBonfirePowerFactory".equals(this.ID)) {
            return new power.TrizonBonfirePower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonTwinsPowerFactory".equals(this.ID)) {
            return new power.TrizonTwinsPower(AbstractDungeon.player, this.amount);
        }
        if ("TrizonIceAmberPowerFactory".equals(this.ID)) {
            return new power.TrizonIceAmberPower(AbstractDungeon.player, this.amount);
        }
        return null;
    }

    @Override
    public String rawDescription() {
        return String.format(TrizonFactoryStrings.getDescription(this.ID), amount);
    }

    @Override
    public AbstractTrizonPowerFactory clone() {
        return new TrizonSimpleTemplatePowerFactory(this.ID, this.amount);
    }

    @Override
    public boolean fuse(AbstractTrizonPowerFactory other) {
        if (other instanceof TrizonSimpleTemplatePowerFactory) {
            TrizonSimpleTemplatePowerFactory typedOther = (TrizonSimpleTemplatePowerFactory) other;
            if (this.ID != null && this.ID.equals(typedOther.ID)) {
                this.amount += other.amount;
                return true;
            }
        }
        return false;
    }

    public String getID() {
        return ID;
    }

    public void setID(String type) {
        this.ID = type;
    }
}
