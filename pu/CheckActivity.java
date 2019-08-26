package pu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionListener;


import javax.swing.event.ListSelectionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class CheckActivity extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JList list;
	private JButton button_1;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JList list_2;
	private JLabel lblctrl;
	private String ID;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CheckActivity() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new AdminMainWin().setVisible(true);
			}
		});

		Container container = this.getContentPane();
		JPanel jPanel = new JPanel();
		JPanel panelTop=new JPanel();
		
		ImageIcon backgroundIcon = new ImageIcon("船.jpeg");
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.getLayeredPane().add(backgroundLabel,new Integer(Integer.MIN_VALUE));
        panelTop=(JPanel)this.getContentPane();
        
        //panel和panelTop设置透明
        panelTop.setOpaque(false);
        jPanel.setOpaque(false);
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds(500, 200, 626, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("\u8BF7\u9009\u62E9\u8981\u901A\u8FC7\u5BA1\u6838\u7684\u6D3B\u52A8");
		label.setBounds(173, 10, 303, 46);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("楷体", Font.PLAIN, 20));
		contentPane.add(label);
		
		button_1 = new JButton("\u786E\u8BA4");
		button_1.addActionListener(new ActionListener() {
			//同意某些活动发布
			public void actionPerformed(ActionEvent e) {
				
				Object[] selectedValues =  list_2.getSelectedValues();
				if(selectedValues.length == 0) {
					new ErrorTip("提示", "请选择活动");
					return;
				}
				for (int i = 0; i < selectedValues.length; i++) {
					String sql = "update activities set checked=1 where id=" 
							+ getID(selectedValues[i].toString());
					ps.println(sql);
				}
				new ErrorTip("恭喜", "审核活动成功！");
				list_2.setListData(new String[0]);
				values = null;
				addList();
				list_2.setListData(values);
				list_2.setVisible(true);
			}
			//获取活动编号
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
		});
		button_1.setFont(new Font("宋体", Font.PLAIN, 20));
		button_1.setBounds(364, 339, 100, 28);
		contentPane.add(button_1);
		
		JLabel label_1 = new JLabel("\u662F\u5426\u4F7F\u8FD9\u4E9B\u6D3B\u52A8\u901A\u8FC7\u5BA1\u6838\uFF1F");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(111, 338, 243, 30);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(SuperAdmin.class.getResource("/org/eclipse/jface/dialogs/images/message_warning.png")));
		lblNewLabel.setBounds(95, 338, 19, 24);
		contentPane.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 66, 523, 216);
		contentPane.add(scrollPane);
		
		list_2 = new JList();
		
		list_2.setFont(new Font("楷体", Font.PLAIN, 20));
		list_2.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
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
		scrollPane.setViewportView(list_2);
		
		lblctrl = new JLabel("\u8BF7\u6309Ctrl\u952E\u4EE5\u5B9E\u73B0\u591A\u9009");
		lblctrl.setFont(new Font("宋体", Font.PLAIN, 20));
		lblctrl.setBounds(192, 292, 249, 37);
		contentPane.add(lblctrl);
	}
	public void addList() {
		ArrayList<String> aList = new ArrayList<String>();
		try {
			ps.println("select * from activities where checked=0");
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
