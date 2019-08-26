package pu;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterWin extends JFrame{
	private JTextField txtpng;
	private JTextField nameField;
	private JTextField ageField;
	private JTextField accountField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JRadioButton adButton;
	private JRadioButton userButton;
	private JButton regButton;
	
	private String name;
	private String age;
	private String gender;
	private String account;
	private String password;
	private boolean isAdmin;
	private String path;
	
	//获取流对象及其输入流和输出流，并且对其输入流和输出流进行装饰
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
	public RegisterWin(){
		addWindowListener(new WindowAdapter() {
			//窗口关闭后新建一个登陆窗口
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new LogWin();
			}
		});
		this.setTitle("\u6CE8\u518C");
		this.setBounds(600,200,300,450);
		this.setResizable(false);
		this.addSome();
		this.setVisible(true);
	}
	@SuppressWarnings("deprecation")
	/*
	 * isRight()函数用于对该窗口的输入栏的各个信息进行检测
	 * 如果有不合规范的信息则弹窗提示，并返回false
	 * 如果信息都符合规范，则返回true
	 * */
	private boolean isRight() {
		name = nameField.getText();
		password = passwordField.getText();
		age = ageField.getText();
		path = txtpng.getText();
		account = accountField.getText();
		
		String password_2 = passwordField_1.getText();
		
		//不能有信息为空
		if("".equals(name) || "".equals(age) || 
				"".equals(gender) || "".equals(path) 
				|| "".equals(password) || "".equals(account)) {
			new ErrorTip("提示", "请填写必要信息");
			return false;
		}
		try {
			//年龄必须是0-200之间的数字
			int a = Integer.parseInt(age);
			if(a < 0 || a > 200) {
				new ErrorTip("错误", "年龄不正确");
				return false;
			}
		}catch(Exception e){
			new ErrorTip("错误", "年龄不正确");
			return false;
		}
		//用正则表达式进行匹配，使账号符合规范
		//账号必须是以非0数字开头，6-12位的数字
		if( !account.matches("[1-9][0-9]{5,11}")) {
			new ErrorTip("错误", "账号格式不对");
			return false;
		}
		//选择按钮的信息
		if(radioButton.isSelected()) {
			gender = "男";
		}
		else if(radioButton_1.isSelected()) {
			gender = "女";
		}
		else if(radioButton_2.isSelected()) {
			gender = "保密";
		}
		else {
			//没有按钮被选择，则会返回false
			new ErrorTip("提示", "请选择性别");
			return false;
		}
		//必须选择注册用户账号还是注册管理员账号
		if(adButton.isSelected()) {
			isAdmin = true;
		}
		else if(userButton.isSelected()) {
			isAdmin = false;
		}
		else {
			new ErrorTip("提示", "请选择要注册账号的身份");
			return false;
		}
		//注册时，两次输入的密码要一致
		if(!password_2.equals(password)) {
			new ErrorTip("错误", "两次密码不一致");
			return false;
		}
		if(account.equals(MainRun.SuperAdminAccount)) {
			new ErrorTip("提示", "该账号已被使用");
			return false;
		}
		return isOccupy();
	}
	private boolean isOccupy() {
		//对储存用户或者管理员信息的数据库进行查询，如果发现该账号已被使用，则返回false
		if(isAdmin) {
			//发送sql语句，对服务端进行询问
			ps.println("select account from admin where account='" + account + "'");
			String s;
			try {
				s = netReader.readLine();//接受返回的数据，如果返回的是false，则该账号已被使用
				if("false".equals(s)) {
					new ErrorTip("提示", "该账号已被使用");
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			ps.println("select account from users where account='" + account + "'");
			String s;
			try {
				s = netReader.readLine();
				if("false".equals(s)) {
					new ErrorTip("提示", "该账号已被使用");
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	private void addSome() {//对注册窗口进行各种控件的添加
		Container container = this.getContentPane();
		JPanel jPanel = new JPanel();
		JPanel panelTop=new JPanel();
       
		//添加背景图片
		ImageIcon backgroundIcon = new ImageIcon("桥2.jpg");
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		regButton = new JButton("确认注册");
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isRight()) {//如果用户提供的所有信息都符合规范，则进行以下操作
					String sql = createSql();
					ps.println(sql);
					if(sql.contains("users")) {
						new ErrorTip("恭喜", "注册成功，欢迎前往登录");
						dispose();
						new LogWin();
					}
					else if(sql.contains("admin")) {
						//注册管理员账号需要超级管理员审核，审核通过后才能登
						new ErrorTip("提示", "账号已发送，等待超级管理员审核");
						dispose();
						new LogWin();
					}
				}
			}
			private String createSql() {
				//创建sql语句
				String sql;
				if(isAdmin) {
					if(path.equals("请输入图片路径,可以不上传头像"))
						sql = "insert into admin values(default,default,'" + name + "', "
							+ age + ", '"+ gender + "', '"+ account + "', '" 
								+ password + "', 0)";
					else {
						sql = "insert into admin values(default, '" + path + "','" + name + "', "
								+ age + ", '"+ gender + "', '"+ account + "', '" 
									+ password + "', 0)";
					}
				}
				else {
					if(path.equals("请输入图片路径,可以不上传头像"))
						sql = "insert into users values(default,default,'" + name + "', "
							+ age + ", '"+ gender + "', '"+ account + "', '" 
								+ password + "', 0, 0, 0 , 0)";
					else {
						sql = "insert into users values(default, '" + path + ",'" + name + "', "
								+ age + ", '"+ gender + "', '"+ account + "', '" 
									+ password + "', 0, 0, 0, 0)";
					}
				}
				return sql;
			}
		});
		regButton.setBounds(90, 370, 100, 30);
		JLabel nameLabel = new JLabel("姓名：");
		nameLabel.setBounds(30, 76, 50, 30);
		JLabel ageLabel = new JLabel("年龄：");
		ageLabel.setBounds(30, 116, 50, 30);
		JLabel sexLabel = new JLabel("性别：");
		sexLabel.setBounds(30, 153, 50, 30);
		JLabel accountLabel = new JLabel("账号：");
		accountLabel.setBounds(30, 193, 50, 30);
		JLabel passwordLabel_1 = new JLabel("密码：");
		passwordLabel_1.setBounds(30, 233, 50, 30);
		JLabel passwordLabel_2 = new JLabel("确认密码：");
		passwordLabel_2.setBounds(10, 273, 70, 30);
		nameField = new JTextField();
		nameField.setBounds(90, 77, 176, 30);
		ageField = new JTextField();
		ageField.setBounds(90, 117, 176, 30);
		accountField = new JTextField();
		//加上聚焦监听，给用户提示
		accountField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(accountField.getText())) {
					accountField.setText("6-12位的数字，不能以0开头");
					accountField.setForeground(Color.GRAY);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if("6-12位的数字，不能以0开头".equals(accountField.getText())) {
					accountField.setText("");
				}
			}
		});
		accountField.setBounds(90, 194, 176, 30);
		passwordField = new JPasswordField();
		passwordField.setBounds(90, 234, 176, 30);
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(90, 274, 176, 30);
		adButton = new JRadioButton("管理员");
		adButton.setBounds(62, 327, 80, 20);
		userButton = new JRadioButton("用户");
		userButton.setBounds(186, 327, 80, 20);
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(adButton);
		bGroup.add(userButton);
		
		ButtonGroup bGroup2 = new ButtonGroup();
		bGroup.add(radioButton);
		bGroup.add(radioButton_1);
		bGroup.add(radioButton_2);
		
		nameLabel.setForeground(Color.white);
		ageLabel.setForeground(Color.white);
		sexLabel.setForeground(Color.white);
		accountLabel.setForeground(Color.white);
		passwordLabel_1.setForeground(Color.white);
		passwordLabel_2.setForeground(Color.white);
		backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		
        this.getLayeredPane().add(backgroundLabel,new Integer(Integer.MIN_VALUE));
        panelTop=(JPanel)this.getContentPane();
        
        //panel和panelTop设置透明
        panelTop.setOpaque(false);
        jPanel.setOpaque(false);
		getContentPane().setLayout(null);
        
		container.add(regButton);
		container.add(nameLabel);
		container.add(ageLabel);
		container.add(sexLabel);
		container.add(accountLabel);
		container.add(passwordLabel_1);
		container.add(passwordLabel_2);
		container.add(nameField);
		container.add(ageField);
		container.add(accountField);
		container.add(passwordField);
		container.add(passwordField_1);
		container.add(adButton);
		container.add(userButton);
		
		radioButton = new JRadioButton("\u7537");
		radioButton.setBounds(90, 157, 42, 23);
		getContentPane().add(radioButton);
		
		radioButton_1 = new JRadioButton("\u5973");
		radioButton_1.setBounds(148, 157, 42, 23);
		getContentPane().add(radioButton_1);
		
		radioButton_2 = new JRadioButton("\u4FDD\u5BC6");
		radioButton_2.setBounds(207, 157, 59, 23);
		getContentPane().add(radioButton_2);
		
		JLabel label = new JLabel("\u5934\u50CF");
		label.setForeground(Color.WHITE);
		label.setBounds(30, 34, 50, 30);
		getContentPane().add(label);
		
		txtpng = new JTextField();
		txtpng.setText("\u5730\u7403.png");
		txtpng.setEditable(false);
		txtpng.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(txtpng.getText())) {
					txtpng.setText("请输入图片路径,可以不上传头像");
					txtpng.setForeground(Color.GRAY);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if("请输入图片路径,可以不上传头像".equals(txtpng.getText())) {
					txtpng.setText("");
				}
			}
		});
		txtpng.setBounds(90, 36, 176, 31);
		getContentPane().add(txtpng);
		txtpng.setColumns(10);
	}
}
