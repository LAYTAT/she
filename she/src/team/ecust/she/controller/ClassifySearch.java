package team.ecust.she.controller;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.view.Colors;
import team.ecust.she.view.GoodsList;
import team.ecust.she.view.Index;
import team.ecust.she.view.PromptBox;

public final class ClassifySearch <K extends JComponent> extends MouseAdapter {
	/**触发业务的组件类型K和实例trigger*/
	private K trigger;
	private String keyWord;
	
	/**
	 * <p>为实例k添加顶栏搜索这一业务控制器。
	 * <p>不对参数做空值判断。
	 * @param k 需要添加这一业务的组件
	 */
	public ClassifySearch(K k, String keyWord) {
		trigger = k;
		this.keyWord = keyWord;
	}
	
	public synchronized static void doIt(String keyWord) {
		IdleGoodsDao dao = new IdleGoodsDao();
		IdleGoods[] goods = dao.getIdleGoodsBySearch(keyWord);
		if(goods == null || goods.length == 0) {
			(new PromptBox()).open("没有记录");
			return;
		}
		JPanel panel = new JPanel();
		Index.getInstance().showInCard(panel);
		if(panel.getHeight() > 120 * goods.length)
			panel.setLayout(new GridLayout(panel.getHeight()/120, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(goods.length, 0, 0, 20));
		for(int i = 0; i < goods.length; i++) {
			GoodsVariety[] variety = dao.getGoodsVarietyByGoods(goods[i].getIdleGoodsNo());
			GoodsList list = new GoodsList(goods[i].getIdleGoodsNo());
			panel.add(list);
			list.displayIdleList(goods[i], variety, true);
		}
		panel.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(keyWord);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		trigger.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		trigger.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
	}
}