package cau2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StudentDAO {
    Connection con;
    Statement stmt;
    ResultSet rs;

    public StudentDAO() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "root");
    }

    public void add(final Student s) throws SQLException {
        final PreparedStatement stm = con
            .prepareStatement("insert into student(`hoTen`, `ngaySinh`, `gioiTinh`, `email`) values(?,?,?,?) ");
        stm.setString(1, s.getName());
        stm.setDate(2, new java.sql.Date(s.getBirthdate().getTime()));
        stm.setBoolean(3, s.getGender());
        stm.setString(4, s.getEmail());
        stm.executeUpdate();
        stm.close();
    }

    public List<Student> getAll() throws SQLException {
        List<Student> kq = new ArrayList<Student>();
        stmt = con.createStatement();
        rs = stmt.executeQuery("select maso,hoTen,ngaySinh,gioiTinh,email from student");
        while(rs.next()){
            Student st = new Student();
            st.setId(rs.getInt(1));
            st.setName(rs.getString(2));
            st.setBirthdate(new Date());
            st.setEmail(rs.getString(4));
            st.setGender(true);
            kq.add(st);
        }

        return kq;

    }
}