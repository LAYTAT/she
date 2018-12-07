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
		Index index = Index.getInstance();
		if(!Index.VISITOR.equals(index.getMemberNo())) {
			(new PromptBox()).open("您已登录");
			return;
		}
		String memberNo = login.getMemberNo();
		if(memberNo == null) {
			(new PromptBox()).open("会员号为空");
			return;
		}
		String regex = "[1-9][0-9]{7}";
		if(!memberNo.matches(regex)) {
			(new PromptBox()).open("会员号格式不对");
			return;
		}
		String cipher = login.getPassword();
		if(cipher == null) {
			(new PromptBox()).open("密码为空");
			return;
		}
		regex = "\\w{6,16}";//匹配任何字类字符，包括下划线。与"[A-Za-z0-9_]"等效。
		if(!cipher.matches(regex)) {
			(new PromptBox()).open("密码格式不对");
			return;
		}
		MemberDao dao = new MemberDao();
		if(!dao.validateCipher(memberNo, cipher)) {
			(new PromptBox(Tips.ERROR)).open(dao.getMessage());
			return;
		}
		if(!dao.loginIn(memberNo)) {
			(new PromptBox(Tips.OFFLINE)).open(dao.getMessage());
			return;
		}
		index.setNickname("登录中...");
		(new Timer()).schedule(new TimerTask() {
			@Override
			public void run() {
				(new PromptBox(Tips.TICK)).open("登录成功");
				index.setMemberNo(memberNo);
				Member member = dao.getMemberWhoLogin(memberNo);
				index.setNickname(member.getNickname());
				index.setHeadPortrait(member.getHeadPortrait());
			}
		}, 1500);
		if(login.remeCipher()) {
			FileTool tool = new FileTool(FileTool.LOGIN_SETTING);
			tool.writeTheLine("YES", 1);
			tool.writeTheLine(login.getMemberNo(), 3);
			tool.writeTheLine(login.getPassword(), 4);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(login);
	}
}