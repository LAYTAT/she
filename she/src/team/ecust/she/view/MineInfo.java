package team.ecust.she.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.wis.pack.component.Photo;

import team.ecust.she.controller.ViewMineElseInfo;
import team.ecust.she.model.Member;
import team.ecust.she.model.Member.Gender;

import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

/**我的信息界面类。*/
@SuppressWarnings("serial")
public final class MineInfo extends JPanel {
	private String memberNo;
	
	/**选项卡片*/
	private JPanel card;
	
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	/**初始化，设置卡片为空对象。 */
	public MineInfo() {
		card = null;
		//display(null);
	}
	
	/**
	 * 展示界面，构造后必先使用该方法初始化，在此之前需要先将该面板添加到容器中，对象属性为空的信息将会为空，编号最好不要为空。
	 * <p>还有一点，如果不能确认传入函数里的参数一定不空，则使用前必须判断是否为空再使用，否则将可能引起重载的函数调用失败。
	 * @param member 需要显示信息的会员对象，可以为空，但啥信息也不显示，之后调用其他方法也不显示。
	 */
	public void display(Member member) {
		setMemberNo(member.getMemberNo());
		int width = getWidth();
		int height = getHeight();
		setLayout(new BorderLayout(0, 20));//使用边界布局
		
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
		
		JLabel credit = new JLabel("信用分：" + member.getCredit());//获取信用分
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
		
		JLabel address = new JLabel(member.getAddress() == null ? "地址：" : "地址：" + member.getAddress());
		address.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		address.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(address);
		
		JLabel profession = new JLabel("专业：" + member.getMajor());
		profession.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		profession.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(profession);
		
		JLabel grade = new JLabel("入学年份：20" + member.getMemberNo().substring(2, 4));//显示年份
		grade.setFont(Fonts.MINE_INFO_DETAILS.getFont());
		grade.setForeground(Colors.MINE_INFO_DETAILS_FOREGROUND.getColor());
		secondRow.add(grade);
		
		JLabel phoneNo = new JLabel(member.getPhone() == null ? "电话：" : "电话：" + member.getPhone());
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
		
		JPanel options = new JPanel(new GridLayout(0, 4, 2, 0));//选项面板
		options.setBackground(Color.darkGray);
		options.setBorder(new LineBorder(Color.darkGray, 2));//设置分割线
		add(options, BorderLayout.CENTER);
		
		JLabel mineIdle = new JLabel("我的闲置");
		mineIdle.addMouseListener(new ViewMineElseInfo<JLabel>(mineIdle, this, 1));
		mineIdle.setOpaque(true);//打开背景色
		mineIdle.setFont(Fonts.MINE_INFO_OPTIONS.getFont());
		mineIdle.setHorizontalAlignment(SwingConstants.CENTER);
		options.add(mineIdle);
		
		JLabel mineDemand = new JLabel("我的心愿");
		mineDemand.addMouseListener(new ViewMineElseInfo<JLabel>(mineDemand, this, 2));
		mineDemand.setOpaque(true);//打开背景色
		mineDemand.setFont(Fonts.MINE_INFO_OPTIONS.getFont());
		mineDemand.setHorizontalAlignment(SwingConstants.CENTER);
		options.add(mineDemand);
		
		JLabel mineOrder = new JLabel("我的交易");
		mineOrder.addMouseListener(new ViewMineElseInfo<JLabel>(mineOrder, this, 3));
		mineOrder.setOpaque(true);//打开背景色
		mineOrder.setFont(Fonts.MINE_INFO_OPTIONS.getFont());
		mineOrder.setHorizontalAlignment(SwingConstants.CENTER);
		options.add(mineOrder);
		
		JLabel relativeComment = new JLabel("相关评价");
		relativeComment.addMouseListener(new ViewMineElseInfo<JLabel>(relativeComment, this, 4));
		relativeComment.setOpaque(true);//打开背景色
		relativeComment.setFont(Fonts.MINE_INFO_OPTIONS.getFont());
		relativeComment.setHorizontalAlignment(SwingConstants.CENTER);
		options.add(relativeComment);
		
		card = new JPanel(new CardLayout());//设置为卡片布局
		card.setPreferredSize(new Dimension(width, height - 430));//固定选项信息面板高度
		add(card, BorderLayout.SOUTH);
		
		ViewMineElseInfo.viewIdle(this, false);
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