package Euler566;

import java.math.BigInteger;

public class FractionUtils {
    public static double maxPortion = 360.0;

    public static Fraction add(Fraction a, Fraction b) {
        if (a.getDenominator().equals(b.getDenominator())) {
            Long newNum = a.getNumerator() + b.getNumerator();
            return new Fraction(newNum, b.getDenominator());
        } else {
            BigInteger aNum = new BigInteger(a.getNumerator().toString());
            BigInteger aDenom = new BigInteger(a.getDenominator().toString());
            BigInteger bNum = new BigInteger(b.getNumerator().toString());
            BigInteger bDenom = new BigInteger(b.getDenominator().toString());
            BigInteger newNum = aNum.multiply(bDenom).add(bNum.multiply(aDenom));
            BigInteger newDenom = aDenom.multiply(bDenom);
            BigInteger divisor = newNum.gcd(newDenom).abs();
            return new Fraction(
                    newNum.divide(divisor).longValueExact(),
                    newDenom.divide(divisor).longValueExact());
        }
    }

    public static Fraction subtract(Fraction a, Fraction b) { // a always has to be bigger
        if (a.getDenominator().equals(b.getDenominator())) {
            Long newNum = a.getNumerator() - b.getNumerator();
            return new Fraction(newNum, b.getDenominator());
        } else {
            BigInteger aNum = new BigInteger(a.getNumerator().toString());
            BigInteger aDenom = new BigInteger(a.getDenominator().toString());
            BigInteger bNum = new BigInteger(b.getNumerator().toString());
            BigInteger bDenom = new BigInteger(b.getDenominator().toString());
            BigInteger newNum = aNum.multiply(bDenom).subtract(bNum.multiply(aDenom));
            BigInteger newDenom = aDenom.multiply(bDenom);
            BigInteger divisor = newNum.gcd(newDenom).abs();
            return new Fraction(
                    newNum.divide(divisor).longValueExact(),
                    newDenom.divide(divisor).longValueExact());
        }
    }

    public static Fraction convertRadical(int c, Fraction corner) { //Recursive
        double sqrtc = Math.sqrt(c);

        int d = (int)sqrtc;
        Long topValue = new Long (c - (int)Math.pow(d,2));
        Fraction bottom = add(new Fraction(new Long(2*d),1L),corner);
        Fraction ratioDecimalDigits = new Fraction(topValue * bottom.getDenominator(), bottom.getNumerator());
        Fraction fractionD = new Fraction(new Long(d),1L);
        Fraction convertedRadical = add(fractionD, ratioDecimalDigits);
        if(sqrtc == convertedRadical.getDoubleValue()){
            return convertedRadical;
        }
        return convertRadical(c, ratioDecimalDigits);
    }

    public static Fraction convertRadical(int c, int iterations) { //Iterating
        int counter = 0;
        int d = (int)Math.sqrt(c);
        Fraction corner = new Fraction(0L,1L);
        Long topValue = new Long (c - (int)Math.pow(d,2));

        for(int i = 0; i < iterations; i++) {
            Fraction bottom = add(new Fraction(new Long(2*d),1L),corner);
            corner = new Fraction(topValue * bottom.getDenominator(), bottom.getNumerator());
        }

        Fraction fractionD = new Fraction(new Long(d),1L);
        Fraction convertedRadical = add(fractionD, corner);
        return convertedRadical;
    }
}
