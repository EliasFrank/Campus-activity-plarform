package pu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;

public class ActivityInfo extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private String[] info = null;
	private static Socket s = null;
	private static PrintStream ps = null;
	private static BufferedReader netReader = null;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	//获取流对象及其输入流和输出流，并且对其输入流和输出流进行装饰
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
	public ActivityInfo(boolean flag, String ID) {
		setTitle("\u6D3B\u52A8");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				if(flag) {
					//如果是所有活动里面点击的查看详情，则新建所有活动窗口
					new AllActivitiesInfo().setVisible(true);
				}
				//如果是搜索活动里面点击的查看详情，则新建搜索活动窗口
				else new SearchActivity().setVisible(true);
			}
		});
		ps.println("select * from activities where id=" + ID);
		//查询活动信息
		String line;
		try {
			//获取活动信息
			line = netReader.readLine();
			//将活动信息切割成字符数组
			info = line.split("==");
		}catch (IOException e2) {
			e2.printStackTrace();
		}
		setBounds(500, 150, 823, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 809, 519);
		contentPane.add(layeredPane);
		
		JLabel lblNewLabel = new JLabel("");
		layeredPane.setLayer(lblNewLabel, -1);
		lblNewLabel.setIcon(new ImageIcon("好多书.jpg"));
		lblNewLabel.setBounds(0, 0, 820, 519);
		layeredPane.add(lblNewLabel);
		
		label = new JLabel("\u6D3B\u52A8\u540D\u79F0\uFF1A" + info[0]);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(35, 41, 445, 30);
		layeredPane.add(label);
		
		label_1 = new JLabel("\u989D\u5B9A\u4EBA\u6570\uFF1A" + info[2]);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(35, 164, 445, 30);
		layeredPane.add(label_1);
		
		label_2 = new JLabel("\u53C2\u4E0E\u4EBA\u6570\uFF1A" + info[3]);
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		label_2.setBounds(35, 232, 445, 30);
		layeredPane.add(label_2);
		
		label_3 = new JLabel("\u6D3B\u52A8\u5B66\u5206\uFF1A" + info[1]);
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("宋体", Font.PLAIN, 20));
		label_3.setBounds(35, 101, 445, 30);
		layeredPane.add(label_3);
		
		label_4 = new JLabel("\u5F00\u59CB\u65F6\u95F4\uFF1A" + info[4]);
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("宋体", Font.PLAIN, 20));
		label_4.setBounds(35, 298, 445, 30);
		layeredPane.add(label_4);
		
		label_5 = new JLabel("\u7ED3\u675F\u65F6\u95F4\uFF1A" + info[5]);
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("宋体", Font.PLAIN, 20));
		label_5.setBounds(35, 366, 445, 30);
		layeredPane.add(label_5);
		
		JLabel label_6 = new JLabel("\u6D3B\u52A8\u5730\u70B9\uFF1A" + info[6]);
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("宋体", Font.PLAIN, 20));
		label_6.setBounds(35, 443, 445, 30);
		layeredPane.add(label_6);
		
		JLabel label_7 = new JLabel("\u6D3B\u52A8\u7B80\u4ECB");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("宋体", Font.PLAIN, 20));
		label_7.setBounds(574, 23, 96, 30);
		layeredPane.add(label_7);
		
		button = new JButton("\u7533\u8BF7\u53C2\u52A0");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MainRun.ID == null) {
					new ErrorTip("提示", "你还未登录");
					return;
				}
				//查看该用户是否已经参加了该活动
				String sql = "select account from user_act where account='" + MainRun.ID + "' and activity = " +ID;
				ps.println(sql);
				try {
					if(netReader.readLine().equals("true")) {
						sql = "insert into user_act values(default, '" + MainRun.ID + "', " +ID + ", 0) ";
						ps.println(sql);
						new ErrorTip("提示", "已发送申请");
					}
					else {
						new ErrorTip("提示", "你已申请过参加该活动");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(574, 383, 97, 23);
		layeredPane.add(button);
		
		button_1 = new JButton("\u7B7E\u5230");
		button_1.addActionListener(new ActionListener() {
			//用户向服务端发送签到请求
			public void actionPerformed(ActionEvent e) {
				if(MainRun.ID == null) {
					new ErrorTip("提示", "你还未登录");
					return;
				}
				String sql = "select name from users where account='" + MainRun.ID + "'";
				ps.println(sql);
				String userName;
				try {
					userName = netReader.readLine();
					
					//查看该用户是否已经参加了该活动
					sql = "select account from user_act where account='" + MainRun.ID + "' and activity = " +ID + " and pass = 1";
					ps.println(sql);
					if(netReader.readLine().equals("true")) {
						new ErrorTip("提示", "报名审核中，还无法签到");
						return;
					}
					
					//查看该用户是否已经发送了签到请求
					sql = "select account from sign where account='" + MainRun.ID + "' and activity = " +ID + " and start = 1";
					ps.println(sql);
					
					if(netReader.readLine().equals("true")) {
						sql = "insert into sign values(default, '" + MainRun.ID + "', '" 
								+ userName + "', " +ID + ", '" + info[0] +  "', 1, 0, 0) ";
						ps.println(sql);
						new ErrorTip("提示", "已发送签到申请");
					}
					else {
						new ErrorTip("提示", "已发送过签到申请");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_1.setBounds(573, 416, 97, 23);
		layeredPane.add(button_1);
		
		button_2 = new JButton("\u7B7E\u9000");
		button_2.addActionListener(new ActionListener() {
			
			//用户向服务端发送签退请求
			public void actionPerformed(ActionEvent e) {
				if(MainRun.ID == null) {
					new ErrorTip("提示", "你还未登录");
					return;
				}
				try {
					String sql = "select name from users where account='" + MainRun.ID + "'";
					ps.println(sql);
					
					String userName;
					userName = netReader.readLine();
					
					//查看该用户是否已经参加了该活动
					sql = "select account from user_act where account='" + MainRun.ID + "' and activity = " +ID + " and pass = 1";
					ps.println(sql);
					if(netReader.readLine().equals("true")) {
						new ErrorTip("提示", "报名审核中，还无法签退");
						return;
					}
					
					//查看该用户是否已经签到
					sql = "select account from sign where account='" + MainRun.ID + "' and activity = " +ID + " and start = 1";
					ps.println(sql);
					if(netReader.readLine().equals("true")) {
						new ErrorTip("提示", "未签到，无法签退");
						return;
					}
					
					//查看该用户是否已经发送了签退请求
					sql = "select account from sign where account='" + MainRun.ID + "' and activity = " +ID + " and end = 1";
					ps.println(sql);
					
					if(netReader.readLine().equals("true")) {
						sql = "insert into sign values(default, '" + MainRun.ID + "', '" 
								+ userName + "', " +ID + ", '" + info[0] +  "', 0, 1, 0) ";
						ps.println(sql);
						new ErrorTip("提示", "已发送签退申请");
					}
					else {
						new ErrorTip("提示", "已发送过签退申请");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_2.setBounds(573, 449, 97, 23);
		layeredPane.add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(525, 101, 226, 263);
		layeredPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea(info[7]);
		scrollPane.setViewportView(textArea);
		try {
			
			//查询该账号是否为签到员
			String sql = "select activity from user_act where pass=1 and account='" + MainRun.ID + "'";
			ps.println(sql);
			while(!(line = netReader.readLine()).equals("数据发送完成！！！")) {
				if(ID.equals(getID(line)))
					button.setVisible(false);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	//获取活动的编号
	private String getID(String str) {
		char[] chs = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chs.length; i++) {
			while(chs[i] >= '0' && chs[i] <= '9') {
				sb.append(chs[i]);
				i++;
				if(i > chs.length)
					break;
			}
			if(sb.length() > 0) {
				return sb.toString();
			}
		}
		return "false";
	}
}
