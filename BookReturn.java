/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BookReturn.java
 *
 * Created on 2011-1-15, 23:52:53
 */
package library;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class BookReturn extends javax.swing.JFrame{
	private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField jTextField1;
    private JTextField jTextField2;

    /** Creates new form BookReturn */
    public BookReturn(){
    	
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents(){
    	
    	Container cp = getContentPane();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jButton1 = new JButton();
        jButton2 = new JButton();
        JPanel jp_north = new JPanel();
        JPanel jp_center = new JPanel(new GridLayout(8, 2));
        JPanel jp_south = new JPanel();
        cp.add(jp_north,"North");
        cp.add(jp_center,"Center");
        cp.add(jp_south,"South");
        jp_south.add(jButton1);
        jp_south.add(jButton2);
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel("       图书号"));
        jp_center.add(jTextField1);
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel("       用户名"));
        jp_center.add(jTextField2);
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        jp_center.add(new JLabel(""));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("还书");
        setLocationByPlatform(true);
        setName("还书"); 
        setResizable(false);

        jLabel1.setText("图书号");
        jLabel2.setText("读者号");

        jButton1.setText("确定");
        jButton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("取消");
        jButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                jButton2ActionPerformed(evt);
            }
        });
        setSize(230, 250);
        Toolkit tkt = Toolkit.getDefaultToolkit();
        Dimension screen = tkt.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        int xcenter = (x - 300) / 2;
        int ycenter = (y - 200) / 2;
        setLocation(xcenter, ycenter);
        setResizable(false);
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt){
        if(jTextField1.getText().equals("")){
            JOptionPane.showMessageDialog(null, "请输入图书号！");
            return;
        }
        else{
            bookReturn();
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt){
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        //this.dispose();
        this.hide();
    }

    /**
     * @param args the command line arguments
     */
    private void bookReturn(){
    	Date date1 = new Date();
    	Date date2 = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String time = df.format(date1);
        double penalty = 0;
        String str1 = "select * from information where bookNo='"
                + jTextField1.getText() + "';";
        String str2 = "update information set Tindate = '"+time+"' where bookNo='" + jTextField1.
                getText() + "' and readNo = '" + jTextField2.getText() + "';";
        
        String str4 = "select indate from information where bookNo='"
                + jTextField1.getText() + "' and readNo = '"+ jTextField2.getText() +"';";
        String sql5 = "update bookno set bookstate = '是' where bookNo='"+jTextField1.getText()+"'";
        String sql6 = "update information set Clstate = '' where bookNo='" + jTextField1.getText() + 
        		"' and readNo = '" + jTextField2.getText() + "';";


        try{
            Database db = new Database();
            db.dbCon();
            ResultSet rs = db.stmt.executeQuery(str1);
            boolean book = rs.next();
            String str_book = rs.getString(7);
            if(book = false){
                JOptionPane.showMessageDialog(null, "无此图书！(图书号错误)");
                return;
            }
            else if(str_book.equals("")){
                JOptionPane.showMessageDialog(null, "此书未借出(图书号错误)！");
                return;
            }
            else{
                int done = db.stmt.executeUpdate(str2);
                if(done == 1){
                    JOptionPane.showMessageDialog(null, "操作成功！");
                    ResultSet rs1 = db.stmt.executeQuery(str4);
                    rs1.next();
                    date2 = rs1.getDate(1);
                    if((date1.getTime()-date2.getTime())/(24*60*60*1000) > 0){
                    	long s = (date1.getTime()-date2.getTime())/(24*60*60*1000);
                    	penalty = s*0.1;
                    	String str3 = "update information set penalty = " + penalty +" where bookNo='" + jTextField1.
                                getText() + "' and readNo = '" + jTextField2.getText() + "';";
                    	rs1 = db.stmt.executeQuery(str3);
                    	if(rs1.next()){JOptionPane.showMessageDialog(null, "超出时间，需要罚款" + penalty + "元");}
                    }
                    db.stmt.executeUpdate(sql5);
                    db.stmt.executeUpdate(sql6);
                    String str = "select * from view_2;";
                    ShowBook showbk = new ShowBook();
                    showbk.showFirst(str);
                }
                else{
                    JOptionPane.showMessageDialog(null, "操作失败！");
                }
            }
        }
        catch(SQLException g){
            System.out.println("Error Code: " + g.getErrorCode());
            System.out.println("Error Message: " + g.getMessage());
            JOptionPane.showMessageDialog(null, "操作失败！");
        }
    }
    // Variables declaration - do not modify
    
    // End of variables declaration
}