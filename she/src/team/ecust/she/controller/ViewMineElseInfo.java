package team.ecust.she.controller;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import team.ecust.she.common.RandomNo;
import team.ecust.she.dao.DemandGoodsDao;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.dao.InformDao;
import team.ecust.she.dao.MatchInfoDao;
import team.ecust.she.dao.OrderDao;
import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Inform;
import team.ecust.she.model.Inform.InformType;
import team.ecust.she.model.MatchInfo;
import team.ecust.she.model.Order;
import team.ecust.she.model.Order.OrderState;
import team.ecust.she.view.Colors;
import team.ecust.she.view.GoodsList;
import team.ecust.she.view.MineInfo;
import team.ecust.she.view.OrderList;
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
			GoodsList list = new GoodsList(goods[i].getIdleGoodsNo());
			panel.add(list);
			list.displayIdleList(goods[i], variety, false);
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
			GoodsList list = new GoodsList(goods[i].getDemandGoodsNo());
			panel.add(list);
			list.displayMineDemandList(goods[i], variety);
		}
		panel.repaint();
	}
	
	public synchronized static void viewOrder(MineInfo info) {
		OrderDao orderDao = new OrderDao();
		Order[] orders = orderDao.getOrderbyMemberNo(info.getMemberNo());
		if(orders == null || orders.length == 0) {
			(new PromptBox()).open("没有记录");
			return;
		}
		JPanel panel = new JPanel();
		info.showInCard(new JScrollPane(panel));
		if(panel.getHeight() > 120 * orders.length)
			panel.setLayout(new GridLayout(panel.getHeight()/120, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(orders.length, 0, 0, 20));
		IdleGoodsDao iDao = new IdleGoodsDao();
		InformDao informDao = new InformDao();
		for(int i = 0; i < orders.length; i++) {
			if(orders[i].getState() == OrderState.UNDERTERMINED &&
					((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()).compareTo(orders[i].getDeadline())) > 0 &&
					orderDao.updateOrderState("succeed", orders[i].getOrderNo())) {
				Inform informToBuyer, informToSeller;
				informToBuyer = new Inform(RandomNo.getDefaultRandomNo().getRandomNo(10));
				for(int j = 0; j < 10; j++) {
					if(!informDao.existInform(informToBuyer.getInformNo()))
						break;
					if(j == 9) {
						informToBuyer.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(9));
						break;
					}
					informToBuyer.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(10));	
				}
				informToBuyer.setContent("订单" + orders[i].getOrderNo() + "已自动成功完成。你现在可以去评价订单了");
				informToBuyer.setInformType(InformType.ORDER_INFO);
				informToBuyer.setMemberNo(orders[i].getMemberNo());
				informToBuyer.setSentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				informToSeller = new Inform(RandomNo.getDefaultRandomNo().getRandomNo(10));
				for(int j = 0; j < 10; j++) {
					if(!informDao.existInform(informToSeller.getInformNo()))
						break;
					if(j == 9) {
						informToSeller.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(9));
						break;
					}
					informToSeller.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(10));
				}
				informToSeller.setContent("订单" + orders[i].getOrderNo() + "已自动成功完成。你现在可以去评价订单了");
				informToSeller.setInformType(InformType.ORDER_INFO);
				informToSeller.setMemberNo(iDao.getMemberNoByIdleGoods(orders[i].getIdleGoodsNo()));
				informToSeller.setSentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				if(informDao.insertNewInform(informToSeller) && informDao.insertNewInform(informToBuyer))
					orders[i].setState(OrderState.SUCCEED);
			}
			OrderList list = new OrderList(orders[i].getOrderNo());
			panel.add(list);
			list.display(iDao.getIdleGoodsByIdleGoods(orders[i].getIdleGoodsNo()).getIdleGoodsName(), orders[i]);
		}
	}

	public synchronized static void viewMatchInfo(MineInfo info) {
		MatchInfoDao infoDao = new MatchInfoDao();
		if(!infoDao.existItemByMemberNo(info.getMemberNo())) {
			(new PromptBox()).open("没有记录");
			return;
		}
		MatchInfo[] infos = infoDao.getMatchInfoByMemebrNo(info.getMemberNo());
		JPanel panel = new JPanel();
		info.showInCard(new JScrollPane(panel));
		if(panel.getHeight() > 120 * infos.length)
			panel.setLayout(new GridLayout(panel.getHeight()/120, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(infos.length, 0, 0, 20));
		IdleGoodsDao goodsDao = new IdleGoodsDao();
		for(int i = 0; i < infos.length; i++) {
			IdleGoods goods = goodsDao.getIdleGoodsByIdleGoods(infos[i].getIdleGoodsNo());
			GoodsVariety[] variety = goodsDao.getGoodsVarietyByGoods(goods.getIdleGoodsNo());
			GoodsList list = new GoodsList(goods.getIdleGoodsNo());
			panel.add(list);
			list.displayIdleList(goods, variety, true);
		}
		panel.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (option) {
		case 1 : viewIdle(info, true); break;
		case 2 : viewDemand(info); break;
		case 3 : viewOrder(info); break;
		case 4 : viewMatchInfo(info); break;
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