package pu;
import java.io.*;
import java.net.*;

public class NetConnection {
	private static Socket s = null;//ʹ��������ʹ��ͬһ����
	static {
		try {
			s = new Socket("192.168.43.145", 12345);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Socket getSocket() {
		return s;
	}
}
