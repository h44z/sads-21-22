package at.uibk.dps.dsB.ex0.decoders;

/**
 * The income object contains data about the net/gross income and levies.
 */
public class IncomeObject {
    private double netIncome;
    private double grossIncome;
    private double levies;


    public IncomeObject(double grossIncome, double netIncome, double levies) {
        this.grossIncome = grossIncome;
        this.netIncome = netIncome;
        this.levies = levies;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public double getLevies() {
        return levies;
    }

    public void setLevies(double levies) {
        this.levies = levies;
    }

    @Override
    public String toString() {
        return "Gross Income: " + grossIncome;
    }
}