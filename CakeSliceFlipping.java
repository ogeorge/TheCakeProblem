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
            int startingCutIndex = findKnifePosCakeIndex(knifePosition);
            int endingCutIndex = findKnifePosCakeIndex(newKnifePosition);
//            CakeSegment startingCut = slices.get(startingCutIndex);
            CakeSegment endingCut = slices.get(endingCutIndex);
            List<CakeSegment> newSlices = new ArrayList<>();
            List<Integer> removeTheseCakeSegments = new ArrayList<>();
            int inBetweenStart = startingCutIndex + 1;
            int inBetweenEnd = endingCutIndex - 1;
            int maxIndex = slices.size() - 1;
            if (inBetweenStart > maxIndex) {
                inBetweenStart = 0;
            }
            if(inBetweenEnd < 0) {
                inBetweenEnd = maxIndex;
            }
            int iterTotal = inBetweenEnd;
            if(inBetweenStart > inBetweenEnd) {
                iterTotal = slices.size() +inBetweenEnd;
            }

            //adjust both cake slices starting and ending and add them to newSlices in order startingCut first and then endingCut
            CakeSegment newCakeSegment = new CakeSegment(endingCut.isFrosting(),endingCut.getBeginningPosition(),newKnifePosition);
            endingCut.setBeginningPosition(newKnifePosition);
            Fraction totalAngleDistance = new Fraction(0L,1L);

            for(int i = inBetweenStart; i <= iterTotal; i++) {
                int index = i;
                if(i >= slices.size()) {
                    index = i - slices.size();
                }
                CakeSegment target = slices.get(index);
                totalAngleDistance = FractionUtils.add(totalAngleDistance, target.findAngleDistance());
                newSlices.add(target);
                removeTheseCakeSegments.add(index);
            }

            //Check if newSlices total angle distance is equal to the currentCut
            //If they are equal dont change newSlices if they are not clear the newSlices list and just add the newSegment
            totalAngleDistance = FractionUtils.add(totalAngleDistance, newCakeSegment.findAngleDistance());
            if(totalAngleDistance.getDoubleValue() != currentCut.getDoubleValue()) {
                newSlices.clear();
                removeTheseCakeSegments.clear();
            }
            newSlices.add(newCakeSegment);

            for(int j = newSlices.size() - 1; j >= 0; j--){
                CakeSegment replacement = newSlices.get(j);
                replacement.setFrosting(!replacement.isFrosting());
                Fraction angleDistance = replacement.findAngleDistance();
                if(removeTheseCakeSegments.size() > 0) {
                    Integer slicesSpot = removeTheseCakeSegments.remove(0);
                    replacement.setBeginningPosition(slices.get(slicesSpot-1 < 0 ? maxIndex : slicesSpot-1).getEndingPosition());
                    replacement.setEndingPosition(FractionUtils.add(replacement.getBeginningPosition(),angleDistance));
                    slices.set(slicesSpot,  replacement);
                } else {
                    replacement.setBeginningPosition(slices.get(inBetweenEnd).getEndingPosition());
                    replacement.setEndingPosition(newKnifePosition);
                    slices.add(endingCutIndex,replacement);
                    if(replacement.findAngleDistance().getDoubleValue() != angleDistance.getDoubleValue()) {
                        System.out.println("------------------HUGE PROBLEM OVER HERE, YOU NEED TO LOOK AT THIS----------------");
                    }
                }
            }
            currentCut = cuts.get((int)(flips % 3L)); //------------THis might mess everything up
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

//        boolean result = false;
//        if(flips != 0L) {
//            for(int i = slices.size() - 1; i >= 0; i--) {
//                CakeSegment cS = slices.get(i);
//                if(cS.getBeginningPosition().getDoubleValue() == cS.getEndingPosition().getDoubleValue()) {
//                    slices.remove(i);
//                } else if(cS.isFrosting()) {
//                    result = true;
//                }
//            }
//        } else {
//            result = true;
//        }
//        return result;
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