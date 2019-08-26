package pu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class AdminMainWin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainWin frame = new AdminMainWin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminMainWin() {
		setTitle("\u7BA1\u7406\u5458\u754C\u9762");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 700, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//设置背景图片
		Container container = this.getContentPane();
		JPanel jPanel = new JPanel();
		JPanel panelTop=new JPanel();
		
		ImageIcon backgroundIcon = new ImageIcon("书四叶草.jpg");
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.getLayeredPane().add(backgroundLabel,new Integer(Integer.MIN_VALUE));
        panelTop=(JPanel)this.getContentPane();
        
        //panel和panelTop设置透明
        panelTop.setOpaque(false);
        jPanel.setOpaque(false);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u6B22\u8FCE\u60A8\u7684\u5230\u6765");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(279, 10, 174, 31);
		contentPane.add(label);
		
		JButton button = new JButton("\u5BA1\u6838\u6D3B\u52A8");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开审核活动窗口
				dispose();
				new CheckActivity().setVisible(true);
			}
		});
		button.setBounds(39, 309, 97, 30);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u5BA1\u6838\u5B66\u5206");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开审核学分窗口
				dispose();
				new CheckCredit().setVisible(true);
			}
		});
		button_1.setBounds(161, 309, 97, 30);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("\u6307\u5B9A\u5B66\u751F\u4F1A\u6210\u5458");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开指定学生会成员的窗口
				dispose();
				new CreateUnion().setVisible(true);
			}
		});
		button_2.setBounds(292, 309, 150, 30);
		contentPane.add(button_2);
		
		JLabel label_1 = new JLabel("\u52B3\u52A8\u662F\u6709\u795E\u5947\u529B\u91CF\u7684\u6C11\u95F4\u6559\u80B2\u5B66");
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(117, 71, 320, 30);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("\u7ED9\u6211\u4EEC\u5F00\u8F9F\u4E86\u6559\u80B2\u667A\u6167\u7684\u65B0\u6E90\u6CC9\u3002");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel.setBounds(117, 100, 348, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8FD9\u79CD\u6E90\u6CC9\u662F\u4E66\u672C\u6559\u80B2\u7406\u8BBA\u6240\u4E0D\u77E5\u9053\u7684\u3002");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(88, 135, 375, 30);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u6211\u4EEC\u6DF1\u4FE1\uFF0C\u53EA\u6709\u901A\u8FC7\u6709\u6C57\u6C34\uFF0C\u6709\u8001\u8327\u548C\u75B2\u4E4F\u4EBA\u7684\u52B3\u52A8\uFF0C");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(117, 162, 498, 30);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u4EBA\u7684\u5FC3\u7075\u624D\u4F1A\u53D8\u5F97\u654F\u611F\u3001\u6E29\u67D4\u3002");
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(117, 193, 461, 30);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u901A\u8FC7\u52B3\u52A8\uFF0C\u4EBA\u624D\u5177\u6709\u7528\u5FC3\u7075\u53BB\u8BA4\u8BC6\u5468\u56F4\u4E16\u754C\u7684\u80FD\u529B\u3002");
		lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_4.setBounds(117, 228, 498, 30);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\u2014\u2014\u82CF\u970D\u59C6\u6797\u65AF\u57FA");
		lblNewLabel_5.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_5.setBounds(457, 256, 178, 30);
		contentPane.add(lblNewLabel_5);
		
		JButton button_3 = new JButton("\u53D6\u6D88\u5B66\u751F\u4F1A\u6210\u5458\u8EAB\u4EFD");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开删除学生会成员的窗口
				dispose();
				new DeleteUnion().setVisible(true);
			}
		});
		button_3.setBounds(481, 309, 150, 30);
		contentPane.add(button_3);
	}
}
