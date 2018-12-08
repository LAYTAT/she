package team.ecust.she.view;

import java.awt.Font;

/**字体常量对象的枚举。*/
public enum Fonts {
	/**提示框文字的字体*/
	PROMPT_BOX(new Font("微软雅黑", Font.BOLD, 30)),
	
	/**顶栏标题的字体*/
	TOP_BAR_TITLE(new Font("黑体", Font.PLAIN, 28)),
	/**顶栏切换部分的字体*/
	TOP_BAR_SWITCH(new Font("黑体", Font.BOLD, 24)),
	/**顶栏查询部分的字体*/
	TOP_BAR_SEARCH(new Font("微软雅黑", Font.PLAIN, 18)),
	/**顶栏昵称的字体*/
	TOP_BAR_NICKNAME(new Font("等线", Font.PLAIN, 20)),
	
	/**左栏目录标题的字体*/
	LEFT_CONTENT_TITLE(new Font("微软雅黑", Font.BOLD, 22)),
	/**左栏目录选项的字体*/
	LEFT_CONTENT_OPTION(new Font("黑体", Font.PLAIN, 20)),
	
	/**我的信息界面标题（即昵称对应的一行）的字体*/
	MINE_INFO_TITLE(new Font("微软雅黑", Font.PLAIN, 22)),
	/**我的信息界面标题（即昵称对应的一行）的字体*/
	MINE_INFO_DETAILS(new Font("等线", Font.PLAIN, 20)),
	/**我的信息界面个人说明的字体*/
	MINE_INFO_STATEMENT(new Font("宋体", Font.PLAIN, 20)),
	/**我的信息界面个人说明面板标题的字体*/
	MINE_INFO_STATEMENT_TITLE(new Font("黑体", Font.PLAIN, 16)),
	/**我的信息界面选项面板的字体*/
	MINE_INFO_OPTIONS(new Font("黑体", Font.PLAIN, 20)),
	
	/**修改信息界面的标题字体*/
	MODIFY_INFO_TITLE(new Font("黑体", Font.PLAIN, 30)),
	/**编辑个人信息界面左侧提示标签的字体*/
	MODIFY_INFO_LEFT_LABEL(new Font("微软雅黑", Font.PLAIN, 22)),
	/**编辑个人信息界面左侧输入框的字体*/
	MODIFY_INFO_LEFT_TEXT(new Font("黑体", Font.PLAIN, 22)),
	;
	
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
	
	/**
	 * 获取枚举的字体对象的大小。
	 * @return 对应字体的大小
	 */
	public int getSize()
	{
		return font.getSize();
	}
}