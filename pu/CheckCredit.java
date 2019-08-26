package pu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/*
 * �������Ա����ͬһ��ͬѧ��ѧ��ʱ������ֲ����޸�
 * */
public class CheckCredit extends JFrame {

	private JPanel contentPane;
	private String[] values = null;
	private static Socket s = null;
	private static PrintStream ps = null;
	private static BufferedReader netReader = null;
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
	@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
	public CheckCredit() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				new AdminMainWin().setVisible(true);
			}
		});
		setBounds(300, 150, 958, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u8BF7\u5728\u4E0B\u65B9\u9009\u62E9\u4F60\u540C\u610F\u53D1\u653E\u7684\u5B66\u5206");
		label.setFont(new Font("����", Font.PLAIN, 20));
		label.setBounds(300, 10, 339, 30);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("\u60A8\u786E\u5B9A\u540C\u610F\u53D1\u653E\u9009\u4E2D\u7684\u7528\u6237\u7684\u5B66\u5206\u5417\uFF1F");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(191, 403, 380, 30);
		contentPane.add(lblNewLabel);
		
		button = new JButton("\u786E\u8BA4");
		button.addActionListener(new ActionListener() {
			private String sql = null;
			public void actionPerformed(ActionEvent e) {
				Object[] selectedValues =  list.getSelectedValues();
				if(selectedValues.length == 0) {
					new ErrorTip("��ʾ", "��ѡ��һ��ѡ��");
					return;
				}
				for (int i = 0; i < selectedValues.length; i++) {
					//��ѯĳ���˻���ѧ��
					sql = "select credit from users where account='" 
							+ getNumber(selectedValues[i].toString(), "�˺ţ�") + "'";
					ps.println(sql);
					try {
						float nowCredit = Float.parseFloat(netReader.readLine());
						//�����Ҫ�����ѧ��
						float addCredit = Float.parseFloat(
								getNumber(selectedValues[i].toString(), "������"));
						float sum = nowCredit + addCredit;
						//��ѧ�ֵ��ܺʹ����������������
						sql = "update users set credit=" + sum + " where account='"
								+ getNumber(selectedValues[i].toString(), "�˺ţ�") + "'";
						ps.println(sql);
						//����ѧ��������Ϊ��ͨ��
						sql = "update check_credit set pass=1 where id=" 
								+ getNumber(selectedValues[i].toString(), "��ţ�");
						ps.println(sql);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				list.setListData(new String[0]);
				values = null;
				addList();
				list.setListData(values);
				list.setVisible(true);
			}
			//��ȡ�ַ�����ĳЩ����
			private String getNumber(String str, String col) {
				//str��ĳ���ض���ʽ���е��ַ�����col��Ҫ��ѯ�����ݵ�ǰ��һ����ʾ
				char[] chs = str.toCharArray();
				StringBuilder sb = new StringBuilder();
				for (int i = str.indexOf(col); i < chs.length; i++) {//��ȡ����ʾ���ڵ�λ�ã�Ȼ��ѭ��ȡ��
					while((chs[i] >= '0' && chs[i] <= '9') || chs[i] == '.') {
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
		button.setBounds(589, 405, 97, 30);
		contentPane.add(button);
		
		JLabel lblctrl = new JLabel("\u6309Ctrl\u952E\u4EE5\u5B9E\u73B0\u591A\u9009");
		lblctrl.setForeground(Color.DARK_GRAY);
		lblctrl.setFont(new Font("����", Font.PLAIN, 20));
		lblctrl.setBounds(341, 363, 230, 30);
		contentPane.add(lblctrl);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(94, 66, 758, 271);
		contentPane.add(scrollPane);
		
		list = new JList();
		list.setFont(new Font("����", Font.PLAIN, 20));
		list.setModel(new AbstractListModel() {
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
		scrollPane.setViewportView(list);
	}
	public void addList() {
		ArrayList<String> aList = new ArrayList<String>();
		try {
			String sql = "select * from check_credit where pass=0";
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
