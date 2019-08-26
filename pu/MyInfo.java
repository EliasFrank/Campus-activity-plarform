package pu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;

public class MyInfo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel label_2;
	private JLabel label_1;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_7;
	private JLabel lblNewLabel;
	private JButton button;
	private String[] values = null;
	private static Socket s = null;
	private static PrintStream ps = null;
	private static BufferedReader netReader = null;
	
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
	//调用者传递用户的基本信息
	public MyInfo(String name, String age, String gender, String account, String credit, String path) {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				MainWin mw = new MainWin();
				mw.setVisible(true);
				mw.setLogButton(false);
				mw.setUnionButton(MainRun.isUnion);
			}
		});
		setBounds(550, 170, 396, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u6211\u7684\u4FE1\u606F");
		label.setBounds(131, 10, 97, 35);
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label);
		
		label_1 = new JLabel("姓名：" +name);
		label_1.setBounds(22, 43, 117, 28);
		contentPane.add(label_1);
		
		label_2 = new JLabel("年龄：" + age);
		label_2.setBounds(22, 81, 117, 28);
		contentPane.add(label_2);
		
		label_3 = new JLabel("性别：" +gender);
		label_3.setBounds(22, 119, 97, 28);
		contentPane.add(label_3);
		
		label_4 = new JLabel("账号：" + account);
		label_4.setBounds(22, 157, 136, 28);
		contentPane.add(label_4);
		
		button = new JButton("\u4FEE\u6539\u4E2A\u4EBA\u4FE1\u606F");
		button.setBounds(115, 226, 125, 35);
		button.addActionListener(new ActionListener() {//修改信息
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ModifyInfo().setVisible(true);
			}
		});
		contentPane.add(button);
		
		JLabel label_5 = new JLabel("\u5DF2\u53C2\u52A0\u6D3B\u52A8");
		label_5.setBounds(123, 269, 107, 28);
		label_5.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label_5);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 307, 294, 197);
		contentPane.add(scrollPane);
		
		//显示用户参加的活动
		JList list = new JList();
		list.setModel(new AbstractListModel() {
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
		scrollPane.setViewportView(list);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(path));
		lblNewLabel.setBounds(231, 55, 120, 120);
		contentPane.add(lblNewLabel);
		
		JLabel label_6 = new JLabel("\u5934\u50CF");
		label_6.setFont(new Font("宋体", Font.PLAIN, 15));
		label_6.setBounds(268, 185, 40, 20);
		contentPane.add(label_6);
		
		label_7 = new JLabel("学分：" + credit);
		label_7.setFont(new Font("宋体", Font.PLAIN, 15));
		label_7.setBounds(22, 195, 117, 28);
		contentPane.add(label_7);
	}
	public void addList() {
		ArrayList<String> aList = new ArrayList<String>();
		try {
			String sql = "select activity from user_act where pass=1 and account='" + MainRun.ID + "'";
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
