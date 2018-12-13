package team.ecust.she.controller;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Picture;
import team.ecust.she.view.IdleGoodsInfo;
import team.ecust.she.view.IdleGoodsInfoToEdit;
import team.ecust.she.view.Index;

public final class ViewIdleGoodsInfo <K extends JComponent> extends MouseAdapter {
	private K trigger;
	private String goodsNo;
	private boolean view;
	public ViewIdleGoodsInfo(K k, String goodsNo, boolean view) {
		this.trigger = k;
		this.goodsNo = goodsNo;
		this.view = view;
	}
	
	public synchronized static void doIt(String goodsNo, boolean view) {
		IdleGoodsDao dao = new IdleGoodsDao();
		IdleGoods goods = dao.getIdleGoodsByIdleGoods(goodsNo);
		GoodsVariety[] label = dao.getGoodsVarietyByGoods(goodsNo);
		Picture[] picture = dao.getPictureByIdleGoods(goodsNo);
		if(view) {
			IdleGoodsInfo info = new IdleGoodsInfo(goodsNo);
			Index.getInstance().showInCard(info);
			info.display(goods, label, picture);
		} else {
			IdleGoodsInfoToEdit info = new IdleGoodsInfoToEdit(goodsNo);
			Index.getInstance().showInCard(info);
			info.display(goods, label, picture);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		trigger.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		trigger.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(goodsNo, view);
	}
}