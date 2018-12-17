package team.ecust.she.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import team.ecust.she.common.RandomNo;
import team.ecust.she.controller.ViewMineInfo;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.dao.InformDao;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.dao.OrderDao;
import team.ecust.she.model.Inform;
import team.ecust.she.model.Inform.InformType;
import team.ecust.she.model.Order;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class OrderToEdit extends JPanel {
	private String goodsNo;
	private JComboBox<String> deadline;
	
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public OrderToEdit(String goodsNo) {
		setGoodsNo(goodsNo);
		//display();
	}
	
	public void display() {
		setBackground(Colors.CENTER_CARD_BACKGROUND.getColor());
		setBorder(new EmptyBorder((getHeight()-320)/2, (getWidth()-480)/2, (getHeight()-320)/2, (getWidth()-480)/2));
		setLayout(new BorderLayout());
		
		JPanel center = new JPanel(new GridLayout(4, 0, 0, 50));
		center.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(center, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		center.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("买家学号：");
		lblNewLabel.setForeground(new Color(0, 191, 255));
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		panel_1.add(lblNewLabel, BorderLayout.WEST);
		
		JTextField buyerNo = new JTextField();
		buyerNo.setForeground(new Color(0, 191, 255));
		buyerNo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		panel_1.add(buyerNo, BorderLayout.CENTER);
		buyerNo.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		center.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("成交价格：");
		lblNewLabel_1.setForeground(new Color(100, 149, 237));
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		panel_2.add(lblNewLabel_1, BorderLayout.WEST);
		
		JTextField price = new JTextField();
		price.setForeground(new Color(100, 149, 237));
		price.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		panel_2.add(price, BorderLayout.CENTER);
		price.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("元");
		lblNewLabel_2.setForeground(new Color(100, 149, 237));
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		panel_2.add(lblNewLabel_2, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		center.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("交易期限：");
		lblNewLabel_3.setForeground(new Color(205, 92, 92));
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		panel_3.add(lblNewLabel_3, BorderLayout.WEST);
		
		deadline = new JComboBox<String>();
		deadline.addItem("一天");
		deadline.addItem("三天");
		deadline.addItem("五天");
		deadline.addItem("七天");
		deadline.setBackground(new Color(169, 169, 169));
		deadline.setForeground(new Color(205, 92, 92));
		deadline.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		panel_3.add(deadline, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 40, 0, 40));
		center.add(panel);
		panel.setLayout(new GridLayout(0, 2, 40, 0));
		
		JButton btnNewButton = new JButton("取消");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Index.getInstance().previousCard();
			}
		});
		btnNewButton.setForeground(new Color(255, 69, 0));
		btnNewButton.setBackground(new Color(169, 169, 169));
		btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("发送");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String memberNo = buyerNo.getText();
				if(memberNo == null || !memberNo.matches("10[0-9]{6}")) {
					(new PromptBox()).open("买家学号格式不对");
					return;
				}
				if(memberNo.equals(Index.getInstance().getMemberNo())) {
					(new PromptBox()).open("你不能卖给你自己");
					return;
				}
				String prices = price.getText();
				if(prices == null || !prices.matches("^([1-9][0-9]{0,4})|([1-9][0-9]{0,4}.[0-9]{1,2})")) {
					(new PromptBox()).open("价格格式不对");
					return;
				}
				MemberDao dao = new MemberDao();
				if(!dao.existItem(memberNo)) {
					(new PromptBox()).open("买家不存在");
					return;
				}
				if(dao.getCreditOfMember(memberNo) < 60) {
					(new PromptBox()).open("买家信用分低于60");
					return;
				}
				String phone = dao.getMemberToEdit(memberNo).getPhone();
				if(phone == null || phone.isEmpty()) {
					(new PromptBox()).open("买家未填写电话信息");
					return;
				}
				Order order = new Order(RandomNo.getDefaultRandomNo().getRandomNo(10));
				OrderDao oDao = new OrderDao();
				for(int i = 0; i < 10; i++) {
					if(!oDao.existItemByOrder(order.getOrderNo()))
						break;
					if(i == 9) {
						(new PromptBox()).open("发送失败");
						return;
					}
					order.setOrderNo(RandomNo.getDefaultRandomNo().getRandomNo(10));
				}
				order.setMemberNo(memberNo);
				order.setIdleGoodsNo(goodsNo);
				Date date = new Date();
				order.setOrderTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date));
				long day = date.getTime();
				day += (deadline.getSelectedIndex()*2 + 1)*24*3600000;
				date.setTime(day);
				order.setDeadline((new SimpleDateFormat("yyyy-MM-dd")).format(date));
				order.setPrice(Float.valueOf(prices));
				if(!oDao.insertNewOrder(order)) {
					(new PromptBox()).open("发送失败");
					return;
				}
				Inform inform = new Inform(RandomNo.getDefaultRandomNo().getRandomNo(10));
				InformDao iDao = new InformDao();
				for(int i = 0; i < 10; i++) {
					if(!iDao.existInform(inform.getInformNo()))
						break;
					if(i == 9) {
						(new PromptBox()).open("发送失败");
						return;
					}
					inform.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(10));
				}
				inform.setMemberNo(memberNo);
				inform.setInformType(InformType.CFM_ORDER);
				inform.setContent(order.getOrderNo());
				inform.setSentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				if(!iDao.insertNewInform(inform)) {
					(new PromptBox()).open("发送失败");
					return;
				}
				IdleGoodsDao dao2 = new IdleGoodsDao();
				if(dao2.updateState(goodsNo, "workoff")) {
					(new PromptBox()).open("发送成功");
					ViewMineInfo.doIt();
					return;
				} else {
					(new PromptBox()).open("发送失败");
					return;
				}
			}
		});
		btnNewButton_1.setForeground(new Color(30, 144, 255));
		btnNewButton_1.setBackground(new Color(169, 169, 169));
		btnNewButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		panel.add(btnNewButton_1);
	}
}