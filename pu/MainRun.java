package pu;

public class MainRun {
		public static String ID = null;//用于记录用户的账号
		public static boolean isCheckMan = false;//辨别用户是否为签到员
		public static boolean isUnion = false;//辨别用户是否为学生会成员
		
		//超级管理员的账号密码，普通用户和管理员不能注册并使用
		//超级管理员用于审核管理员账户，处理他们的账号申请
		public static final String SuperAdminAccount = "1972341610";
		public static final String SuperAdminPass = "1";

	public static void main(String[] args) {
		//用于启动项目
		new LogWin();
		//new AddSignman().setVisible(true);
		//new ActivityInfo(true, "1").setVisible(true);
		//new SignOff().setVisible(true);
		//new MainWin().setVisible(true);
		//new AdminMainWin().setVisible(true);
		//new RegisterWin();
		//new SuperAdmin().setVisible(true);
		//new CheckActivity().setVisible(true);
		//new CreateUnion().setVisible(true);
		//new DeleteUnion().setVisible(true);
		//new CheckCredit().setVisible(true);
	}
}
