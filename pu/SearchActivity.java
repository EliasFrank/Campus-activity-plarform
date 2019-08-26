package pu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class SearchActivity extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JList list;
	private JButton button;
	private JButton button_1;
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
	public SearchActivity() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				MainWin mw = new MainWin();
				if(MainRun.ID != null)
					mw.setLogButton(false);
				mw.setUnionButton(MainRun.isUnion);
				mw.setVisible(true);
			}
		});
		setBounds(550, 150, 499, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u641C\u7D22\u6D3B\u52A8");
		label.setFont(new Font("����", Font.PLAIN, 20));
		label.setBounds(188, 23, 118, 30);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if("".equals(textField.getText())) {
					textField.setText("����������");
					textField.setForeground(Color.GRAY);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if("����������".equals(textField.getText())) {
					textField.setText("");
				}
			}
		});
		textField.setFont(new Font("����", Font.PLAIN, 20));
		textField.setBounds(106, 74, 200, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				if("".equals(name)) {
					new ErrorTip("��ʾ", "����������");
					return ;
				}
				list.setListData(new String[0]);
				values = null;
				addList(name);//������ƴ��ݸ�addList������ʹ���ѯ������ȷ�Ľ��
				list.setListData(values);
				list.setVisible(true);
			}
		});
		button.setIcon(new ImageIcon(SearchActivity.class.getResource("/icons/progress/ani/4@2x.png")));
		button.setBounds(334, 74, 32, 32);
		contentPane.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(70, 150, 333, 280);
		contentPane.add(scrollPane);
		
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("����", Font.PLAIN, 20));
		list.setModel(new AbstractListModel() {
			{
				addList(null);
			}
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		
		button_1 = new JButton("\u67E5\u770B\u8BE6\u60C5");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Object[] selectedValues =  list.getSelectedValues();
				if(selectedValues.length == 0) {
					new ErrorTip("��ʾ", "��ѡ��һ���");
					return;
				}
				//��ѯ�����ϸ��Ϣ
				new ActivityInfo(true, getID(selectedValues[0].toString(), "����")).setVisible(true);
			}
			//�õ�ĳ�����Ե���Ϣ
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
		button_1.setBounds(185, 453, 97, 30);
		contentPane.add(button_1);
	}
	public void addList(String s) {
		ArrayList<String> aList = new ArrayList<String>();
		String sql = null;
		try {
			if(s == null) 
				sql = "select * from activities where checked=1";
			else sql = "select * from activities where checked=1 and name='" + s + "'";
			ps.println(sql);
			String line;
			while(!(line = netReader.readLine()).equals("���ݷ�����ɣ�����")) {
				aList.add(line);
			}
			values = (String[]) aList.toArray(new String[0]);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
