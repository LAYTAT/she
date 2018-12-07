package team.ecust.she.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.wis.pack.component.Photo;

import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Member;
import team.ecust.she.model.Member.Gender;

import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**我的信息界面类。*/
@SuppressWarnings("serial")
public final class MineInfo extends JPanel {
	/**选项卡片*/
	private JPanel card;
	
	/**初始化，设置卡片为空对象。 */
	public MineInfo() {
		card = null;
	}
	
	/**
	 * 展示界面，构造后必先使用该方法初始化，在此之前需要先将该面板添加到容器中，对象属性为空的信息将会为空，编号最好不要为空。
	 * <p>还有一点，如果不能确认传入函数里的参数一定不空，则使用前必须判断是否为空再使用，否则将可能引起重载的函数调用失败。
	 * @param member 需要显示信息的会员对象，可以为空，但啥信息也不显示，之后调用其他方法也不显示。
	 */
	public void display(Member member) {
		if(member == null)
			return;
		
		int width = getWidth();
		int height = getHeight();
		setLayout(new BorderLayout());//使用边界布局
		
		JPanel myInfo = new JPanel(new BorderLayout(10, 0));//设置我的信息面板的布局和水平间距
		myInfo.setPreferredSize(new Dimension(width, 340));//固定信息面板高度
		myInfo.setBorder(new EmptyBorder(20, 20, 40, 20));//调整面板边界宽度
		add(myInfo, BorderLayout.NORTH);
		
		Photo headPortrait = new Photo(member.getHeadPortrait());//获取头像数据
		headPortrait.setPreferredSize(new Dimension(300, 300));//根据以上设置图片大小
		myInfo.add(headPortrait, BorderLayout.WEST);
		
		JPanel wordInfo = new JPanel(new BorderLayout());//文字信息面板，设置面板竖直间距
		myInfo.add(wordInfo, BorderLayout.CENTER);
		
		JPanel firstRow = new JPanel(new GridLayout(0, 2));//文字信息面板的第一行，即包含昵称的标题
		firstRow.setBorder(new MatteBorder(0, 0, 2, 0, Colors.CENTER_CARD_BACKGROUND.getColor()));
		wordInfo.add(firstRow, BorderLayout.NORTH);
		
		JLabel nickname = new JLabel("昵称：" + member.getNickname());//获取昵称
		if(member.getSex() == Gender.MALE)
			nickname.setIcon(new ImageIcon(MineInfo.class.getResource("/team/ecust/she/resource/image/male.png")));
		else if(member.getSex() == Gender.FEMALE)
			nickname.setIcon(new ImageIcon(MineInfo.class.getResource("/team/ecust/she/resource/image/female.png")));
		nickname.setFont(Fonts.MINE_INFO_TITLE.getFont());
		nickname.setForeground(Colors.MINE_INFO_TITLE_FOREGROUND.getColor());
		firstRow.add(nickname);
		
		JLabel credit = new JLabel(member.getCredit() == Member.NULL_CREDIT ? "信用分：" : "信用分：" + member.getCredit());//获取信用分
		credit.setIcon(new ImageIcon(MineInfo.class.getResource("/team/ecust/she/resource/image/credit.png")));
		credit.setFont(Fonts.MINE_INFO_TITLE.getFont());
		credit.setForeground(Colors.MINE_INFO_TITLE_FOREGROUND.getColor());
		firstRow.add(credit);
		
		JPanel secondRow = new JPanel(new GridLayout(3, 2));//文字信息面板的第二部分
		wordInfo.add(secondRow, BorderLayout.CENTER);
		
		JLabel realName = new JLabel("真实姓名：" + member.getRealName());
		realName.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		realName.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(realName);
		
		JLabel address = new JLabel("地址：" + member.getAddress());
		address.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		address.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(address);
		
		JLabel profession = new JLabel("专业：" + member.getMajor());
		profession.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		profession.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(profession);
		
		JLabel grade = new JLabel(member.getMemberNo() == null ? "入学年份：" : "入学年份：20" + member.getMemberNo().substring(2, 4));//显示年份
		grade.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		grade.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(grade);
		
		JLabel phoneNo = new JLabel("电话：" + member.getPhone());
		phoneNo.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		phoneNo.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(phoneNo);
		
		JLabel mailbox = new JLabel("邮箱：" + member.getMailbox());
		mailbox.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		mailbox.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(mailbox);
		
		JTextPane thirdRow = new JTextPane();
		LineBorder border = new LineBorder(Colors.MINE_INFO_STATEMENT_BORDER.getColor(), 2);//自定义线框
		thirdRow.setBorder(new TitledBorder(border, "个人介绍", TitledBorder.CENTER, TitledBorder.TOP,
				Fonts.MINE_INFO_STATEMENT_TITLE.getFont(), Colors.MINE_INFO_STATEMENT_TITLE_FOREGROUND.getColor()));//设置标题边界
		thirdRow.setText(member.getSignature());
		thirdRow.setEditable(false);//设为不可编辑
		thirdRow.setPreferredSize(new Dimension(width - 330, 100));//设置最佳高度
		thirdRow.setFont(Fonts.MINE_INFO_STATEMENT.getFont());
		thirdRow.setBackground(Colors.MINE_INFO_STATEMENT_BACKGROUND.getColor());
		wordInfo.add(thirdRow, BorderLayout.SOUTH);
		
		JPanel options = new JPanel(new GridLayout(0, 3, 1, 0));//选项面板
		options.setBorder(new MatteBorder(1, 0, 1, 0, Colors.MINE_INFO_OPTIONS_BORDER.getColor()));//设置分割线
		add(options, BorderLayout.CENTER);
		
		JLabel mineUpload = new JLabel("我的上传");
		mineUpload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mineUpload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				mineUpload.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mineUpload.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				mineUpload.setBackground(Colors.DEFAULT_BACKGROUND.getColor());
			}
		});
		mineUpload.setOpaque(true);//打开背景色
		mineUpload.setFont(Fonts.MINE_INFO_OPTIONS.getFont());
		mineUpload.setHorizontalAlignment(SwingConstants.CENTER);
		options.add(mineUpload);
		
		JLabel mineOrder = new JLabel("我的交易");
		mineOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mineOrder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				mineOrder.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mineOrder.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				mineOrder.setBackground(Colors.DEFAULT_BACKGROUND.getColor());
			}
		});
		mineOrder.setOpaque(true);//打开背景色
		mineOrder.setFont(Fonts.MINE_INFO_OPTIONS.getFont());
		mineOrder.setHorizontalAlignment(SwingConstants.CENTER);
		mineOrder.setBorder(new MatteBorder(0, 1, 0, 1, Colors.MINE_INFO_OPTIONS_BORDER.getColor()));
		options.add(mineOrder);
		
		JLabel relativeComment = new JLabel("相关评价");
		relativeComment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				relativeComment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				relativeComment.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				relativeComment.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				relativeComment.setBackground(Colors.DEFAULT_BACKGROUND.getColor());
			}
		});
		relativeComment.setOpaque(true);//打开背景色
		relativeComment.setFont(Fonts.MINE_INFO_OPTIONS.getFont());
		relativeComment.setHorizontalAlignment(SwingConstants.CENTER);
		options.add(relativeComment);
		
		card = new JPanel(new CardLayout());//设置为卡片布局
		card.setPreferredSize(new Dimension(width, height - 400));//固定选项信息面板高度
		add(card, BorderLayout.SOUTH);
	}
	
	/**
	 * 在卡片里展示我的上传。
	 */
	public void showMyUploads(IdleGoods k[], DemandGoods v[]) {
		if(k != null) {
			int rows = k.length;
			for(int i = 0; i < rows; i++) {
				//添加闲置物品列表
			}
		}
		if(v != null) {
			int rows = v.length;
			for(int i = 0; i < rows; i++) {
				//添加需求物品列表
			}
		}
	}
	
	/**
	 * 在卡片里展示我的交易。
	 */
	public void showMyTrades() {
		
	}
	
	/**
	 * 在卡片里展示相关评论。
	 */
	public void showRelativeComments() {
		
	}
	
	/**
	 * 将参数对应的组件添加到个人信息的卡片中，并自动作为最上层显示。
	 * @param component 需要添加到卡片显示的组件，为空啥也不做
	 */
	public void showInCard(Component component) {
		if(component != null) {
			card.add(component);
			CardLayout layout = (CardLayout)card.getLayout();
			layout.last(card);
		}
	}
}