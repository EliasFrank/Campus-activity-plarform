package pu;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//通用对话框
public class ErrorTip {
	private Dialog dialog = null;
	private JLabel messageLabel = null;
	private JLabel picLable = null;
	private JButton OKButton = new JButton("好的");
	ErrorTip(){
		this("","");
	}
	ErrorTip(String title){
		this(title, "");
	}
	//给你对话框的标题和提示信息进行初始化
	ErrorTip(String title, String message){
		dialog = new Dialog(new JFrame(), title, true);
		dialog.setLayout(null);
		init(message);//初始化的函数
		myEvent();//添加监听
		dialog.setVisible(true);
	}
	private void myEvent() {
		//两个监听都是关闭对话框
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
		ImageIcon picIcon = new ImageIcon("！警告.png");
		picLable = new JLabel(picIcon);
		
		messageLabel.setBounds(80, 50, 200, 30);
		picLable.setBounds(20, 50, 50, 50);
		OKButton.setBounds(110, 90, 70, 30);
		
		dialog.add(messageLabel);
		dialog.add(picLable);
		dialog.add(OKButton);
	}
}
