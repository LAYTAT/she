package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import team.ecust.she.common.FileTool;
import team.ecust.she.common.RandomNo;
import team.ecust.she.dao.MessageDao;
import team.ecust.she.model.Message;
import team.ecust.she.view.Index;
import team.ecust.she.view.Messages;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;

public final class SendMessageToOthers extends MouseAdapter {
	private Messages view;
	public SendMessageToOthers(Messages view) {
		this.view = view;
	}
	
	public synchronized static void doIt(Messages view) {
		if(view.getObjectNo() == null || view.getObjectNo().isEmpty()) {
			(new PromptBox(Tips.ERROR)).open("请选择聊天对象");
			return;
		}
		
		if(view.getMessage() == null || view.getMessage().isEmpty()) {
			(new PromptBox(Tips.ERROR)).open("输入为空");
			return;
		}
		
		Index index = Index.getInstance();
		RandomNo randomNo = RandomNo.getDefaultRandomNo();
		
		String messageNo = randomNo.getRandomNo(10);
		MessageDao dao = new MessageDao();
		Message message = new Message(messageNo, index.getMemberNo(), view.getObjectNo());
		
		for(int i = 0; i < 10; i++) {
			if(!dao.existItem(message))
				break;
			if(i == 10) {
				(new PromptBox(Tips.ERROR)).open("发送失败");
				return;
			}
			message.setMessageNo(randomNo.getRandomNo(10));
		}
		
		String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		message.setContent(view.getMessage());
		message.setSentTime(time);
		if(!dao.insertNewMessage(message)) {
			(new PromptBox(Tips.OFFLINE)).open("发送失败");
			return;
		}
		
		FileTool tool = new FileTool(index.getMemberNo() + "/" + view.getObjectNo());
		tool.append("\n[" + time + " 我]");
		tool.append("\n" + view.getMessage());
		view.clearMessage();
		
		view.getChatRecord().display(view.getObjectNo());
		//view.getChatRecord().addRecord("\n[" + time + " 我]", true);
		//view.getChatRecord().addRecord(view.getMessage(), false);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(view);
	}
}
