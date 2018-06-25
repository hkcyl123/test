package library;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AdminAdd extends JFrame implements ActionListener{
	JTextField admin, psw, tele, adminN;
	JPanel pn1 = new JPanel(new GridLayout(1, 2));
	JPanel pn2 = new JPanel();
	JPanel pn1_1 = new JPanel(new GridLayout(9, 1));
    JPanel pn1_2 = new JPanel(new GridLayout(9, 1));
	JLabel label1 = new JLabel("管理员账号");
	JLabel label5 = new JLabel("管理员姓名");
    JLabel label2 = new JLabel("密码");
    JLabel label3 = new JLabel("电话");
    JLabel label4 = new JLabel("注册信息");
    JButton ok = new JButton("注册");
    JButton cancle = new JButton("取消");
	AdminAdd(){
		Container cp = getContentPane();
		admin = new JTextField(10);
	    psw = new JTextField(10);
	    tele = new JTextField(10);
	    adminN = new JTextField(10);
        JLabel label9 = new JLabel("<html><font color=#CC00FF size='7'><i>"
                + "欢迎注册</i></font>", SwingConstants.CENTER);
        cp.add(label9, "North");
        cp.add(pn1, "Center");
        cp.add(pn2, "South");
        pn1_1.add(new JLabel("     "));
        pn1_2.add(label4);
        pn1_1.add(new JLabel("     "));
        pn1_2.add(new JLabel("     "));
        pn1_1.add(label1);
        pn1_2.add(admin);
        pn1_1.add(new JLabel("     "));
        pn1_2.add(new JLabel("     "));
        pn1_1.add(label5);
        pn1_2.add(adminN);
        pn1_1.add(new JLabel("     "));
        pn1_2.add(new JLabel("     "));
        pn1_1.add(label2);
        pn1_2.add(psw);
        pn1_1.add(new JLabel("     "));
        pn1_2.add(new JLabel("     "));
        pn1_1.add(label3);
        pn1_2.add(tele);
        
        pn1.setBackground(Color.PINK);
        pn1_1.setBackground(Color.PINK);
        pn1_2.setBackground(Color.PINK);
        pn1.add(pn1_1);
        pn1.add(pn1_2);
        pn2.add(ok);
        pn2.add(cancle);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        //setSize(x,y); /*让系统窗口平铺整个显示器窗口*/

        setSize(320, 380);
        int xcenter = (x - 300) / 2;
        int ycenter = (y - 430) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/
        setResizable(false);
        ok.addActionListener(this);//注册事件监听器
        cancle.addActionListener(this);
        addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
	}
	public void actionPerformed(ActionEvent e){
        //String cmd=e.getActionCommand();
        Object source = e.getSource();
        if(source == ok){
            if(admin.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入账号！", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            if(adminN.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入姓名！"
                        , "提示", JOptionPane.YES_NO_OPTION);
                return;
            }
            if(psw.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入密码！"
                        , "提示", JOptionPane.YES_NO_OPTION);
                return;
            }
            if(tele.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入电话！", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            confirm();
        }
        else if(source == cancle){
            dispose();
        }
    }

    /*public static void main(String[] arg)
    {
    new UserAdd(1).setVisible(true);
    }*/
//    public void confirm(String aaa, String bbb)
    public void confirm(){
        String sql_inset, sql_queryID;
        String name = admin.getText().trim();
        String sex;
            sql_queryID =
                    "select * from administrator where MID='" + name + "'";
            sql_inset =
                    "insert into administrator values('"+ admin.getText() + "','" 
                            + adminN.getText() + "','" + psw.getText() + "','" + tele.getText() + "')";
        try{
            Database db = new Database();
            db.dbCon();
            db.stmt2 = Database.con.createStatement();
            //if (!MainWin.userName.equals("root"))
            //{
            ResultSet user = db.stmt2.executeQuery(sql_queryID);
            if(user.next()){
                JOptionPane.showMessageDialog(null,
                        "该用户名已存在！", "提示！", JOptionPane.YES_NO_OPTION);
                admin.setText("");
                return;
            }
            else{
                int inseted = db.stmt.executeUpdate(sql_inset);
                if(inseted == 1){
                    JOptionPane.showMessageDialog(null, "注册成功！");
                    db.dbClose();
                    hide();
                }
            }

        }
        catch(SQLException g){
            System.out.println("E Code:" + g.getErrorCode());
            System.out.println("E M:" + g.getMessage());
            JOptionPane.showMessageDialog(null, "操作失败！");
        }
    }
}

