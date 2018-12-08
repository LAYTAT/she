package team.ecust.she.controller;

import java.util.TimerTask;

import team.ecust.she.view.Index;
import team.ecust.she.view.Login;

public final class InitializeAndUpdate extends TimerTask {
	private boolean choice;
	
	public InitializeAndUpdate(boolean initialize) {
		this.choice = initialize;
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
		//read message from database
	}
}