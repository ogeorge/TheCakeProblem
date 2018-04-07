package Euler566;

import java.math.BigInteger;

public class Fraction {
    private Long numerator;
    private Long denominator;

    public Fraction() {
    }

    public Fraction(Long numerator, Long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        if(this.denominator < 0L) {
            this.denominator = this.denominator * -1;
            this.numerator = this.numerator * -1;
        }
        reduce();
    }

    public Fraction(Long numerator, Fraction denominator) { //Right now this will just throw an exception if it cant convert BigInteger into Long
        BigInteger num = new BigInteger(numerator.toString());
        BigInteger denomsDenominator = new BigInteger(denominator.getDenominator().toString());
        this.numerator = num.multiply(denomsDenominator).longValueExact(); //I might want to write a function of reduce that will take BigIntegers and reduce them before I set numerator and denominator
        this.denominator = denominator.getNumerator();
        if(this.denominator < 0L) {
            this.denominator = this.denominator * -1;
            this.numerator = this.numerator * -1;
        }
        reduce();
    }

    public Fraction(Fraction numerator, Long denominator) {
        this(numerator.getNumerator(), numerator.getDenominator() * denominator);
    }

    public Fraction(Fraction numerator, Fraction denominator) {
        this(numerator.getNumerator() * denominator.getDenominator(), numerator.getDenominator() * denominator.getNumerator());
    }

    public void reduce() {
        if(numerator == null || denominator == null)
            return;

        Long gcd = findGCD(numerator, denominator);
        if(gcd < 0L)
            gcd = gcd * -1;
        numerator = numerator/gcd;
        denominator = denominator/gcd;
    }

    private Long findGCD(Long a, Long b) {
        if (b==0) return a;
        return findGCD(b,a%b);
    }

    public Long getNumerator() {
        return numerator;
    }

    public void setNumerator(Long numerator) {
        this.numerator = numerator;
        reduce();
    }

    public Long getDenominator() {
        return denominator;
    }

    public void setDenominator(Long denominator) {
        this.denominator = denominator;
        reduce();
    }

    public double getDoubleValue() {
        return (double)numerator/denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;
        fraction.reduce();

        return fraction.getNumerator().equals(this.numerator) && fraction.getDenominator().equals(this.denominator);
    }
}
