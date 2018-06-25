package library;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Container;
import java.util.regex.Pattern;
import java.sql.*;

public class EditBook
        extends JFrame
        implements ActionListener{

    static JTextField eISBN, ebookNo, ebookname, ebookwriter, eBPNo, ebookprice, eBCNo, ebookmain, ebookprim;  //定义文本框
    JRadioButton bookstate1, bookstate2;
    ButtonGroup rg = new ButtonGroup();
    JButton ok = new JButton("确定");
    JButton cancle = new JButton("取消");
    JButton edit_bookNo = new JButton("图书号修改");
    static boolean book_No_setEditable = false;

    public EditBook(){
    	String ser1 ="select BCNo from bookclass where BCname = '"+MainWin.sbcno+"'";
    	String ser2 ="select BPNo from bookpublish where BPname = '"+MainWin.sbpno+"'";
    	String ser3 = null,ser4 = null;
    	Database src = new Database();
    	src.dbCon();
    	try {
			ResultSet w = src.stmt.executeQuery(ser1);
			if(w.next()){ser3 = w.getString(1);}
			w = src.stmt.executeQuery(ser2);
			if(w.next()){ser4 = w.getString(1);}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Container cp = getContentPane(); // 初始化面板、按钮、标签、文本框

        JPanel pn_north = new JPanel();
        JLabel label = new JLabel("图书修改", SwingConstants.CENTER);
        label.setForeground(Color.blue);
        pn_north.add(label);
//        String s = "select * from view_2 whrere bookNo = '" + MainWin.sbookNo +"'";
        JPanel pn_west = new JPanel();
        JPanel JP = new JPanel();
//        Database db = new Database();
//        db.dbCon();
        JPanel pn_center = new JPanel(new GridLayout(10, 1));
        eISBN = new JTextField(MainWin.sISBN);
        ebookNo = new JTextField(MainWin.sbookNo);
        ebookname = new JTextField(MainWin.sbookname);
        ebookwriter = new JTextField(MainWin.sbookwriter);
        eBPNo = new JTextField(ser4);
        ebookprice = new JTextField(MainWin.sbookprice);
        eBCNo = new JTextField(ser3);
        ebookmain = new JTextField(MainWin.sbookmain);
        ebookprim = new JTextField(MainWin.sbookprim);
        bookstate1 = new JRadioButton("是");
        bookstate2 = new JRadioButton("否");
        if(MainWin.sbookstate.equals(bookstate1.getText())){
        	bookstate1.setSelected(true);
        	bookstate2.setSelected(false);
        	}else{bookstate1.setSelected(true);bookstate2.setSelected(false);}
        rg.add(bookstate1);
        rg.add(bookstate2);
        pn_west.setLayout(new GridLayout(10, 1));
        pn_west.add(new JLabel("ISBN", SwingConstants.CENTER));
        pn_center.add(eISBN);
        pn_west.add(new JLabel("图书编号", SwingConstants.CENTER));
        pn_center.add(ebookNo);
        pn_west.add(new JLabel("书名", SwingConstants.CENTER));
        pn_center.add(ebookname);
        pn_west.add(new JLabel("作  者", SwingConstants.CENTER));
        pn_center.add(ebookwriter);
        pn_west.add(new JLabel("出版号", SwingConstants.CENTER));
        pn_center.add(eBPNo);
        pn_west.add(new JLabel("单  价", SwingConstants.CENTER));
        pn_center.add(ebookprice);
        pn_west.add(new JLabel("书类别号", SwingConstants.CENTER));
        pn_center.add(eBCNo);
        pn_west.add(new JLabel("摘要", SwingConstants.CENTER));
        pn_center.add(ebookmain);
        pn_west.add(new JLabel("关键字", SwingConstants.CENTER));
        pn_center.add(ebookprim);
        pn_west.add(new JLabel("是否可借", SwingConstants.CENTER));
        JP.add(bookstate1);
        JP.add(bookstate2);
        rg.add(bookstate1);
        rg.add(bookstate2);
        pn_center.add(JP);
        JPanel pn_east = new JPanel();

        JPanel pn_south = new JPanel();
        pn_south.setLayout(new GridLayout(3, 1));
        JPanel pn_south1 = new JPanel();
        JPanel pn_south2 = new JPanel();
        JPanel pn_south3 = new JPanel();
        pn_south.add(pn_south1);
        pn_south.add(pn_south3);
        pn_south.add(pn_south2);

        pn_south3.add(edit_bookNo);
        pn_south2.add(ok);
        pn_south2.add(cancle);

        cp.add(pn_north, "North");
        cp.add(pn_west, "West");
        cp.add(pn_center, "Center");
        cp.add(pn_south, "South");
        cp.add(pn_east, BorderLayout.EAST);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        setSize(400, 500);
        int xcenter = (x - 350) / 2;
        int ycenter = (y - 400) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/
        setTitle("修改");
        setResizable(false);
        ebookNo.setEditable(false);

        ok.addActionListener(this);//注册监听器
        cancle.addActionListener(this);
        edit_bookNo.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        Object source = e.getSource();
        //Pattern p=Pattern.compile("/^([1-9]\d*|\d+\.\d+)$/");
        //Pattern p=Pattern.compile("(\\d+)(.(\\d+))?");
        boolean b = Pattern.matches("(\\d+)(.(\\d+))?", ebookprice.getText());
        if(cmd.equals("图书号修改")){
        	ebookNo.setEditable(true);
        	eISBN.setEditable(false);
        	ebookname.setEditable(false);
        	ebookwriter.setEditable(false);
        	eBPNo.setEditable(false);
        	ebookprice.setEditable(false);
        	eBCNo.setEditable(false);
        	ebookmain.setEditable(false);
        	ebookprim.setEditable(false);
            book_No_setEditable = true;
            edit_bookNo.setText("其他信息修改");
        }
        if(cmd.equals("其他信息修改")){
        	ebookNo.setEditable(false);
        	eISBN.setEditable(true);
        	ebookname.setEditable(true);
        	ebookwriter.setEditable(true);
        	eBPNo.setEditable(true);
        	ebookprice.setEditable(true);
        	eBCNo.setEditable(true);
        	ebookmain.setEditable(true);
        	ebookprim.setEditable(true);
            book_No_setEditable = false;
            edit_bookNo.setText("图书号修改");
        }
        if(source == ok){
            if(ebookNo.getText().equals("") || eISBN.getText().equals("") || 
            		ebookname.getText().equals("") || ebookwriter.getText().equals("") 
            		|| eBPNo.getText().equals("") || ebookprice.getText().equals("")
                    || eBCNo.getText().equals("") || ebookmain.getText().equals("")
                    || ebookprim.getText().equals("")){
                JOptionPane.showMessageDialog(this, "请填写所有图书信息！",
                        "提示", JOptionPane.OK_OPTION);
            }
            else if(b == false){
                JOptionPane.showMessageDialog(null, "价格错误", "提示",
                        JOptionPane.YES_NO_OPTION);
            }
            else{
                editBook(1);
            }
        }
        else if(source == cancle){
            this.dispose();
        }
    }

    public void editBook(int index){
        Database db = new Database();
        String s;
        if(bookstate1.isSelected()){s = bookstate1.getText();}
        else {s = bookstate2.getText();}
        db.dbCon();
        String updateISBN = "update bookno set ISBN='" + eISBN.getText()
        + "' where bookNo='" + MainWin.sbookNo + "';";
        String updateBookNo = "update bookno set bookNo='" + ebookNo.getText()
                + "'where bookNo='" + MainWin.sbookNo + "'";
        String updateBookName = "update book set bookName='"
                + ebookname.getText()
                + "' where ISBN='" + eISBN.getText() + "';";
        String updatewriter = "update book set bookwriter='" + ebookwriter.getText()
                + "' where ISBN='" + eISBN.getText() + "';";
        String updateBPNo = "update book set BPNo='" + eBPNo.getText()
                + "' where ISBN='" + eISBN.getText() + "';";
        String updatePrice = "update book set bookprice='" + ebookprice.getText()
                + "' where ISBN='" + eISBN.getText() + "';";
        String updateBCNo = "update book set BCNo='" + eBCNo.getText()
                + "' where ISBN='" + eISBN.getText() + "';";
        String updatebookmain = "update book set bookmain='" + ebookmain.getText()
        		+ "' where ISBN='" + eISBN.getText() + "';";
        String updatebookprim = "update book set bookprim='" + ebookprim.getText()
        		+ "' where ISBN='" + eISBN.getText() + "';";
        String updatestate = "update bookno set bookstate='" + s
        		+ "' where bookNo='" + ebookNo.getText() + "';";
        String query1 = "select * from bookno where bookNo='" + ebookNo.getText()
                + "'";
        int ISBNupdate = 0, BookNoupdate = 0, BookNameupdate = 0, writerupdate =
                0, BPNoupdate = 0, Priceupdate = 0, BCNoupdate = 0, bookmainupdate = 0, bookprimupdate = 0,
                		 stateupdate = 0;
        try{
            if(book_No_setEditable){
                ResultSet rs1 = db.stmt.executeQuery(query1);//返回查询结果集
                boolean exist1 = rs1.next();//判断结果集是否有数据
                if(exist1){
                    JOptionPane.showMessageDialog(null, "该图书号已被使用！",
                            "信息", JOptionPane.YES_NO_OPTION);
                    return;
                }
                else{
                	BookNoupdate = db.stmt.executeUpdate(updateBookNo);
                }
            }
            else{
            	ISBNupdate = db.stmt.executeUpdate(updateISBN);
            	BookNameupdate = db.stmt.executeUpdate(updateBookName);
            	writerupdate = db.stmt.executeUpdate(updatewriter);
            	BPNoupdate = db.stmt.executeUpdate(updateBPNo);
            	Priceupdate = db.stmt.executeUpdate(updatePrice);
            	BCNoupdate = db.stmt.executeUpdate(updateBCNo);
            	bookmainupdate = db.stmt.executeUpdate(updatebookmain);
            	bookprimupdate = db.stmt.executeUpdate(updatebookprim);
            	stateupdate = db.stmt.executeUpdate(updatestate);
            }
            if(BookNoupdate == 1 || (ISBNupdate == 1 && BookNameupdate
                    == 1 && writerupdate
                    == 1 && BPNoupdate == 1 && Priceupdate == 1 && BCNoupdate 
                    == 1 && bookmainupdate == 1 && bookprimupdate == 1&&stateupdate == 1)){
                JOptionPane.showMessageDialog(null, "修改成功！",
                        "信息", JOptionPane.YES_NO_OPTION);
                this.hide();
            }
            else{
                JOptionPane.showMessageDialog(null, "信息无法写入数据库！",
                        "信息", JOptionPane.YES_NO_OPTION);
            }
            String str = "select * from view_2;";
            ShowBook showbk = new ShowBook();
            showbk.showFirst(str);
        }
        catch(SQLException g){
            System.out.println("E Code:" + g.getErrorCode());
            System.out.println("E M:" + g.getMessage());
            JOptionPane.showMessageDialog(null, "输入出错！",
                    "信息", JOptionPane.YES_NO_OPTION);
        }
        finally{
            db.dbClose();
        }
    }

    /*public static void main(String args[]){

        new EditBook().setVisible(true);
    }*/
}
