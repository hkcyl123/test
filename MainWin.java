package library;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.awt.Container;

public class MainWin
        extends JFrame
        implements ActionListener{

    //全局变量定义
    public static String userName;
    public static int PAGE = 20;//页数最大值
    public static int ROW = 50;//每页固定的行数
    public static String ar[][] = new String[ROW][10];//二维数组存放数据库中的数据从而在表table中显示
    public static final  Object columnName[] = {
        "ISBN", "图书编号", "书名", "作者", "出版社", "单价", "类别", "摘要",
        "关键字", "是否可借"
    };//表项值
    

    public static String sISBN, sbookNo, sbookname, sbookwriter, sbpno, sbookprice, sbcno, sbookmain, sbookprim, sbookstate;
    public static boolean prevButton=false,nextButton=false,
            firstButton=false,lastButton=false;//“首页”，“末页”，“前一页”，“下一页”按钮的控制阀
    public static int pageValue=0;//控制显示第几页，调用ShowBook.showPage(int n)时的参数


    //控件定义

    ///////////////北面板
    private JPanel pn_north = new JPanel(new GridLayout(2, 1));

    ////////菜单栏1
    private JMenuBar menubar1 = new JMenuBar();

    ////文件菜单
//    private JMenu file = new JMenu("文件");
//    private JMenuItem exitItem = new JMenuItem("退出");

    ////管理菜单
//    private JMenu manage = new JMenu("管理");
    
    private JMenu bookManage = new JMenu("图书管理");
    private JMenuItem bookInItem = new JMenuItem("图书入库");
    private JMenuItem bookEditItem = new JMenuItem("图书编辑");
    private JMenuItem bookRemoveItem = new JMenuItem("图书删除");
    private JMenuItem bookClass = new JMenuItem("增加书类");
    private JMenuItem bookPublic = new JMenuItem("增加出版社");
    private JMenu borrowManage = new JMenu("借阅管理");
    private JMenuItem bookborrowItem = new JMenuItem("借书");
    private JMenuItem bookReturnItem = new JMenuItem("还书");
    private JMenu userManage = new JMenu("账户管理");
    private JMenuItem changePasswd = new JMenuItem("密  码  修  改");
    private JMenu readerManage = new JMenu("读者账户管理");
    private JMenuItem addReader = new JMenuItem("增加读者");
    private JMenuItem delReader = new JMenuItem("删除读者");
    private JMenu root = new JMenu("根管理员功能");
    private JMenuItem addAdmin = new JMenuItem("增加管理员");
    private JMenuItem delAdmin = new JMenuItem("删除管理员");
    private JMenu exit = new JMenu("退出");
    private JMenuItem exitButton = new JMenuItem("退出");
    ////查看菜单
    private JMenu look = new JMenu("查询");
    
    private JMenuItem showAllItem = new JMenuItem("所有藏书");
    private JMenuItem showBorrowed = new JMenuItem("已借图书");
    private JMenuItem bookQueryItem = new JMenuItem("图书查询");
    
    ////////菜单栏2
    private JMenuBar menubar2 = new JMenuBar();
    
    private JButton bookQueryButton = new JButton("查询");
    private JButton bookEditButton = new JButton("编辑");
    private JButton bookRemoveButton = new JButton("删除");
    private JButton showAllButton = new JButton("所有藏书");
    


    //Pan////////////中面板
    private JPanel pn_center = new JPanel();//用来填放子模块

    public static JLabel label1;
    public static JScrollPane scrollpane;
    public static JTable table;

    //////////////西面板及东面板
    private JPanel pn_west = new JPanel();
    private JPanel pn_east = new JPanel();

    //////////////南面板
    private JPanel pn_south = new JPanel();

    private JButton previous = new JButton("上一页");
    private JButton next = new JButton("下一页");
    private JButton first=new JButton("首页");
    private JButton last=new JButton("末页");
    public  static JLabel showPage = new JLabel("第 1 页");

    /**
     * 构造函数
     * @param uname
     */
    MainWin(String uname){

        userName = uname;
        String lab;

        if(Login.is_tourist){
            menubar1.setVisible(false);
            bookRemoveButton.setVisible(false);
            bookEditButton.setVisible(false);

            lab = "同学 你好！欢迎进入图书查询系统！";
        }
        else{
            lab = uname + "管理员你好！欢迎登录图书管理系统！";
            bookQueryButton.setVisible(false);
            
        }
        if(!userName.equals("201535020209")){
            //root.enable(false);
            root.setEnabled(false);

        }
        Container cp = getContentPane(); // 初始化面板、按钮、标签、文本框
        label1 = new JLabel(lab, SwingConstants.CENTER);
        table = new JTable(ar,columnName);
        table.isCellEditable(1, 6);
        //table.setFocusable(true);
        scrollpane = new JScrollPane(table);

        cp.add(pn_north, "North");
        cp.add(pn_center, "Center");
        cp.add(pn_west, "West");
        cp.add(pn_east, "East");
        cp.add(pn_south, "South");
        pn_north.add(menubar1);
        pn_north.add(menubar2);
        menubar1.add(bookManage);
        menubar1.add(borrowManage);
        menubar1.add(userManage);
        menubar1.add(look);
        bookManage.add(bookInItem);
        bookManage.add(bookEditItem);
        bookManage.add(bookRemoveItem);
        bookManage.add(bookClass);
        bookManage.add(bookPublic);
        userManage.add(changePasswd);
        userManage.add(readerManage);
        userManage.add(root);
        borrowManage.add(bookborrowItem);
        borrowManage.add(bookReturnItem);
        root.add(addAdmin);
        root.add(delAdmin);
        readerManage.add(addReader);
        readerManage.add(delReader);
        look.add(showAllItem);
        look.add(showBorrowed);
        look.add(bookQueryItem);
        exit.add(exitButton);


        menubar2.add(showAllButton);
        menubar2.add(bookQueryButton);
        menubar2.add(bookEditButton);
        menubar2.add(bookRemoveButton);
        menubar1.add(exit);
        pn_center.setLayout(new BorderLayout());
        pn_center.setBackground(Color.PINK);
        pn_center.add(label1, "North");
        pn_center.add(scrollpane, "Center");
        pn_south.add(first);
        pn_south.add(previous);
        pn_south.add(showPage);
        pn_south.add(next);
        pn_south.add(last);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        //setSize(x,y); /*让系统窗口平铺整个显示器窗口*/

        setSize(900, 600);
        int xcenter = (x - 900) / 2;
        int ycenter = (y - 600) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/

        setTitle("图书管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        DefaultCellEditor cellEdit = new DefaultCellEditor(new JTextField());
//        cellEdit.setClickCountToStart(2);//双击后使选择的格子可编辑

        //注册临听器
        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent me){
                if(SwingUtilities.isRightMouseButton(me)){
                    final int row = table.rowAtPoint(me.getPoint());
                    table.setRowSelectionInterval(row, row);
                    if(row != -1){
                        final int column = table.columnAtPoint(me.getPoint());
                        final JPopupMenu popup = new JPopupMenu();
                        JMenuItem edit = new JMenuItem("编辑");
                        popup.add(edit);
                        edit.addActionListener(new ActionListener(){

                            public void actionPerformed(ActionEvent e){
                                table.clearSelection(); //清除高亮选择状态
                                table.editCellAt(row, column); //设置某列为可编辑
                            }
                        });
                        JMenuItem del = new JMenuItem("删除");
                        popup.add(del);
                        del.addActionListener(new ActionListener(){

                            public void actionPerformed(ActionEvent ae){
                                new DelBook().delBook();
                            }
                        });
                        popup.show(me.getComponent(), me.getX(), me.getY());
                    }
                }
            }
        });
        bookQueryItem.addActionListener(this);
        bookInItem.addActionListener(this);
        bookEditItem.addActionListener(this);
        bookRemoveItem.addActionListener(this);
        showAllItem.addActionListener(this);
        bookQueryButton.addActionListener(this);
        bookEditButton.addActionListener(this);
        bookRemoveButton.addActionListener(this);
        showAllButton.addActionListener(this);
        showBorrowed.addActionListener(this);
        changePasswd.addActionListener(this);
        userManage.addActionListener(this);
        readerManage.addActionListener(this);
        addAdmin.addActionListener(this);
        bookClass.addActionListener(this);
        bookPublic.addActionListener(this);
        delAdmin.addActionListener(this);
        addReader.addActionListener(this);
        delReader.addActionListener(this);
        exitButton.addActionListener(this);
        root.addActionListener(this);
        previous.addActionListener(this);
        next.addActionListener(this);
        bookReturnItem.addActionListener(this);
        bookborrowItem.addActionListener(this);
        first.addActionListener(this);
        last.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == showAllItem || source == showAllButton){
            String str = "select * from view_2;";
            ShowBook showbk = new ShowBook();
            showbk.showFirst(str);
            label1.setText("书库现在共有图书 " + ShowBook.count + " 本");
        }
        if(source == bookQueryItem || source == bookQueryButton){
            QueryBook queryBook = new QueryBook();
            queryBook.setVisible(true);
        }
        if(source == bookInItem){
            BookIn bookIn1 = new BookIn();
            bookIn1.setVisible(true);
        }
        if(source == bookEditItem || source == bookEditButton){
            int index = table.getSelectedRow();
            if(index == -1 || table.getValueAt(index, 1) == null){
                JOptionPane.showMessageDialog(null, "请选定要编辑的表格行",
                        "错误", JOptionPane.YES_NO_OPTION);
            }
            else{
                sISBN = (String) table.getValueAt(index, 0);
                sbookNo = (String) table.getValueAt(index, 1);
                sbookname = (String) table.getValueAt(index, 2);
                sbookwriter = (String) table.getValueAt(index, 3);
                sbpno = (String) table.getValueAt(index, 4);
                sbookprice = (String) table.getValueAt(index, 5);
                sbcno = (String) table.getValueAt(index, 6);
                sbookmain = (String) table.getValueAt(index, 7);
                sbookprim = (String) table.getValueAt(index, 8);
                sbookstate = (String) table.getValueAt(index, 9);
                new EditBook().setVisible(true);
            }
        }
        if(source == bookRemoveItem || source == bookRemoveButton){
            DelBook delBook = new DelBook();
            delBook.delBook();
        }
        if(source == showBorrowed){
            String str = "select * from book where borrower!='';";
            ShowBook showbk = new ShowBook();
            showbk.showFirst(str);
            label1.setText("已借出 " + ShowBook.count + " 本");
        }
        if(source == changePasswd){
            ChangePasswd changePasswd1 = new ChangePasswd(userName);
            changePasswd1.setVisible(true);
        }
        if(source == addAdmin){
        	new AdminAdd().setVisible(true);
        }
        if(source == bookClass){
        	new AddBookClass().setVisible(true);
        }
        if(source == bookPublic){
        	new AddBP().setVisible(true);
        }
        if(source == delAdmin){
        	new deladmin().setVisible(true);
        }
        if(source == addReader){
            new ReaderAdd().setVisible(true);
        }
        if(source == delReader){
        	new DelReader().setVisible(true);;
        }
        if(source == bookborrowItem){
            new BookBorrow().setVisible(true);
        }
        if(source == bookReturnItem){
            new BookReturn().setVisible(true);
        }
        if(source == previous){
            if(prevButton&&pageValue>0){
                pageValue--;
                new ShowBook().showPage(pageValue);
            }
        }
        if(source == next){
            if(nextButton&&pageValue<ShowBook.maxPage){
                pageValue++;
                new ShowBook().showPage(pageValue);
//                prevButton=true;
//                firstButton=true;
            }
        }
        if(source == first){
            if(firstButton&&pageValue!=0){
                pageValue=0;
                new ShowBook().showPage(0);
            }
        }
        if(source == last){
            if(lastButton&&pageValue!=ShowBook.maxPage){
                pageValue=ShowBook.maxPage;
                new ShowBook().showPage(pageValue);
//                firstButton=true;
//                prevButton=true;
            }
        }
        if(firstButton == true) first.setEnabled(true);
        else first.setEnabled(false);
        if(nextButton == true) next.setEnabled(true);
        else next.setEnabled(false);
        if(prevButton == true) previous.setEnabled(true);
        else previous.setEnabled(false);
        if(lastButton == true) last.setEnabled(true);
        else last.setEnabled(false);
        if(source == exitButton){
            int selection = JOptionPane.showConfirmDialog(null, "确定要退出吗？",
                    "提醒！", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if(selection == JOptionPane.OK_OPTION){
                System.exit(0);
            }
        }
    }
}
//private JButton previous = new JButton("上一页");
//private JButton next = new JButton("下一页");
//private JButton first=new JButton("首页");
//private JButton last=new JButton("末页");
//prevButton=false,nextButton=false,
//firstButton=false,lastButton=false

