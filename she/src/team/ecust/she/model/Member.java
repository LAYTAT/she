package team.ecust.she.model;

import java.awt.Image;

/**会员模型类，对应数据库里的会员表，所有属性都设为private，并实现get和set方法。*/
public final class Member {
	/**信用分对应的空常量值*/
	public final static int NULL_CREDIT = -1;
	
	/**性别的枚举。*/
	public enum Gender {
		MALE,
		FEMALE
	}
	
	/**账号状态的枚举。*/
	public enum MemberState {
		OFFLINE,
		ONLINE,
		FROZEN
	}
	
	private String memberNo;
	private String cipher;//也许需要使用字符数组加密
	private String nickname;
	private String realName;
	private Gender sex;
	private String major;
	private String address;
	private String phone;
	private int credit;
	private MemberState state;
	private String mailbox;
	private String signature;
	private String registerTime;
	private Image headPortrait;
	
	/**
	 * 根据会员编号和登录密码初始化会员对象，其他默认为空对象，基本类型默认为本类中对应的空常量值。
	 * @param memberNo 会员编号
	 * @param cipher 登录密码
	 */
	public Member(String memberNo, String cipher) {
		this.setMemberNo(memberNo);
		this.cipher = cipher;
		this.nickname = null;
		this.realName = null;
		this.sex = null;
		this.major = null;
		this.address = null;
		this.phone = null;
		this.credit = NULL_CREDIT;
		this.state = null;
		this.mailbox = null;
		this.signature = null;
		this.registerTime = null;
		this.headPortrait = null;
	}
	
	/**
	 * 根据会员编号初始化会员对象，其他默认为空对象，基本类型默认为本类中对应的空常量值。
	 * @param memberNo 会员编号
	 */
	public Member(String memberNo) {
		this(memberNo, null);
	}
	
	/** 所有属性默认为空对象，基本类型默认为本类中对应的空常量值。 */
	public Member() {
		this(null, null);
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Gender getSex() {
		return sex;
	}

	public void setSex(Gender sex) {
		this.sex = sex;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public MemberState getState() {
		return state;
	}

	public void setState(MemberState state) {
		this.state = state;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public Image getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(Image headPortrait) {
		this.headPortrait = headPortrait;
	}
}