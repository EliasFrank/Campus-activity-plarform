package pu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewActivity extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private String name;
	private String ratedNumber;
	private String credit;
	private String startTime;
	private String endTime;
	private String place;
	private String brief;
	
	private static Socket s = null;
	private static PrintStream ps = null;
	private static BufferedReader netReader = null;
	private JTextArea textArea;
	private JButton button;
	
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
	public NewActivity() {
		setResizable(false);
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
		setBounds(400, 150, 987, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u6D3B\u52A8\u540D\u79F0\uFF1A");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(82, 109, 110, 30);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u6D3B\u52A8\u989D\u5B9A\u4EBA\u6570\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(44, 165, 175, 30);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u6D3B\u52A8\u5B66\u5206\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		label_2.setBounds(82, 219, 110, 30);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u6D3B\u52A8\u5F00\u59CB\u65F6\u95F4\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 20));
		label_3.setBounds(40, 286, 152, 30);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u6D3B\u52A8\u7ED3\u675F\u65F6\u95F4\uFF1A");
		label_4.setFont(new Font("宋体", Font.PLAIN, 20));
		label_4.setBounds(40, 349, 152, 30);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("\u521B\u5EFA\u65B0\u6D3B\u52A8");
		label_5.setFont(new Font("宋体", Font.PLAIN, 25));
		label_5.setBounds(415, 28, 198, 40);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("\u6D3B\u52A8\u5730\u70B9\uFF1A");
		label_6.setFont(new Font("宋体", Font.PLAIN, 20));
		label_6.setBounds(82, 401, 110, 30);
		contentPane.add(label_6);
		
		JLabel label_7 = new JLabel("\u6D3B\u52A8\u8BF4\u660E");
		label_7.setFont(new Font("宋体", Font.PLAIN, 20));
		label_7.setBounds(707, 96, 110, 30);
		contentPane.add(label_7);
		
		textArea = new JTextArea();
		textArea.setBounds(628, 153, 271, 278);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(215, 112, 200, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(215, 168, 200, 30);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(215, 226, 200, 30);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		//添加聚焦监听，给予用户提示
		textField_3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(textField_3.getText())) {
					textField_3.setText("按此格式填写：2019-08-14 14:00:00");
					textField_3.setForeground(Color.GRAY);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if("按此格式填写：2019-08-14 14:00:00".equals(textField_3.getText())) {
					textField_3.setText("");
					textField_3.setForeground(Color.black);
				}
			}
		});
		textField_3.setColumns(10);
		textField_3.setBounds(215, 286, 200, 30);
		contentPane.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setForeground(Color.BLACK);
		textField_4.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(textField_4.getText())) {
					textField_4.setText("按此格式填写：2019-08-14 14:00:00");
					textField_4.setForeground(Color.GRAY);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if("按此格式填写：2019-08-14 14:00:00".equals(textField_4.getText())) {
					textField_4.setText("");
					textField_4.setForeground(Color.black);
				}
			}
		});
		textField_4.setColumns(10);
		textField_4.setBounds(215, 349, 200, 30);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(215, 401, 200, 30);
		contentPane.add(textField_5);
		
		button = new JButton("\u521B\u5EFA\u65B0\u6D3B\u52A8");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isRight()) {//isRight函数用于判断用户输入的信息是否符合规范
					String sql = createSql();
					ps.println(sql);
					dispose();
					new ErrorTip("提示", "活动提交成功");
					MainWin mw = new MainWin();
					mw.setLogButton(false);
					mw.setUnionButton(true);
					mw.setVisible(true);
				}
			}
			private String createSql() {
				String sql = "insert into activities values (default, '" 
						+ name + "', " + ratedNumber + ", 0, " + credit + ", '" 
						+ startTime + "', '" + endTime + "', '" + place + "', '" 
						+ brief + "', 1, 0)";
				return sql;
			}
			private boolean isRight() {
				name = textField.getText();
				ratedNumber = textField_1.getText();
				credit = textField_2.getText();
				startTime = textField_3.getText();
				endTime = textField_4.getText();
				place = textField_5.getText();
				brief = textArea.getText();
				if("".equals(name) || "".equals(ratedNumber) || 
						"".equals(credit) || "".equals(startTime) || 
						"".equals(endTime) || "".equals(place) || 
						"".equals(brief)) {
					new ErrorTip("提示", "请完整填写所有信息");
					return false;
				}
				if(name.length() > 20) {
					new ErrorTip("提示", "名称太长");
					return false;
				}
				try {
					int num = Integer.parseInt(ratedNumber);
					if(num < 0 || num > 1000) {
						new ErrorTip("提示", "人数信息错误");
						return false;
					}
				}catch (Exception e) {
					new ErrorTip("提示", "人数信息错误");
					return false;
				}
				String regex = "\\d{4}[-][0-1]\\d[-][0-3]\\d[ ][0-2]\\d[:|：][0-6]\\d[:|：][0-6]\\d";
				if(!(startTime.matches(regex) || endTime.matches(regex))) {
					new ErrorTip("提示", "日期格式不对");
					return false;
				}
				//将用户输入的中文符号转换成英文符号
				startTime.replace("：", ":");
				endTime.replace("：", ":");
				//学分格式也要符合规范
				regex = "\\d+[\\.]\\d{2}";//
				if(!credit.matches(regex)) {
					new ErrorTip("提示", "学分格式不对");
					return false;
				}
				if(place.length() > 50) {
					new ErrorTip("提示", "地点名称太长");
					return false;
				}
				if(brief.length() > 100) {
					new ErrorTip("提示", "简介太长");
					return false;
				}
				return true;
			}
		});
		button.setBounds(443, 460, 147, 30);
		contentPane.add(button);
	}

}
