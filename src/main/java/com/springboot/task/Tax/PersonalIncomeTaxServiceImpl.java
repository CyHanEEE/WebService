package com.springboot.task.Tax;

import javax.jws.WebService;

@WebService(endpointInterface = "com.springboot.task.Tax.PersonalIncomeTaxService")
public class PersonalIncomeTaxServiceImpl implements PersonalIncomeTaxService {
    @Override
    public double calculatePersonalIncomeTax(double income) {
        // 假设起征点为5000元
        double threshold = 5000;
        // 超过起征点的部分
        double taxableIncome = income - threshold * 12;

        // 根据2024年个税法，工资薪金所得适用的税率表
        double[] brackets = {0, 36000, 144000, 300000, 420000, 660000, 960000};
        double[] rates = {0.03, 0.1, 0.2, 0.25, 0.3, 0.35, 0.45};
        double[] deductions = {0, 2520, 16920, 31920, 52920, 85920, 181920};

        if (taxableIncome <= 0) {
            return 0;
        }

        int i;
        for (i = 0; i < brackets.length; i++) {
            if (i == brackets.length - 1 || taxableIncome <= brackets[i + 1]) {
                break;
            }
        }
        return (taxableIncome * rates[i] - deductions[i]);
    }
}