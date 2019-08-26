package pu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.AbstractListModel;


import javax.swing.event.ListSelectionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CreateUnion extends JFrame {

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
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CreateUnion() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new AdminMainWin().setVisible(true);
			}
		});

		//添加背景图片
		Container container = this.getContentPane();
		JPanel jPanel = new JPanel();
		JPanel panelTop=new JPanel();
		
		ImageIcon backgroundIcon = new ImageIcon("船.jpeg");
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.getLayeredPane().add(backgroundLabel,new Integer(Integer.MIN_VALUE));
        panelTop=(JPanel)this.getContentPane();
        
        //panel和panelTop设置透明
        panelTop.setOpaque(false);
        jPanel.setOpaque(false);
        
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds(500, 200, 629, 535);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("\u8BF7\u9009\u62E9\u5B66\u751F\u4F1A\u6210\u5458");
		label.setBounds(205, 10, 209, 46);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("楷体", Font.PLAIN, 20));
		contentPane.add(label);
		
		button_1 = new JButton("\u786E\u8BA4");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object[] selectedValues =  list_2.getSelectedValues();
				if(selectedValues.length == 0) {
					new ErrorTip("提示", "请指定用户");
					return;
				}
				for (int i = 0; i < selectedValues.length; i++) {
					String sql = "update users set isMemberOfUnion=1 where account='" 
							+ getID(selectedValues[i].toString());
					ps.println(sql);
				}
				new ErrorTip("恭喜", "已添加学生会成员！");
				list_2.setListData(new String[0]);
				values = null;
				addList();
				list_2.setListData(values);
				list_2.setVisible(true);
			}
			//获取用户的账号，账号是字符串的最后一位数字
			private String getID(String str) {
				char[] chs = str.toCharArray();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < chs.length; i++) {
					while(chs[i] >= '0' && chs[i] <= '9') {
						sb.append(chs[i]);
						i++;
						if(i == chs.length)
							break;
					}
					if(i == chs.length) {
						return sb.toString() + "'";
					}
					else sb.delete(0, sb.length());
				}
				return "false";
			}
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 20));
		button_1.setBounds(437, 433, 100, 28);
		contentPane.add(button_1);
		
		JLabel label_1 = new JLabel("\u662F\u5426\u4F7F\u8FD9\u4E9B\u7528\u6237\u6210\u4E3A\u5B66\u751F\u4F1A\u6210\u5458\uFF1F");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(102, 432, 325, 30);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(SuperAdmin.class.getResource("/org/eclipse/jface/dialogs/images/message_warning.png")));
		lblNewLabel.setBounds(77, 437, 15, 24);
		contentPane.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 147, 523, 216);
		contentPane.add(scrollPane);
		
		list_2 = new JList();
		
		list_2.setFont(new Font("楷体", Font.PLAIN, 20));
		list_2.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			{
				addList();
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
		lblctrl.setBounds(191, 373, 249, 37);
		contentPane.add(lblctrl);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(textField.getText())) {
					textField.setText("请输入要搜索的人的姓名");
					textField.setForeground(Color.GRAY);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if("请输入要搜索的人的姓名".equals(textField.getText())) {
					textField.setText("");
				}
			}
		});
		textField.setBounds(156, 86, 209, 30);
		contentPane.add(textField);
		textField.setColumns(20);
		
		JButton button = new JButton("\u641C\u7D22");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> aList = new ArrayList<String>();
				String name = textField.getText();
				//搜索用户
				if("".equals(name)) {
					new ErrorTip("提示", "请输入姓名后再搜索");
					return;
				}
				name = name + "'";
				try {
					//可能好多个姓名相同的人，所以循环接受服务端传来的数据
					ps.println("select * from users where isMemberOfUnion=0  and name='" + name);
					String line;
					while(!(line = netReader.readLine()).equals("数据发送完成！！！")) {
						aList.add(line);
					}
					if(aList.size() == 0) {
						new ErrorTip("提示", "查无此人，或此人已是学生会成员");
						textField.setText("");
						return;
					}
					values = (String[]) aList.toArray(new String[0]);
					list_2.setListData(new String[0]);
					list_2.setListData(values);
					list_2.setVisible(true);
					textField.setText("");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(387, 85, 97, 30);
		contentPane.add(button);
	}
	public void addList() {
		ArrayList<String> aList = new ArrayList<String>();
		try {
			ps.println("select * from users where isMemberOfUnion=0");
			String line;
			while(!(line = netReader.readLine()).equals("数据发送完成！！！")) {
				aList.add(line);
			}
			values = (String[]) aList.toArray(new String[0]);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
