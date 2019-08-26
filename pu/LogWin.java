package pu;
//登录界面
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

class LogWin {
	private JFrame logFrame = new JFrame("登录");
	private ImageIcon background = null;
	private JTextField accountField;
	private JPasswordField passwordField;
	private JRadioButton adButton;
	private JRadioButton userButton;
	private JButton logButton;
	private JButton regButton;
	private JButton button;
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
	
	LogWin(){
		init(logFrame);//初始化登录界面
		add(logFrame);//添加各种控件
		logFrame.setVisible(true);
	}
	private void init(JFrame jf) {
		jf.setBounds(600,200,300,250);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.getContentPane().setLayout(null);
	}
	private void add(JFrame jf) {
		Container container = jf.getContentPane();//获取容器对象
		
		background = new ImageIcon("地球.png");
		JLabel picLabel = new JLabel(background);
		JLabel welcomLabel = new JLabel("欢迎来到校园活动平台");
		JLabel accountLabel = new JLabel("账号：");
		JLabel passwordLabel = new JLabel("密码：");
		logButton = new JButton("登录");
		logButton.addActionListener(new ActionListener() {//登录按钮
			public void actionPerformed(ActionEvent e) {
				String account = accountField.getText();
				String password = passwordField.getText();
				//如果是超级管理员的账号和密码，则直接登录
				if(MainRun.SuperAdminAccount.equals(account) && MainRun.SuperAdminPass.equals(password)) {
					new SuperAdmin().setVisible(true);
					logFrame.dispose();
					return;
				}
				String sql = "";
				//需要用户选择登录的身份
				if(!(adButton.isSelected() || userButton.isSelected())) {
					new ErrorTip("提示", "请选择登陆身份");
					return;
				}
				else{
					if(adButton.isSelected()) {//如果想以管理员身份登录，则向管理员的表查询账号信息
						sql = "select * from admin where account='"
								+ account + "' and password='" + password + "' and can=1";
						ps.println(sql);
						try {
							String line = netReader.readLine();
							if(line.equals("false")) {//账号还在审核，就会返回false
								new ErrorTip("错误", "账号或密码错误，或还在审核");
								return;
							}
							else if(line.equals("true")) {
								//进入主界面
								logFrame.dispose();
								new AdminMainWin().setVisible(true);
							}
						}catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					else if(userButton.isSelected()) {
						sql = "select * from users where account='"+
								account + "' and password='" + password + "'";
						ps.println(sql);
						try {
							String line = netReader.readLine();
							if(line.equals("false")) {
								new ErrorTip("错误", "账号或密码错误");
								return;
							}
							else if(line.equals("true")) {
								MainRun.ID = account;
								ps.println("select isMemberOfUnion,checkMan from users where account='" 
										+ account + "'");
								String s = netReader.readLine();
								String[] temp  =s.split(" ");
								//初始化各种属性，方便以后的操作
								if("1".equals(temp[0])) {
									MainRun.isUnion = true;
								}
								else if("0".equals(temp[0])) {
									MainRun.isUnion = false;
								}
								else if("1".equals(temp[1])) {
									MainRun.isCheckMan = true;
								}
								else if("0".equals(temp[1])) {
									MainRun.isCheckMan = false;
								}
								logFrame.dispose();
								MainWin mw = new MainWin();
								mw.setLogButton(false);
								mw.setUnionButton(MainRun.isUnion);
								mw.setVisible(true);
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		regButton = new JButton("注册");
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logFrame.dispose();
				new RegisterWin();
			}
		});
		accountField = new JTextField(10);
		passwordField = new JPasswordField(10);
		adButton = new JRadioButton("管理员");
		userButton = new JRadioButton("用户");	
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(adButton);
		bGroup.add(userButton);
		
		picLabel.setBounds(0, 0, 105, 75);
		welcomLabel.setBounds(100, 30, 200, 20);
		accountLabel.setBounds(30, 80, 50, 20);
		passwordLabel.setBounds(30, 110, 50, 20);
		logButton.setBounds(10, 170, 80, 30);
		regButton.setBounds(100, 170, 80, 30);
		accountField.setBounds(80, 80, 150, 20);
		passwordField.setBounds(80, 110, 150, 20);
		adButton.setBounds(70, 130, 70, 30);
		userButton.setBounds(150, 130, 70, 30);
		
		container.add(picLabel);
		container.add(logButton);
		container.add(regButton);
		container.add(accountField);
		container.add(passwordField);
		container.add(accountLabel);
		container.add(passwordLabel);
		container.add(welcomLabel);
		container.add(adButton);
		container.add(userButton);
		
		button = new JButton("\u8DF3\u8FC7");//跳过登录
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logFrame.dispose();
				new LeapForg("不登录将无法参加活动").setVisible(true);
			}
		});
		button.setBounds(190, 170, 80, 30);
		logFrame.getContentPane().add(button);
	}
}
