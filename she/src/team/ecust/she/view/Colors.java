package team.ecust.she.view;

import java.awt.Color;

/**颜色常量对象的枚举。*/
public enum Colors {
	/**所有组件的默认背景色*/
	DEFAULT_BACKGROUND(new Color(240, 240, 240)),
	
	/**提示框的前景色，即文字颜色*/
	PROMPT_BOX_FOREGROUND(new Color(240, 240, 240, 200)),
	/**提示框的背景色*/
	PROMPT_BOX_BACKGROUND(new Color(0, 0, 0, 200)),
	
	/**顶栏的标准背景色*/
	TOP_BAR_BACKGROUND(new Color(50, 150, 250)),
	/**顶栏的背景色，鲜红色*/
	TOP_BAR_BACKGROUND_RED(new Color(200, 50, 50)),
	/**顶栏的背景色，浅灰色*/
	TOP_BAR_BACKGROUND_GRAY(new Color(130, 130, 130)),
	/**顶栏的背景色，青绿色*/
	TOP_BAR_BACKGROUND_GREEN(new Color(60, 190, 120)),
	/**顶栏的背景色，粉红色*/
	TOP_BAR_BACKGROUND_PINK(new Color(250, 140, 180)),
	/**顶栏的背景色，橙色*/
	TOP_BAR_BACKGROUND_ORANGE(new Color(255, 130, 40)),
	
	/**顶栏标题的前景色*/
	TOP_BAR_TILTLE_FOREGROUND(new Color(255, 240, 0)),
	/**顶栏切换按钮的前景色*/
	TOP_BAR_SWITCH_FOREGROUND(new Color(230, 230, 230)),
	/**顶栏查询部分的前景色*/
	TOP_BAR_SEARCH_FOREGROUND(new Color(220, 220, 220)),
	/**顶栏退出昵称标签的前景色*/
	TOP_BAR_NICKNAME_FOREGROUND_OUT(new Color(180, 180, 180)),
	/**顶栏进入昵称标签的前景色*/
	TOP_BAR_NICKNAME_FOREGROUND_IN(new Color(250, 250, 250)),
	/**顶栏分隔符的前景色*/
	TOP_BAR_SEVERANCE_FOREGROUND(new Color(80, 80, 80)),
	
	/**左栏目录的背景色*/
	LEFT_CONTENT_BACKGROUND(new Color(250, 250, 250)),
	/**左栏目录标题的前景色*/
	LEFT_CONTENT_TITLE_FOREGROUND(new Color(120, 120, 120)),
	/**左栏目录选项的前景色*/
	LEFT_CONTENT_OPTION_FOREGROUND(new Color(100, 100, 100)),
	
	/**中心卡片的背景色*/
	CENTER_CARD_BACKGROUND(new Color(120, 120, 120)),
	
	/**我的信息界面标题（即昵称对应的一行）的前景色*/
	MINE_INFO_TITLE_FOREGROUND(new Color(10, 10, 10)),
	/**我的信息界面详细信息对应的一行的前景色*/
	MINE_INFO_DETAILS_FOREGROUND(new Color(10, 10, 10)),
	/**我的信息界面个人说明面板的背景色*/
	MINE_INFO_STATEMENT_BACKGROUND(new Color(240, 240, 240)),
	/**我的信息界面个人说明面板标题的前景色*/
	MINE_INFO_STATEMENT_TITLE_FOREGROUND(new Color(60, 70, 200)),
	/**我的信息界面个人说明面板边款的前景色*/
	MINE_INFO_STATEMENT_BORDER(new Color(150, 220, 230)),
	/**我的信息界面选项面板边款的前景色*/
	MINE_INFO_OPTIONS_BORDER(new Color(150, 150 , 150)),
	
	/**编辑个人信息界面标题的分割线前景色*/
	MODIFY_INFO_TITLE_BORDER(new Color(120, 120, 120)),
	/**编辑个人信息界面下拉框的背景色*/
	MODIFY_INFO_COMBOC_BACKGROUND(new Color(100, 100, 100)),
	/**编辑个人信息界面下拉框的前景色*/
	MODIFY_INFO_COMBOC_FOREGROUND(new Color(200, 200, 200)),
	/**编辑个人信息界面的按钮背景色*/
	MODIFY_INFO_BUTTON_BACKGROUND(new Color(40, 150, 250)),
	
	/**消息界面分割线前景色*/
	MESSAGES_SEVERANCE(new Color(50, 100, 180)),
	/**消息界面模式选择背景色*/
	MESSAGES_MODE_OPTIONS_BACKGROUND(new Color(250, 140, 50)),
	/**消息界面聊天窗口背景色*/
	MESSAGES_WINDOW_BACKGROUND(new Color(100, 100, 100)),
	/**消息界面发送框发送按钮背景色*/
	MESSAGES_SEND_BUTTON_BACKGROUND(new Color(40, 150, 250)),
	/**消息界面发送框举报按钮背景色*/
	MESSAGES_TIPOFF_BUTTON_BACKGROUND(new Color(250, 50, 50)),
	;
	
	/**枚举的颜色变量*/
	private Color color;
	
	/**
	 * 枚举的构造函数，用于初始化颜色变量。
	 * @param color 需要设置的颜色对象
	 */
	private Colors(Color color) {
		this.color = color;
	}
	
	/**
	 * 获取枚举的颜色对象。
	 * @return 当前枚举常量的颜色对象
	 */
	public Color getColor() {
		return color;
	}
}