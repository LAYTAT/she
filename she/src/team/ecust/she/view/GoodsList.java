package team.ecust.she.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Picture;

import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GoodsList extends JPanel {
	private String goodsNo;
	
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public GoodsList(String goodsNo) {
		this.goodsNo = goodsNo;
		//displayOthersDemandList(new DemandGoods(""), null);
	}
	
	public void displayIdleList(IdleGoods goods, GoodsVariety[]variety) {
		setGoodsNo(goods.getIdleGoodsNo());
		setPreferredSize(new Dimension(1000, 100));
		setBorder(new LineBorder(Color.blue, 1));
		setLayout(new GridLayout(3, 3, 0, 0));
		
		JLabel name = new JLabel(goods.getIdleGoodsName());
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				name.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				IdleGoodsDao dao = new IdleGoodsDao();
				IdleGoods goods = dao.getIdleGoodsByIdleGoods(getGoodsNo());
				GoodsVariety[] label = dao.getGoodsVarietyByGoods(getGoodsNo());
				Picture[] picture = dao.getPictureByIdleGoods(getGoodsNo());
				IdleGoodsInfo info = new IdleGoodsInfo(getGoodsNo());
				Index.getInstance().showInCard(info);
				info.display(goods, label, picture);
			}
		});
		name.setFont(new Font("黑体", Font.PLAIN, 22));
		name.setOpaque(true);
		name.setForeground(Color.BLUE);
		add(name);
		
		JLabel time = new JLabel("上传时间：" + goods.getUploadTime());
		time.setFont(new Font("宋体", Font.PLAIN, 20));
		time.setOpaque(true);
		add(time);
		
		JLabel state = new JLabel("物品状态：" + goods.switchIdleGoodsStateToChinese());
		state.setFont(new Font("宋体", Font.PLAIN, 20));
		state.setOpaque(true);
		add(state);
		
		JLabel degree = new JLabel("新旧度：" + goods.getDegree() + "成新");
		degree.setFont(new Font("宋体", Font.PLAIN, 20));
		degree.setOpaque(true);
		add(degree);
		
		JLabel salePrice = new JLabel("售价：" + goods.getSalePrice() + "元");
		salePrice.setFont(new Font("宋体", Font.PLAIN, 20));
		salePrice.setOpaque(true);
		add(salePrice);
		
		JLabel originalPrice = new JLabel("原价：" + goods.getOriginalPrice() + "元");
		originalPrice.setFont(new Font("宋体", Font.PLAIN, 20));
		originalPrice.setOpaque(true);
		add(originalPrice);
		
		JLabel label1 = new JLabel();
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(new Font("等线", Font.PLAIN, 18));
		label1.setForeground(Color.pink);
		label1.setOpaque(true);
		add(label1);
		
		JLabel label2 = new JLabel();
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setFont(new Font("等线", Font.PLAIN, 18));
		label2.setForeground(Color.cyan);
		label2.setOpaque(true);
		add(label2);
		
		JLabel label3 = new JLabel();
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setFont(new Font("等线", Font.PLAIN, 18));
		label3.setForeground(Color.green);
		label3.setOpaque(true);
		add(label3);
		
		for(int i = 0; i < variety.length; i++) {
			if(variety[i] == null)
				return;
			switch (i) {
			case 0 : label1.setText(variety[i].getKeyWord()); break;
			case 1 : label2.setText(variety[i].getKeyWord()); break;
			case 2 : label3.setText(variety[i].getKeyWord()); break;
			default: break;
			}
		}
	}
	
	public void displayMineDemandList(DemandGoods goods, GoodsVariety[]variety) {
		setGoodsNo(goods.getDemandGoodsNo());
		setPreferredSize(new Dimension(1000, 200));
		setBorder(new LineBorder(Color.cyan, 1));
		setLayout(new GridLayout(2, 0, 0, 1));
		
		JPanel above = new JPanel(new GridLayout(3, 0, 0, 1));
		add(above);
		
		JPanel first = new JPanel(new GridLayout(0, 2));
		above.add(first);
		
		JLabel name = new JLabel(goods.getDemandGoodsName());
		name.setFont(new Font("黑体", Font.PLAIN, 22));
		name.setForeground(Color.BLUE);
		first.add(name);
		
		JPanel operate = new JPanel(new GridLayout(0, 3, 50, 0));
		first.add(operate);
		
		JButton delete = new JButton("删除");
		delete.setBackground(new Color(255, 69, 0));
		delete.setFont(new Font("黑体", Font.PLAIN, 20));
		operate.add(delete);
		
		JButton cancel = new JButton("取消");
		cancel.setFont(new Font("黑体", Font.PLAIN, 20));
		cancel.setBackground(new Color(128, 128, 128));
		operate.add(cancel);
		
		JButton done = new JButton("完成");
		done.setFont(new Font("黑体", Font.PLAIN, 20));
		done.setBackground(new Color(0, 255, 0));
		operate.add(done);
		
		JPanel second = new JPanel(new GridLayout(0, 4));
		above.add(second);
		
		JLabel price = new JLabel("预期价格：" + goods.getPrice() + "元");
		price.setFont(new Font("宋体", Font.PLAIN, 20));
		second.add(price);
		
		JLabel degree = new JLabel("最旧程度：" + goods.getDegree() + "成新");
		degree.setFont(new Font("宋体", Font.PLAIN, 20));
		second.add(degree);
		
		JLabel state = new JLabel("愿望状态：" + goods.switchDemandGoodsStateToChinese());
		state.setFont(new Font("宋体", Font.PLAIN, 20));
		second.add(state);
		
		JLabel time = new JLabel("上传时间：" + goods.getUploadTime());
		time.setFont(new Font("宋体", Font.PLAIN, 20));
		second.add(time);
		
		JPanel third = new JPanel(new GridLayout(0, 3));
		above.add(third);
		
		JLabel label1 = new JLabel();
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(new Font("等线", Font.PLAIN, 20));
		label1.setForeground(Color.pink);
		label1.setOpaque(true);
		third.add(label1);
		
		JLabel label2 = new JLabel();
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setFont(new Font("等线", Font.PLAIN, 20));
		label2.setForeground(Color.cyan);
		label2.setOpaque(true);
		third.add(label2);
		
		JLabel label3 = new JLabel();
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setFont(new Font("等线", Font.PLAIN, 20));
		label3.setForeground(Color.green);
		label3.setOpaque(true);
		third.add(label3);
		
		for(int i = 0; i < variety.length; i++) {
			if(variety[i] == null)
				return;
			switch (i) {
			case 0 : label1.setText(variety[i].getKeyWord()); break;
			case 1 : label2.setText(variety[i].getKeyWord()); break;
			case 2 : label3.setText(variety[i].getKeyWord()); break;
			default: break;
			}
		}
		
		JTextArea note = new JTextArea(goods.getNote());
		note.setBorder(new TitledBorder(new LineBorder(new Color(30, 144, 255),2), "\u8BE6\u7EC6\u4ECB\u7ECD",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("黑体", Font.PLAIN, 18), null));
		note.setBackground(new Color(240, 240, 240));
		note.setFont(new Font("宋体", Font.PLAIN, 22));
		note.setEditable(false);
		note.setLineWrap(true);
		add(note);
	}
	
	public void displayOthersDemandList(DemandGoods goods, GoodsVariety[]variety) {
		setGoodsNo(goods.getDemandGoodsNo());
		setPreferredSize(new Dimension(1000, 200));
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setBackground(Color.cyan);
		setLayout(new GridLayout(2, 0, 0, 1));
		
		JPanel above = new JPanel(new GridLayout(3, 0));
		add(above);
		
		JPanel first = new JPanel(new GridLayout(0, 2));
		above.add(first);
		
		JLabel name = new JLabel(goods.getDemandGoodsName());
		name.setFont(new Font("黑体", Font.PLAIN, 22));
		name.setForeground(Color.BLUE);
		first.add(name);
		
		JLabel state = new JLabel("愿望状态：" + goods.switchDemandGoodsStateToChinese());
		first.add(state);
		state.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JPanel second = new JPanel(new GridLayout(0, 3));
		above.add(second);
		
		JLabel price = new JLabel("预期价格：" + goods.getPrice() + "元");
		price.setFont(new Font("宋体", Font.PLAIN, 20));
		second.add(price);
		
		JLabel degree = new JLabel("最旧程度：" + goods.getDegree() + "成新");
		degree.setFont(new Font("宋体", Font.PLAIN, 20));
		second.add(degree);
		
		JLabel time = new JLabel("上传时间：" + goods.getUploadTime());
		second.add(time);
		time.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JPanel third = new JPanel(new GridLayout(0, 3));
		above.add(third);
		
		JLabel label1 = new JLabel();
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(new Font("等线", Font.PLAIN, 18));
		label1.setForeground(Color.pink);
		label1.setOpaque(true);
		third.add(label1);
		
		JLabel label2 = new JLabel();
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setFont(new Font("等线", Font.PLAIN, 18));
		label2.setForeground(Color.cyan);
		label2.setOpaque(true);
		third.add(label2);
		
		JLabel label3 = new JLabel();
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setFont(new Font("等线", Font.PLAIN, 18));
		label3.setForeground(Color.green);
		label3.setOpaque(true);
		third.add(label3);
		
		for(int i = 0; i < variety.length; i++) {
			if(variety[i] == null)
				return;
			switch (i) {
			case 0 : label1.setText(variety[i].getKeyWord()); break;
			case 1 : label2.setText(variety[i].getKeyWord()); break;
			case 2 : label3.setText(variety[i].getKeyWord()); break;
			default: break;
			}
		}
		
		JTextArea note = new JTextArea(goods.getNote());
		note.setBorder(new TitledBorder(new LineBorder(new Color(30, 144, 255),2), "\u8BE6\u7EC6\u4ECB\u7ECD",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("黑体", Font.PLAIN, 18), null));
		note.setBackground(new Color(240, 240, 240));
		note.setFont(new Font("宋体", Font.PLAIN, 22));
		note.setEditable(false);
		note.setLineWrap(true);
		add(note);
	}
}