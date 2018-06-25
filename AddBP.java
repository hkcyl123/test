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

public class AddBP extends JFrame implements ActionListener{
	JTextField BPNo, BPName, BPTeleph, BPAddr;
	JPanel pn1 = new JPanel(new GridLayout(1, 2));
	JPanel pn2 = new JPanel();
	JPanel pn1_1 = new JPanel(new GridLayout(8, 1));
    JPanel pn1_2 = new JPanel(new GridLayout(8, 1));
	JLabel label1 = new JLabel("出版社号");
    JLabel label2 = new JLabel("出版社名");
    JLabel label3 = new JLabel("出版社电话");
    JLabel label4 = new JLabel("出版社地址");
    JButton ok = new JButton("添加");
    JButton cancle = new JButton("取消");
	AddBP(){
		Container cp = getContentPane();
		BPNo = new JTextField(10);
	    BPName = new JTextField(10);
	    BPTeleph = new JTextField(10);
	    BPAddr = new JTextField(10);
        JLabel label9 = new JLabel("<html><font color=#CC00FF size='7'><i>"
                + "添加出版社</i></font>", SwingConstants.CENTER);
        cp.add(label9, "North");
        cp.add(pn1, "Center");
        cp.add(pn2, "South");
        pn1_1.add(label1);
        pn1_2.add(BPNo);
        pn1_1.add(new JLabel("     "));
        pn1_2.add(new JLabel("     "));
        pn1_1.add(label2);
        pn1_2.add(BPName);
        pn1_1.add(new JLabel("     "));
        pn1_2.add(new JLabel("     "));
        pn1_1.add(label3);
        pn1_2.add(BPTeleph);
        pn1_1.add(new JLabel("     "));
        pn1_2.add(new JLabel("     "));
        pn1_1.add(label4);
        pn1_2.add(BPAddr);
        pn1_1.add(new JLabel("     "));
        pn1_2.add(new JLabel("     "));
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
            if(BPNo.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入出版社号！", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            if(BPName.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入出版社名！"
                        , "提示", JOptionPane.YES_NO_OPTION);
                return;
            }
            if(BPTeleph.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入出版社电话！", "提示",
                        JOptionPane.YES_NO_OPTION);
                return;
            }
            if(BPAddr.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "请输入出版社地址！", "提示",
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
        String sql_inset, sql_queryID1, sql_queryID2;
        String bpno = BPNo.getText().trim();
        String bpname = BPName.getText().trim();
        String sex;
            sql_queryID1 =
                    "select * from bookpublish where BPNo='" + bpno + "'";
            sql_queryID2 =
                    "select * from bookpublish where BPName='" + bpname + "'";
            sql_inset =
                    "insert into bookpublish values('"+ BPNo.getText() + "','" 
            + BPName.getText() + "','" + BPTeleph.getText() + "','"+ BPAddr.getText() + "')";
        try{
            Database db = new Database();
            db.dbCon();
            db.stmt2 = Database.con.createStatement();
            //if (!MainWin.userName.equals("root"))
            //{
            ResultSet user1 = db.stmt2.executeQuery(sql_queryID1);
            if(user1.next()){
                JOptionPane.showMessageDialog(null,
                        "该出版社号已存在！", "提示！", JOptionPane.YES_NO_OPTION);
                BPNo.setText("");
                return;
            }
            ResultSet user2 = db.stmt2.executeQuery(sql_queryID2);
            if(user2.next()){
            	JOptionPane.showMessageDialog(null,
                        "该出版社已存在！", "提示！", JOptionPane.YES_NO_OPTION);
                BPName.setText("");
                return;
            }
            else{
                int inseted = db.stmt.executeUpdate(sql_inset);
                if(inseted == 1){
                    JOptionPane.showMessageDialog(null, "添加成功！");
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
