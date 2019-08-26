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
	
	//��ȡ��������������������������Ҷ��������������������װ��
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
			//���ڹرպ��½�һ����½����
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
	 * isRight()�������ڶԸô��ڵ��������ĸ�����Ϣ���м��
	 * ����в��Ϲ淶����Ϣ�򵯴���ʾ��������false
	 * �����Ϣ�����Ϲ淶���򷵻�true
	 * */
	private boolean isRight() {
		name = nameField.getText();
		password = passwordField.getText();
		age = ageField.getText();
		path = txtpng.getText();
		account = accountField.getText();
		
		String password_2 = passwordField_1.getText();
		
		//��������ϢΪ��
		if("".equals(name) || "".equals(age) || 
				"".equals(gender) || "".equals(path) 
				|| "".equals(password) || "".equals(account)) {
			new ErrorTip("��ʾ", "����д��Ҫ��Ϣ");
			return false;
		}
		try {
			//���������0-200֮�������
			int a = Integer.parseInt(age);
			if(a < 0 || a > 200) {
				new ErrorTip("����", "���䲻��ȷ");
				return false;
			}
		}catch(Exception e){
			new ErrorTip("����", "���䲻��ȷ");
			return false;
		}
		//��������ʽ����ƥ�䣬ʹ�˺ŷ��Ϲ淶
		//�˺ű������Է�0���ֿ�ͷ��6-12λ������
		if( !account.matches("[1-9][0-9]{5,11}")) {
			new ErrorTip("����", "�˺Ÿ�ʽ����");
			return false;
		}
		//ѡ��ť����Ϣ
		if(radioButton.isSelected()) {
			gender = "��";
		}
		else if(radioButton_1.isSelected()) {
			gender = "Ů";
		}
		else if(radioButton_2.isSelected()) {
			gender = "����";
		}
		else {
			//û�а�ť��ѡ����᷵��false
			new ErrorTip("��ʾ", "��ѡ���Ա�");
			return false;
		}
		//����ѡ��ע���û��˺Ż���ע�����Ա�˺�
		if(adButton.isSelected()) {
			isAdmin = true;
		}
		else if(userButton.isSelected()) {
			isAdmin = false;
		}
		else {
			new ErrorTip("��ʾ", "��ѡ��Ҫע���˺ŵ����");
			return false;
		}
		//ע��ʱ���������������Ҫһ��
		if(!password_2.equals(password)) {
			new ErrorTip("����", "�������벻һ��");
			return false;
		}
		if(account.equals(MainRun.SuperAdminAccount)) {
			new ErrorTip("��ʾ", "���˺��ѱ�ʹ��");
			return false;
		}
		return isOccupy();
	}
	private boolean isOccupy() {
		//�Դ����û����߹���Ա��Ϣ�����ݿ���в�ѯ��������ָ��˺��ѱ�ʹ�ã��򷵻�false
		if(isAdmin) {
			//����sql��䣬�Է���˽���ѯ��
			ps.println("select account from admin where account='" + account + "'");
			String s;
			try {
				s = netReader.readLine();//���ܷ��ص����ݣ�������ص���false������˺��ѱ�ʹ��
				if("false".equals(s)) {
					new ErrorTip("��ʾ", "���˺��ѱ�ʹ��");
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
					new ErrorTip("��ʾ", "���˺��ѱ�ʹ��");
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	private void addSome() {//��ע�ᴰ�ڽ��и��ֿؼ������
		Container container = this.getContentPane();
		JPanel jPanel = new JPanel();
		JPanel panelTop=new JPanel();
       
		//��ӱ���ͼƬ
		ImageIcon backgroundIcon = new ImageIcon("��2.jpg");
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		regButton = new JButton("ȷ��ע��");
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isRight()) {//����û��ṩ��������Ϣ�����Ϲ淶����������²���
					String sql = createSql();
					ps.println(sql);
					if(sql.contains("users")) {
						new ErrorTip("��ϲ", "ע��ɹ�����ӭǰ����¼");
						dispose();
						new LogWin();
					}
					else if(sql.contains("admin")) {
						//ע�����Ա�˺���Ҫ��������Ա��ˣ����ͨ������ܵ�
						new ErrorTip("��ʾ", "�˺��ѷ��ͣ��ȴ���������Ա���");
						dispose();
						new LogWin();
					}
				}
			}
			private String createSql() {
				//����sql���
				String sql;
				if(isAdmin) {
					if(path.equals("������ͼƬ·��,���Բ��ϴ�ͷ��"))
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
					if(path.equals("������ͼƬ·��,���Բ��ϴ�ͷ��"))
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
		JLabel nameLabel = new JLabel("������");
		nameLabel.setBounds(30, 76, 50, 30);
		JLabel ageLabel = new JLabel("���䣺");
		ageLabel.setBounds(30, 116, 50, 30);
		JLabel sexLabel = new JLabel("�Ա�");
		sexLabel.setBounds(30, 153, 50, 30);
		JLabel accountLabel = new JLabel("�˺ţ�");
		accountLabel.setBounds(30, 193, 50, 30);
		JLabel passwordLabel_1 = new JLabel("���룺");
		passwordLabel_1.setBounds(30, 233, 50, 30);
		JLabel passwordLabel_2 = new JLabel("ȷ�����룺");
		passwordLabel_2.setBounds(10, 273, 70, 30);
		nameField = new JTextField();
		nameField.setBounds(90, 77, 176, 30);
		ageField = new JTextField();
		ageField.setBounds(90, 117, 176, 30);
		accountField = new JTextField();
		//���Ͼ۽����������û���ʾ
		accountField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(accountField.getText())) {
					accountField.setText("6-12λ�����֣�������0��ͷ");
					accountField.setForeground(Color.GRAY);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if("6-12λ�����֣�������0��ͷ".equals(accountField.getText())) {
					accountField.setText("");
				}
			}
		});
		accountField.setBounds(90, 194, 176, 30);
		passwordField = new JPasswordField();
		passwordField.setBounds(90, 234, 176, 30);
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(90, 274, 176, 30);
		adButton = new JRadioButton("����Ա");
		adButton.setBounds(62, 327, 80, 20);
		userButton = new JRadioButton("�û�");
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
        
        //panel��panelTop����͸��
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
					txtpng.setText("������ͼƬ·��,���Բ��ϴ�ͷ��");
					txtpng.setForeground(Color.GRAY);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if("������ͼƬ·��,���Բ��ϴ�ͷ��".equals(txtpng.getText())) {
					txtpng.setText("");
				}
			}
		});
		txtpng.setBounds(90, 36, 176, 31);
		getContentPane().add(txtpng);
		txtpng.setColumns(10);
	}
}
