package team.ecust.she.view;

import java.awt.Font;

/**字体常量对象的枚举。*/
public enum Fonts {
	/**提示框文字的字体*/
	PROMPT_BOX(new Font("微软雅黑", Font.BOLD, 30)),
	
	/**顶栏标题的字体*/
	SHE_TITLE(new Font("黑体", Font.PLAIN, 28)),
	
	/**顶栏昵称的字体*/
	TOP_BAR_NICKNAME(new Font("宋体", Font.PLAIN, 20)),
	
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
}