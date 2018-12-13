package team.ecust.she.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import team.ecust.she.common.FileTool;
import team.ecust.she.controller.SendMessageToOthers;
import team.ecust.she.controller.TipOffMessage;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.dao.InformDao;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.dao.MessageDao;
import team.ecust.she.dao.OrderDao;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Inform;
import team.ecust.she.model.Member;
import team.ecust.she.model.Message;
import team.ecust.she.model.Order;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Messages extends JPanel {
	private static Timer update;
	
	private boolean options;//true:inform, false:chat
	private String objectNo;
	
	private JPanel content;
	private JLabel object;
	private ChatRecord record;
	private JTextArea message;
	private Vector<MessagesList>lists;
	
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
	public Vector<MessagesList> getLists() {
		return lists;
	}
	public static void cancelUpdate() {
		if(update != null)
			update.cancel();
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
		if(list == null || list.length == 0)
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
		showInContent(new JScrollPane(panel));
		if(members.size() < (getHeight() - 100)/100)
			panel.setLayout(new GridLayout((getHeight() - 100)/100, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(members.size(), 0, 0, 20));
		FileTool tool = new FileTool("");
		File file = null;
		lists = new Vector<MessagesList>();
		for(int i = 0; i < members.size(); i++) {
			file = members.elementAt(i);
			MessagesList messages = new MessagesList(file.getName());
			lists.add(i, messages);
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
	}
	
	public void showInformList() {
		File f = new File("src/team/ecust/she/resource/file/" + Index.getInstance().getMemberNo());
		if(!f.exists())
			f.mkdir();
		f = new File("src/team/ecust/she/resource/file/" + Index.getInstance().getMemberNo() + "/informs");
		if(!f.exists())
			return;
		JPanel panel = new JPanel();
		showInContent(new JScrollPane(panel));
		FileTool tool = new FileTool(Index.getInstance().getMemberNo() + "/informs");
		int rows = (tool.getAllLines() - 1)/4;
		if(rows < (getHeight() - 100)/140)
			panel.setLayout(new GridLayout((getHeight() - 100)/140, 0, 0, 20));
		else
			panel.setLayout(new GridLayout(rows, 0, 0, 20));
		for(int i = 0; i < rows; i++) {
			Inform inform = new Inform(tool.readTheLine(4*i + 2));
			inform.setSentTime(tool.readTheLine(4*i + 3));
			inform.switchTypeStringToEnum(tool.readTheLine(4*i + 4));
			inform.setContent(tool.readTheLine(4*i + 5));
			InformList list = new InformList(inform.getInformNo());
			list.setOrderNo(inform.getContent());
			panel.add(list);
			list.display(inform);
			list.setContent(getContentFromType(inform));
		}
	}
	
	public String getContentFromType(Inform inform) {
		if(inform.getInformType() == null)
			return "通知信息丢失";
		switch (inform.getInformType()) {
		case CREDIT:
			return inform.getContent();
		case NEW_MSG:
			return inform.getContent();
		case ORDER_INFO:
			return inform.getContent();
		case COMMENT:
			return inform.getContent();
		case CFM_ORDER:
			OrderDao dao = new OrderDao();
			if(!dao.existItemByOrder(inform.getContent()))
				return "订单不存在";
			Order order = dao.getOrderByOrder(inform.getContent());
			if(order == null)
				return "订单查询失败";
			IdleGoodsDao goodsDao = new IdleGoodsDao();
			IdleGoods goods = goodsDao.getIdleGoodsByIdleGoods(order.getIdleGoodsNo());
			if(goods == null)
				return "订单查询失败";
			return "物品编号：" + order.getIdleGoodsNo() + "\n物品名称：" + goods.getIdleGoodsName() +
					"\n成交价格：" + order.getPrice() + "元\n交易期限：" + order.getDeadline();
		default: return "";
		}
	}
	
	public void updateMessage() {
		if(update != null)
			update.cancel();
		(update = new Timer()).schedule(new TimerTask() {
			@Override
			public void run() {
				Index index = Index.getInstance();
				if(index.isReadMessage())
					return;
				MessageDao messageDao = new MessageDao();
				InformDao informDao = new InformDao();
				Message[] messages = messageDao.getUnreadMessages(index.getMemberNo());
				Inform[] informs = informDao.getUnreadInforms(index.getMemberNo());
				if((messages == null || messages.length == 0) && (informs == null || informs.length == 0))
					return;
				
				for(int i = 0; i < informs.length; i++) {
					FileTool tool = new FileTool(index.getMemberNo() + "/informs");
					tool.append("\n" + informs[i].getInformNo());
					tool.append("\n" + informs[i].getSentTime());
					tool.append("\n" + informs[i].switchInformTypeToString());
					tool.append("\n" + informs[i].getContent());
				}
				for(int i = 0; i < messages.length; i++) {
					FileTool tool = new FileTool(index.getMemberNo() + "/" + messages[i].getSenderNo());
					tool.append("\n[" + messages[i].getSentTime() + " 对方]");
					tool.append("\n" + messages[i].getContent());
					if(messages[i].getSenderNo().equals(getChatRecord().getObjectNo()))
						getChatRecord().display(objectNo);
				}
				index.setReadMessage(true);
			}
		}, 2000, 2000);
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
		
		JButton send = new JButton("发送消息");
		send.addMouseListener(new SendMessageToOthers(this));
		send.setFont(Fonts.MESSAGES_SEND_BUTTON.getFont());
		send.setBackground(Colors.MESSAGES_SEND_BUTTON_BACKGROUND.getColor());
		operate.add(send);
		
		JButton tipoff = new JButton("举报最近消息");
		tipoff.addMouseListener(new TipOffMessage(this));
		tipoff.setFont(Fonts.MESSAGES_SEND_BUTTON.getFont());
		tipoff.setBackground(Colors.MESSAGES_TIPOFF_BUTTON_BACKGROUND.getColor());
		operate.add(tipoff);
		
		updateMessage();
		if(options) {
			inform.setBackground(Colors.MESSAGES_MODE_OPTIONS_BACKGROUND.getColor());
			showInformList();
		} else {
			chat.setBackground(Colors.MESSAGES_MODE_OPTIONS_BACKGROUND.getColor());
			showChatList();
		}
	}
}