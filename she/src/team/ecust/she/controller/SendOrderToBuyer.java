package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.IdleGoods.IdlegoodsState;
import team.ecust.she.view.IdleGoodsInfoToEdit;
import team.ecust.she.view.Index;
import team.ecust.she.view.OrderToEdit;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;

public final class SendOrderToBuyer <K extends JComponent> extends MouseAdapter {
	private K trigger;
	private IdleGoodsInfoToEdit edit;
	public SendOrderToBuyer(K k, IdleGoodsInfoToEdit edit) {
		this.edit = edit;
		this.trigger = k;
	}
	
	public synchronized static void doIt(IdleGoodsInfoToEdit edit) {
		IdleGoodsDao dao = new IdleGoodsDao();
		IdleGoods goods = dao.getIdleGoodsByIdleGoods(edit.getIdleGoodsNo());
		if(goods.getState() == IdlegoodsState.CANCEL) {
			(new PromptBox(Tips.ERROR)).open("物品已失效");
			return;
		}
		OrderToEdit order = new OrderToEdit(edit.getIdleGoodsNo());
		Index.getInstance().showInCard(order);
		order.display();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(trigger.isEnabled())
			doIt(edit);
	}
}