package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;

import team.ecust.she.common.FileTool;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.model.Member;
import team.ecust.she.view.Index;
import team.ecust.she.view.Login;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;

public class LoginIn <K extends JComponent> extends MouseAdapter {
	private Login login;
	
	public LoginIn(K k) {
		login = (Login) k;
	}
	
	public synchronized static void doIt(Login login) {
		if(!login.isEnabledOfConfirmButton())
			return;
		login.setConfirmButtonEnabled(false);
		Index index = Index.getInstance();
		if(!Index.VISITOR.equals(index.getMemberNo())) {
			(new PromptBox()).open("您已登录");
			login.setConfirmButtonEnabled(true);
			return;
		}
		String memberNo = login.getMemberNo();
		String regex = "[1-9][0-9]{7}";
		if(memberNo == null || !memberNo.matches(regex)) {
			(new PromptBox()).open("会员号格式不对");
			login.setConfirmButtonEnabled(true);
			return;
		}
		String cipher = login.getPassword();
		regex = "\\w{6,16}";//匹配任何字类字符，包括下划线。与"[A-Za-z0-9_]"等效。
		if(cipher == null || !cipher.matches(regex)) {
			(new PromptBox()).open("密码格式不对");
			login.setConfirmButtonEnabled(true);
			return;
		}
		MemberDao dao = new MemberDao();
		if(!dao.validateCipher(memberNo, cipher)) {
			(new PromptBox(Tips.ERROR)).open(dao.getMessage());
			login.setConfirmButtonEnabled(true);
			return;
		}
		if(!dao.loginIn(memberNo)) {
			(new PromptBox(Tips.OFFLINE)).open(dao.getMessage());
			login.setConfirmButtonEnabled(true);
			return;
		}
		index.setNickname("登录中...");
		Member member = dao.getMemberWhoLogin(memberNo);
		(new Timer()).schedule(new TimerTask() {
			@Override
			public void run() {
				(new PromptBox(Tips.TICK)).open("登录成功");
				index.setMemberNo(memberNo);
				index.setNickname(member.getNickname());
				index.setHeadPortrait(member.getHeadPortrait());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.err.println("->线程被打断");
				}
				login.setConfirmButtonEnabled(true);
				ViewMineInfo.doIt();
			}
		}, 1500);
		FileTool tool = new FileTool(FileTool.LOGIN_SETTING);
		tool.writeTheLine(login.getMemberNo(), 3);
		if(login.remeCipher()) {
			tool.writeTheLine("YES", 1);
			tool.writeTheLine(login.getPassword(), 4);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(login);
	}
}