package library;

import java.awt.*;
import java.awt.event.*;
import java.awt.Container;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public class BookBorrow
        extends JFrame
        implements ActionListener{

    private JButton ok, cancle;
    private JTextField book_num, user_name;
    JPanel pn, pn_center, pn_south, pn_east;

    public BookBorrow(){
        initComponents();
    }

    private void initComponents(){
        Container cp = getContentPane();
        pn_center = new JPanel(new GridLayout(8, 2));
        pn_south = new JPanel();
        pn_east = new JPanel();
        book_num = new JTextField(18);
        user_name = new JTextField(18);
        ok = new JButton("确定");
        cancle = new JButton("取消");

        cp.add(pn_center, "Center");
        cp.add(pn_south, "South");
        cp.add(pn_east, "East");
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel("       图书号"));
        pn_center.add(book_num);
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel("       读者名"));
        pn_center.add(user_name);
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_south.add(ok);
        pn_south.add(cancle);

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("借书");
        setSize(230, 250);
        Toolkit tkt = Toolkit.getDefaultToolkit();
        Dimension screen = tkt.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        int xcenter = (x - 300) / 2;
        int ycenter = (y - 200) / 2;
        setLocation(xcenter, ycenter);
        setResizable(false);

        ok.addActionListener(this);
        cancle.addActionListener(this);
    }

    /*public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
    new BookBorrow().setVisible(true);
    }
    });
    }*/
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == ok){
            if(book_num.getText().equals("") || user_name.getText().equals("")){
                JOptionPane.showMessageDialog(null, "请输入图书号和用户ID！");
                return;
            }
            else{
                bookBorrow();
            }
        }
        if(source == cancle){
            //frame.setVisible(false);
            dispose();
        }

    }

    private void bookBorrow(){
    	int s;
    	Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance(); 
        String time = df.format(date);
        String time2 = (df.format(c.getTime()));;
        String sql1 = "select * from reader where readNo='" + user_name.
                getText() + "';";
        String sql2 = "select * from bookno where bookNo='" + book_num.getText()
                + "';";
        String sql3 = "insert into information values('" + user_name.getText()
                + "','" + book_num.getText() + "','" + MainWin.userName + "','" 
        		+ time + "','" + time2 + "'," 
                + "Null" + ",'" + "未还" + "'," 
        		+ "Null" + ")";
        String sql4 = "select RCNo from reader where readNo = '"+book_num.getText()+"'";
        String sql5 = "update bookno set bookstate = '否' where bookNo='" + book_num.
                getText() + "';";
        try{
            Database db = new Database();
            db.dbCon();
            ResultSet rs1 = db.stmt.executeQuery(sql1);
            boolean reader = rs1.next();
            ResultSet rs2 = db.stmt.executeQuery(sql2);
            boolean book = rs2.next();
            if(reader == false){
                JOptionPane.showMessageDialog(null, "无此读者！");
                return;
            }
            if(book == false){
                JOptionPane.showMessageDialog(null, "无此图书！");
                return;
            }
            ResultSet rs3 = db.stmt.executeQuery(sql4);
            rs3.next();
            String sql = rs3.getString(1);
            if(sql.equals("01")){
            	s = 2;
            }else s = 4;
            c.add(Calendar.MONTH, s);
            int done1 = db.stmt.executeUpdate(sql3);
            if(done1 == 1){
                JOptionPane.showMessageDialog(null, "操作成功！");
                book_num.setText("");
                user_name.setText("");
                db.stmt.executeUpdate(sql5);
            }
            else{
                JOptionPane.showMessageDialog(null, "操作失败！");
            }

        }
        catch(SQLException g){
            System.out.println("Error Code: " + g.getErrorCode());
            System.out.println("Error Message: " + g.getMessage());
            JOptionPane.showMessageDialog(null, "操作失败！");
        }
    }
}
