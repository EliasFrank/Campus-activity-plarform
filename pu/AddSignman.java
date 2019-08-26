package pu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionListener;


import javax.swing.event.ListSelectionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JTextField;

public class AddSignman extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JList list;
	private JButton button_1;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JList list_2;
	private JLabel lblctrl;
	private String ID;
	private String[] values = null;
	private static Socket s = null;
	private static PrintStream ps = null;
	private static BufferedReader netReader = null;
	private JTextField textField;
	
	static {
		s = NetConnection.getSocket();
		try {
			ps = new PrintStream(s.getOutputStream());
			netReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	/**
	 * 设置活动签到员
	 * 每个活动都有不同的签到员
	 * 输入活动并选择签到员
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddSignman() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				MainWin mw = new MainWin();
				mw.setLogButton(false);
				mw.setUnionButton(true);
				mw.setVisible(true);
			}
		});
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds(500, 200, 628, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("\u8BF7\u9009\u62E9\u7B7E\u5230\u5458");
		label.setBounds(212, 105, 166, 46);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("楷体", Font.PLAIN, 20));
		contentPane.add(label);
		
		button_1 = new JButton("\u786E\u8BA4");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String activity = textField.getText();
				//检查活动编号是否正确
				if("".equals(activity)) {
					new ErrorTip("提示", "请填写活动编号");
					return;
				}
				ps.println("select * from activities where id=" + activity);
				try {
					String line = netReader.readLine();
					if("false".equals(line)) {
						new ErrorTip("提示", "活动编号不存在");
						return;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Object[] selectedValues =  list_2.getSelectedValues();
				if(selectedValues.length == 0) {
					new ErrorTip("提示", "请至少选择一个人");
					return;
				}
				//将所有选项进行记录
				for (int i = 0; i < selectedValues.length; i++) {
					String sql = "insert into signman values(default, '" 
							+ getID(selectedValues[i].toString()) + "', " + activity + ")";
					ps.println(sql);
				}
				new ErrorTip("恭喜", "指定签到员成功！");
				list_2.setListData(new String[0]);
				values = null;
				addList();
				list_2.setListData(values);
				list_2.setVisible(true);
			}
			//获取账号，因为信息按照一定的规则排序，所以，账号是字符串中出现的第一串数字
			private String getID(String str) {
				char[] chs = str.toCharArray();//将其置为字符数组方便于寻找字符
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < chs.length; i++) {
					while(chs[i] >= '0' && chs[i] <= '9') {//如果该字符是数字，则取出添加到StringBuilder中
						sb.append(chs[i]);
						i++;
						//如果下标达到数组长度，则跳出循环
						if(i >= chs.length)
							break;
					}
					//如果容器不为空，则返回容器中的内容
					if(sb.length() > 0) {
						return sb.toString();
					}
				}
				return "false";
			}
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 20));
		button_1.setBounds(361, 444, 100, 28);
		contentPane.add(button_1);
		
		JLabel label_1 = new JLabel("\u662F\u5426\u786E\u8BA4\u8FD9\u4E9B\u4EBA\u4E3A\u7B7E\u5230\u5458");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(108, 443, 243, 30);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(SuperAdmin.class.getResource("/org/eclipse/jface/dialogs/images/message_warning.png")));
		lblNewLabel.setBounds(79, 448, 19, 24);
		contentPane.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 161, 523, 216);
		contentPane.add(scrollPane);
		
		list_2 = new JList();
		
		list_2.setFont(new Font("楷体", Font.PLAIN, 20));
		list_2.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			{
				addList();//构造代码块，初始化values数组
			}
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list_2);
		
		lblctrl = new JLabel("\u8BF7\u6309Ctrl\u952E\u4EE5\u5B9E\u73B0\u591A\u9009");
		lblctrl.setFont(new Font("宋体", Font.PLAIN, 20));
		lblctrl.setBounds(171, 384, 249, 37);
		contentPane.add(lblctrl);
		
		textField = new JTextField();
		textField.setBounds(183, 65, 200, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label_2 = new JLabel("\u8BF7\u8F93\u5165\u6D3B\u52A8\u7F16\u53F7");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("楷体", Font.PLAIN, 20));
		label_2.setBounds(202, 9, 218, 46);
		contentPane.add(label_2);
	}
	public void addList() {
		ArrayList<String> aList = new ArrayList<String>();//创建一个链表用于储存数据
		try {
			//向服务端查询信息
			ps.println("select name,account from users");
			String line;
			while(!(line = netReader.readLine()).equals("数据发送完成！！！")) {
				aList.add(line);
			}
			//将链表转换成数组
			values = (String[]) aList.toArray(new String[0]);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
	}
}
