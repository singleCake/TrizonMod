package card.helper;

import fusable.Fusable;

public class TrizonCardBooleans implements Fusable<TrizonCardBooleans> {
    public boolean canUse = true;
    public boolean yandere = false;
    public boolean blaze = false;
    public boolean ghost = false;
    public boolean vampire = false;
    public boolean goldfish = false;
    public boolean rain = false;

    public TrizonCardBooleans() {
    }

    @Override
    public void fuse(TrizonCardBooleans other) {
        this.canUse = this.canUse && other.canUse;
        this.yandere = this.yandere || other.yandere;
        this.blaze = this.blaze || other.blaze;
        this.ghost = this.ghost || other.ghost;
        this.vampire = this.vampire || other.vampire;
        this.goldfish = this.goldfish || other.goldfish;
        this.rain = this.rain || other.rain;
    }
}