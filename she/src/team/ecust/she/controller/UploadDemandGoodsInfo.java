package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import team.ecust.she.view.Colors;
import team.ecust.she.view.Index;
import team.ecust.she.view.Login;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.UploadDemandGoods;

public class UploadDemandGoodsInfo <K extends JComponent> extends MouseAdapter {
	private K trigger;
	
	public UploadDemandGoodsInfo(K k) {
		trigger = k;
	}
	
	public synchronized static void doIt() {
		Index index = Index.getInstance();
		if(Index.VISITOR.equals(index.getMemberNo())) {
			(new PromptBox()).open("请登录");
			Login login = new Login();
			index.showInCard(login);
			login.display();
			return;
		}
		UploadDemandGoods upload = new UploadDemandGoods();
		index.showInCard(upload);
		upload.display();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		trigger.setBackground(Colors.TOP_BAR_BACKGROUND.getColor());
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		trigger.setBackground(Colors.LEFT_CONTENT_BACKGROUND.getColor());
	}
}