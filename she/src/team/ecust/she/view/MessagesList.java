package team.ecust.she.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.wis.pack.component.Photo;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class MessagesList extends JPanel {
	private String memberNo;
	private JLabel brief;
	private Photo headPortrait;
	private JLabel nickname;
	private JLabel state;
	
	public String getMemberNo() {
		return memberNo;
	}
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait.setPhoto(Toolkit.getDefaultToolkit().getImage(headPortrait));
		this.headPortrait.repaint();
	}
	public void setNickName(String nickname) {
		this.nickname.setText(nickname);
	}
	public void setReadMessage(boolean read) {
		if(read)
			state.setForeground(new Color(240, 240, 240));
		else
			state.setForeground(Color.red);
	}
	public void setBriefInfo(String info) {
		brief.setText(info);
	}
	
	public MessagesList(String memberNo) {
		this.memberNo = memberNo;
		//display();
	}
	
	public void display(Messages messages) {
		setPreferredSize(new Dimension(400, 80));
		setBorder(new LineBorder(Colors.MESSAGES_SEVERANCE.getColor(), 1));
		setLayout(new BorderLayout(10, 0));
		
		headPortrait = new Photo();
		headPortrait.setPreferredSize(new Dimension(80, 80));
		add(headPortrait, BorderLayout.WEST);
		
		JPanel word = new JPanel(new GridLayout(2, 0));
		add(word, BorderLayout.CENTER);
		
		JPanel title = new JPanel();
		word.add(title);
		title.setLayout(new BorderLayout(0, 0));
		
		nickname = new JLabel();
		nickname.setFont(new Font("黑体", Font.PLAIN, 20));
		nickname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				state.setForeground(new Color(240, 240, 240));
				messages.getChatRecord().display(getMemberNo());
				messages.setObjectNickName(getMemberNo());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				nickname.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				nickname.setForeground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				nickname.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				nickname.setForeground(Color.black);
			}
		});
		title.add(nickname, BorderLayout.CENTER);
		
		state = new JLabel("●");
		state.setForeground(new Color(240, 240, 240));
		title.add(state, BorderLayout.EAST);
		
		brief = new JLabel();
		brief.setFont(new Font("宋体", Font.PLAIN, 20));
		word.add(brief);
	}
}