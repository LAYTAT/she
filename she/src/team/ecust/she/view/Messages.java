package team.ecust.she.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import team.ecust.she.common.FileTool;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.model.Member;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Messages extends JPanel {
	
	private boolean options;//true:inform, false:chat
	private String objectNo;
	
	private JPanel content;
	private JLabel object;
	private ChatRecord record;
	private JTextArea message;
	
	public boolean getOptions() {
		return options;
	}
	public String getObjectNo() {
		return objectNo;
	}
	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}
	public void setObjectNickName(String objectNickName) {
		object.setText("当前聊天对象：" + objectNickName);
	}
	public ChatRecord getChatRecord() {
		return record;
	}
	public String getMessage() {
		return message.getText();
	}
	public void clearMessage() {
		message.setText("");
	}
	
	public void showInContent(JComponent component) {
		if(component != null) {
			content.add(component);
			CardLayout card = (CardLayout)content.getLayout();
			card.last(content);
		}
	}
	
	public void showChatList() {
		File f = new File("src/team/ecust/she/resource/file/" + Index.getInstance().getMemberNo());
		if(!f.exists())
			f.mkdir();
		File[] list = f.listFiles();
		if(list == null)
			return;
		Vector<File> members = new Vector<File>();
		for(int i = 0; i < list.length; i++) {
			if(!list[i].isFile())
				continue;
			if(!list[i].getName().matches("10[0-9]{6}"))
				continue;
			members.add(list[i]);
		}
		if(members.size() == 0)
			return;
		JPanel panel = new JPanel();
		if(members.size() < (getHeight() - 100)/100)
			panel.setLayout(new GridLayout((getHeight() - 100)/100, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(members.size(), 0, 0, 20));
		FileTool tool = new FileTool("");
		File file = null;
		for(int i = 0; i < members.size(); i++) {
			file = members.elementAt(i);
			MessagesList messages = new MessagesList(file.getName());
			panel.add(messages);
			messages.display(this);
			tool.setFilePath(file.getAbsolutePath());
			messages.setBriefInfo(tool.readTheLine(tool.getAllLines() - 1));
			MemberDao dao = new MemberDao();
			Member member = dao.getMemberToEdit(file.getName());
			if(member != null) {
				messages.setHeadPortrait(member.getHeadPortrait());
				messages.setNickName(member.getNickname());
			}
		}
		showInContent(panel);
	}
	
	public void showInformList() {
		
	}
	
	public Messages(boolean option) {
		options = option;
		objectNo = "";
		//display();
	}
	
	public void display() {
		int width = getWidth();
		int height = getHeight();
		setLayout(new BorderLayout());
		setBorder(new LineBorder(Colors.MESSAGES_SEVERANCE.getColor(), 2));
		
		JPanel list = new JPanel(new BorderLayout());
		list.setPreferredSize(new Dimension(400, height - 20));
		add(list, BorderLayout.WEST);
		
		JPanel mode = new JPanel(new GridLayout(0, 2));
		mode.setBorder(new MatteBorder(0, 0, 2, 0, Colors.MESSAGES_SEVERANCE.getColor()));
		list.add(mode, BorderLayout.NORTH);
		
		JLabel inform = new JLabel("系统通知");
		inform.setFont(Fonts.MESSAGES_TITLE.getFont());
		inform.setOpaque(true);
		inform.setHorizontalAlignment(SwingConstants.CENTER);
		mode.add(inform);
		
		JLabel chat = new JLabel("聊天消息");
		chat.setFont(Fonts.MESSAGES_TITLE.getFont());
		chat.setOpaque(true);
		chat.setHorizontalAlignment(SwingConstants.CENTER);
		mode.add(chat);
		
		inform.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chat.setBackground(Colors.DEFAULT_BACKGROUND.getColor());
				inform.setBackground(Colors.MESSAGES_MODE_OPTIONS_BACKGROUND.getColor());
				options = true;
				showInformList();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!options)
					inform.setBackground(Colors.DEFAULT_BACKGROUND.getColor());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				inform.setBackground(Colors.MESSAGES_MODE_OPTIONS_BACKGROUND.getColor());
			}
		});
		chat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inform.setBackground(Colors.DEFAULT_BACKGROUND.getColor());
				chat.setBackground(Colors.MESSAGES_MODE_OPTIONS_BACKGROUND.getColor());
				options = false;
				showChatList();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(options)
					chat.setBackground(Colors.DEFAULT_BACKGROUND.getColor());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				chat.setBackground(Colors.MESSAGES_MODE_OPTIONS_BACKGROUND.getColor());
			}
		});
		
		content = new JPanel(new CardLayout());
		list.add(content, BorderLayout.CENTER);
		
		JPanel window = new JPanel(new BorderLayout());
		window.setBorder(new MatteBorder(0, 2, 0, 0, Colors.MESSAGES_SEVERANCE.getColor()));
		add(window, BorderLayout.CENTER);
		
		object = new JLabel("当前聊天对象：");
		object.setFont(Fonts.MESSAGES_TITLE.getFont());
		object.setBorder(new MatteBorder(0, 0, 2, 0, Colors.MESSAGES_SEVERANCE.getColor()));
		object.setHorizontalAlignment(SwingConstants.CENTER);
		window.add(object, BorderLayout.NORTH);
		
		record = new ChatRecord();
		window.add(record, BorderLayout.CENTER);
		
		JPanel input = new JPanel(new BorderLayout(20, 0));
		input.setBorder(new EmptyBorder(20, 20, 20, 20));
		input.setPreferredSize(new Dimension(width - 406, 302));
		window.add(input, BorderLayout.SOUTH);
		
		message = new JTextArea();
		message.setFont(Fonts.MESSAGES_SEND_TEXT.getFont());
		message.setLineWrap(true);
		input.add(message, BorderLayout.CENTER);
		
		JPanel operate = new JPanel(new GridLayout(3, 0, 0, 80));
		operate.setPreferredSize(new Dimension(200, 300));
		input.add(operate, BorderLayout.EAST);
		
		JButton send = new JButton("发送");
		send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearMessage();
			}
		});
		send.setFont(Fonts.MESSAGES_SEND_BUTTON.getFont());
		send.setBackground(Colors.MESSAGES_SEND_BUTTON_BACKGROUND.getColor());
		operate.add(send);
		
		JButton tipoff = new JButton("举报");
		tipoff.setFont(Fonts.MESSAGES_SEND_BUTTON.getFont());
		tipoff.setBackground(Colors.MESSAGES_TIPOFF_BUTTON_BACKGROUND.getColor());
		operate.add(tipoff);
		
		if(options) {
			inform.setBackground(Colors.MESSAGES_MODE_OPTIONS_BACKGROUND.getColor());
			showInformList();
		} else {
			chat.setBackground(Colors.MESSAGES_MODE_OPTIONS_BACKGROUND.getColor());
			showChatList();
		}
	}
}