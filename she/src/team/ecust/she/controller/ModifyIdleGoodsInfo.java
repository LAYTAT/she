package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.IdleGoods.IdlegoodsState;
import team.ecust.she.view.IdleGoodsInfoToEdit;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;

public final class ModifyIdleGoodsInfo <K extends JComponent> extends MouseAdapter {
	private IdleGoodsInfoToEdit edit;
	private K trigger;
	
	public ModifyIdleGoodsInfo(IdleGoodsInfoToEdit edit, K k) {
		this.edit = edit;
		this.trigger = k;
	}
	
	public synchronized static void doIt(IdleGoodsInfoToEdit edit) {
		IdleGoodsDao dao = new IdleGoodsDao();
		IdleGoods goods = dao.getIdleGoodsByIdleGoods(edit.getIdleGoodsNo());
		if(goods.getState() == IdlegoodsState.WORKOFF) {
			(new PromptBox(Tips.ERROR)).open("不能修改售出物品");
			return;
		}
		String name = edit.getGoodsName();
		if(name == null || name.length() < 2 || name.length() > 20) {
			(new PromptBox()).open("物品名称长度不对");
			return;
		}
		String salePrice = edit.getSalePrice();
		if(salePrice == null || !salePrice.matches("^([1-9][0-9]{0,4})|([1-9][0-9]{0,4}.[0-9]{1,2})")) {
			(new PromptBox()).open("物品售价格式错误");
			return;
		}
		String originalPrice = edit.getOriginalPrice();
		if(originalPrice == null || !originalPrice.matches("^([1-9][0-9]{0,4})|([1-9][0-9]{0,4}.[0-9]{1,2})")) {
			(new PromptBox()).open("物品原价格式错误");
			return;
		}
		IdlegoodsState state = edit.getState();
		if(state == IdlegoodsState.WORKOFF) {
			(new PromptBox()).open("物品状态不能改为售出");
			return;
		}
		String note = edit.getNote();
		if(note != null && note.length() > 255) {
			(new PromptBox()).open("物品说明长度不对");
			return;
		}
		goods.setIdleGoodsName(name);
		goods.setSalePrice(Float.valueOf(salePrice));
		goods.setOriginalPrice(Float.valueOf(originalPrice));
		goods.setDegree(edit.getDegree());
		goods.setState(state);
		goods.setNote(note);
		if(dao.updateIdleGoods(goods))
			(new PromptBox(Tips.TICK)).open("修改成功");
		else
			(new PromptBox(Tips.OFFLINE)).open("修改失败");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(trigger.isEnabled())
			doIt(edit);
	}
}