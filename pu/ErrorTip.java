package pu;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//ͨ�öԻ���
public class ErrorTip {
	private Dialog dialog = null;
	private JLabel messageLabel = null;
	private JLabel picLable = null;
	private JButton OKButton = new JButton("�õ�");
	ErrorTip(){
		this("","");
	}
	ErrorTip(String title){
		this(title, "");
	}
	//����Ի���ı������ʾ��Ϣ���г�ʼ��
	ErrorTip(String title, String message){
		dialog = new Dialog(new JFrame(), title, true);
		dialog.setLayout(null);
		init(message);//��ʼ���ĺ���
		myEvent();//��Ӽ���
		dialog.setVisible(true);
	}
	private void myEvent() {
		//�����������ǹرնԻ���
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dialog.dispose();
			}
		});
		OKButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
	}
	private void init(String message) {
		dialog.setBounds(650,250,300,150);
		messageLabel = new JLabel(message);
		ImageIcon picIcon = new ImageIcon("������.png");
		picLable = new JLabel(picIcon);
		
		messageLabel.setBounds(80, 50, 200, 30);
		picLable.setBounds(20, 50, 50, 50);
		OKButton.setBounds(110, 90, 70, 30);
		
		dialog.add(messageLabel);
		dialog.add(picLable);
		dialog.add(OKButton);
	}
}
