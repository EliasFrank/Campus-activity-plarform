package pu;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//跳过登录的提示界面
public class LeapForg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblAsdfaf;
	private JButton button_1;
	private JButton button;
	/**
	 * Create the dialog.
	 */
	public LeapForg(String message) {
		setResizable(false);
		setTitle("\u63D0\u793A");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(500, 200, 359, 218);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblAsdfaf = new JLabel(message);
		lblAsdfaf.setForeground(Color.RED);
		lblAsdfaf.setFont(new Font("宋体", Font.PLAIN, 20));
		lblAsdfaf.setBounds(54, 23, 264, 54);
		contentPanel.add(lblAsdfaf);
		
		button = new JButton("\u786E\u8BA4\u8DF3\u8FC7");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainWin mw = new MainWin();
				mw.setLogButton(true);
				mw.setUnionButton(false);
				mw.setVisible(true);
			}
		});
		button.setBounds(193, 121, 97, 23);
		contentPanel.add(button);
		
		button_1 = new JButton("\u53BB\u767B\u5F55");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LogWin();
			}
		});
		button_1.setBounds(50, 121, 97, 23);
		contentPanel.add(button_1);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(310, 5, 45, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBounds(360, 5, 71, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		this.setVisible(true);
	}
}
