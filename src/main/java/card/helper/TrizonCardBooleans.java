package card.helper;

import fusable.Fusable;

public class TrizonCardBooleans implements Fusable<TrizonCardBooleans> {
    public boolean canUse = true;
    public boolean yandere = false;
    public boolean fire = false;
    public boolean ghost = false;
    public boolean goldfish = false;
    public boolean rain = false;

    public TrizonCardBooleans() {
    }

    @Override
    public boolean fuse(TrizonCardBooleans other) {
        this.canUse = this.canUse && other.canUse;
        this.yandere = this.yandere || other.yandere;
        this.fire = this.fire || other.fire;
        this.ghost = this.ghost || other.ghost;
        this.goldfish = this.goldfish || other.goldfish;
        this.rain = this.rain || other.rain;

        return true;
    }

    public TrizonCardBooleans clone() {
        TrizonCardBooleans copy = new TrizonCardBooleans();
        copy.canUse = this.canUse;
        copy.yandere = this.yandere;
        copy.fire = this.fire;
        copy.ghost = this.ghost;
        copy.goldfish = this.goldfish;
        copy.rain = this.rain;
        return copy;
    }
}