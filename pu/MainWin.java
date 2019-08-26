package pu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;

public class MainWin extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JLayeredPane layeredPane_1;
	private JLayeredPane layeredPane;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton searchButton;
	private JButton button_5;
	private JButton btnNewButton;
	private JButton button;
	private JLabel label_1;
	private JLabel label_3;
	private JButton addButton;
	private JButton unionButton;
	private static Socket s = null;
	private static PrintStream ps = null;
	private static BufferedReader netReader = null;
	private JButton button_4;
	private JButton button_7;
	private JButton button_6;
	
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
	public MainWin() {
		setTitle("\u4E3B\u9875");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(500, 150, 850, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(0, 0, 900, 500);
		contentPane.add(layeredPane_1);
		layeredPane_1.setLayout(null);
		
		label = new JLabel("");
		layeredPane_1.setLayer(label, -1);
		label.setIcon(new ImageIcon("扭曲.jpeg"));
		label.setBounds(0, 0, 895, 568);
		layeredPane_1.add(label);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 10, 844, 474);
		layeredPane_1.add(layeredPane);
		
		button = new JButton("");
		button.addActionListener(new ActionListener() {//我的信息
			public void actionPerformed(ActionEvent e) {
				if(MainRun.ID == null) {
					new ErrorTip("提示", "你还未登录");
					return;
				}
				dispose();
				ps.println("select * from users where account ='" + MainRun.ID + "'");
				try {
					//将查询到的详细信息给用户窗口
					String line = netReader.readLine();
					String[] info = line.split(" ");
					new MyInfo(info[0], info[1], info[2], info[3], info[4], info[5]).setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(783, 30, 20, 20);
		layeredPane.add(button);
		button.setIcon(new ImageIcon(MainWin.class.getResource("/org/eclipse/jface/fieldassist/images/info_ovr@2x.png")));
		
		label_1 = new JLabel("\u6211\u7684\u4FE1\u606F");
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(770, 60, 57, 15);
		layeredPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u6821\u56ED\u6D3B\u52A8\u5E73\u53F0");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("宋体", Font.PLAIN, 25));
		label_2.setBounds(357, 10, 195, 40);
		layeredPane.add(label_2);
		
		button_1 = new JButton("\u767B\u5F55");//登录按钮，在用户登录状态会隐藏
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LogWin();
			}
		});
		button_1.setBounds(10, 10, 97, 23);
		layeredPane.add(button_1);
		
		button_2 = new JButton("\u6CE8\u518C");//注册按钮，在用户登录状态下会隐藏
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new RegisterWin().setVisible(true);
			}
		});
		button_2.setBounds(10, 36, 97, 23);
		layeredPane.add(button_2);
		
		button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(MainWin.class.getResource("/org/eclipse/jface/dialogs/images/popup_menu@2x.png")));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//查看所有活动
				dispose();
				new AllActivitiesInfo().setVisible(true);
			}
		});
		button_3.setBounds(177, 77, 32, 32);
		layeredPane.add(button_3);
		
		searchButton = new JButton("");
		searchButton.setIcon(new ImageIcon(MainWin.class.getResource("/icons/progress/ani/6@2x.png")));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//搜索活动
				dispose();
				new SearchActivity().setVisible(true);
			}
		});
		searchButton.setBounds(343, 77, 32, 32);
		layeredPane.add(searchButton);
		
		button_5 = new JButton("");
		button_5.setIcon(new ImageIcon(MainWin.class.getResource("/org/eclipse/jface/text/source/projection/images/collapsed@2x.png")));
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//申请学分，未登录则无法申请
				if(MainRun.ID == null) {
					new ErrorTip("提示", "你还未登录");
					return;
				}
				dispose();
				new ApplyCredit().setVisible(true);
			}
		});
		button_5.setBounds(501, 77, 32, 32);
		layeredPane.add(button_5);
		
		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//作者信息
				dispose();
				new KonwUs().setVisible(true);
			}
		});
		btnNewButton.setIcon(new ImageIcon(MainWin.class.getResource("/icons/full/message_info@2x.png")));
		btnNewButton.setBounds(674, 77, 41, 32);
		layeredPane.add(btnNewButton);
		
		unionButton = new JButton("\u53D1\u5E03\u65B0\u6D3B\u52A8");
		unionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//新建活动，只有学生会成员才能新建活动，对其他用户隐藏该功能
				dispose();
				new NewActivity().setVisible(true);
			}
		});
		unionButton.setBounds(10, 75, 116, 23);
		layeredPane.add(unionButton);
		
		label_3 = new JLabel("\u641C\u7D22\u6D3B\u52A8");
		label_3.setFont(new Font("宋体", Font.PLAIN, 20));
		label_3.setForeground(Color.WHITE);
		label_3.setBounds(317, 119, 97, 32);
		layeredPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u4E86\u89E3\u6211\u4EEC");
		label_4.setFont(new Font("宋体", Font.PLAIN, 20));
		label_4.setForeground(Color.WHITE);
		label_4.setBounds(656, 119, 89, 32);
		layeredPane.add(label_4);
		
		JLabel label_5 = new JLabel("\u6240\u6709\u6D3B\u52A8");
		label_5.setFont(new Font("宋体", Font.PLAIN, 20));
		label_5.setForeground(Color.WHITE);
		label_5.setBounds(157, 119, 97, 32);
		layeredPane.add(label_5);
		
		JLabel label_6 = new JLabel("\u5B66\u5206\u7533\u8BF7");
		label_6.setFont(new Font("宋体", Font.PLAIN, 20));
		label_6.setForeground(Color.WHITE);
		label_6.setBounds(479, 119, 89, 32);
		layeredPane.add(label_6);
		
		addButton = new JButton("\u6DFB\u52A0\u7B7E\u5230\u5458");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//添加签到员，只有学生会成员才能添加签到员，对其他用户隐藏该功能
				dispose();
				new AddSignman().setVisible(true);
			}
		});
		addButton.setBounds(10, 110, 116, 23);
		layeredPane.add(addButton);
		
		JLabel label_7 = new JLabel("\u6500\u767B\u79D1\u5B66\u9AD8\u5CF0\uFF0C");
		label_7.setForeground(Color.ORANGE);
		label_7.setFont(new Font("宋体", Font.PLAIN, 20));
		label_7.setBounds(284, 189, 336, 30);
		layeredPane.add(label_7);
		
		JLabel label_8 = new JLabel("\u5C31\u50CF\u767B\u5C71\u8FD0\u52A8\u5458\u6500\u767B\u73E0\u7A46\u6717\u739B\u5CF0\u4E00\u6837\uFF0C");
		label_8.setForeground(Color.CYAN);
		label_8.setFont(new Font("宋体", Font.PLAIN, 20));
		label_8.setBounds(284, 255, 383, 30);
		layeredPane.add(label_8);
		
		JLabel label_9 = new JLabel("\u8981\u514B\u670D\u65E0\u6570\u8270\u96BE\u9669\u963B\u3002");
		label_9.setForeground(Color.GREEN);
		label_9.setFont(new Font("宋体", Font.PLAIN, 20));
		label_9.setBounds(284, 306, 336, 30);
		layeredPane.add(label_9);
		
		JLabel label_10 = new JLabel("\u61E6\u592B\u548C\u61D2\u6C49\u662F\u4E0D\u53EF\u80FD\u4EAB\u53D7\u5230\u80DC\u5229\u7684\u559C\u60A6\u548C\u5E78\u798F\u7684\u3002");
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("宋体", Font.PLAIN, 20));
		label_10.setBounds(284, 358, 461, 30);
		layeredPane.add(label_10);
		
		JLabel label_11 = new JLabel(" \u2014\u2014\u9648\u666F\u6DA6");
		label_11.setForeground(Color.MAGENTA);
		label_11.setFont(new Font("宋体", Font.PLAIN, 20));
		label_11.setBounds(642, 402, 144, 30);
		layeredPane.add(label_11);
		
		JLabel label_12 = new JLabel("");
		label_12.setIcon(new ImageIcon("攀登.jpg"));
		label_12.setBounds(111, 173, 163, 236);
		layeredPane.add(label_12);
		
		button_4 = new JButton("\u5BA1\u6838\u53C2\u4E0E\u6D3B\u52A8\u8005");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//审核学生参加活动，只有学生会成员才能审核，对其他用户隐藏该功能
				dispose();
				new CheckAct_User().setVisible(true);
			}
		});
		button_4.setBounds(552, 23, 150, 23);
		layeredPane.add(button_4);
		
		button_6 = new JButton("\u7B7E\u5230");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//对某活动的参与者进行签到，只有签到员才能使用，对其他用户隐藏该功能
				dispose();
				new SignUp().setVisible(true);
			}
		});
		button_6.setBounds(10, 195, 97, 23);
		layeredPane.add(button_6);
		
		button_7 = new JButton("\u7B7E\u9000");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//对某活动的参与者进行签退，只有签到员才能使用，对其他用户隐藏该功能
				dispose();
				new SignOff().setVisible(true);
			}
		});
		button_7.setBounds(10, 261, 97, 23);
		layeredPane.add(button_7);
		//查看该用户是否为签到员
		ps.println("select * from signman where account='" + MainRun.ID + "'");
		try {
			String line = netReader.readLine();
			if("false".equals(line)) {
				button_6.setVisible(false);
				button_7.setVisible(false);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	//设置登录，注册两按钮是否可见
	public void setLogButton(boolean temp) {
		button_1.setVisible(temp);
		button_2.setVisible(temp);
	}
	//设置学生会的特有按钮是否可见
	public void setUnionButton(boolean temp) {
		addButton.setVisible(temp);
		unionButton.setVisible(temp);
		button_4.setVisible(temp);
	}
}
