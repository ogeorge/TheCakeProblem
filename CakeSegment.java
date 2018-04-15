package Euler566;

import static Euler566.FractionUtils.maxPortion;

public class CakeSegment {
    private boolean frosting;
    private Fraction beginningPosition;
    private Fraction endingPosition;

    public CakeSegment(boolean frosting, Fraction beginningPosition, Fraction endingPosition) {
        this.frosting = frosting;

        this.beginningPosition = beginningPosition.getDoubleValue() >= maxPortion
                ? FractionUtils.subtract(beginningPosition, new Fraction(360L,1L))
                : beginningPosition;
        this.endingPosition = endingPosition.getDoubleValue() >= maxPortion
                ? FractionUtils.subtract(endingPosition, new Fraction(360L,1L))
                :endingPosition;
    }

    public boolean isFrosting() {
        return frosting;
    }

    public void setFrosting(boolean frosting) {
        this.frosting = frosting;
    }

    public Fraction getBeginningPosition() {
        return beginningPosition;
    }

    public void setBeginningPosition(Fraction beginningPosition) {
        this.beginningPosition = beginningPosition.getDoubleValue() >= maxPortion
                ? FractionUtils.subtract(beginningPosition, new Fraction(360L,1L))
                : beginningPosition;
    }

    public Fraction getEndingPosition() {
        return endingPosition;
    }

    public void setEndingPosition(Fraction endingPosition) {
        this.endingPosition = endingPosition.getDoubleValue() >= maxPortion
                ? FractionUtils.subtract(endingPosition, new Fraction(360L,1L))
                :endingPosition;
    }

    public Fraction findAngleDistance() {
        if(endingPosition.getDoubleValue() < beginningPosition.getDoubleValue()) {
            Fraction sumTerm1 = FractionUtils.subtract(new Fraction(360L, 1L),beginningPosition);
            return FractionUtils.add(sumTerm1, endingPosition);
        } else if(endingPosition.getDoubleValue() > beginningPosition.getDoubleValue()) {
            return FractionUtils.subtract(endingPosition, beginningPosition);
        } else {
            return new Fraction(0L, 1L);
        }
    }
}
