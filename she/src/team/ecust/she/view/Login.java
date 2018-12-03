package team.ecust.she.view;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.MatteBorder;
import com.wis.pack.component.Photo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**登录界面类*/
@SuppressWarnings("serial")
public final class Login extends JPanel {
	/**类变量声明*/
	private JTextField memberNoTextField;
	
	private JPasswordField pwdTextField;
	
	//private Photo headPortrait;

	/**
	 * Create the panel.
	 */
	
	/**@param password 用户密码
	 * 		<br>			  MemNo 会员账号
	 * */
	public void setPassword(String password) {
		this.pwdTextField.setText(password);
	}
	public void setMemberNo(String MemNo){
		this.memberNoTextField.setText(MemNo);; 
	}
	public String getPassword(){
		return pwdTextField.getPassword().toString();
	}
	public String getMemberNo(){
		return memberNoTextField.getText();
	}
	
	
	public Login() {
		//display();
	}

	/**
	 * 
	 * @Param  
	 *  Login_Panel_width = idth(),  <br> Login_Panel_height= getheight(); 
	 *  <br>
	 * Sub_Panel_width(子面板宽度), <br>Sub_Panel _height(子面板高度),
	 *                 
	 *                  
	 */
	public void display()
	{
		int width = getWidth();
		int height = getHeight();
		int subPanelHeight = 600;  
		int subPanelWidth  = 500;
		
		setBackground(Color.gray);
		setLayout(new GridLayout(2, 1));
		setBorder(new EmptyBorder((height-subPanelHeight)/2, (width-subPanelWidth)/2, (height-subPanelHeight)/2, (width-subPanelWidth)/2));
		
		Photo photo =new Photo("src/team/ecust/she/resource/image/login.jpg");
		photo.setScaleFunction(true);
		photo.setDragFunction(true);
		photo.setRecoverFunction(true);
		add(photo);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
		panel.setLayout(null);
		add(panel);
		
		//开始摆放部件
		memberNoTextField = new JTextField();
		memberNoTextField.setToolTipText("账号");
		memberNoTextField.setFont(new Font("微软雅黑", Font.BOLD, 25));
		memberNoTextField.setBounds(100, 40, 300, 50);
		memberNoTextField.setColumns(20);
	    panel.add(memberNoTextField);

		pwdTextField = new JPasswordField();
		pwdTextField.setToolTipText("密码");
		pwdTextField.setFont(new Font("微软雅黑", Font.BOLD, 25));
		pwdTextField.setBounds(100, 100, 300, 50);
		pwdTextField.setColumns(10);
		panel.add(pwdTextField);
		
		JLabel findPwdLabel = new JLabel("找回密码");
		findPwdLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		findPwdLabel.setFont(Fonts.LOGIN_LABEL_FONT.getFont());
		findPwdLabel.setBounds(20, 260, 72, 20);
		panel.add(findPwdLabel);
		
		JCheckBox autoLoginCheckBox = new JCheckBox("自动登录");
		autoLoginCheckBox.setFont(Fonts.LOGIN_LABEL_FONT.getFont());
		autoLoginCheckBox.setBounds(100, 160, 100, 20);
		panel.add(autoLoginCheckBox);
		
		JCheckBox remPwdCheckBox = new JCheckBox("记住密码");
		remPwdCheckBox.setFont(Fonts.LOGIN_LABEL_FONT.getFont());
		remPwdCheckBox.setBounds(290, 160, 100, 20);
		panel.add(remPwdCheckBox);
		
		JButton loginConfirmButton = new JButton("登录");
		loginConfirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		loginConfirmButton.setBackground(SystemColor.textHighlight);
		loginConfirmButton.setForeground(Color.WHITE);
		loginConfirmButton.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
		loginConfirmButton.setBounds(100, 190, 300, 50);
		panel.add(loginConfirmButton);
		
		JLabel toRegisterLabel = new JLabel("注册会员");
		toRegisterLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 切换到注册界面
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		toRegisterLabel.setForeground(Colors.TOP_BAR_BACKGROUND.getColor());
		toRegisterLabel.setFont(new Font("等线", Font.PLAIN, 18));
		toRegisterLabel.setBounds(400, 260, 72, 20);
		panel.add(toRegisterLabel);

	}
	public enum Fonts {
		/*登陆框主要字体*/
		LOGIN_MAIN_FONT( new Font("微软雅黑", Font.PLAIN,20)),
		LOGIN_LABEL_FONT( new Font("等线", Font.PLAIN, 18));
		
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