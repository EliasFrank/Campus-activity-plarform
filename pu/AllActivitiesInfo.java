package pu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AllActivitiesInfo extends JFrame {

	private JPanel contentPane;
	private String[] values = null;
	private static Socket s = null;
	private static PrintStream ps = null;
	private static BufferedReader netReader = null;
	private JComboBox comboBox;
	private JList list;
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
	public AllActivitiesInfo() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//返回主界面
				dispose();
				MainWin mw = new MainWin();
				if(MainRun.ID != null)
					mw.setLogButton(false);
				mw.setUnionButton(MainRun.isUnion);
				mw.setVisible(true);
			}
		});
		setBounds(550, 170, 483, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u6240\u6709\u6D3B\u52A8");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(177, 22, 140, 30);
		contentPane.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(62, 142, 351, 252);
		contentPane.add(scrollPane);
		
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("宋体", Font.PLAIN, 20));
		list.setModel(new AbstractListModel() {
			{
				addList(null);//初始化values数组
			}
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		
		button = new JButton("\u67E5\u770B\u8BE6\u60C5");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Object[] selectedValues =  list.getSelectedValues();
				if(selectedValues.length == 0) {
					new ErrorTip("提示", "请选择一个活动");
					return;
				}
				//给予活动编号，创建活动详细信息的窗口
				new ActivityInfo(true, getID(selectedValues[0].toString(), "活动编号")).setVisible(true);
			}
			private String getID(String str, String col) {
				char[] chs = str.toCharArray();
				StringBuilder sb = new StringBuilder();
				for (int i = str.indexOf(col); i < chs.length; i++) {
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
		button.setBounds(174, 414, 97, 30);
		contentPane.add(button);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				list.setListData(new String[0]);
				values = null;
				if(index == 1) {
					addList("signUpTime");
				}
				else if(index == 2) {
					addList("credit");
				}
				else if(index == 3) {
					addList("ratedNumber");
				}
				else if(index == 4) {
					addList("nowNumber");
				}
				list.setListData(values);
				list.setVisible(true);
			}
		});
		comboBox.setFont(new Font("宋体", Font.PLAIN, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] 
				{"\u6392\u5E8F", "\u6309\u65F6\u95F4", "\u6309\u5B66\u5206",
						"\u6309\u989D\u5B9A\u4EBA\u6570", "\u6309\u5DF2\u53C2\u52A0\u4EBA\u6570"}));
		comboBox.setBounds(135, 80, 196, 30);
		contentPane.add(comboBox);
	}
	private void addList(String order) {
		//order代表查询结果的顺序，可以按时间，按学分，按人数进行排序
		ArrayList<String> aList = new ArrayList<String>();
		String sql = null;
		try {
			if(order == null) 
				sql = "select * from activities where checked=1";
			else sql = "select * from activities where checked=1 order by " + order + " desc";
			ps.println(sql);
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
