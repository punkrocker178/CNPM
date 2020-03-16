package tdtu.Lythuyet8.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate extends javax.servlet.http.HttpServlet {

    private Double calculate(String s) {

        Pattern numPattern = Pattern.compile("\\d+");
        Pattern operator = Pattern.compile("[^\\w]+");
        Matcher m = numPattern.matcher(s);
        Matcher m2 = operator.matcher(s);
        ArrayList<String> numList = new ArrayList<String>();
        ArrayList<String> operatorList = new ArrayList<String>();

        while(m.find()) {
            numList.add(m.group().replace("\\s+", ""));
        }

        while(m2.find()) {
            operatorList.add(m2.group().replace("\\s+", ""));
        }


        // 2*3-5
        Double result = 0.0;
        int i = 0, j = 0;
        while(i < numList.size() && j < operatorList.size()) {
            if (i == 0) {
                switch(operatorList.get(i)) {
                    case "+":
                        result = Double.parseDouble(numList.get(i)) + Double.parseDouble(numList.get(i+1));
                        break;
                    case "-":
                        result = Double.parseDouble(numList.get(i)) - Double.parseDouble(numList.get(i+1));
                        break;
                    case "*":
                        result = Double.parseDouble(numList.get(i)) * Double.parseDouble(numList.get(i+1));
                        break;
                    case "/": case ":":
                        result = Double.parseDouble(numList.get(i)) / Double.parseDouble(numList.get(i+1));
                        break;
                }
            } else {
                switch(operatorList.get(i)) {
                    case "+":
                        result += Double.parseDouble(numList.get(i+1));
                        break;
                    case "-":
                        result -= Double.parseDouble(numList.get(i+1));
                        break;
                    case "*":
                        result *= Double.parseDouble(numList.get(i+1));
                        break;
                    case "/": case ":":
                        result /= Double.parseDouble(numList.get(i+1));
                        break;
                }
            }

            i++;
            j++;

        }


        return result;
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out = response.getWriter();
        Double s = this.calculate(request.getParameter("expression"));
        out.println("<html><body>" + s + "</body></html>");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }
}
