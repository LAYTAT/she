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