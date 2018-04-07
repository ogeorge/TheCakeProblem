package Euler566;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            int startingCutIndex = findKnifePosCakeIndex(knifePosition);//I possible dont even need this
            int endingCutIndex = findKnifePosCakeIndex(newKnifePosition);
            CakeSegment endingCut = slices.get(endingCutIndex);
            int inBetweenStart = startingCutIndex + 1;
            int maxIndex = slices.size() - 1;
            if (inBetweenStart > maxIndex) {
                inBetweenStart = 0;
            }

            CakeSegment newCakeSegment = new CakeSegment(endingCut.isFrosting(),endingCut.getBeginningPosition(),newKnifePosition);
            endingCut.setBeginningPosition(newKnifePosition);
            slices.add(endingCutIndex, newCakeSegment);

            int iterTotal = endingCutIndex;
            if(inBetweenStart > endingCutIndex) {
                inBetweenStart++;
                iterTotal = slices.size() + endingCutIndex;
            }

            iterTotal = inBetweenStart + ((iterTotal - inBetweenStart)/2);

            Fraction frontEndingPosition = newKnifePosition;
            Fraction backBeginningPosition = knifePosition;
            for(int i = inBetweenStart; i <= iterTotal; i++) {
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
        }
        return flips;
    }

    private boolean checkTheFrosting(){
        //Pulls out the 0 slices
        slices = slices.stream()
                .filter(cS -> (cS.getBeginningPosition().getDoubleValue() != cS.getEndingPosition().getDoubleValue() || flips == 0L))
                .collect(Collectors.toList());
        //Checking if all the frosting is on top
        return !slices.stream().allMatch(cakeSegment -> cakeSegment.isFrosting() == false && flips != 0L);
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
        return -1;
    }
}