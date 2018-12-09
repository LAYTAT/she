package team.ecust.she.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import team.ecust.she.controller.RegisterMembership;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
/*注册界面类**/
public class Register extends JPanel {


	
	/*类变量声明*/
	/**@param    password 会员密码
	 * 	<br>				  studentID 学生学号
	 *  <br>                ID  身份证号码
	 * 	<br>			  Email 学生电子邮箱
	 * <br>             
	 * <br>
	 * */
	private JTextField studentID;     								//学号
	private JTextField eMail;        									//邮箱
	private JTextField ID;												//身份证号码
	private JPasswordField password;						//密码					
	private JPasswordField passwordConfirm;      //确认密码                                       
    private JTextField Alias;                                           //昵称

	
	/**@param    password 会员密码
	 * 	<br>				  studentID 学生学号
	 *  <br>                ID  身份证号码
	 * 	<br>			  Email 学生电子邮箱
	 * */

	public String getStudentID(){
		 return studentID.getText();
	}
	public String getPassword(){
		return new String(password.getPassword());
	}
	public String getCfmPassword(){
		return new String(passwordConfirm.getPassword());
	}
	public String getID(){
		return ID.getText();
	}
	public String getEmail(){
		return eMail.getText();
	}
	public String getAlias(){
		return Alias.getText();
	}
	
	/**
	 * Create the panel.
	 */
	public Register() {
	//display();
	}
	/*
	 *显示注册界面 */
	public void display(){
//		int width =1920;
//		int height =1080;
	 int width = getWidth();
     int height =getHeight();		
		
	
		int subPanelWidth = 630;
		int subPanelHeight =600;  
			
		setBorder(new EmptyBorder((height-subPanelHeight)/2, (width-subPanelWidth)/2, (height-subPanelHeight)/2, (width-subPanelWidth)/2));
		//开始摆放部件
		JPanel mainPanel = new JPanel();   //主组件panel
		mainPanel.setPreferredSize(new Dimension(630, 630));
		mainPanel.setLocation(0, 0);
		add(mainPanel);
		mainPanel.setLayout(new GridLayout(11, 1, 10, 25));   //11行
		
		JPanel panel_Logo = new JPanel();
		mainPanel.add(panel_Logo);
		
		JLabel label_Logo = new JLabel("");
		label_Logo.setIcon(new ImageIcon(Register.class.getResource("/team/ecust/she/resource/image/window.png")));
		panel_Logo.add(label_Logo);
		
		JPanel panel_Hello = new JPanel();
		mainPanel.add(panel_Hello);
		
		JLabel lblsecndhandecust = new JLabel("  同学你好,欢迎你注册SecndHandEcust信息平台");
		panel_Hello.add(lblsecndhandecust);
		lblsecndhandecust.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		
		JPanel panel_Welcome = new JPanel();
		mainPanel.add(panel_Welcome);
		
		JLabel IDTextLabel = new JLabel("请输入您的以下信息用于我们检验身份:");
		panel_Welcome.add(IDTextLabel);
		IDTextLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		
		JPanel panel_ID = new JPanel();
		mainPanel.add(panel_ID);
		panel_ID.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel IDLable = new JLabel("  身份证号码:");
		IDLable.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		panel_ID.add(IDLable);
		
		ID = new JTextField();
		ID.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		panel_ID.add(ID);
		ID.setColumns(20);
		
		JPanel stuIdPanel = new JPanel();
		mainPanel.add(stuIdPanel);
		stuIdPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel stuIDLabel = new JLabel("  学号:");
		stuIDLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		stuIdPanel.add(stuIDLabel);
		
		studentID = new JTextField();
		studentID.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		studentID.setColumns(20);
		stuIdPanel.add(studentID);
		
		JPanel aliasPanel = new JPanel();
		mainPanel.add(aliasPanel);
		aliasPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel aliasLabel = new JLabel("  昵称:");
		aliasLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		aliasPanel.add(aliasLabel);
		
		Alias = new JTextField();
		Alias.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		Alias.setColumns(20);
		aliasPanel.add(Alias);
		
		JPanel Email_panel = new JPanel();
		mainPanel.add(Email_panel);
		Email_panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label_email = new JLabel("  电子邮箱:");
		label_email.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		Email_panel.add(label_email);
		
		eMail = new JTextField();
		eMail.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		eMail.setColumns(20);
		Email_panel.add(eMail);
		
		JPanel pwdPanel = new JPanel();
		mainPanel.add(pwdPanel);
		pwdPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label_stuID = new JLabel("  登陆密码(字母,数字 下滑线等):");
		label_stuID.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		pwdPanel.add(label_stuID);
		
		password = new JPasswordField();
		password.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		password.setColumns(20);
		pwdPanel.add(password);
		
		
		JPanel pwdConfirmPanel = new JPanel();
		mainPanel.add(pwdConfirmPanel);
		pwdConfirmPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel pwdConfirmLabel = new JLabel("  确认密码:");
		pwdConfirmLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		pwdConfirmPanel.add(pwdConfirmLabel);
		
		passwordConfirm = new JPasswordField();
		passwordConfirm.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		passwordConfirm.setColumns(20);
		pwdConfirmPanel.add(passwordConfirm);
		
		
		JPanel button_panel = new JPanel();
		mainPanel.add(button_panel);
		button_panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton ReturnToLoginButton = new JButton("返回登陆");
		ReturnToLoginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Index index = Index.getInstance();
				Login login=new Login();
				index.showInCard(login);
				login.display();
			}
		});
		ReturnToLoginButton.setBackground(SystemColor.textHighlight);
		ReturnToLoginButton.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		ReturnToLoginButton.setForeground(Color.WHITE);
		button_panel.add(ReturnToLoginButton);
		
		JButton IdentityCheckButton = new JButton("校验并注册");
		IdentityCheckButton.setBackground(SystemColor.textHighlight);
		IdentityCheckButton.addMouseListener(new RegisterMembership<Register>(this));
		IdentityCheckButton.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		IdentityCheckButton.setForeground(Color.WHITE);
		button_panel.add(IdentityCheckButton);
		
		///*底部标签图片*/
//		JPanel panel_Logo_down = new JPanel();
//		panel.add(panel_Logo_down);
//	/
//		JLabel label_Logo_down = new JLabel("");
//		label_Logo_down.setIcon(new ImageIcon(Rigister.class.getResource("/team/ecust/she/resource/image/window_down.png")));
//		panel_Logo_down.add(label_Logo_down);
//

		

		
		
		
	}
	
	public enum Fonts {
		/*登陆框主要字体*/
		LOGIN_MAIN_FONT( new Font("微软雅黑", Font.PLAIN,20) ),
		LOGIN_LABEL_FONT( new Font("等线", Font.PLAIN, 18));
		
		
		/**提示框文字的字体*/
		//PROMPT_BOX(new Font("微软雅黑", Font.BOLD, 30)),
		
		/**顶栏标题的字体*/
		//SHE_TITLE(new Font("黑体", Font.PLAIN, 28)),
		
		/**顶栏昵称的字体*/
		//TOP_BAR_NICKNAME(new Font("宋体", Font.PLAIN, 20)),
		
		
		
		/**枚举的字体变量*/
		private Font font;
		
		/**
		 * 枚举的构造函数，用于初始化字体变量。
		 * @param color 需要设置的字体对象
		 */
		private Fonts(Font font) {
			this.font = font;
		}
		
		/**
		 * 获取枚举的字体对象。
		 * @return 当前枚举常量的字体对象
		 */
		public Font getFont() {
			return font;
		}
	}
}



