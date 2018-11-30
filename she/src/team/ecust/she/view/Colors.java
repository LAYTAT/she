package team.ecust.she.view;

import java.awt.Color;

/**颜色常量对象的枚举。*/
public enum Colors {
	/**提示框的前景色，即文字颜色*/
	PROMPT_BOX_FOREGROUND(new Color(240, 240, 240, 200)),
	
	/**提示框的背景色*/
	PROMPT_BOX_BACKGROUND(new Color(0, 0, 0, 200)),
	
	/**顶栏的背景色*/
	TOP_BAR_BACKGROUND(new Color(50, 150, 250)),
	
	/**顶栏标题的前景色*/
	SHE_TILTLE_FOREGROUND(new Color(250, 200, 50)),
	
	/**顶栏头像标签边框的前景色*/
	TOP_BAR_HEAD_PORTRAIT_BORDER(new Color(240, 240, 240, 200)),
	
	/**顶栏昵称标签的前景色1*/
	TOP_BAR_NICKNAME_FOREGROUND_OUT(new Color(180, 180, 180)),
	
	/**顶栏昵称标签的前景色2*/
	TOP_BAR_NICKNAME_FOREGROUND_IN(new Color(250, 250, 250)),
	
	/**顶栏分隔符的前景色*/
	TOP_BAR_SEVERANCE_FOREGROUND(new Color(100, 100, 100)),
	
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