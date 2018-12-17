package team.ecust.she.controller;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.view.Colors;
import team.ecust.she.view.GoodsList;
import team.ecust.she.view.Index;
import team.ecust.she.view.PromptBox;

public final class ViewIdleGoodsByContent <K extends JComponent> extends MouseAdapter {
	private K trigger;
	private boolean choice;

	public ViewIdleGoodsByContent(K k, boolean choice) {
		trigger = k;
		this.choice = choice;
	}
	
	public synchronized static void doIt(boolean choice) {
		IdleGoodsDao dao = new IdleGoodsDao();
		IdleGoods[] goods = null;
		if(choice)
			goods = dao.getIdleGoodsByHotDegree();
		else
			goods = dao.getIdleGoodsByLatestGoods();
		if(goods == null || goods.length == 0) {
			(new PromptBox()).open("没有记录");
			return;
		}
		JPanel panel = new JPanel();
		Index.getInstance().showInCard(new JScrollPane(panel));
		if(panel.getHeight() > goods.length * 120)
			panel.setLayout(new GridLayout(panel.getHeight()/120, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(goods.length, 0, 0, 20));
		for(int i = 0; i < goods.length; i++) {
			GoodsVariety[] variety = dao.getGoodsVarietyByGoods(goods[i].getIdleGoodsNo());
			GoodsList list = new GoodsList(goods[i].getIdleGoodsNo());
			panel.add(list);
			list.displayIdleList(goods[i], variety, true);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(choice);
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
