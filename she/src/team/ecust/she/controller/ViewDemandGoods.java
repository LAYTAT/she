package team.ecust.she.controller;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import team.ecust.she.dao.DemandGoodsDao;
import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.view.Colors;
import team.ecust.she.view.GoodsList;
import team.ecust.she.view.Index;
import team.ecust.she.view.PromptBox;

public final class ViewDemandGoods <K extends JComponent> extends MouseAdapter {
	private K trigger;

	public ViewDemandGoods(K k) {
		trigger = k;
	}
	
	public synchronized static void doIt() {
		DemandGoodsDao dao = new DemandGoodsDao();
		DemandGoods[] goods = dao.getAllDemandGoods();
		if(goods == null || goods.length == 0) {
			(new PromptBox()).open("没有记录");
			return;
		}
		JPanel panel = new JPanel();
		Index.getInstance().showInCard(new JScrollPane(panel));
		if(panel.getHeight() > goods.length * 220)
			panel.setLayout(new GridLayout(panel.getHeight()/220, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(goods.length, 0, 0, 20));
		for(int i = 0; i < goods.length; i++) {
			GoodsVariety[] variety = dao.getGoodsVarietyByGoods(goods[i].getDemandGoodsNo());
			GoodsList list = new GoodsList(goods[i].getDemandGoodsNo());
			panel.add(list);
			list.displayOthersDemandList(goods[i], variety);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt();
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
