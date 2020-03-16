import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
//import javax.swing.event.*;

import com.mysql.jdbc.exceptions.MySQLDataException;


public class MySqlConn extends JFrame {
    JTable table;
    JButton cmdBtn;
    JButton deleteBtn;
    JButton addBtn;
    JTextArea txt;
    JTextField txtMssv;
    JTextField txtName;
    JTextField txtAddress;
    JTextField txtBirthdate;
    JTextField txtEmail;
    JPanel panel;
    Connection con;
    Statement stmt;
    ResultSet rs;
    static final String tableName = "SinhVien";

    public MySqlConn() throws Exception {
        
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:33060/School","root","root");  

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        txt = new JTextArea("select * from " + tableName, 4, 30);

        cmdBtn = new JButton("Exec SQL");
        deleteBtn = new JButton("Delete");
        addBtn = new JButton("Add");

        panel.add(new JScrollPane(txt), BorderLayout.EAST);
        panel.add(cmdBtn, BorderLayout.SOUTH);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel boxContainer = new JPanel();

        boxContainer.setLayout(new BoxLayout(boxContainer, BoxLayout.Y_AXIS));

        panel1.setLayout(new FlowLayout());
        panel1.add(new JLabel("MSSV:"));
        txtMssv = new JTextField(20);
        panel1.add(txtMssv);
        panel1.add(deleteBtn);

        panel2.setLayout(new FlowLayout());
        panel2.add(new JLabel("Name:"));
        txtName = new JTextField(60);
        panel2.add(txtName);

        panel3.setLayout(new FlowLayout());
        panel3.add(new JLabel("BirthDate:"));
        txtBirthdate = new JTextField(60);
        panel3.add(txtBirthdate);

        panel4.setLayout(new FlowLayout());
        panel4.add(new JLabel("Address:"));
        txtAddress = new JTextField(60);
        panel4.add(txtAddress);

        panel5.setLayout(new FlowLayout());
        panel5.add(new JLabel("Email:"));
        txtEmail = new JTextField(60);
        panel5.add(txtEmail);

        table = new JTable(4,4);
        panel.add(boxContainer, BorderLayout.WEST);
        boxContainer.add(panel1, BorderLayout.WEST);
        boxContainer.add(panel2, BorderLayout.WEST);
        boxContainer.add(panel3, BorderLayout.WEST);
        boxContainer.add(panel4, BorderLayout.WEST);
        boxContainer.add(panel5, BorderLayout.WEST);
        boxContainer.add(addBtn, BorderLayout.WEST);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(panel, BorderLayout.NORTH);
        c.add(table, BorderLayout.CENTER);

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                add();
            }
        });

        cmdBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                showTable();
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                delete();
            }
        });

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent event){
                try{
                    MySqlConn.this.con.close();
                }catch(Exception ex){}
                System.exit(0);
            }
        });
        setSize(500,400);
        setVisible(true);
    }

    public void showTable(){
        try{
            Vector<String> columnHeads = new Vector<String>();
            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
            
            stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery(txt.getText());  
            ResultSetMetaData rsmd = rs.getMetaData();
            for ( int i = 1; i <= rsmd.getColumnCount(); ++i ) 
                columnHeads.addElement( rsmd.getColumnName( i ) );

            while(rs.next()){
                Vector<Object> currentRow = new Vector<Object>();
                for ( int i = 1; i <= rsmd.getColumnCount(); ++i ) 
                {
                    int tp = rsmd.getColumnType(i);
                    switch (tp) {
                        case Types.VARCHAR:
                        case Types.NVARCHAR:
                        case Types.LONGVARCHAR:
                        case Types.LONGNVARCHAR:
                            currentRow.addElement(rs.getString(i)); 
                            break;
                        case Types.INTEGER:
                        case Types.BIGINT:
                            currentRow.addElement(rs.getInt(i)); 
                            break;
                        default:
                            break;
                    }
                }
                // currentRow.addElement( rs.getInt(1)+"" ); 
                // currentRow.addElement( rs.getString(2)+"" ); 
                // currentRow.addElement( rs.getString(3)+"" ); 
                rows.addElement(currentRow);
            }
            rs.close();
            stmt.close();
            table = new JTable( rows, columnHeads );
            JScrollPane scroller = new JScrollPane( table );
            Container c = getContentPane();
            c.remove( 1 );
            c.add( scroller, BorderLayout.CENTER );
            c.validate();

        }catch(Exception e){
            System.out.println(e);
        }  
    }

    public void delete() {
        try {
            stmt = con.createStatement();  
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE mssv=" + txtMssv.getText()); 
            System.out.println("Record deleted successfully");
            showTable();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void add() {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO " + tableName + "(mssv, name, birthdate, address, email) VALUES (?,?,?,?,?)");
            
            stmt.setString(1, txtMssv.getText());
            stmt.setString(2, txtName.getText());
            stmt.setDate(3, new Date(new java.util.Date().getTime()));
            stmt.setString(4, txtAddress.getText());
            stmt.setString(5, txtEmail.getText());
            stmt.executeUpdate();

            System.out.println("Record added successfully");
            showTable();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) throws Exception{  
        MySqlConn app=new MySqlConn();
        app.showTable();
    }  
}  