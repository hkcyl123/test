package library;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class BookIn
        extends JFrame
        implements ActionListener{

	JTextField ISBN, bookNo, bookname, bookwriter, BPNo, bookprice, BCNo, bookmain, bookprim;    //定义文本框
    JRadioButton bookstate1, bookstate2;
    JButton ok = new JButton("确定");
    JButton cancle = new JButton("取消");

    public BookIn(){
        Container cp = getContentPane(); // 初始化面板、按钮、标签、文本框

        JPanel pn_north = new JPanel();
        JPanel JP = new JPanel();
        JLabel label = new JLabel("图书入库", SwingConstants.CENTER);
        label.setForeground(Color.blue);
        pn_north.add(label);

        JPanel pn_west = new JPanel();
        JPanel pn_center = new JPanel(new GridLayout(10, 1));
        ISBN = new JTextField(20);
        bookNo = new JTextField(20);
        bookname = new JTextField(20);
        bookwriter = new JTextField(20);
        BPNo = new JTextField(20);
        bookprice = new JTextField(20);
        BCNo = new JTextField(20);
        bookmain = new JTextField(20);
        bookprim = new JTextField(20);
        bookstate1 = new JRadioButton("是");
        bookstate2 = new JRadioButton("否");
        ButtonGroup rg = new ButtonGroup();
        pn_west.setLayout(new GridLayout(10, 1));
        pn_west.add(new JLabel("ISBN", SwingConstants.CENTER));
        pn_center.add(ISBN);
        pn_west.add(new JLabel("图书编号", SwingConstants.CENTER));
        pn_center.add(bookNo);
        pn_west.add(new JLabel("书名", SwingConstants.CENTER));
        pn_center.add(bookname);
        pn_west.add(new JLabel("作  者", SwingConstants.CENTER));
        pn_center.add(bookwriter);
        pn_west.add(new JLabel("出版号", SwingConstants.CENTER));
        pn_center.add(BPNo);
        pn_west.add(new JLabel("单  价", SwingConstants.CENTER));
        pn_center.add(bookprice);
        pn_west.add(new JLabel("书类别号", SwingConstants.CENTER));
        pn_center.add(BCNo);
        pn_west.add(new JLabel("摘要", SwingConstants.CENTER));
        pn_center.add(bookmain);
        pn_west.add(new JLabel("关键字", SwingConstants.CENTER));
        pn_center.add(bookprim);
        pn_west.add(new JLabel("是否可借", SwingConstants.CENTER));
        bookstate1.setSelected(true);
        bookstate2.setSelected(false);
        JP.add(bookstate1);
        JP.add(bookstate2);
        rg.add(bookstate1);
        rg.add(bookstate2);
        pn_center.add(JP);
        JPanel pn_east = new JPanel();

        JPanel pn_south = new JPanel();
        pn_south.setLayout(new GridLayout(2, 1));
        JPanel pn_south1 = new JPanel();
        JPanel pn_south2 = new JPanel();
        pn_south.add(pn_south1);
        pn_south.add(pn_south2);

        pn_south2.add(ok);
        pn_south2.add(cancle);

        cp.add(pn_north, "North");
        cp.add(pn_west, "West");
        cp.add(pn_center, "Center");
        cp.add(pn_south, "South");
        cp.add(pn_east, "East");


        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        setSize(350, 400);
        int xcenter = (x - 350) / 2;
        int ycenter = (y - 400) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/
        setTitle("图书入库");
        setResizable(false);

        ok.addActionListener(this);//注册监听器
        cancle.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e){
        //String cmd=e.getActionCommand();
        Object source = e.getSource();
        boolean b = Pattern.matches("(\\d+)(.(\\d+))?", bookprice.getText());
        if(source == ok){
            if(ISBN.getText().equals("") ||bookNo.getText().equals("") ||
            		bookname.getText().equals("") ||bookwriter.getText().equals("") ||
            		BPNo.getText().equals("") || bookprice.getText().equals("")
                    || BCNo.getText().equals("") || bookmain.getText().equals("")
                    || bookprim.getText().equals("")){
                JOptionPane.showMessageDialog(this,
                        "请填写所有图书信息！", "提示",
                        JOptionPane.OK_OPTION);
            }
            else if(b == false){
                JOptionPane.showMessageDialog(null, "价格错误", "提示",
                        JOptionPane.YES_NO_OPTION);
            }
            else{
                insertRecord();
            }
        }
        else if(source == cancle){
            this.hide();
        }

    }
    public void insertRecord(){
        String state = "";
        Database db = new Database();
        if(bookstate1.isSelected()){
        	state = bookstate1.getText();
        }else state = bookstate2.getText();
        db.dbCon();
        try{
            String s1 = "insert into book values('" + ISBN.getText() + "','"
                    + bookname.getText() + "','" + bookwriter.getText() + "','"+ BPNo.getText() + "','" 
            		+ bookprice.getText() + "','" + BCNo.getText()+ "','" + bookmain.getText() + "','"
            		+ bookprim.getText() + "')";
            String s2 = "insert into bookno values('" + bookNo.getText() + "','" + ISBN.getText() + "','" + state + "')";
            //查询输入的图书号是否在数据库中存在
            String query1 = "select * from bookno where bookNo='" + bookNo.
                    getText() + "'";
            String query2 = "select * from book where ISBN='" + ISBN.
                    getText() + "'";
            ResultSet rs1 = db.stmt.executeQuery(query1);//返回查询结果集
            if(rs1.next()){
                JOptionPane.showMessageDialog(this, "图书号已经被"
                        + "使用，请重新输入");
                db.dbClose();
                bookNo.setText("");
                return;
            }
            ResultSet rs2 = db.stmt.executeQuery(query2);//返回查询结果集
            if(rs2.next()){
            	int insert = db.stmt.executeUpdate(s2);
            	if(insert == 1){
                    JOptionPane.showMessageDialog(null, "图书信息录入成功！");
                    ISBN.setText("");
                    bookNo.setText("");
                    bookname.setText("");
                    bookwriter.setText("");
                    BPNo.setText("");
                    bookprice.setText("");
                    BCNo.setText("");
                    bookmain.setText("");
                    bookprim.setText("");
                    String str = "select * from view_2;";
                    ShowBook showbk = new ShowBook();
                    showbk.showFirst(str);
                    MainWin.label1.setText("书库现在共有图书 " + ShowBook.count + " 本");
                }
            }
            else{
                int insert1 = db.stmt.executeUpdate(s1);
                int insert2 = db.stmt.executeUpdate(s2);
                if(insert1 == 1 && insert2 == 1){
                    JOptionPane.showMessageDialog(null, "图书信息录入成功！");
                    ISBN.setText("");
                    bookNo.setText("");
                    bookname.setText("");
                    bookwriter.setText("");
                    BPNo.setText("");
                    bookprice.setText("");
                    BCNo.setText("");
                    bookmain.setText("");
                    bookprim.setText("");
                    String str = "select * from view_2;";
                    ShowBook showbk = new ShowBook();
                    showbk.showFirst(str);
                    MainWin.label1.setText("书库现在共有图书 " + ShowBook.count + " 本");
                }
            }
        }
        catch(SQLException g){
            System.out.println("E Code" + g.getErrorCode());
            System.out.println("E M" + g.getMessage());
            JOptionPane.showMessageDialog(null, "输入有错误", "提示",
                    JOptionPane.YES_NO_OPTION);
        }
    }
}
