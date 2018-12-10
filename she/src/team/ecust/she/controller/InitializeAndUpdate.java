package team.ecust.she.controller;

import java.util.TimerTask;

import team.ecust.she.dao.InformDao;
import team.ecust.she.dao.MessageDao;
import team.ecust.she.view.Index;
import team.ecust.she.view.Login;

public final class InitializeAndUpdate extends TimerTask {
	private boolean choice;
	
	public InitializeAndUpdate(boolean initialize) {
		this.choice = initialize;
	}
	
	public synchronized static void doIt() {
		Index index = Index.getInstance();
		if(Index.VISITOR.equals(index.getMemberNo())) {
			return;
		}
		MessageDao messageDao = new MessageDao();
		InformDao informDao = new InformDao();
		if(messageDao.hasUnreadMessages(index.getMemberNo()) || informDao.hasUnreadInforms(index.getMemberNo())) {
			index.setReadMessage(false);
		}
		else {
			index.setReadMessage(true);
		}
		
		/**
		MessageDao messageDao = new MessageDao();
		InformDao informDao = new InformDao();
		Message[] messages = messageDao.getUnreadMessages(index.getMemberNo());
		Inform[] informs = informDao.getUnreadInforms(index.getMemberNo());
		if((messages == null || messages.length == 0) && (informs == null || informs.length == 0)) {
			index.setReadMessage(true);
			return;
		}
		index.setReadMessage(false);
		
		for(int i = 0; i < informs.length; i++) {
			FileTool tool = new FileTool(index.getMemberNo() + "/informs");
			tool.append("\n#" + informs[i].getSentTime());
			tool.append("\n@" + informs[i].getInformType());
			tool.append("\n$" + informs[i].getContent());
		}
		for(int i = 0; i < messages.length; i++) {
			FileTool tool = new FileTool(index.getMemberNo() + "/" + messages[i].getSenderNo());
			tool.append("\n[" + messages[i].getSentTime() + " 对方]");
			tool.append("\n" + messages[i].getContent());
		}*/
	}
	
	@Override
	public void run() {
		if(choice) {
			Login login = new Login();
			Index.getInstance().showInCard(login);
			login.display();
			login.repaint();
			if(login.autoLogin())
				LoginIn.doIt(login);
			return;
		}
		doIt();
	}
}