package pu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//作者信息栏
public class KonwUs extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public KonwUs() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				MainWin mw = new MainWin();
				if(MainRun.ID == null)
					mw.setLogButton(true);
				mw.setUnionButton(MainRun.isUnion);
				mw.setVisible(true);
			}
		});
		setBounds(500, 150, 496, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 496, 370);
		contentPane.add(layeredPane);
		layeredPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		layeredPane.setLayer(lblNewLabel, -1);
		lblNewLabel.setIcon(new ImageIcon("海螺.jpeg"));
		lblNewLabel.setBounds(0, 0, 496, 370);
		layeredPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u6B22\u8FCE\u60A8\u7684\u4F7F\u7528");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(176, 10, 156, 30);
		layeredPane.add(lblNewLabel_1);
		
		JLabel label = new JLabel("\u7248\u672C\uFF1A 1.0");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(72, 50, 138, 30);
		layeredPane.add(label);
		
		JLabel lblhl = new JLabel("\u4F5C\u8005\uFF1AHL");
		lblhl.setFont(new Font("宋体", Font.PLAIN, 20));
		lblhl.setBounds(72, 92, 122, 30);
		layeredPane.add(lblhl);
		
		JLabel lblNewLabel_2 = new JLabel("\u529F\u80FD\u4ECB\u7ECD");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(192, 132, 109, 30);
		layeredPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(87, 172, 305, 131);
		layeredPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textArea.setEditable(false);
		textArea.setText("1\u3001\u5E26\u6709\u767B\u5F55\u6CE8\u518C\u529F\u80FD \r\n 2\u3001\u7BA1\u7406\u5458   \r\n\t\u5BA1\u6838\u6D3B\u52A8\uFF08\u51B3\u5B9A\u5B66\u751F\u4F1A\u53D1\u5E03\u7684\u6D3B\u52A8\u662F\u5426\u901A\u8FC7\uFF09   \r\n\t\u5BA1\u6838\u5B66\u5206\uFF08\u51B3\u5B9A\u5B66\u751F\u7533\u8BF7\u7684\u5B66\u5206\u662F\u5426\u901A\u8FC7\uFF09  \r\n\t\u6307\u5B9A\u5B66\u751F\u4F1A\u6210\u5458   \r\n3\u3001\u666E\u901A\u7528\u6237  \r\n\t\u7533\u8BF7\u53C2\u52A0\u6D3B\u52A8   \r\n\t\u4FEE\u6539\u81EA\u5DF1\u7684\u4FE1\u606F   \r\n\t\u7533\u8BF7\u5B66\u5206   \r\n\t\u7B7E\u5230\u7B7E\u9000   \r\n4\u3001\u5B66\u751F\u4F1A   \r\n\t\u7533\u8BF7\u53D1\u5E03\u6D3B\u52A8   \r\n\t\u6307\u5B9A\u7B7E\u5230\u5458   \r\n5\u3001\u7B7E\u5230\u5458    \r\n\t\u7ED9\u5176\u4ED6\u7528\u6237\u8FDB\u884C\u7B7E\u5230\u7B7E\u9000 ");
		scrollPane.setViewportView(textArea);
	}
}
