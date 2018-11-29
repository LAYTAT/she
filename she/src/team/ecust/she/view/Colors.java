package team.ecust.she.view;

import java.awt.Color;

/**颜色常量对象的枚举。*/
public enum Colors {
	/**测试*/
	TEST(new Color(0, 0 ,0)),
	;
	
	/**枚举的颜色变量*/
	private Color color;
	
	/**
	 * 枚举的构造函数，用于初始化颜色变量。
	 * @param color 需要设置的颜色对象
	 */
	private Colors(Color color)
	{
		this.color = color;
	}
	
	/**
	 * 获取枚举的颜色对象。
	 * @return 当前枚举常量的颜色对象
	 */
	public Color getColor()
	{
		return color;
	}
}