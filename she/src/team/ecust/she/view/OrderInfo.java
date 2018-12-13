package team.ecust.she.view;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import team.ecust.she.common.RandomNo;
import team.ecust.she.controller.ViewIdleGoodsInfo;
import team.ecust.she.controller.ViewMineInfo;
import team.ecust.she.dao.CommentDao;
import team.ecust.she.dao.InformDao;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.dao.OrderDao;
import team.ecust.she.model.Comment;
import team.ecust.she.model.Comment.CommentState;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Inform;
import team.ecust.she.model.Inform.InformType;
import team.ecust.she.model.Member;
import team.ecust.she.model.Order;
import team.ecust.she.model.Order.OrderState;
import team.ecust.she.view.PromptBox.Tips;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public final class OrderInfo extends JPanel {
	private String goodsNo;
	private String orderNo;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public OrderInfo(String orderNo) {
		this.setOrderNo(orderNo);
		//display(null, null, null, null);
	}
	
	public void display(Order order, Member seller, Member buyer, IdleGoods goods) {
		int width = getWidth();
		int height = getHeight();
		//int width = 1620;
		//int height = 1020;
		//
			//setSize(width, height);
		//
		setLayout(new GridLayout(2, 0, 0, 20));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		goodsNo = goods.getIdleGoodsNo();
		
		JPanel above = new JPanel(new GridLayout(7, 2, 0, height/12 - 40));
		add(above);
		
		JLabel goodsName = new JLabel("物品名称：" + goods.getIdleGoodsName());
		goodsName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewIdleGoodsInfo.doIt(goodsNo, true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				goodsName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				goodsName.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		goodsName.setForeground(new Color(102, 205, 170));
		goodsName.setFont(new Font("微软雅黑", Font.BOLD, 22));
		above.add(goodsName);
		
		JLabel sellerNo = new JLabel("卖家学号：" + seller.getMemberNo());
		sellerNo.setForeground(new Color(138, 43, 226));
		sellerNo.setFont(new Font("微软雅黑", Font.BOLD, 22));
		above.add(sellerNo);
		
		JLabel salePrice = new JLabel("物品售价：" + goods.getSalePrice() + "元");
		salePrice.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(salePrice);
		
		JLabel sellerName = new JLabel("卖家昵称：" + seller.getNickname());
		sellerName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(sellerName);
		
		JLabel donePrice = new JLabel("成交价格：" + order.getPrice() + "元");
		donePrice.setForeground(new Color(233, 150, 122));
		donePrice.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(donePrice);
		
		JLabel sellerPhone = new JLabel("卖家电话：" + seller.getPhone());
		sellerPhone.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(sellerPhone);
		
		JLabel state = new JLabel("订单状态：" + order.switchStateToChinese());
		state.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(state);
		
		JLabel buyerNo = new JLabel("买家学号：" + buyer.getMemberNo());
		buyerNo.setForeground(new Color(0, 191, 255));
		buyerNo.setFont(new Font("微软雅黑", Font.BOLD, 22));
		above.add(buyerNo);
		
		JLabel deadline = new JLabel("交易期限：" + order.getDeadline());
		deadline.setForeground(new Color(233, 150, 122));
		deadline.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(deadline);
		
		JLabel buyerName = new JLabel("买家昵称：" + buyer.getNickname());
		buyerName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(buyerName);
		
		JLabel orderTime = new JLabel("下单时间：" + order.getOrderTime());
		orderTime.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(orderTime);
		
		JLabel buyerPhone = new JLabel("买家电话：" + buyer.getPhone());
		buyerPhone.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(buyerPhone);
		
		JLabel effectiveTime = new JLabel(order.getEffectiveTime() == null ? "生效时间：" : "生效时间：" + order.getEffectiveTime());
		effectiveTime.setForeground(new Color(233, 150, 122));
		effectiveTime.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		above.add(effectiveTime);
		
		JPanel panel = new JPanel(new GridLayout(0, 2, width/2 - 400, 0));
		panel.setBorder(new EmptyBorder(0, 20, 0, 20));
		above.add(panel);
		
		if(order.getState() == OrderState.ORDER && Index.getInstance().getMemberNo().equals(seller.getMemberNo())) {
			JButton delete = new JButton("删除订单");
			delete.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					OrderDao dao = new OrderDao();
					Order order = dao.getOrderByOrder(orderNo);
					if(order.getState() != OrderState.ORDER) {
						state.setText("订单状态：" + order.switchStateToChinese());
						(new PromptBox(Tips.ERROR)).open("订单无法删除");
						return;
					}
					if(dao.deleteOrder(orderNo)) {
						ViewMineInfo.doIt();
						(new PromptBox(Tips.ERROR)).open("成功删除订单");
					} else {
						(new PromptBox(Tips.ERROR)).open("删除失败");
					}
				}
			});
			delete.setBackground(new Color(255, 127, 80));
			delete.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel.add(delete);
		}
		
		if(order.getState() == OrderState.UNDERTERMINED || order.getState() == OrderState.FAIL) {
			JButton cancel = new JButton("举报订单");
			cancel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					OrderDao dao = new OrderDao();
					if(dao.getOrderByOrder(orderNo).getState() == OrderState.SUCCEED) {
						(new PromptBox(Tips.TICK)).open("已过举报时间");
						return;
					}
					if(dao.updateOrderState("fail", orderNo)) {
						(new PromptBox(Tips.TICK)).open("举报成功");
					} else {
						(new PromptBox(Tips.ERROR)).open("举报失败");
					}
				}
			});
			cancel.setBackground(new Color(255, 20, 147));
			cancel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel.add(cancel);
		}
		
		if(order.getState() != OrderState.ORDER)
			loadComment(seller, buyer);
	}
	
	private void loadComment(Member seller, Member buyer) {
		JPanel below =  new JPanel(new GridLayout(2, 0, 0, 20));
		add(below);
		
		String othersNo;
		if(Index.getInstance().getMemberNo().equals(buyer.getMemberNo()))
			othersNo = seller.getMemberNo();
		else
			othersNo = buyer.getMemberNo();
		
		CommentDao dao = new CommentDao();
		if(dao.existComment(orderNo, Index.getInstance().getMemberNo())) {
			Comment comment = dao.getComment(orderNo, Index.getInstance().getMemberNo());
			
			JPanel panel_1 = new JPanel(new BorderLayout(0, 5));
			panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 128, 128), 4), "我的评价", 
					TitledBorder.CENTER, TitledBorder.TOP, new Font("微软雅黑", Font.BOLD, 20), new Color(186, 85, 211)));
			below.add(panel_1);
			
			JPanel panel_2 = new JPanel(new GridLayout(0, (getWidth() - 60)/300, 20, 0));
			panel_1.add(panel_2, BorderLayout.NORTH);
			
			JLabel lblNewLabel = new JLabel("评分：" + comment.getMark());
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel_2.add(lblNewLabel);
			
			JLabel lblNewLabel_2 = new JLabel("时间：" + comment.getCommentTime());
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel_2.add(lblNewLabel_2);
			
			JLabel lblNewLabel_1 = new JLabel("状态：" + comment.switchCommentStateToChinese());
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel_2.add(lblNewLabel_1);
			
			JTextArea textArea = new JTextArea(comment.getContent());
			textArea.setEditable(false);
			textArea.setFont(new Font("宋体", Font.PLAIN, 20));
			textArea.setBorder(new TitledBorder(null, "评价内容", 
					TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 16), new Color(135, 206, 250)));
			panel_1.add(textArea, BorderLayout.CENTER);
		} else {
			JPanel panel_1 = new JPanel(new BorderLayout(0, 5));
			panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 128, 128), 4), "我的评价", 
					TitledBorder.CENTER, TitledBorder.TOP, new Font("微软雅黑", Font.BOLD, 20), new Color(186, 85, 211)));
			below.add(panel_1);
			
			JPanel panel_2 = new JPanel(new GridLayout(0, (getWidth() - 60)/120, 20, 0));
			panel_1.add(panel_2, BorderLayout.NORTH);
			
			JLabel lblNewLabel = new JLabel("评分：");
			lblNewLabel.setForeground(new Color(0, 191, 255));
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel_2.add(lblNewLabel);
			
			JComboBox<String> comboBox = new JComboBox<String>();
			comboBox.addItem("一分");
			comboBox.addItem("两分");
			comboBox.addItem("三分");
			comboBox.addItem("四分");
			comboBox.addItem("五分");
			comboBox.setBackground(new Color(211, 211, 211));
			comboBox.setForeground(new Color(0, 191, 255));
			comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			panel_2.add(comboBox);
			
			JButton btnNewButton = new JButton("提交");
			btnNewButton.setBackground(new Color(152, 251, 152));
			btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			panel_2.add(btnNewButton);
			
			JTextArea textArea = new JTextArea();
			textArea.setFont(new Font("宋体", Font.PLAIN, 20));
			textArea.setBorder(new TitledBorder(null, "评价内容", 
					TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 16), new Color(135, 206, 250)));
			panel_1.add(textArea, BorderLayout.CENTER);
			
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!btnNewButton.isEnabled())
						return;
					if(textArea.getText() == null || textArea.getText().length() > 255) {
						(new PromptBox(Tips.ERROR)).open("评价内容长度不正确");
						return;
					}
					Comment comment = new Comment(orderNo, Index.getInstance().getMemberNo());
					comment.setCommentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
					comment.setContent(textArea.getText());
					comment.setMark(comboBox.getSelectedIndex() + 1);
					CommentDao dao = new CommentDao();
					if(dao.insertNewComment(comment)) {
						MemberDao dao2 = new MemberDao();
						int credit = dao2.getCreditOfMember(othersNo);
						int dc = comment.getMark();
						if(dc < 4)
							credit = credit > dc ? credit + (dc -= 4) : 0;
						else
							credit = credit > 100 - dc + 4 ? 100 : credit + (dc -= 4);
						if(dao2.updateCreditOfMember(othersNo, credit)) {
							Inform inform = new Inform(RandomNo.getDefaultRandomNo().getRandomNo(10));
							InformDao dao3 = new InformDao();
							for(int i = 0; i < 10; i++) {
								if(!dao3.existInform(inform.getInformNo()))
									break;
								if(i == 9) {
									(new PromptBox(Tips.TICK)).open("评论成功");
									btnNewButton.setEnabled(false);
									textArea.setEditable(false);
									return;
								}
								inform.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(10));
							}
							inform.setInformType(InformType.COMMENT);
							inform.setMemberNo(othersNo);
							inform.setSentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
							inform.setContent("用户" + Index.getInstance().getMemberNo() + "已评论订单。您的信用分变化为" + credit);
							dao3.insertNewInform(inform);
							(new PromptBox(Tips.TICK)).open("评论成功");
							btnNewButton.setEnabled(false);
							textArea.setEditable(false);
						} else {
							(new PromptBox(Tips.ERROR)).open("评论失败");
						}
					} else {
						(new PromptBox(Tips.ERROR)).open("评论失败");
					}
				}
			});
		}
		
		if(dao.existComment(orderNo, othersNo)) {
			Comment comment = dao.getComment(orderNo, othersNo);
			JPanel panel_1 = new JPanel(new BorderLayout(0, 5));
			panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 128, 128), 4), "他的评价", 
					TitledBorder.CENTER, TitledBorder.TOP, new Font("微软雅黑", Font.BOLD, 20), new Color(186, 85, 211)));
			below.add(panel_1);
			
			JPanel panel_2 = new JPanel(new GridLayout(0, (getWidth() - 60)/300, 20, 0));
			panel_1.add(panel_2, BorderLayout.NORTH);
			
			JLabel lblNewLabel = new JLabel("评分：" + comment.getMark());
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel_2.add(lblNewLabel);
			
			JLabel lblNewLabel_2 = new JLabel("时间：" + comment.getCommentTime());
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel_2.add(lblNewLabel_2);
			
			JLabel lblNewLabel_1 = new JLabel("状态：" + comment.switchCommentStateToChinese());
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			panel_2.add(lblNewLabel_1);
			
			if(comment.getState() == CommentState.NORMAL) {
				JButton btnNewButton = new JButton("举报此评论");
				btnNewButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(!btnNewButton.isEnabled())
							return;
						CommentDao dao = new CommentDao();
						Comment comment = new Comment(orderNo, othersNo);
						comment.setState(CommentState.TIPOFF);
						if(dao.updateCommentState(comment)) {
							MemberDao dao2 = new MemberDao();
							int credit = dao2.getCreditOfMember(othersNo);
							credit = credit >= 2 ? credit - 2 : 0;
							dao2.updateCreditOfMember(othersNo, credit);
							Inform inform = new Inform(RandomNo.getDefaultRandomNo().getRandomNo(10));
							InformDao dao3 = new InformDao();
							for(int i = 0; i < 10; i++) {
								if(!dao3.existInform(inform.getInformNo()))
									break;
								if(i == 9) {
									(new PromptBox(Tips.TICK)).open("举报成功");
									btnNewButton.setEnabled(false);
									lblNewLabel_1.setText("状态：被举报");
									return;
								}
								inform.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(10));
							}
							inform.setInformType(InformType.CREDIT);
							inform.setMemberNo(othersNo);
							inform.setSentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
							inform.setContent("用户" + Index.getInstance().getMemberNo() + "举报你的订单评论。您的信用分暂扣2分");
							dao3.insertNewInform(inform);
							(new PromptBox(Tips.TICK)).open("举报成功");
							btnNewButton.setEnabled(false);
							lblNewLabel_1.setText("状态：被举报");
						} else {
							(new PromptBox(Tips.ERROR)).open("举报失败");
						}
					}
				});
				btnNewButton.setBackground(new Color(255, 99, 71));
				btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));
				panel_2.add(btnNewButton);
			}
			
			JTextArea textArea = new JTextArea(comment.getContent());
			textArea.setEditable(false);
			textArea.setFont(new Font("宋体", Font.PLAIN, 20));
			textArea.setBorder(new TitledBorder(null, "评价内容", 
					TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 16), new Color(135, 206, 250)));
			panel_1.add(textArea, BorderLayout.CENTER);
		}
	}
}