package library;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DelReader extends JFrame implements ActionListener{
	
	JButton ok = new JButton("确定");
	JButton cancle = new JButton("取消");
	JTextField user_name = new JTextField(18);
	JPanel panel,panel_south;
	DelReader(){
		Container cp = getContentPane();
		panel = new JPanel(new GridLayout(8, 2));
		panel_south = new JPanel();
		cp.add(panel, "Center");
        cp.add(panel_south, "South");
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel("读者号:"));
        panel.add(user_name);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel_south.add(ok);
        panel_south.add(cancle);

		setTitle("删除读者");
        setSize(250, 250);
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
	public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == ok){
            if(user_name.getText().equals("")){
                JOptionPane.showMessageDialog(null, "请输入读者名字！");
                return;
            }
            else{
            	delreader();
            }
        }
        if(source == cancle){
            //frame.setVisible(false);
            dispose();
        }

    }
	public void delreader(){
		String sql1 = "select * from reader where readNo ='" + user_name.getText() + "'";
		String sql2 = "delete from reader where readNo ='" + user_name.getText() + "'";
		try{
            Database db = new Database();
            db.dbCon();
            ResultSet rs1 = db.stmt.executeQuery(sql1);
            boolean reader = rs1.next();
            if(reader == false){
                JOptionPane.showMessageDialog(null, "无此读者！");
                return;
            }
            int done = db.stmt.executeUpdate(sql2);
            if(done == 1){
                JOptionPane.showMessageDialog(null, "操作成功！");
                user_name.setText("");
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
