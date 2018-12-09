package team.ecust.she.controller;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import team.ecust.she.view.Index;
import team.ecust.she.view.PromptBox;

/**
 * <p>实现顶栏搜索功能的业务控制器。
 * <p>基于闲置物品的模糊搜索。
 * <p>响应鼠标的点击、进入、退出事件，业务由点击事件触发。
 * <p>使用泛型K，限定可以响应的组件范围为JComponent。
 */
public final class Search <K extends JComponent> extends MouseAdapter {
	/**触发业务的组件类型K和实例trigger*/
	private K trigger;
	
	/**
	 * <p>为实例k添加顶栏搜索这一业务控制器。
	 * <p>不对参数做空值判断。
	 * @param k 需要添加这一业务的组件
	 */
	public Search(K k) {
		trigger = k;
	}
	
	public synchronized static void doIt(String keyWord) {
		if(keyWord == null || keyWord.equals("")) {
			(new PromptBox()).open("输入为空");
			return;
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(Index.getInstance().getSearchContent());
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		trigger.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		trigger.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}