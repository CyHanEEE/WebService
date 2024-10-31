package com.springboot.task.Tax;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "PersonalIncomeTax", value = "/tax")
public class PersonalIncomeTax extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>请输入您的工资收入以计算个人所得税:</h2>");
        // 添加一个表单，以便用户输入收入
        out.println("<form action='" + request.getContextPath() + "/tax' method='post'>");
        out.println("<input type='text' name='income' required>");
        out.println("<input type='submit' value='计算税额'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // 获取用户输入的工资收入
        String incomeStr = request.getParameter("income");
        double income = Double.parseDouble(incomeStr);

        // 计算个人所得税
        double tax = calculatePersonalIncomeTax(income);

        // 输出结果
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>您的个人所得税为: " + tax + "元</h1>");
        out.println("</body></html>");
    }

    private double calculatePersonalIncomeTax(double income) {
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
