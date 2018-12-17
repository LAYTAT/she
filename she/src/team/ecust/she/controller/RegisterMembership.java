package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JComponent;

import team.ecust.she.dao.EcustDao;
import team.ecust.she.dao.MemberDao;
import team.ecust.she.model.Member;
import team.ecust.she.view.Index;
import team.ecust.she.view.Login;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;
import team.ecust.she.view.Register;

/**注册会员身份*/
public class RegisterMembership <K extends JComponent> extends MouseAdapter {
private Register register;
	
	public RegisterMembership(K k){
		register =(Register) k;
	}
	
	public synchronized static void doIt(Register register) {
		//Index index = Index.getInstance();
		String studentID = register.getStudentID();
		String ID=register.getID();
		String Alias=register.getAlias();
		String Email=register.getEmail();
		String Password=register.getPassword();
		String confirmPassword=register.getCfmPassword();
		//注册时间:
		SimpleDateFormat sp1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//SimpleDateFormat("yyyy-MM-dd hh"+"'"+"mm""ss");
		String registerTime=sp1.format(new java.util.Date());
		EcustDao ecustDao=new EcustDao();
			
		//检测是否已经注册
    	 MemberDao memberDao =new MemberDao();
			//新建member会员信息
		 Member member = ecustDao.getEcustinfo(studentID);
		 
		 if(ID ==null || ID.length()!=18){
		    	(new PromptBox()).open("请输入正确的18位身份证号码!");
		    	return;
		    }
		else if(studentID == null || studentID.length()!=8) {
			(new PromptBox()).open("请输入正确的8位学号!");
			return;
		}
		else if(memberDao.existItem(member.getMemberNo()))
			 {
				 (new PromptBox(Tips.ERROR)).open("你已经注册过了,请不要重复注册!");
				 return;
			 }
		else if(Alias.length() == 0|| Alias.length()>=10) {
			(new PromptBox()).open("请输入10位内昵称!");
			return;
		}
		else if(Email.length() == 0){
			(new PromptBox()).open("请输入邮箱号!");
			return;
		}
		else  if(!Email.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"))
		 {
			 (new PromptBox()).open("请输入正确的邮箱号!");
		 }
//		else if(Password.length==0){
//			(new PromptBox()).open("请输入密码!");
//			return;
//		}
//		 
		//匹配任何字类字符，包括下划线。与"[A-Za-z0-9_]"等效。
    	else if(!Password.matches("\\w{6,16}")) {
 		(new PromptBox()).open("密码格式不对!");
  		return;
  		}
		else if (!confirmPassword.matches("\\w{6,16}")){
			(new PromptBox()).open("请再次输入正确的密码!");
			return;
			
		}
		else if(!Password.equals(confirmPassword))
		{
			(new PromptBox()).open("两次密码不一致!");//string.equals()有问题
			return;
		}


		 if(ecustDao.identityCheck(ID, studentID)){
			 //身份验证通过之后:
			//打开登陆界面
			 Login login=new Login();
			 Index index = Index.getInstance();
			 index.showInCard(login);
			 login.display();

			 member.setMemberNo(studentID);
	     	 member.setCipher(Password.toString());
	     	 member.setMailbox(Email);
	     	 member.setNickname(Alias);
	     	 member.setRegisterTime(registerTime);
	     	 

	     	
	      	boolean isRegistered=ecustDao.insertMembership(member);
	     	 if(isRegistered){
	     		(new PromptBox(Tips.TICK)).open("身份验证通过,注册成功!");
	     	 }
	     	 else {
	     		(new PromptBox(Tips.ERROR)).open("数据库中断传输!");
	     	 }
		 }
		 else{
			 (new PromptBox(Tips.ERROR)).open("身份校验失败,请检查你的是否正确!");
		 }
}

     	 //member信息已经完全:8个
     	 


	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(register);
	}
		
}
