package team.ecust.she.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import team.ecust.she.common.RandomNo;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.dao.InformDao;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.dao.OrderDao;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Inform;
import team.ecust.she.model.Order;
import team.ecust.she.model.Inform.InformType;
import team.ecust.she.model.Member;
import team.ecust.she.model.Order.OrderState;

import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public final class OrderList extends JPanel {
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public OrderList(String orderNo) {
		setOrderNo(orderNo);
		//display("", null);
	}

	public void display(String goodsName, Order order) {
		setPreferredSize(new Dimension(800, 100));
		setBorder(new LineBorder(Color.blue, 1));
		setLayout(new GridLayout(3, 2, 0, 4));
		
		JLabel name = new JLabel("物品名称：" + goodsName);
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OrderDao dao = new OrderDao();
				Order order = dao.getOrderByOrder(orderNo);
				if(order.getState() == OrderState.UNDERTERMINED &&
						((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()).compareTo(order.getDeadline())) > 0 &&
						dao.updateOrderState("succeed", order.getOrderNo())) {
					InformDao informDao = new InformDao();
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
					informToBuyer.setContent("订单" + order.getOrderNo() + "已自动成功完成。你现在可以去评价订单了");
					informToBuyer.setInformType(InformType.ORDER_INFO);
					informToBuyer.setMemberNo(order.getMemberNo());
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
					informToSeller.setContent("订单" + order.getOrderNo() + "已自动成功完成。你现在可以去评价订单了");
					informToSeller.setInformType(InformType.ORDER_INFO);
					informToSeller.setMemberNo((new IdleGoodsDao()).getMemberNoByIdleGoods(order.getIdleGoodsNo()));
					informToSeller.setSentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
					if(informDao.insertNewInform(informToSeller) && informDao.insertNewInform(informToBuyer))
						order.setState(OrderState.SUCCEED);
				}
				MemberDao dao2 = new MemberDao();
				IdleGoodsDao dao3 = new IdleGoodsDao(); 
				Member buyer = dao2.getMemberToEdit(order.getMemberNo());
				IdleGoods goods = dao3.getIdleGoodsByIdleGoods(order.getIdleGoodsNo());
				Member seller = dao2.getMemberToEdit(dao3.getMemberNoByIdleGoods(order.getIdleGoodsNo()));
				OrderInfo info = new OrderInfo(orderNo);
				Index.getInstance().showInCard(info);
				info.display(order, seller, buyer, goods);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				name.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));;
			}
		});
		name.setForeground(Color.BLUE);
		name.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		add(name);
		
		JLabel state = new JLabel("订单状态：" + order.switchStateToChinese());
		state.setFont(new Font("黑体", Font.PLAIN, 22));
		add(state);
		
		JLabel price = new JLabel("成交价格：" + order.getPrice() + "元");
		price.setForeground(new Color(139, 0, 0));
		price.setFont(new Font("宋体", Font.PLAIN, 20));
		add(price);
		
		JLabel deadline = new JLabel("截止日期：" + order.getDeadline());
		deadline.setForeground(new Color(199, 21, 133));
		deadline.setFont(new Font("宋体", Font.PLAIN, 20));
		add(deadline);
		
		JLabel ordertime = new JLabel("下单时间：" + order.getOrderTime());
		ordertime.setForeground(new Color(255, 127, 80));
		ordertime.setFont(new Font("宋体", Font.PLAIN, 20));
		add(ordertime);
		
		JLabel effectivetime = new JLabel("生效时间：" + order.getEffectiveTime());
		effectivetime.setForeground(new Color(138, 43, 226));
		effectivetime.setFont(new Font("宋体", Font.PLAIN, 20));
		add(effectivetime);
	}
}