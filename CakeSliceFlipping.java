package Euler566;

import java.util.ArrayList;
import java.util.List;

public class CakeSliceFlipping {
    private List<CakeSegment> slices = new ArrayList<>();
    private Long flips = 0L;

    public CakeSliceFlipping() {
    }

    public Long countFlips(int a, int b, int c) {
        Fraction segmentA = new Fraction(360L,new Long (a));
        Fraction segmentB = new Fraction(360L,new Long (b));
        Fraction segmentC = new Fraction(360L, FractionUtils.convertRadical(c, new Fraction(0L,1L)));
        List<Fraction> cuts = new ArrayList<>(); cuts.add(segmentA); cuts.add(segmentB); cuts.add(segmentC);
        slices.add(new CakeSegment(false,
                new Fraction(0L, 1L),
                new Fraction(360L, 1L)));
        Fraction knifePosition = new Fraction(0L,1L);
        Fraction currentCut = cuts.get(0);

        while(checkTheFrosting()) {
            flips++;
            Fraction newKnifePosition = FractionUtils.add(knifePosition, currentCut);
            if(newKnifePosition.getDoubleValue() >= 360.0) {
                newKnifePosition = FractionUtils.subtract(newKnifePosition, new Fraction(360L,1L));
            }
            int startingCutIndex = findKnifePosCakeIndex(knifePosition); // I can probably optimize these two findKnifePosition function calls together to just iterate through it once
            int endingCutIndex = findKnifePosCakeIndex(newKnifePosition);
            CakeSegment startingCut = slices.get(startingCutIndex);
            CakeSegment endingCut = slices.get(endingCutIndex);

            CakeSegment newEndPiece = new CakeSegment(endingCut.isFrosting(),endingCut.getBeginningPosition(),newKnifePosition);
            CakeSegment newStartPiece = new CakeSegment(startingCut.isFrosting(), knifePosition, startingCut.getEndingPosition());

            int iterTotal;
            if(startingCutIndex < endingCutIndex) {
                endingCut.setBeginningPosition(newKnifePosition);
                startingCut.setEndingPosition(knifePosition);
                startingCutIndex++;
                slices.add(endingCutIndex, newEndPiece);
                slices.add(startingCutIndex, newStartPiece);
                endingCutIndex++;
                iterTotal = endingCutIndex;
            } else if(startingCutIndex > endingCutIndex) {
                endingCut.setBeginningPosition(newKnifePosition);
                startingCut.setEndingPosition(knifePosition);
                slices.add(endingCutIndex, newEndPiece);
                startingCutIndex = startingCutIndex + 2;
                slices.add(startingCutIndex, newStartPiece);
                iterTotal = slices.size() + endingCutIndex;
            } else {
                CakeSegment leftExtra = new CakeSegment(startingCut.isFrosting(), knifePosition, startingCut.getEndingPosition());
                if(leftExtra.findAngleDistance().getDoubleValue() >= currentCut.getDoubleValue()) {
                    CakeSegment newFirstPiece = new CakeSegment(startingCut.isFrosting(), startingCut.getBeginningPosition(), knifePosition);
                    CakeSegment middlePiece = new CakeSegment(startingCut.isFrosting(), knifePosition, newKnifePosition);
                    startingCut.setBeginningPosition(newKnifePosition);
                    slices.add(startingCutIndex, middlePiece);
                    slices.add(startingCutIndex, newFirstPiece);
                    startingCutIndex++;
                    endingCutIndex = startingCutIndex;
                    iterTotal = endingCutIndex;
                } else {
                    CakeSegment rightExtra = new CakeSegment(startingCut.isFrosting(), startingCut.getBeginningPosition(), newKnifePosition);
                    startingCut.setBeginningPosition(newKnifePosition);
                    startingCut.setEndingPosition(knifePosition);
                    slices.add(endingCutIndex, rightExtra);
                    startingCutIndex = startingCutIndex + 2;
                    slices.add(startingCutIndex, leftExtra);
                    iterTotal = slices.size() + endingCutIndex;
                }
            }

            iterTotal = startingCutIndex + ((iterTotal - startingCutIndex)/2);

            Fraction frontEndingPosition = newKnifePosition;
            Fraction backBeginningPosition = knifePosition;
            for(int i = startingCutIndex; i <= iterTotal; i++) {
                int index = i;
                if(i >= slices.size()) {
                    index = i - slices.size();
                }
                if(endingCutIndex < 0) {
                    endingCutIndex = slices.size() - 1;
                }

                if(index == endingCutIndex) {
                    CakeSegment middleSlice = slices.get(index);
                    middleSlice.setFrosting(!middleSlice.isFrosting());
                    middleSlice.setBeginningPosition(backBeginningPosition);
                    middleSlice.setEndingPosition(frontEndingPosition); //Could check if the distance between these two is the same as middleSlice.findAngleDIstance()
                } else {
                    CakeSegment frontSwap = slices.get(index);
                    frontSwap.setFrosting(!frontSwap.isFrosting());
                    Fraction frontDistance = frontSwap.findAngleDistance();
                    CakeSegment backSwap = slices.get(endingCutIndex);
                    backSwap.setFrosting(!backSwap.isFrosting());
                    Fraction backDistance = backSwap.findAngleDistance();

                    frontSwap.setEndingPosition(frontEndingPosition);
                    Fraction newFrontBeginning = FractionUtils.subtract(frontSwap.getEndingPosition(), frontDistance);
                    if(newFrontBeginning.getNumerator() < 0L) {
                        newFrontBeginning = FractionUtils.add(new Fraction(360L,1L), newFrontBeginning);
                    }
                    frontSwap.setBeginningPosition(newFrontBeginning);
                    slices.set(endingCutIndex,frontSwap);

                    backSwap.setBeginningPosition(backBeginningPosition);
                    Fraction newBackEnding = FractionUtils.add(backSwap.getBeginningPosition(), backDistance);
                    if(newBackEnding.getDoubleValue() >= 360.0) {
                        newBackEnding = FractionUtils.subtract(newBackEnding, new Fraction(360L, 1L));
                    }
                    backSwap.setEndingPosition(newBackEnding);
                    slices.set(index,backSwap);

                    endingCutIndex--;
                    frontEndingPosition = frontSwap.getBeginningPosition();
                    backBeginningPosition = backSwap.getEndingPosition();
                }
            }
            currentCut = cuts.get((int)(flips % 3L));
            knifePosition = newKnifePosition;
            cleanUpCake();
        }
        return flips;
    }

    private boolean checkTheFrosting(){
        //Checking if all the frosting is on top
        return !(slices.size() == 1 && !slices.get(0).isFrosting() && flips != 0L);
    }

    private int findKnifePosCakeIndex(Fraction knifePosition) {
        for (int i = 0; i < slices.size(); i++) {
            CakeSegment cakeSegment = slices.get(i);
            double bp = cakeSegment.getBeginningPosition().getDoubleValue();
            double ep = cakeSegment.getEndingPosition().getDoubleValue();
            if(bp > ep || (bp == 0.0 && ep == 0.0 && slices.size() == 1)) {
                if(bp < knifePosition.getDoubleValue() || ep > knifePosition.getDoubleValue()
                        || ep == knifePosition.getDoubleValue()) {
                    return i;
                }
            }

            if ((bp < knifePosition.getDoubleValue()
                    && ep > knifePosition.getDoubleValue()) || (ep == knifePosition.getDoubleValue())) {
                return i;
            }
        }
        System.out.println(flips);
        return -1;
    }

    //Pulls out cake segments where there angle distance is 0 and merges slices together
    private void cleanUpCake() {
        List<CakeSegment> newCake = new ArrayList<>();
        for(int i = 0; i < slices.size(); i++) {
            CakeSegment rightSide = slices.get(i);
            if(rightSide.getBeginningPosition().getDoubleValue() != rightSide.getEndingPosition().getDoubleValue()) {
                if(newCake.isEmpty() || newCake.get(newCake.size()-1).isFrosting() != rightSide.isFrosting()) {
                    newCake.add(rightSide);
                } else {
                    CakeSegment leftSide = newCake.get(newCake.size()-1);
                    leftSide.setEndingPosition(rightSide.getEndingPosition());
                }
            }
        }
        slices = newCake;
    }

    //If newKnifePosition is less then knifePosition then add 360 to newKnifePosition and check if beginningPosition and endingPosition is inbetween those
    //If both are then return true
    //If neither are add 360 to both beginning and ending and then see if they are both inbetween newKnifePosition and knifePosition
    //If they are return true
    //After adding 360 to newKnifePosition and checking if beginning and ending is inbetween if only endingPosition passes then add 360 to beginning position and check that
    //If that passes return true
    //If newKnifePosition is not less than knifePosition then check if both beginningPosition and endingPosition is inbewtween knifePosition and newKnifePosition
    //If they are return true
    //If all of the above evaluation fails then return false

    //Or completely different approach would be:
    //Measure the distance between knifePosition and beginning posistion if that is less than currentCut then its true that knife and new knife is on either side of beginning and ending
    private boolean isInsideCakeSegment(CakeSegment piece, Fraction knifePosition, Fraction newKnifePosition) {
//        if(knifePosition.getDoubleValue() >= 360.0)
//            knifePosition = FractionUtils.subtract(knifePosition, new Fraction(360L,1L));
//
//        return (piece.getBeginningPosition().getDoubleValue() < knifePosition.getDoubleValue()) &&
//                piece.getEndingPosition().getDoubleValue() >= newKnifePosition.getDoubleValue();
//        (knifePosition - piece.getEndingPosition()) > currentCut
//        return piece.getEndingPosition().getDoubleValue() >= knifePosition.getDoubleValue() &&
//                newKnifePosition.getDoubleValue() > piece.getBeginningPosition().getDoubleValue();

        return false;
    }
}