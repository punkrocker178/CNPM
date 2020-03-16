package tdtu.Lythuyet8.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate extends javax.servlet.http.HttpServlet {

    private String calculate(String s) {

        Pattern numPattern = Pattern.compile("\\d+");
        Pattern operator = Pattern.compile("[^\\w]+");
        Matcher m = numPattern.matcher(s);
        Matcher m2 = operator.matcher(s);
        ArrayList numAl = new ArrayList();
        ArrayList operatorAl = new ArrayList();

        while(m.find()) {
            numAl.add(m.group().trim());
        }

        while(m2.find()) {
            operatorAl.add(m2.group().trim());
        }

        String res = numAl.toString() + operatorAl.toString();
        return res;
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out = response.getWriter();
        String s = this.calculate(request.getParameter("expression"));
        out.println("<html><body>" + s + "</body></html>");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }
}
