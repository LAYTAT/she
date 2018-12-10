package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import team.ecust.she.common.FileTool;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.view.Index;
import team.ecust.she.view.Messages;
import team.ecust.she.view.PromptBox;

public final class ContactSeller extends MouseAdapter {
	private String goodsNo;
	public ContactSeller(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	
	public synchronized static void doIt(String goodsNo) {
		Index index = Index.getInstance();
		if(Index.VISITOR.equals(index.getMemberNo())) {
			(new PromptBox()).open("请登录");
			return;
		}
		
		IdleGoodsDao dao = new IdleGoodsDao();
		String objectNo = dao.getMemberNoByIdleGoods(goodsNo);
		if(index.getMemberNo().equals(objectNo)) {
			(new PromptBox()).open("这是你啊");
			return;
		}
		
		Messages messages = new Messages(false);
		index.showInCard(messages);
		
		FileTool tool = new FileTool(index.getMemberNo());
		tool.createFolderIfNotExist();
		tool.setFilePath("src/team/ecust/she/file/" + index.getMemberNo() + "/" + objectNo);
		SimpleDateFormat sp1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sp1.format(new java.util.Date());
		tool.append("\n[" + time + " 系统]");
		
		messages.display();
		messages.getChatRecord().display(objectNo);
		messages.setObjectNo(objectNo);
		messages.setObjectNickName((new MemberDao()).getMemberToEdit(objectNo).getNickname());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(goodsNo);
	}
}