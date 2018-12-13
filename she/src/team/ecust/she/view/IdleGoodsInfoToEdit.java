package team.ecust.she.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.wis.pack.component.Photo;

import team.ecust.she.controller.ModifyIdleGoodsInfo;
import team.ecust.she.controller.SendOrderToBuyer;
import team.ecust.she.controller.ViewMineInfo;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.IdleGoods.IdlegoodsState;
import team.ecust.she.view.PromptBox.Tips;
import team.ecust.she.model.Picture;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class IdleGoodsInfoToEdit extends JPanel {
	private String idleGoodsNo;
	private JTextField name;
	private JTextField salePrice;
	private JTextField originalPrice;
	private JComboBox<String> degree;
	private JComboBox<String> state;
	private JTextArea right;
	
	public String getIdleGoodsNo() {
		return idleGoodsNo;
	}

	public void setIdleGoodsNo(String idleGoodsNo) {
		this.idleGoodsNo = idleGoodsNo;
	}
	
	public String getGoodsName() {
		return name.getText();
	}
	public String getSalePrice() {
		return salePrice.getText();
	}
	public String getOriginalPrice() {
		return originalPrice.getText();
	}
	/**已调整*/
	public int getDegree() {
		return degree.getSelectedIndex() + 1;
	}
	/**已调整*/
	public IdlegoodsState getState() {
		switch(state.getSelectedIndex()) {
		case 0 : return IdlegoodsState.ON_SALE;
		case 1 : return IdlegoodsState.WORKOFF;
		case 2 : return IdlegoodsState.CANCEL;
		default : return IdlegoodsState.ON_SALE;
		}
	}
	public String getNote() {
		return right.getText();
	}

	public IdleGoodsInfoToEdit(String goodsNo) {
		setIdleGoodsNo(goodsNo);
		//display(null, null, null);
	}
	
	public void display(IdleGoods goods, GoodsVariety[] label, Picture[] picture) {
		int width = getWidth();
		int height = getHeight();
		setLayout(new GridLayout(2, 0, 0, 2));
		setBorder(new LineBorder(new Color(240, 240, 240), 20));
		
		JPanel above = new JPanel(new GridLayout(0, 2, 20, 0));
		add(above);
		
		JPanel left = new JPanel(new GridLayout(6, 0, 0, ((height - 60)/2 - 200)/5));
		above.add(left);
		
		JPanel panel = new JPanel(new BorderLayout());
		left.add(panel);
		
		JLabel namelabel = new JLabel("商品：");
		panel.add(namelabel, BorderLayout.WEST);
		namelabel.setForeground(new Color(255, 102, 51));
		namelabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		
		name = new JTextField(goods.getIdleGoodsName());
		name.setBorder(null);
		name.setForeground(new Color(255, 102, 51));
		name.setFont(new Font("微软雅黑", Font.BOLD, 22));
		panel.add(name, BorderLayout.CENTER);
		name.setColumns(10);
			
		JPanel second = new JPanel(new GridLayout(0, 2, 10, 0));
		left.add(second);
			
		JPanel sale = new JPanel(new BorderLayout());
		second.add(sale);
			
		JLabel salePricelabel = new JLabel("售价：");
		sale.add(salePricelabel, BorderLayout.WEST);
		salePricelabel.setForeground(new Color(30, 144, 255));
		salePricelabel.setFont(new Font("黑体", Font.PLAIN, 22));
		
		salePrice = new JTextField("" + goods.getSalePrice());
		salePrice.setFont(new Font("黑体", Font.PLAIN, 20));
		salePrice.setForeground(new Color(30, 144, 255));
		salePrice.setBorder(null);
		sale.add(salePrice, BorderLayout.CENTER);
		salePrice.setColumns(10);
		
		JLabel saleUnit = new JLabel("元");
		saleUnit.setFont(new Font("黑体", Font.PLAIN, 22));
		saleUnit.setForeground(new Color(30, 144, 255));
		sale.add(saleUnit, BorderLayout.EAST);
			
		JPanel original = new JPanel(new BorderLayout());
		second.add(original);
		
		JLabel originalPricelabel = new JLabel("原价：");
		original.add(originalPricelabel, BorderLayout.WEST);
		originalPricelabel.setForeground(new Color(0, 0, 255));
		originalPricelabel.setFont(new Font("黑体", Font.PLAIN, 22));
			
		originalPrice = new JTextField("" + goods.getOriginalPrice());
		originalPrice.setForeground(new Color(0, 0, 255));
		originalPrice.setFont(new Font("黑体", Font.PLAIN, 20));
		originalPrice.setBorder(null);
		original.add(originalPrice, BorderLayout.CENTER);
		originalPrice.setColumns(10);
			
		JLabel originalUnit = new JLabel("元");
		originalUnit.setFont(new Font("黑体", Font.PLAIN, 22));
		originalUnit.setForeground(new Color(0, 0, 255));
		original.add(originalUnit, BorderLayout.EAST);
			
		JPanel third = new JPanel(new GridLayout(0, 4));
		left.add(third);
		
		JLabel degreelabel = new JLabel("新旧度：");
		degreelabel.setHorizontalAlignment(SwingConstants.CENTER);
		degreelabel.setFont(new Font("宋体", Font.PLAIN, 22));
		third.add(degreelabel);
			
		degree = new JComboBox<String>();
		for(int i = 1; i < 10; i++)
			degree.addItem("" + i + "成新");
		degree.setSelectedIndex(goods.getDegree() - 1);
		degree.setBackground(new Color(0, 191, 255));
		degree.setFont(new Font("宋体", Font.PLAIN, 22));
		third.add(degree);
			
		JLabel statelabel = new JLabel("商品状态：");
		statelabel.setHorizontalAlignment(SwingConstants.CENTER);
		statelabel.setFont(new Font("宋体", Font.PLAIN, 22));
		third.add(statelabel);
			
		state = new JComboBox<String>();
		state.setBackground(new Color(0, 191, 255));
		state.setFont(new Font("宋体", Font.PLAIN, 22));
		third.add(state);
			
		JLabel idlelabel = new JLabel();
		String labeltext = "商品标签：";
		for(int i = 0; i < label.length; i++)
			labeltext += label[i].getKeyWord() + "——";
		idlelabel.setText(labeltext);
		idlelabel.setForeground(new Color(0, 206, 209));
		idlelabel.setFont(new Font("黑体", Font.PLAIN, 22));
		left.add(idlelabel);
			
		JLabel time = new JLabel("上线时间：" + goods.getUploadTime());
		time.setForeground(new Color(153, 50, 204));
		time.setFont(new Font("宋体", Font.PLAIN, 22));
		left.add(time);
			
		JPanel button = new JPanel(new GridLayout(0, 3, 50, 0));
		button.setBorder(new EmptyBorder(0, (width - 60)/4 - 260, 0, (width - 60)/4 - 260));
		left.add(button);
		
		JButton delete = new JButton("删除闲置");
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!delete.isEnabled())
					return;
				IdleGoodsDao dao = new IdleGoodsDao();
				IdleGoods goods = dao.getIdleGoodsByIdleGoods(getIdleGoodsNo());
				if(goods.getState() == IdlegoodsState.WORKOFF) {
					(new PromptBox(Tips.ERROR)).open("物品已售出，无法删除");
					return;
				}
				if(dao.deleteIdleGoods(getIdleGoodsNo())) {
					(new PromptBox(Tips.TICK)).open("删除成功");
					ViewMineInfo.doIt();
					return;
				}
				(new PromptBox(Tips.OFFLINE)).open("删除失败");
			}
		});
		delete.setBackground(new Color(250, 144, 55));
		delete.setFont(new Font("黑体", Font.PLAIN, 22));
		delete.setEnabled(false);
		button.add(delete);
		
		JButton modify = new JButton("确认修改");
		modify.addMouseListener(new ModifyIdleGoodsInfo<JButton>(this, modify));
		modify.setBackground(new Color(50, 254, 200));
		modify.setFont(new Font("黑体", Font.PLAIN, 22));
		modify.setEnabled(false);
		button.add(modify);
		
		JButton sendOrder = new JButton("发送订单");
		sendOrder.addMouseListener(new SendOrderToBuyer<JButton>(sendOrder, this));
		sendOrder.setBackground(new Color(30, 144, 255));
		sendOrder.setFont(new Font("黑体", Font.PLAIN, 22));
		sendOrder.setEnabled(false);
		button.add(sendOrder);
			
		state.addItem("在售");
		state.addItem("售出");
		state.addItem("失效");
		switch (goods.getState()) {
		case ON_SALE : state.setSelectedIndex(0); delete.setEnabled(true); modify.setEnabled(true); sendOrder.setEnabled(true); break;
		case WORKOFF : state.setSelectedIndex(1); break;
		case CANCEL  : state.setSelectedIndex(2); delete.setEnabled(true); modify.setEnabled(true); break;
		default : break;
		}
		
		right = new JTextArea(goods.getNote());
		right.setFont(new Font("宋体", Font.PLAIN, 20));
		right.setBackground(new Color(240, 240, 240));
		LineBorder border = new LineBorder(new Color(100, 100, 200), 3);
		right.setBorder(new TitledBorder(border, "\u8BE6\u7EC6\u8BF4\u660E", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("黑体", Font.PLAIN, 20), new Color(244, 164, 96)));
		right.setLineWrap(true);
		above.add(right);
		
		JPanel below = new JPanel(new GridLayout(0, 4, 10, 0));
		below.setBackground(new Color(50, 50, 50));
		below.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(below);
		
		for(int i = 0; i < picture.length; i++) {
			Photo photo = new Photo(picture[i].getPhoto());
			photo.setDragFunction(true);
			photo.setScaleFunction(true);
			photo.setRecoverFunction(true);
			below.add(photo);
		}
	}
}