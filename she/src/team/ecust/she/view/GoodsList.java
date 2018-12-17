package team.ecust.she.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import team.ecust.she.controller.ViewIdleGoodsInfo;
import team.ecust.she.dao.DemandGoodsDao;
import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.DemandGoods.DemandGoodsState;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;

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
	
	public void displayIdleList(IdleGoods goods, GoodsVariety[]variety, boolean view) {
		setGoodsNo(goods.getIdleGoodsNo());
		setPreferredSize(new Dimension(1000, 100));
		setBorder(new LineBorder(Color.blue, 1));
		setLayout(new GridLayout(3, 3, 0, 0));
		
		JLabel name = new JLabel(goods.getIdleGoodsName());
		name.addMouseListener(new ViewIdleGoodsInfo<JLabel>(name, goodsNo, view));
		name.setFont(new Font("黑体", Font.PLAIN, 22));
		name.setOpaque(true);
		name.setForeground(Color.BLUE);
		add(name);
		
		JLabel time = new JLabel("上传时间：" + goods.getUploadTime());
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.setFont(new Font("宋体", Font.PLAIN, 20));
		time.setOpaque(true);
		add(time);
		
		JLabel state = new JLabel("物品状态：" + goods.switchIdleGoodsStateToChinese());
		state.setHorizontalAlignment(SwingConstants.CENTER);
		state.setFont(new Font("宋体", Font.PLAIN, 20));
		state.setOpaque(true);
		add(state);
		
		JLabel degree = new JLabel("新旧度：" + goods.getDegree() + "成新");
		degree.setHorizontalAlignment(SwingConstants.CENTER);
		degree.setFont(new Font("宋体", Font.PLAIN, 20));
		degree.setOpaque(true);
		add(degree);
		
		JLabel salePrice = new JLabel("售价：" + goods.getSalePrice() + "元");
		salePrice.setHorizontalAlignment(SwingConstants.CENTER);
		salePrice.setFont(new Font("宋体", Font.PLAIN, 20));
		salePrice.setOpaque(true);
		add(salePrice);
		
		JLabel originalPrice = new JLabel("原价：" + goods.getOriginalPrice() + "元");
		originalPrice.setHorizontalAlignment(SwingConstants.CENTER);
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
		delete.setBackground(new Color(200, 100, 50));
		delete.setFont(new Font("黑体", Font.PLAIN, 20));
		operate.add(delete);
		
		JButton cancel = new JButton("取消");
		cancel.setFont(new Font("黑体", Font.PLAIN, 20));
		cancel.setBackground(new Color(120, 180, 180));
		operate.add(cancel);
		
		JButton done = new JButton("完成");
		done.setFont(new Font("黑体", Font.PLAIN, 20));
		done.setBackground(new Color(50, 200, 50));
		if(goods.getState() == DemandGoodsState.CANCEL || goods.getState() == DemandGoodsState.DONE) {
			cancel.setEnabled(false);
			done.setEnabled(false);
		}
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
		
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!cancel.isEnabled())
					return;
				DemandGoodsDao dao = new DemandGoodsDao();
				if(dao.deleteDemandGoods(goodsNo)) {
					delete.setEnabled(false);
					cancel.setEnabled(false);
					done.setEnabled(false);
					(new PromptBox()).open("删除成功");
				} else {
					(new PromptBox()).open(dao.getMessage());
				}
			}
		});
		
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!cancel.isEnabled())
					return;
				DemandGoodsDao dao = new DemandGoodsDao();
				if(dao.updateGoodsStateToCancel(goodsNo)) {
					cancel.setEnabled(false);
					done.setEnabled(false);
					state.setText("愿望状态：不再需要");
					(new PromptBox()).open("已为您取消匹配");
				} else
					(new PromptBox()).open(dao.getMessage());
			}
		});
		
		done.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!cancel.isEnabled())
					return;
				DemandGoodsDao dao = new DemandGoodsDao();
				if(dao.updateGoodsStateToDone(goodsNo)) {
					cancel.setEnabled(false);
					done.setEnabled(false);
					state.setText("愿望状态：成功完成");
					(new PromptBox()).open("已为您完成愿望");
				} else
					(new PromptBox()).open(dao.getMessage());
			}
		});
		
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