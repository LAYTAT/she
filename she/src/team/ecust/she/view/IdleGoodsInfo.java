package team.ecust.she.view;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Picture;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import com.wis.pack.component.Photo;

@SuppressWarnings("serial")
public class IdleGoodsInfo extends JPanel {
	private String idleGoodsNo;
	
	public String getIdleGoodsNo() {
		return idleGoodsNo;
	}

	public void setIdleGoodsNo(String idleGoodsNo) {
		this.idleGoodsNo = idleGoodsNo;
	}

	public IdleGoodsInfo() {
		setIdleGoodsNo("");
		//display(null, null, null);
	}
	
	public void display(IdleGoods goods, GoodsVariety[] label, Picture[] picture) {
		int width = getWidth();
		int height = getHeight();
		setLayout(new GridLayout(2, 0, 0, 20));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setIdleGoodsNo(goods.getIdleGoodsNo());
		
		JPanel above = new JPanel(new GridLayout(0, 2, 20, 0));
		add(above);
		
		JPanel left = new JPanel(new GridLayout(6, 0, 0, ((height - 60)/2 - 200)/5));
		above.add(left);
		
		JLabel name = new JLabel("商品：" + goods.getIdleGoodsName());
		name.setForeground(new Color(255, 102, 51));
		name.setFont(new Font("微软雅黑", Font.BOLD, 24));
		left.add(name);
		
		JPanel second = new JPanel(new GridLayout(0, 2));
		left.add(second);
		
		JLabel salePrice = new JLabel("售价：" + goods.getSalePrice() + "元");
		salePrice.setForeground(new Color(30, 144, 255));
		salePrice.setFont(new Font("黑体", Font.PLAIN, 22));
		second.add(salePrice);
		
		JLabel originalPrice = new JLabel("原价：" + goods.getOriginalPrice() + "元");
		originalPrice.setForeground(new Color(0, 0, 255));
		originalPrice.setFont(new Font("黑体", Font.PLAIN, 22));
		second.add(originalPrice);
		
		JPanel third = new JPanel(new GridLayout(0, 2));
		left.add(third);
		
		JLabel degree = new JLabel("新旧度："  + goods.getDegree() + "成新");
		degree.setFont(new Font("宋体", Font.PLAIN, 22));
		third.add(degree);
		
		JLabel state = new JLabel("商品状态：" + goods.switchIdleGoodsStateToString());
		state.setFont(new Font("宋体", Font.PLAIN, 22));
		third.add(state);
		
		JLabel idlelabel = new JLabel();
		String labeltext = "商品标签：";
		for(int i = 0; i < label.length; i++)
			labeltext += label[i].getKeyWord();
		idlelabel.setText(labeltext);
		idlelabel.setForeground(new Color(0, 206, 209));
		idlelabel.setFont(new Font("黑体", Font.PLAIN, 22));
		left.add(idlelabel);
		
		JLabel time = new JLabel("上线时间：" + goods.getUploadTime());
		time.setForeground(new Color(153, 50, 204));
		time.setFont(new Font("宋体", Font.PLAIN, 22));
		left.add(time);
		
		JPanel button = new JPanel();
		button.setBorder(new EmptyBorder(0, (width - 60)/4 - 80, 0, (width - 60)/4 - 80));
		left.add(button);
		button.setLayout(new BorderLayout(0, 0));
		
		JButton contact = new JButton("联系卖家");
		contact.setBackground(new Color(30, 144, 255));
		contact.setFont(new Font("黑体", Font.PLAIN, 22));
		button.add(contact, BorderLayout.CENTER);
		
		JTextArea right = new JTextArea(goods.getNote());
		right.setBackground(new Color(240, 240, 240));
		LineBorder border = new LineBorder(new Color(100, 200, 100), 3);
		right.setBorder(new TitledBorder(border, "\u8BE6\u7EC6\u8BF4\u660E", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("黑体", Font.PLAIN, 20), new Color(244, 164, 96)));
		right.setLineWrap(true);
		above.add(right);
		
		JPanel below = new JPanel(new GridLayout(0, 4, 10, 0));
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