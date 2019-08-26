package pu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ApplyCredit extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextArea textArea;
	private String name;
	private String credit;
	private String reason;
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
	public ApplyCredit() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				MainWin mw = new MainWin();
				mw.setLogButton(false);
				mw.setUnionButton(MainRun.isUnion);
				mw.setVisible(true);
			}
		});
		setBounds(520, 150, 458, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u5B66\u5206\u7533\u8BF7");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(170, 22, 114, 31);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u59D3\u540D\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(61, 100, 89, 30);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(162, 100, 165, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label_2 = new JLabel("\u5B66\u5206\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		label_2.setBounds(61, 169, 89, 30);
		contentPane.add(label_2);
		
		textField_1 = new JTextField();
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if("请精确到小数点后两位".equals(textField_1.getText())) {
					textField_1.setText("");
					textField_1.setForeground(Color.black);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(textField_1.getText())) {
					textField_1.setText("请精确到小数点后两位");
					textField_1.setForeground(Color.gray);
				}
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(162, 172, 165, 30);
		contentPane.add(textField_1);
		
		JLabel label_3 = new JLabel("\u7406\u7531");
		label_3.setFont(new Font("宋体", Font.PLAIN, 20));
		label_3.setBounds(189, 230, 78, 30);
		contentPane.add(label_3);
		
		textArea = new JTextArea();
		textArea.setBounds(61, 272, 296, 143);
		contentPane.add(textArea);
		
		JButton button = new JButton("\u786E\u8BA4\u7533\u8BF7");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isRight()) {//isRight函数用于检测所有输入信息是否符合规范，符合规范才可以进行一下操作
					String sql = "insert into check_credit values (default, '" 
							+ MainRun.ID + "', '" + name + "', " + credit + ", '" + reason + "', 0)";
					ps.println(sql);
					new ErrorTip("提示", "学分申请已提交");
					dispose();
					MainWin mw = new MainWin();
					mw.setVisible(true);
					mw.setLogButton(false);
					mw.setUnionButton(MainRun.isUnion);
				}
			}
			private boolean isRight() {
				name = textField.getText();
				credit = textField_1.getText();
				reason = textArea.getText();
				//填写的信息不能为空
				if("".equals(name) || "".equals(credit) || "".equals(reason)) {
					new ErrorTip("提示", "请填写所有信息");
					return false;
				}
				//用正则表达式判断学分的格式是否正确
				String regex = "\\d+\\.\\d{2}";
				//学分必须是多个数字开头，接一个小数点，小数点后带两位小数的格式
				if(!credit.matches(regex)) {
					new ErrorTip("提示", "学分信息不对");
					return false;
				}
				return true;
			}
		});
		button.setBounds(162, 431, 97, 31);
		contentPane.add(button);
	}
}
