package team.ecust.she.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.wis.pack.component.Photo;

import team.ecust.she.controller.ModifyMineInfo;
import team.ecust.she.controller.OpenFileChooser;
import team.ecust.she.model.Member;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MineInfoToEdit extends JPanel {
	private JComboBox<String> floor;
	private JComboBox<String> layer;
	private JComboBox<String> order;
	private JTextField nickname;
	private JPasswordField cipher;
	private JTextField phone;
	private JTextField mailbox;
	private JTextArea statement;
	private String headPortrait;

	public MineInfoToEdit() {
		//display(null);
	}
	
	public String getNickName() {
		return nickname.getText();
	}
	public String getCipher() {
		return (new String(cipher.getPassword()));
	}
	public String getPhone() {
		return phone.getText();
	}
	public String getMailbox() {
		return mailbox.getText();
	}
	public String getAddress() {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(floor.getSelectedIndex() + 1);
		buffer.append("号楼第");
		buffer.append(layer.getSelectedIndex() + 1);
		buffer.append("层第");
		buffer.append(order.getSelectedIndex() + 1);
		buffer.append("室");
		return buffer.toString();
	}
	public String getSignature() {
		return statement.getText();
	}
	public String getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public void display(Member member) {
		int width = getWidth();
		int height = getHeight();
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout(20, 20));
		
		JLabel title = new JLabel("编辑个人信息");
		title.setFont(Fonts.MODIFY_INFO_TITLE.getFont());
		title.setBorder(new MatteBorder(0, 0, 1, 0, Colors.MODIFY_INFO_TITLE_BORDER.getColor()));
		add(title, BorderLayout.NORTH);
		
		JPanel west = new JPanel(new GridLayout(2, 0, 0, 40));
		west.setPreferredSize(new Dimension(400, height - 70));
		add(west, BorderLayout.WEST);
		
		JPanel first = new JPanel();
		first.setLayout(new GridLayout(5, 0, 0, (height - 110)/10 - 20));
		west.add(first);
		
		JPanel one = new JPanel(new BorderLayout());
		first.add(one);
		
		JLabel nicknamelabel = new JLabel("昵称：");
		nicknamelabel.setFont(Fonts.MODIFY_INFO_LEFT_LABEL.getFont());
		one.add(nicknamelabel, BorderLayout.WEST);
		
		nickname = new JTextField(member.getNickname() == null ? "" : member.getNickname());
		nickname.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		nickname.setBorder(null);
		one.add(nickname, BorderLayout.CENTER);
		
		JPanel two = new JPanel(new BorderLayout());
		first.add(two);
		
		JLabel cipherlabel = new JLabel("密码：");
		cipherlabel.setFont(Fonts.MODIFY_INFO_LEFT_LABEL.getFont());
		two.add(cipherlabel, BorderLayout.WEST);
		
		cipher = new JPasswordField(member.getCipher());
		cipher.setEchoChar('●');
		cipher.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		cipher.setBorder(null);
		two.add(cipher, BorderLayout.CENTER);
		
		JPanel three = new JPanel(new BorderLayout());
		first.add(three);
		
		JLabel phonelabel = new JLabel("电话：");
		phonelabel.setFont(Fonts.MODIFY_INFO_LEFT_LABEL.getFont());
		three.add(phonelabel, BorderLayout.WEST);
		
		phone = new JTextField(member.getPhone() == null ? "" : member.getPhone());
		phone.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		phone.setBorder(null);
		three.add(phone, BorderLayout.CENTER);
		
		JPanel four = new JPanel(new BorderLayout());
		first.add(four);
		
		JLabel mailboxlabel = new JLabel("邮箱：");
		mailboxlabel.setFont(Fonts.MODIFY_INFO_LEFT_LABEL.getFont());
		four.add(mailboxlabel, BorderLayout.WEST);
		
		mailbox = new JTextField(member.getMailbox() == null ? "" : member.getMailbox());
		mailbox.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		mailbox.setBorder(null);
		four.add(mailbox, BorderLayout.CENTER);
		
		JPanel five = new JPanel(new BorderLayout());
		first.add(five);
		
		JLabel addresslabel = new JLabel("地址：");
		addresslabel.setFont(Fonts.MODIFY_INFO_LEFT_LABEL.getFont());
		five.add(addresslabel, BorderLayout.WEST);
		
		JPanel address = new JPanel(new GridLayout(0, 3, 20, 0));
		five.add(address, BorderLayout.CENTER);
		
		floor = new JComboBox<String>();
		floor.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		floor.setForeground(Colors.MODIFY_INFO_COMBOC_FOREGROUND.getColor());
		floor.setBackground(Colors.MODIFY_INFO_COMBOC_BACKGROUND.getColor());
		for(int i = 1; i <= 28; i++)
			floor.addItem("" + i + "号楼");
		address.add(floor);
		
		layer = new JComboBox<String>();
		layer.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		layer.setForeground(Colors.MODIFY_INFO_COMBOC_FOREGROUND.getColor());
		layer.setBackground(Colors.MODIFY_INFO_COMBOC_BACKGROUND.getColor());
		for(int i = 1; i <= 6; i++) 
			layer.addItem("第" + i + "层");
		address.add(layer);
		
		order = new JComboBox<String>();
		order.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		order.setForeground(Colors.MODIFY_INFO_COMBOC_FOREGROUND.getColor());
		order.setBackground(Colors.MODIFY_INFO_COMBOC_BACKGROUND.getColor());
		for(int i = 1; i <= 14; i++)
			order.addItem("第" + i + "室");
		address.add(order);
		
		/**有待改进*/
		String temp = member.getAddress();
		if(temp != null && !temp.isEmpty()) {
			if(temp.charAt(1) >= '0' && temp.charAt(1) <= '9') {
				floor.setSelectedIndex(Integer.parseInt(temp.substring(0, 2)) - 1);
				temp = temp.substring(2);
			} else {
				floor.setSelectedIndex(Integer.parseInt(temp.substring(0, 1)) - 1);
				temp = temp.substring(1);
			}
			layer.setSelectedIndex(Integer.parseInt(temp.substring(3, 4)) - 1);
			if(temp.charAt(7) >= '0' && temp.charAt(7) <= '9')
				order.setSelectedIndex(Integer.parseInt(temp.substring(6, 8)) - 1);
			else
				order.setSelectedIndex(Integer.parseInt(temp.substring(6, 7)) - 1);
		}
		
		JPanel second = new JPanel(new BorderLayout(0, 20));
		west.add(second);
		
		JLabel statelabel = new JLabel("介绍：");
		statelabel.setFont(Fonts.MODIFY_INFO_LEFT_LABEL.getFont());
		second.add(statelabel, BorderLayout.NORTH);
		
		statement = new JTextArea(member.getSignature() == null ? "" : member.getSignature());
		statement.setLineWrap(true);
		statement.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		second.add(statement);
		
		JButton modify = new JButton("修  改");
		modify.addMouseListener(new ModifyMineInfo<JButton>(this));
		modify.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		modify.setBackground(Colors.MODIFY_INFO_BUTTON_BACKGROUND.getColor());
		second.add(modify, BorderLayout.SOUTH);
		
		JPanel third = new JPanel(new BorderLayout(0, 10));
		third.setBorder(new EmptyBorder(0, 0, height - 400, width - 720));
		add(third, BorderLayout.CENTER);
		
		Photo photo = new Photo(headPortrait = member.getHeadPortrait());
		third.add(photo, BorderLayout.CENTER);
		
		JButton choose = new JButton("选择头像");
		choose.addMouseListener(new OpenFileChooser<JPanel>(this, photo));
		choose.setFont(Fonts.MODIFY_INFO_LEFT_TEXT.getFont());
		choose.setBackground(Colors.MODIFY_INFO_BUTTON_BACKGROUND.getColor());
		third.add(choose, BorderLayout.SOUTH);
	}
}