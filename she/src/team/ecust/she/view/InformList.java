package team.ecust.she.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

import team.ecust.she.common.RandomNo;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.dao.InformDao;
import team.ecust.she.dao.OrderDao;
import team.ecust.she.model.Inform;
import team.ecust.she.model.Inform.InformType;
import team.ecust.she.model.Order;
import team.ecust.she.model.Order.OrderState;
import team.ecust.she.view.PromptBox.Tips;

import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class InformList extends JPanel {
	private String orderNo;
	private String informNo;
	private JTextArea content;
	
	public String getInformNo() {
		return informNo;
	}
	public void setInformNo(String informNo) {
		this.informNo = informNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setContent(String text) {
		content.setText(text);
	}
	
	public InformList(String informNo) {
		setInformNo(informNo);
		//display(null);
	}
	
	public void display(Inform inform) {
		setPreferredSize(new Dimension(400, 120));
		setLayout(new BorderLayout());
		setBorder(new LineBorder(new Color(120, 142, 198)));
		
		JPanel above = new JPanel(new GridLayout(2, 0));
		above.setPreferredSize(new Dimension(400, 40));
		add(above, BorderLayout.NORTH);
		
		JPanel title = new JPanel(new GridLayout(0, 2));
		above.add(title);
		
		JLabel type = new JLabel(inform.switchInformTypeToChinese());
		type.setForeground(new Color(255, 105, 180));
		type.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		title.add(type);
		
		if(inform.getInformType() == InformType.CFM_ORDER) {
			JButton btnNewButton = new JButton("确认订单");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					OrderDao dao1 = new OrderDao();
					if(!dao1.existItemByOrder(orderNo)) {
						(new PromptBox(Tips.ERROR)).open("订单不存在");
						return;
					}
					Order order = dao1.getOrderByOrder(orderNo);
					if(!Index.getInstance().getMemberNo().equals(order.getMemberNo())) {
						(new PromptBox()).open("不是你的订单");
						return;
					}
					if(order.getState() != OrderState.ORDER) {
						(new PromptBox()).open("订单早已确认");
						return;
					}
					if(!dao1.updateOrderState("undetermined", orderNo) || !dao1.updateOrderEffectiveTime
							((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), orderNo)) {
						(new PromptBox()).open("确认订单失败");
						return;
					}
					Inform inform = new Inform(RandomNo.getDefaultRandomNo().getRandomNo(10));
					InformDao dao2 = new InformDao();
					for(int i = 0; i < 10; i++) {
						if(!dao2.existInform(inform.getInformNo()))
							break;
						if(i == 9) {
							(new PromptBox()).open("通知对方失败");
							return;
						}
						inform.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(10));
					}
					inform.setInformType(InformType.ORDER_INFO);
					inform.setContent("买家已确认订单");
					IdleGoodsDao dao3 = new IdleGoodsDao();
					inform.setMemberNo(dao3.getMemberNoByIdleGoods(order.getIdleGoodsNo()));
					inform.setSentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
					if(dao2.insertNewInform(inform)) {
						(new PromptBox()).open("订单确认成功");
						return;
					} else {
						(new PromptBox(Tips.ERROR)).open("通知对方失败");
						return;
					}
				}
			});
			btnNewButton.setForeground(new Color(138, 43, 226));
			btnNewButton.setBackground(new Color(100, 150, 255));
			btnNewButton.setFont(new Font("宋体", Font.PLAIN, 16));
			title.add(btnNewButton);
		}
		
		JLabel time = new JLabel("时间：" + inform.getSentTime());
		time.setFont(new Font("黑体", Font.PLAIN, 16));
		above.add(time);
		
		content = new JTextArea();
		content.setBackground(new Color(240, 240, 240));
		content.setForeground(Color.blue);
		content.setFont(new Font("等线", Font.PLAIN, 18));
		content.setEditable(false);
		content.setLineWrap(true);
		add(content, BorderLayout.CENTER);
	}
}