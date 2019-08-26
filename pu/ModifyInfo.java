package pu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ModifyInfo extends JFrame {

	private JPanel contentPane;
	private JButton button;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	private String name;
	private String gender;
	private String age;
	private String password;
	private String account;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JLabel lblNewLabel;
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
	public ModifyInfo() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//�޸�����Ϣ�󣬷����ҵ���Ϣ����
				dispose();
				ps.println("select * from users where account ='" + MainRun.ID + "'");
				try {
					String line = netReader.readLine();
					String[] info = line.split(" ");
					new MyInfo(info[0], info[1], info[2], info[3], info[4], info[5]).setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		setBounds(550, 150, 380, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u59D3\u540D");
		label.setBounds(56, 74, 70, 30);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5E74\u9F84");
		label_1.setBounds(56, 114, 70, 30);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u6027\u522B");
		label_2.setBounds(56, 154, 70, 30);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u5BC6\u7801");
		label_3.setBounds(56, 244, 70, 30);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_4.setBounds(43, 284, 70, 30);
		contentPane.add(label_4);
		
		button = new JButton("\u786E\u8BA4\u4FEE\u6539");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isTrue()) {//isTrue���������ж��û���������Ϣ�Ƿ���Ϲ淶
					try {
						String sql = "update users set name='" + name + "', age="
							+ age + ", gender='"+ gender + "', password='" + password +"' where account='"+ MainRun.ID + "'";
						ps.println(sql);
					}catch (Exception e1) {
						e1.printStackTrace();
					}
					new ErrorTip("��ʾ", "������Ϣ���޸ĳɹ�");
					dispose();
					ps.println("select * from users where account ='" + MainRun.ID + "'");
					try {
						String line = netReader.readLine();
						String[] info = line.split(" ");
						new MyInfo(info[0], info[1], info[2], info[3], info[4], info[5]).setVisible(true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			public boolean isTrue() {
				name = textField.getText();
				age = textField_1.getText();
				password = passwordField.getText();
				String password_2 = passwordField_1.getText();
				if(radioButton.isSelected())
					gender = "��";
				else if(radioButton_1.isSelected())
					gender = "Ů";
				else if(radioButton_2.isSelected())
					gender = "����";
				else {
					new ErrorTip("����", "��ѡ���Ա�");
					return false;
				}
				if(name.equals("") || age.equals("") 
						|| password.equals("") || password_2.equals("")) {
					new ErrorTip("����", "�뽫��Ϣ������");
					return false;
				}
				String regex = "[0-9]{0,3}";//��������ʽƥ������
				if(!age.matches(regex)) {
					new ErrorTip("����", "�����ʽ���ԣ�");
					return false;
				}
				if(!password.equals(password_2)) {
					new ErrorTip("����", "�������벻һ��");
					return false;
				}
				return true;
			}
		});
		button.setBounds(116, 347, 119, 43);
		contentPane.add(button);
		
		JLabel label_5 = new JLabel("\u8BF7\u8C28\u614E\u7684\u4FEE\u6539\u4FE1\u606F");
		label_5.setFont(new Font("������", Font.PLAIN, 20));
		label_5.setBounds(106, 10, 172, 43);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("\u8D26\u53F7");
		label_6.setBounds(56, 194, 70, 30);
		contentPane.add(label_6);
		
		textField = new JTextField();
		textField.setBounds(135, 79, 100, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(135, 119, 100, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setText(MainRun.ID);
		textField_3.setEditable(false);
		textField_3.setBounds(136, 203, 100, 21);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(136, 253, 100, 21);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(135, 289, 100, 21);
		contentPane.add(passwordField_1);
		
		radioButton = new JRadioButton("\u7537");
		radioButton.setBounds(131, 158, 56, 23);
		contentPane.add(radioButton);
		
		radioButton_1 = new JRadioButton("\u5973");
		radioButton_1.setBounds(189, 158, 56, 23);
		contentPane.add(radioButton_1);
		
		radioButton_2 = new JRadioButton("\u4FDD\u5BC6");
		radioButton_2.setBounds(244, 158, 77, 23);
		contentPane.add(radioButton_2);
		
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(radioButton_2);
		bGroup.add(radioButton_1);
		bGroup.add(radioButton);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(43, 347, 58, 15);
		contentPane.add(lblNewLabel);
		
	}
}
