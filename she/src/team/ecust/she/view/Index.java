package team.ecust.she.view;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import team.ecust.she.view.PromptBox.Tips;

import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.wis.pack.component.Photo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.border.LineBorder;

/**首页界面类，也是窗口的整体框架，包含主函数。*/
public final class Index {
	/**设备屏幕的最大宽度*/
	public final static int SCREEN_WIDTH  = 1920;//Toolkit.getDefaultToolkit().getScreenSize().width;
	/**设备屏幕的最大高度*/
	public final static int SCREEN_HEIGHT = 1080;//Toolkit.getDefaultToolkit().getScreenSize().height;
	
	/**消息已读未读状态*/
	public static boolean READ_MESSAGE;
	
	/**窗体对象*/
	private JFrame frame;
	/**头像面板*/
	private Photo headPortrait;
	/**昵称标签*/
	private JLabel nickname;
	/**消息标签*/
	private JLabel messages;

	/**
	 * 在主函数里启动应用程序。
	 * @param args 使用命令行启动该程序时输入的参数
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index window = new Index();
					window.frame.setVisible(true);
				} catch (Exception e) {
					(new PromptBox(Tips.ERROR)).open("窗体异常退出");
				}
			}
		});
	}

	/**加载首页对象。 */
	public Index() {
		READ_MESSAGE = false;
		initialize();
	}

	/**初始化首页界面内容。*/
	private void initialize() {
		frame = new JFrame();
		//测试完毕删除
			frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		//........
		frame.setUndecorated(true);//去除原始顶栏
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getState() | JFrame.MAXIMIZED_BOTH);//最大化启动
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Index.class.getResource("/team/ecust/she/resource/image/window.png")));//设置任务栏图标
		
		JPanel topBar = new JPanel();
		topBar.setBorder(new EmptyBorder(12, 20, 12, 20));//限定顶栏的边界宽度
		topBar.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
		frame.getContentPane().add(topBar, BorderLayout.NORTH);
		
		FlowLayout topBarLayout = (FlowLayout) topBar.getLayout();//设置顶栏布局
		topBarLayout.setHgap(20);
		topBarLayout.setVgap(0);
		
		JLabel topBarTitle = new JLabel("华理二手平台");
		int width = SCREEN_WIDTH - 440 - 12*Fonts.SHE_TITLE.getFont().getSize();
		topBarTitle.setBorder(new EmptyBorder(0, 0, 0, width));//设置标题标签的右边界以适应窗口宽度
		topBarTitle.setFont(Fonts.SHE_TITLE.getFont());
		topBarTitle.setForeground(Colors.SHE_TILTLE_FOREGROUND.getColor());
		topBarTitle.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/logo.png")));
		topBar.add(topBarTitle);
		
		headPortrait = new Photo("src/team/ecust/she/resource/image/unknown.jpg");
		headPortrait.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				headPortrait.setBorder(new LineBorder(Colors.TOP_BAR_HEAD_PORTRAIT_BORDER.getColor(), 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				headPortrait.setBorder(new LineBorder(Colors.TOP_BAR_BACKGROUND.getColor(), 2));
			}
		});
		headPortrait.setPhotoLocation(2, 2);
		headPortrait.setRatio(90);//设置图片相对面板的缩放比例
		headPortrait.setPreferredSize(new Dimension(36, 36));//强制头像的大小，实际大小要减边界宽度
		headPortrait.setBorder(new LineBorder(Colors.TOP_BAR_BACKGROUND.getColor(), 2));
		headPortrait.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
		topBar.add(headPortrait);
		
		nickname = new JLabel("未登录...");
		nickname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				nickname.setForeground(Colors.TOP_BAR_NICKNAME_FOREGROUND_IN.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				nickname.setForeground(Colors.TOP_BAR_NICKNAME_FOREGROUND_OUT.getColor());
			}
		});
		nickname.setFont(Fonts.TOP_BAR_NICKNAME.getFont());
		nickname.setForeground(Colors.TOP_BAR_NICKNAME_FOREGROUND_OUT.getColor());
		topBar.add(nickname);
		
		JPanel severance_1 = new JPanel();
		severance_1.setBackground(Colors.TOP_BAR_SEVERANCE_FOREGROUND.getColor());
		severance_1.setPreferredSize(new Dimension(1, 30));
		topBar.add(severance_1);
		
		JLabel appearance = new JLabel();
		appearance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				appearance.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/severance_i.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				appearance.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/severance_o.png")));
			}
		});
		appearance.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/severance_o.png")));
		topBar.add(appearance);
		
		messages = new JLabel();
		messages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(READ_MESSAGE)
					messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_i.png")));
				else
					messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_ui.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(READ_MESSAGE)
					messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_o.png")));
				else
					messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_uo.png")));
			}
		});
		if(READ_MESSAGE)
			messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_o.png")));
		else
			messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_uo.png")));
		topBar.add(messages);
		
		JLabel settings = new JLabel();
		settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				settings.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/settings_i.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				settings.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/settings_o.png")));
			}
		});
		settings.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/settings_o.png")));
		topBar.add(settings);
		
		JPanel severance_2 = new JPanel();
		severance_2.setBackground(Colors.TOP_BAR_SEVERANCE_FOREGROUND.getColor());
		severance_2.setPreferredSize(new Dimension(1, 30));
		topBar.add(severance_2);
		
		JLabel minimum = new JLabel();
		minimum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				minimum.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/minimum_i.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				minimum.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/minimum_o.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED | frame.getExtendedState());
			}
		});
		minimum.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/minimum_o.png")));
		topBar.add(minimum);
		
		JLabel maximum = new JLabel();
		maximum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				maximum.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/maximum_fi.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				maximum.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/maximum_fo.png")));
			}
		});
		maximum.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/maximum_fo.png")));
		topBar.add(maximum);
		
		JLabel close = new JLabel();
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				close.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/close_i.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				close.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/close_o.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		close.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/close_o.png")));
		topBar.add(close);
		
	}
	
	/**
	 * 设置顶栏的头像面板。
	 * 使用Toolkit.getDefaultToolkit().getImage()方法，文件名必须以src开头
	 * @param icon 需要设置的图标对象
	 */
	public void setHeadPortrait(Image image) {
		headPortrait.setPhoto(image);
	}
	
	/**
	 * 设置顶栏的昵称文字，如果字符串长度超过5，只显示前四个字符，用三个.隐藏后半部分。
	 * @param name 需要设置的名字字符串
	 */
	public void setNickname(String name) {
		StringBuffer buffer = new StringBuffer(name);
		while(buffer.length() < 5) {
			buffer.append(' ');
		}
		if(buffer.length() > 5) {
			buffer = new StringBuffer(name.substring(0, 4));
			buffer.append("...");
		}
		nickname.setText(buffer.toString());
	}
}