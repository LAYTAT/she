package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import team.ecust.she.common.RandomNo;
import team.ecust.she.dao.InformDao;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.dao.MessageDao;
import team.ecust.she.model.Inform;
import team.ecust.she.model.Inform.InformType;
import team.ecust.she.view.Index;
import team.ecust.she.view.Messages;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;

public final class TipOffMessage extends MouseAdapter {
	private Messages view;
	public TipOffMessage(Messages view) {
		this.view = view;
	}
	
	public synchronized static void doIt(Messages view) {
		if(view.getObjectNo() == null || view.getObjectNo().isEmpty()) {
			(new PromptBox()).open("请选择举报对象");
			return;
		}
		MessageDao dao = new MessageDao();
		if(dao.tipOffLatestMessage(Index.getInstance().getMemberNo(), view.getObjectNo())) {
			MemberDao memberDao = new MemberDao();
			int credit = memberDao.getCreditOfMember(view.getObjectNo());
			credit = credit < 2 ? 0 : credit - 2;
			if(memberDao.updateCreditOfMember(view.getObjectNo(), credit)) {
				InformDao iDao = new InformDao();
				Inform inform = new Inform(RandomNo.getDefaultRandomNo().getRandomNo(10));
				for(int i = 0; i < 10; i++) {
					if(!iDao.existInform(inform.getInformNo()))
						break;
					if(i == 9) {
						(new PromptBox()).open("举报成功");
						return;
					}
					inform.setInformNo(RandomNo.getDefaultRandomNo().getRandomNo(10));
				}
				inform.setMemberNo(view.getObjectNo());
				inform.setSentTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				inform.setContent("收到其他用户举报不雅言论，信用分暂扣2分");
				inform.setInformType(InformType.CREDIT);
				if(iDao.insertNewInform(inform))
					(new PromptBox()).open("举报成功，已通知对方");
				else
					(new PromptBox()).open("举报成功");
			}
			else
				(new PromptBox()).open("举报处理中");
		}
		else
			(new PromptBox(Tips.ERROR)).open("举报失败");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(view);
	}
}