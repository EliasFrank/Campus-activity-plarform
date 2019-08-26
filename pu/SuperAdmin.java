package pu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionListener;


import javax.swing.event.ListSelectionEvent;

public class SuperAdmin extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JList list;
	private JButton button_1;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JList list_2;
	private JLabel label_2;
	private String[] values = null;
	private static Socket s = null;
	private static PrintStream ps = null;
	private static BufferedReader netReader = null;
	/**
	 * Create the frame.
	 */
	static {
		s = NetConnection.getSocket();
		try {
			ps = new PrintStream(s.getOutputStream());
			netReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SuperAdmin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 626, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("\u8BF7\u8003\u8651\u662F\u5426\u7ED9\u4E88\u4ED6\u4EEC\u7684\u7BA1\u7406\u5458\u8EAB\u4EFD");
		label.setBounds(146, 10, 303, 46);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("楷体", Font.PLAIN, 20));
		contentPane.add(label);
		
		button_1 = new JButton("\u786E\u8BA4");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//审核选中的活动
				Object[] selectedValues =  list_2.getSelectedValues();
				if(selectedValues.length == 0) {
					new ErrorTip("提示", "请选择用户");
					return;
				}
				try {
					for (int i = 0; i < selectedValues.length; i++) {
						String sql = "update admin set can=1 where account='"
								+ getAccount(selectedValues[i].toString());
						ps.println(sql);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				new ErrorTip("恭喜", "您已处理了申请账号！");
				list_2.setListData(new String[0]);
				values = null;
				addList();
				list_2.setListData(values);
				list_2.setVisible(true);
			}
			//获取账号
			public String getAccount(String in) {
				char[] temp = in.toCharArray();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < temp.length; i++) {
					while(temp[i] >= '0' && temp[i] <= '9') {
						sb.append(temp[i]);
						i++;
						if(i == temp.length) break;
					}
					if(i == temp.length) break;
					else sb.delete(0, sb.length());
				}
				return sb.toString() + "'";
			}
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 20));
		button_1.setBounds(401, 339, 100, 28);
		contentPane.add(button_1);
		
		JLabel label_1 = new JLabel("\u662F\u5426\u7ED9\u9009\u5B9A\u7684\u89D2\u8272\u7ED9\u4E88\u7BA1\u7406\u5458\u6743\u9650");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(75, 338, 322, 30);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(SuperAdmin.class.getResource("/org/eclipse/jface/dialogs/images/message_warning.png")));
		lblNewLabel.setBounds(46, 339, 19, 24);
		contentPane.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(89, 66, 412, 216);
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
		
		label_2 = new JLabel("\u8BF7\u6309\u8521\u5F90\u5764\u952E\u4EE5\u5B9E\u73B0\u591A\u9009");
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		label_2.setBounds(173, 292, 249, 37);
		contentPane.add(label_2);
	}
	public void addList() {
		ArrayList<String> aList = new ArrayList<String>();
		try {
			String sql = "select * from admin where can=0";
			ps.println(sql);
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
