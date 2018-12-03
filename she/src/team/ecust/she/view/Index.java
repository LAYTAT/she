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
import java.util.Timer;
import java.util.TimerTask;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.border.MatteBorder;

/**首页界面类，也是窗口的整体框架，包含主函数。*/
public final class Index {
	/**设备屏幕的最大宽度*/
	public final static int SCREEN_WIDTH  = Toolkit.getDefaultToolkit().getScreenSize().width;
	/**设备屏幕的最大高度*/
	public final static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	/**用于保存对象的实例*/
	private static Index INDEX = null;
	
	/**获取首页的实例对象。*/
	public static Index getInstance() {
		if(INDEX == null)
			INDEX = new Index();
		return INDEX;
	}
	
	/**当前皮肤对应的数字标识符，默认为零*/
	private int type;
	/**当前皮肤的颜色，默认为标准背景色*/
	private Colors colors;
	/**消息已读未读状态，默认为已读*/
	private boolean readMessage;
	/**登录会员的号码，默认为空串，代表未登录*/
	private String memberNo;
	/**窗体对象*/
	private JFrame frame;
	/**搜索文本框*/
	private JTextField search;
	/**头像面板*/
	private Photo headPortrait;
	/**昵称标签*/
	private JLabel nickname;
	/**卡片内容面板*/
	private JPanel card;

	/**
	 * 在主函数里启动应用程序。
	 * @param args 使用命令行启动该程序时输入的参数
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index window = getInstance();
					window.frame.setVisible(true);
				} catch (Exception e) {
					(new PromptBox(Tips.ERROR)).open("软件异常退出");
				}
			}
		});
	}

	/**初始化各项参数并加载显示首页对象，背景色默认为顶栏标准背景色，消息默认为已读，会员号码默认为空串。 */
	private Index() {
		type = 0;
		colors = Colors.TOP_BAR_BACKGROUND;
		readMessage = true;
		memberNo = "";
		initialize();
		readMessagesFromDataBase(1000);
	}

	/**初始化首页界面内容。*/
	private void initialize() {
		frame = new JFrame();
		frame.setUndecorated(true);//去除原始顶栏
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置系统退出方式
		frame.setExtendedState(frame.getState() | JFrame.MAXIMIZED_BOTH);//最大化启动
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Index.class.getResource("/team/ecust/she/resource/image/window.png")));//设置任务栏图标
		loadTopBar();
		loadContent();
		card = new JPanel();//加载卡片内容
		frame.getContentPane().add(card, BorderLayout.CENTER);
		card.setLayout(new CardLayout());
	}
	
	/**加载顶栏内容。*/
	private void loadTopBar() {
		JPanel topBar = new JPanel();
		topBar.setBackground(colors.getColor());
		frame.getContentPane().add(topBar, BorderLayout.NORTH);
		
		FlowLayout topBarLayout = (FlowLayout) topBar.getLayout();//设置顶栏布局
		topBarLayout.setHgap(20);//组件的水平间距width
		topBarLayout.setVgap(12);//设置顶栏的上下边界高度
		
		JLabel topBarTitle = new JLabel("华理二手物品交易平台");
		topBarTitle.setFont(Fonts.TOP_BAR_TITLE.getFont());
		topBarTitle.setForeground(Colors.TOP_BAR_TILTLE_FOREGROUND.getColor());
		topBarTitle.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/logo.png")));
		topBar.add(topBarTitle);//-width-title.length(10)
		
		JPanel switchTo = new JPanel(new GridLayout(0, 2, 0, 0));//切换卡片的面板，并设置边界
		switchTo.setBorder(new EmptyBorder(0, 20, 0, 0));//拉大左边界间距
		switchTo.setBackground(colors.getColor());
		topBar.add(switchTo);//-width-20
		
		JButton previous = new JButton("<");//前一页按钮
		previous.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				previousCard();
			}
		});
		previous.setBorder(new LineBorder(colors.getColor().darker(), 1));
		previous.setPreferredSize(new Dimension(50, 28));
		previous.setBackground(colors.getColor());
		previous.setForeground(Colors.TOP_BAR_SWITCH_FOREGROUND.getColor());
		previous.setFont(Fonts.TOP_BAR_SWITCH.getFont());
		switchTo.add(previous);//-50
		
		JButton next = new JButton(">");//下一页按钮
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nextCard();
			}
		});
		next.setBorder(new LineBorder(colors.getColor().darker(), 1));
		next.setPreferredSize(new Dimension(50, 28));
		next.setBackground(colors.getColor());
		next.setForeground(Colors.TOP_BAR_SWITCH_FOREGROUND.getColor());
		next.setFont(Fonts.TOP_BAR_SWITCH.getFont());
		switchTo.add(next);//-50
		
		JPanel input = new JPanel();//查询部分的面板
		input.setBackground(colors.getColor());
		topBar.add(input);//-width
		
		FlowLayout inputLayout = (FlowLayout) input.getLayout();//设置查询部分的布局
		inputLayout.setVgap(0);//紧密贴合
		inputLayout.setHgap(0);
		
		search = new JTextField();
		search.setBorder(null);
		search.setPreferredSize(new Dimension(240, 28));//适应大小
		search.setFont(Fonts.TOP_BAR_SEARCH.getFont());
		search.setBackground(colors.getColor().darker());
		search.setForeground(Colors.TOP_BAR_SEARCH_FOREGROUND.getColor());
		input.add(search);//-240
		
		JButton query = new JButton("搜索");
		query.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		query.setBorder(null);
		query.setPreferredSize(new Dimension(70, 28));
		query.setFont(Fonts.TOP_BAR_SEARCH.getFont());
		query.setBackground(colors.getColor().darker());
		query.setForeground(Colors.TOP_BAR_SEARCH_FOREGROUND.getColor());
		input.add(query);//-70
		
		//公式：SCREEN_WIDTH - 15*width + adjust - 548 - 1.2*10*title.size - 1.2*5*nickname.size//adjust=80微调//中文=1.2*英文
		int fitWidth = SCREEN_WIDTH - 808 - 12*Fonts.TOP_BAR_TITLE.getSize() - 6*Fonts.TOP_BAR_NICKNAME.getSize();
		JPanel blank = new JPanel();
		blank.setPreferredSize(new Dimension(fitWidth, 10));//设置占位面板的最佳宽度
		blank.setBackground(colors.getColor());
		topBar.add(blank);//-width
		
		headPortrait = new Photo("src/team/ecust/she/resource/image/unknown.jpg");
		headPortrait.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				headPortrait.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				headPortrait.setBorder(new LineBorder(colors.getColor().brighter(), 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				headPortrait.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				headPortrait.setBorder(new LineBorder(colors.getColor(), 2));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				showHeadPortraitInCard();
			}
		});
		headPortrait.setPhotoLocation(2, 2);
		headPortrait.setRatio(90);//设置图片相对面板的缩放比例
		headPortrait.setPreferredSize(new Dimension(36, 36));//强制头像的大小，实际大小要减边界宽度
		headPortrait.setBorder(new LineBorder(colors.getColor(), 2));
		headPortrait.setBackground(colors.getColor());
		topBar.add(headPortrait);//-width-36
		
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
		topBar.add(nickname);//-width-nickname.length(5)
		
		JPanel severance_1 = new JPanel();
		severance_1.setBackground(Colors.TOP_BAR_SEVERANCE_FOREGROUND.getColor());
		severance_1.setPreferredSize(new Dimension(1, 30));
		topBar.add(severance_1);//-width-1
		
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
			@Override
			public void mouseClicked(MouseEvent e) {//个性在线换肤
				if(++type > 5)
					type = 0;
				switch(type) {
				case 0:
					colors = Colors.TOP_BAR_BACKGROUND; break;
				case 1:
					colors = Colors.TOP_BAR_BACKGROUND_RED; break;
				case 2:
					colors = Colors.TOP_BAR_BACKGROUND_GRAY; break;
				case 3:
					colors = Colors.TOP_BAR_BACKGROUND_GREEN; break;
				case 4:
					colors = Colors.TOP_BAR_BACKGROUND_ORANGE; break;
				case 5:
					colors = Colors.TOP_BAR_BACKGROUND_PINK; break;
				default:break;
				}
				topBar.setBackground(colors.getColor());//更新各个背景色
				switchTo.setBackground(colors.getColor());
				previous.setBackground(colors.getColor());
				previous.setBorder(new LineBorder(colors.getColor().darker(), 1));
				next.setBackground(colors.getColor());
				next.setBorder(new LineBorder(colors.getColor().darker(), 1));
				search.setBackground(colors.getColor().darker());
				query.setBackground(colors.getColor().darker());
				blank.setBackground(colors.getColor());
				headPortrait.setBorder(new LineBorder(colors.getColor(), 2));
			}
		});
		appearance.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/severance_o.png")));
		topBar.add(appearance);//-width-20
		
		JLabel messages = new JLabel();
		messages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(readMessage)
					messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_i.png")));
				else
					messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_ui.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(readMessage)
					messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_o.png")));
				else
					messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_uo.png")));
			}
		});
		messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages_o.png")));
		topBar.add(messages);//-width-20
		
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
		topBar.add(settings);//-width-20
		
		JPanel severance_2 = new JPanel();
		severance_2.setBackground(Colors.TOP_BAR_SEVERANCE_FOREGROUND.getColor());
		severance_2.setPreferredSize(new Dimension(1, 30));
		topBar.add(severance_2);//-width-1
		
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
		topBar.add(minimum);//-width-20
		
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
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				int width = frame.getWidth();
				if(width != SCREEN_WIDTH) {
					blank.setPreferredSize(new Dimension(blank.getWidth()+width/3, 10));
					frame.setExtendedState(frame.getState() | JFrame.MAXIMIZED_BOTH);
				} else {
					blank.setPreferredSize(new Dimension(blank.getWidth()-width/3, 10));
					frame.setBounds(100, 100, 2*width/3, 2*frame.getHeight()/3);
				}*/
			}
		});
		maximum.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/maximum_fo.png")));
		topBar.add(maximum);//-width-20
		
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
		topBar.add(close);//-width-20-width
	}
	
	/**加载目录内容，不要尝试模块化提高代码重用率，会出错。*/
	private void loadContent() {
		JPanel content = new JPanel(new GridLayout(22, 0, 0, 0));//根据实际情况调整目录行数和竖直间距
		content.setBorder(new EmptyBorder(10, 10, 0, 20));//设置左右边界宽度
		content.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		
		JScrollPane scrollPane = new JScrollPane(content);
		scrollPane.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(300, 100));//设置滚动面板宽度，高度自适应
		frame.getContentPane().add(scrollPane, BorderLayout.WEST);
		
		JLabel mine = new JLabel(" 我的账户");
		mine.setBorder(new MatteBorder(0, 10, 0, 0, Colors.LEFT_CONTENT_TITLE_FOREGROUND.getColor()));
		mine.setFont(Fonts.LEFT_CONTENT_TITLE.getFont());
		mine.setForeground(Colors.LEFT_CONTENT_TITLE_FOREGROUND.getColor());
		content.add(mine);
		
		JButton myInfo = new JButton("个人信息");
		myInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				myInfo.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				myInfo.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				(new PromptBox()).open("请登录");
				Login login = new Login();
				showInCard(login);
				login.display();
			}
		});
		myInfo.setBorder(null);
		myInfo.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		myInfo.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		myInfo.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/myinfo.png")));
		content.add(myInfo);
		
		JButton modifyInfo = new JButton("编辑信息");
		modifyInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				modifyInfo.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				modifyInfo.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SoftwareInfo info = new SoftwareInfo();
				showInCard(info);
			}
		});
		modifyInfo.setBorder(null);
		modifyInfo.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		modifyInfo.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		modifyInfo.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/modifyinfo.png")));
		content.add(modifyInfo);
		
		JButton uploadIdleGoods = new JButton("上传闲置");
		uploadIdleGoods.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				uploadIdleGoods.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				uploadIdleGoods.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		uploadIdleGoods.setBorder(null);
		uploadIdleGoods.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		uploadIdleGoods.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		uploadIdleGoods.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/uploadidlegoods.png")));
		content.add(uploadIdleGoods);
		
		JButton uploadDemandGoods = new JButton("添加愿望");
		uploadDemandGoods.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				uploadDemandGoods.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				uploadDemandGoods.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		uploadDemandGoods.setBorder(null);
		uploadDemandGoods.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		uploadDemandGoods.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		uploadDemandGoods.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/uploaddemandgoods.png")));
		content.add(uploadDemandGoods);
		
		JButton messages = new JButton("消息记录");
		messages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				messages.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				messages.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		messages.setBorder(null);
		messages.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		messages.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		messages.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/messages.png")));
		content.add(messages);
		
		JLabel recommand = new JLabel(" 推荐内容");
		recommand.setBorder(new MatteBorder(0, 10, 0, 0, Colors.LEFT_CONTENT_TITLE_FOREGROUND.getColor()));
		recommand.setFont(Fonts.LEFT_CONTENT_TITLE.getFont());
		recommand.setForeground(Colors.LEFT_CONTENT_TITLE_FOREGROUND.getColor());
		content.add(recommand);
		
		JButton hotsale = new JButton("热卖物品");
		hotsale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hotsale.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hotsale.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		hotsale.setBorder(null);
		hotsale.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		hotsale.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		hotsale.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/hotsale.png")));
		content.add(hotsale);
		
		JButton latest = new JButton("最近更新");
		latest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				latest.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				latest.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		latest.setBorder(null);
		latest.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		latest.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		latest.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/latest.png")));
		content.add(latest);
		
		JButton wishwall = new JButton("心愿墙");
		wishwall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				wishwall.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				wishwall.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		wishwall.setBorder(null);
		wishwall.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		wishwall.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		wishwall.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/wishwall.png")));
		content.add(wishwall);

		JLabel variety = new JLabel(" 分类浏览");
		variety.setBorder(new MatteBorder(0, 10, 0, 0, Colors.LEFT_CONTENT_TITLE_FOREGROUND.getColor()));
		variety.setFont(Fonts.LEFT_CONTENT_TITLE.getFont());
		variety.setForeground(Colors.LEFT_CONTENT_TITLE_FOREGROUND.getColor());
		content.add(variety);
		
		JButton books = new JButton("实体类书籍");
		books.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				books.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				books.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		books.setBorder(null);
		books.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		books.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		books.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/books.png")));
		content.add(books);
		
		JButton devices = new JButton("电子类产品");
		devices.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				devices.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				devices.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		devices.setBorder(null);
		devices.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		devices.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		devices.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/devices.png")));
		content.add(devices);
		
		JButton dailyuse = new JButton("生活类用品");
		dailyuse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dailyuse.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				dailyuse.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		dailyuse.setBorder(null);
		dailyuse.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		dailyuse.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		dailyuse.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/dailyuse.png")));
		content.add(dailyuse);
		
		JButton foods = new JButton("美食一条街");
		foods.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				foods.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				foods.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		foods.setBorder(null);
		foods.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		foods.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		foods.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/foods.png")));
		content.add(foods);
		
		JButton souvenir = new JButton("纪念装饰品");
		souvenir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				souvenir.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				souvenir.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		souvenir.setBorder(null);
		souvenir.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		souvenir.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		souvenir.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/souvenir.png")));
		content.add(souvenir);
		
		JButton tools = new JButton("百宝箱工具");
		tools.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tools.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tools.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		tools.setBorder(null);
		tools.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		tools.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		tools.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/tools.png")));
		content.add(tools);
		
		JButton others = new JButton("其他物品");
		others.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				others.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				others.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		others.setBorder(null);
		others.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		others.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		others.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/others.png")));
		content.add(others);
		
		JLabel settings = new JLabel(" 设置");
		settings.setBorder(new MatteBorder(0, 10, 0, 0, Colors.LEFT_CONTENT_TITLE_FOREGROUND.getColor()));
		settings.setFont(Fonts.LEFT_CONTENT_TITLE.getFont());
		settings.setForeground(Colors.LEFT_CONTENT_TITLE_FOREGROUND.getColor());
		content.add(settings);
		
		JButton permission = new JButton("权限设置");
		permission.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				permission.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				permission.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		permission.setBorder(null);
		permission.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		permission.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		permission.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/permission.png")));
		content.add(permission);
		
		JButton software = new JButton("软件设置");
		software.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				software.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				software.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
		});
		software.setBorder(null);
		software.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		software.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		software.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/software.png")));
		content.add(software);
		
		JButton softinfo = new JButton("关于我们");
		softinfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				softinfo.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				softinfo.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SoftwareInfo info = new SoftwareInfo();
				showInCard(info);
				info.display();
			}
		});
		softinfo.setBorder(null);
		softinfo.setFont(Fonts.LEFT_CONTENT_OPTION.getFont());
		softinfo.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
		softinfo.setIcon(new ImageIcon(Index.class.getResource("/team/ecust/she/resource/image/softinfo.png")));
		content.add(softinfo);
	}
	
	/**
	 * 每隔一段时间从数据库读取消息，并处理。
	 * @param duration 间隔时间，单位为毫秒
	 */
	private void readMessagesFromDataBase(int duration) {
		(new Timer()).schedule(new TimerTask() {
			@Override
			public void run() {
				
			}
		}, 2000);
	}
	
	/**显示高清头像。*/
	private void showHeadPortraitInCard() {
		Photo photo = new Photo(getHeadPortrait());
		photo.setScaleFunction(true);//增加缩放功能
		photo.setRecoverFunction(true);//增加双击复原功能
		photo.setDragFunction(true);//增加拖拽功能
		photo.setBackground(Colors.CENTER_CARD_BACKGROUND.getColor());
		
		JPanel panel = new JPanel(new BorderLayout());//用另外的面板限制照片大小和位置
		panel.setBackground(Colors.CENTER_CARD_BACKGROUND.getColor());
		panel.add(photo, BorderLayout.CENTER);
		showInCard(panel);
		
		int width = photo.getWidth();
		int height = photo.getHeight();
		if(width >= height) {//调整为正方形
			panel.setBorder(new EmptyBorder(0, (width - height)/2, 0, (width - height)/2));
		} else {
			panel.setBorder(new EmptyBorder((height - width)/2, 0,  (height - width)/2, 0));
		}
	}
	
	/**
	 * 设置顶栏的头像面板。
	 * 使用Toolkit.getDefaultToolkit().getImage()方法，文件路径必须以src开头
	 * @param image 需要设置的图标对象
	 */
	public void setHeadPortrait(Image image) {
		headPortrait.setPhoto(image);
	}
	
	/**
	 * 获取顶栏的头像对象。
	 * @return 头像对应的图像对象
	 */
	public Image getHeadPortrait() {
		return headPortrait.getPhoto();
	}
	
	/**
	 * 设置顶栏的昵称文字，如果字符串长度超过5，只显示前四个字符，用三个.隐藏后半部分。
	 * @param name 需要显示的名字字符串
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
	
	/**
	 * 获取检索的信息。
	 * @return 搜索框内的字符串
	 */
	public String getSearchContent() {
		return search.getText();
	}
	
	/**
	 * 将参数对应的组件添加到首页的中心卡片中，并自动作为最上层显示。
	 * @param component 需要添加到中心卡片显示的组件，为空啥也不做
	 */
	public void showInCard(Component component) {
		if(component != null) {
			card.add(component);
			CardLayout layout = (CardLayout)card.getLayout();
			layout.last(card);
		}
	}
	
	/**跳转至下一张卡片，如果是最后一张，将跳到第一张。*/
	public void nextCard() {
		CardLayout layout = (CardLayout)card.getLayout();
		layout.next(card);
	}
	
	/**跳转至上一张卡片，如果是第一张，将跳到最后一张。*/
	public void previousCard() {
		CardLayout layout = (CardLayout)card.getLayout();
		layout.previous(card);
	}
	
	/**跳转至第一张卡片。*/
	public void firstCard() {
		CardLayout layout = (CardLayout)card.getLayout();
		layout.first(card);
	}
	
	/**跳转至最后一张卡片。*/
	public void lastCard() {
		CardLayout layout = (CardLayout)card.getLayout();
		layout.last(card);
	}

	/**
	 * 获取消息已读状态，已读返回true, 否则返回false。
	 * @return 消息状态
	 */
	public boolean isReadMessage() {
		return readMessage;
	}
	
	/**
	 * 设置消息的已读状态，true表示已读，false表示未读。
	 * @param readMessage 需要设置的消息的状态
	 */
	public void setReadMessage(boolean readMessage) {
		this.readMessage = readMessage;
	}

	/**
	 * 获取当前登录的账号，未登录则为空串。
	 * @return 当前登录的账号
	 */
	public String getMemberNo() {
		return memberNo;
	}

	/**
	 * 设置当前登录的账号，未登录请使用空串""。
	 * @param memberNo 需要设置的会员账号
	 */
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
}