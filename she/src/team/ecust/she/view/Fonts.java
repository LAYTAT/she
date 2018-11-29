package team.ecust.she.view;

import java.awt.Font;

/**字体常量对象的枚举。*/
public enum Fonts {
	/**test*/
	TEST(new Font("宋体", Font.BOLD, 20)),
	;
	
	/**枚举的字体变量*/
	private Font font;
	
	/**
	 * 枚举的构造函数，用于初始化字体变量。
	 * @param color 需要设置的字体对象
	 */
	private Fonts(Font font)
	{
		this.font = font;
	}
	
	/**
	 * 获取枚举的字体对象。
	 * @return 当前枚举常量的字体对象
	 */
	public Font getColor()
	{
		return font;
	}
}