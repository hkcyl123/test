package library;

import javax.swing.*;
import java.sql.*;

public class Database{

    private String url = "jdbc:sqlserver://localhost:1433; DatabaseName = ks";//
    //private String url = "jdbc:mysql://localhost:3306/library";
    public static Connection con;
    public Statement stmt, stmt2;
    //MySQL数据库账户密码
    private String userName = "sa";
    private String userPasswd = "hkcyl132457";

    public void dbCon(){
           
        try{
            con = DriverManager.getConnection(url, userName, userPasswd);
            stmt = con.createStatement();
        }
        catch(Exception g){
            JOptionPane.showMessageDialog(null, "数据库连接失败！", "提示！",
                    JOptionPane.YES_NO_OPTION);
            System.out.println("E M: " + g.getMessage());
        }
    }

    public void dbClose(){
        try{
            con.close();
        }
        catch(SQLException g){
            JOptionPane.showMessageDialog(null, "数据库断开时出错！", "提示！",
                    JOptionPane.YES_NO_OPTION);
            System.out.println("E M" + g.getMessage());
        }
    }
}
