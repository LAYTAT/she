package team.ecust.she.controller;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import team.ecust.she.dao.DemandGoodsDao;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.view.Colors;
import team.ecust.she.view.GoodsList;
import team.ecust.she.view.MineInfo;
import team.ecust.she.view.PromptBox;

public final class ViewMineElseInfo <K extends JComponent> extends MouseAdapter {
	private K trigger;
	private MineInfo info;
	private int option;
	public ViewMineElseInfo(K k, MineInfo info, int option) {
		this.trigger = k;
		this.info = info;
		this.option = option;
	}
	
	public synchronized static void viewIdle(MineInfo info, boolean tip) {
		IdleGoodsDao goodsDao = new IdleGoodsDao();
		IdleGoods[] goods = goodsDao.getIdleGoodsByMember(info.getMemberNo());
		if(goods == null || goods.length == 0) {
			if(tip)
				(new PromptBox()).open("您还没有上传的闲置物品");
			return;
		}
		JPanel panel = new JPanel();
		info.showInCard(new JScrollPane(panel));
		if(panel.getHeight() > 120 * goods.length)
			panel.setLayout(new GridLayout(panel.getHeight()/120, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(goods.length, 0, 0, 20));
		for(int i = 0; i < goods.length; i++) {
			GoodsVariety[] variety = goodsDao.getGoodsVarietyByGoods(goods[i].getIdleGoodsNo());
			GoodsList list = new GoodsList();
			panel.add(list);
			list.displayIdleList(goods[i], variety, true);
		}
		panel.repaint();
	}
	
	public synchronized static void viewDemand(MineInfo info) {
		DemandGoodsDao goodsDao = new DemandGoodsDao();
		DemandGoods[] goods = goodsDao.getDemandGoodsByMember(info.getMemberNo());
		if(goods == null || goods.length == 0) {
			(new PromptBox()).open("您还没有上传的心愿单");
			return;
		}
		JPanel panel = new JPanel();
		info.showInCard(new JScrollPane(panel));
		if(panel.getHeight() > 220 * goods.length)
			panel.setLayout(new GridLayout(panel.getHeight()/220, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(goods.length, 0, 0, 20));
		for(int i = 0; i < goods.length; i++) {
			GoodsVariety[] variety = goodsDao.getGoodsVarietyByGoods(goods[i].getDemandGoodsNo());
			GoodsList list = new GoodsList();
			panel.add(list);
			list.displayMineDemandList(goods[i], variety);
		}
		panel.repaint();
	}

	public synchronized static void viewOrder(MineInfo info) {
		
	}

	public synchronized static void viewComment(MineInfo info) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (option) {
		case 1 : viewIdle(info, true); break;
		case 2 : viewDemand(info); break;
		case 3 : viewOrder(info); break;
		case 4 : viewComment(info); break;
		default: break;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		trigger.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
		trigger.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		trigger.setBackground(Colors.DEFAULT_BACKGROUND.getColor());
		trigger.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}