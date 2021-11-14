package at.uibk.dps.dsB.ex0.decoders;

import org.junit.Assert;
import org.junit.Test;

public class MyFirstDecoderTest {
    private static final double delta = 0.0001;
    @Test
    public void calculateNetIncome_Rate1() {
        MyFirstDecoder decoder = new MyFirstDecoder();

        // Lower bound
        double got = decoder.calculateNetIncome(0.0);
        Assert.assertEquals(0.0, got, delta);

        // Upper bound7000
        got = decoder.calculateNetIncome(11000.0);
        Assert.assertEquals(11000.0, got, delta);
    }

    @Test
    public void calculateNetIncome_Rate2() {
        MyFirstDecoder decoder = new MyFirstDecoder();

        // Lower bound
        double got = decoder.calculateNetIncome(11001.0);
        Assert.assertEquals(11000.8, got, delta);

        // Upper bound
        got = decoder.calculateNetIncome(18000.0); // 7000 taxable -> 5600 + 11000 = 16600
        Assert.assertEquals(16600.0, got, delta);
    }

    @Test
    public void calculateNetIncome_Rate3() {
        MyFirstDecoder decoder = new MyFirstDecoder();

        // Lower bound
        double got = decoder.calculateNetIncome(18001.0);
        Assert.assertEquals(16600.65, got, delta);

        // Upper bound
        got = decoder.calculateNetIncome(31000.0); // 13000 taxable -> 8450 + 16600 = 21150
        Assert.assertEquals(25050.0, got, delta);
    }

    @Test
    public void calculateNetIncome_Rate4() {
        MyFirstDecoder decoder = new MyFirstDecoder();

        // Lower bound
        double got = decoder.calculateNetIncome(31001.0);
        Assert.assertEquals(25050.58, got, delta);

        // Upper bound
        got = decoder.calculateNetIncome(60000.0);
        Assert.assertEquals(41870.0, got, delta);
    }

    @Test
    public void calculateNetIncome_Rate5() {
        MyFirstDecoder decoder = new MyFirstDecoder();

        // Lower bound
        double got = decoder.calculateNetIncome(60001.0);
        Assert.assertEquals(41870.52, got, delta);

        // Upper bound
        got = decoder.calculateNetIncome(90000.0);
        Assert.assertEquals(57470.0, got, delta);
    }

    @Test
    public void calculateNetIncome_Rate6() {
        MyFirstDecoder decoder = new MyFirstDecoder();

        // Lower bound
        double got = decoder.calculateNetIncome(90001.0);
        Assert.assertEquals(57470.5, got, delta);

        // Upper bound
        got = decoder.calculateNetIncome(1000000.0);
        Assert.assertEquals(512470.0, got, delta);
    }

    @Test
    public void calculateNetIncome_Rate7() {
        MyFirstDecoder decoder = new MyFirstDecoder();

        // Lower bound
        double got = decoder.calculateNetIncome(1000001.0);
        Assert.assertEquals(512470.45, got, delta);

        // My target income ;)
        got = decoder.calculateNetIncome(10000000.0);
        Assert.assertEquals(4562470.0, got, delta);
    }
}