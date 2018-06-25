package library;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReaderAdd
        extends JFrame
        implements ActionListener{

    JTextField readNo, readID, readCompan, readAddr, readcont;
    JComboBox RCNo;
    JRadioButton sex1, sex2;
    String[] s = {"01","02"};
//    JPasswordField user_passwd, user_passwd_confirm, lib_card_passwd;
    JButton ok = new JButton("注册");
    JButton cancle = new JButton("取消");

    //static boolean isRoot=false;
    public ReaderAdd(){
        setTitle("注册");
        Container cp = getContentPane();
        JLabel label0 = new JLabel("");
        JLabel label1 = new JLabel("读者编号");
        JLabel label2 = new JLabel("性别");
        JLabel label3 = new JLabel("身份证");
        JLabel label4 = new JLabel("所在单位");
        JLabel label5 = new JLabel("地址");
        JLabel label6 = new JLabel("联系方式");
        JLabel label7 = new JLabel("读者类别(01普通,02会员)");
        JPanel pn1 = new JPanel(new GridLayout(1, 2));
        JPanel pn2 = new JPanel();
        JPanel pn3 = new JPanel();
        JPanel pn1_1 = new JPanel(new GridLayout(8, 1));
        JPanel pn1_2 = new JPanel(new GridLayout(8, 1));
//        JPanel pn1_north = new JPanel();
//        JPanel pn1_south = new JPanel();
//        JPanel pn1_north_west = new JPanel();
//        JPanel pn1_north_center = new JPanel(new GridLayout(4, 1));
//        JPanel pn1_south_west = new JPanel(new GridLayout(9, 1));
//        JPanel pn1_south_center = new JPanel(new GridLayout(7, 1));
        JLabel label8 = new JLabel("注册信息");
        readNo = new JTextField(18);
        readID = new JTextField(18);
        readCompan = new JTextField(18);
        readAddr = new JTextField(18);
        readcont = new JTextField(18);
        RCNo = new JComboBox(s);
        sex1 = new JRadioButton("男");
        sex2 = new JRadioButton("女");
        ButtonGroup rg = new ButtonGroup();
        pn3.add(sex1);
        rg.add(sex1);
        pn3.add(sex2);
        rg.add(sex2);
        sex1.setSelected(true);
        sex2.setSelected(false);
        JLabel label9 = new JLabel("<html><font color=#CC00FF size='7'><i>"
                + "欢迎注册</i></font>", SwingConstants.CENTER);
        cp.add(label9, "North");
        cp.add(pn1, "Center");
        cp.add(pn2, "South");
        pn1_1.add(label0);
        pn1_2.add(label8);
        pn1_1.add(label1);
        pn1_2.add(readNo);
        pn1_1.add(label2);
        pn1_2.add(pn3);
        pn1_1.add(label3);
        pn1_2.add(readID);
        pn1_1.add(label4);
        pn1_2.add(readCompan);
        pn1_1.add(label5);
        pn1_2.add(readAddr);
        pn1_1.add(label6);
        pn1_2.add(readcont);
        pn1_1.add(label7);
        pn1_2.add(RCNo);
        pn1.add(pn1_1);
        pn1.add(pn1_2);
        pn1.setBackground(Color.PINK);
        pn1_1.setBackground(Color.PINK);
        pn1_2.setBackground(Color.PINK);
        pn3.setBackground(Color.PINK);
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

        /*if (!Login.isRegisterButton)
        {
        pn1_north.setVisible(false);
        }*/

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
            if(readID.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入身份证！", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            if(readCompan.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入单位！"
                        , "提示", JOptionPane.YES_NO_OPTION);
                return;
            }
            if(readAddr.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入地址", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            if(readcont.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入联系方式", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            /*if (!MainWin.userName.equals("root"))
            {
            confirm("reader", "readerID");
            }
            else
            {
            confirm("admin", "adminID");
            }*/
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
        String name = readNo.getText().trim();
        String sex;
        if(sex1.isSelected()) sex = sex1.getText();
        else sex = sex2.getText();
            sql_queryID =
                    "select * from reader where readNo='" + name + "'";
            sql_inset =
                    "insert into reader values('"+ readNo.getText() + "','" 
            + sex + "','" + readID.getText() + "','" 
            + readCompan.getText() + "','" + readAddr.getText() + "','" 
            + readcont.getText() + "','" + (String)RCNo.getSelectedItem() + "')";
        try{
            Database db = new Database();
            db.dbCon();
            db.stmt2 = Database.con.createStatement();
            //if (!MainWin.userName.equals("root"))
            //{
            ResultSet user = db.stmt2.executeQuery(sql_queryID);
            if(user.next()){
                JOptionPane.showMessageDialog(null,
                        "该读者号已存在！", "提示！", JOptionPane.YES_NO_OPTION);
                readNo.setText("");
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
