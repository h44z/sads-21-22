#!/bin/env python3

from matplotlib import pyplot as plt

def calcNetIncome(grossIncome):
    netIncome = 0.0

    taxRates = [0.0, 0.2, 0.35, 0.42, 0.48, 0.5, 0.55]
    taxRateLimits = [0.0, 11000.0, 18000.0, 31000.0, 60000.0, 90000.0, 1000000.0]

    for i in range(len(taxRateLimits)-1, -1, -1):
        taxRateLimit = taxRateLimits[i]
        taxRate = taxRates[i]

        if(grossIncome > taxRateLimit):
            taxableIncome = grossIncome - taxRateLimit
            taxes = taxableIncome * taxRate
            netIncome += taxableIncome - taxes

            grossIncome = taxRateLimit

    return netIncome


def calcNetIncome22(grossIncome):
    netIncome = 0.0

    taxRates = [0.0, 0.2, 0.30, 0.40, 0.48, 0.5, 0.55]
    taxRateLimits = [0.0, 11000.0, 18000.0, 31000.0, 60000.0, 90000.0, 1000000.0]

    for i in range(len(taxRateLimits)-1, -1, -1):
        taxRateLimit = taxRateLimits[i]
        taxRate = taxRates[i]

        if(grossIncome > taxRateLimit):
            taxableIncome = grossIncome - taxRateLimit
            taxes = taxableIncome * taxRate
            netIncome += taxableIncome - taxes

            grossIncome = taxRateLimit

    return netIncome

grossIncomeX = []
grossIncomeY = []
netIncomeY = []
leviesY = []
gapY = []
ratioY = []
for grossIncome in range(1,150000,100):
    grossIncomeX.append(grossIncome)

    netIncome = calcNetIncome(grossIncome)

    grossIncomeY.append(grossIncome)
    netIncomeY.append(netIncome)
    leviesY.append(grossIncome-netIncome)
    gapY.append(netIncome - (grossIncome-netIncome))
    ratioY.append(netIncome/grossIncome - (grossIncome-netIncome)/grossIncome)
    #ratioY.append((grossIncome-netIncome)/grossIncome)


# Function to plot scatter
plt.plot(grossIncomeX, netIncomeY)
plt.plot(grossIncomeX, leviesY)
plt.plot(grossIncomeX, grossIncomeY)
plt.plot(grossIncomeX, gapY)
#plt.plot(grossIncomeX, ratioY)
plt.xlabel('gross income')
plt.ylabel('some numbers')

# function to show the plot
plt.show()