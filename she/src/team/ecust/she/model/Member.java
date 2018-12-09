package team.ecust.she.model;

/**
 * <p>会员模型类，对应数据库里的会员表。
 * <p>基本类型的属性都设置一个对应的空常量。
 * <p>数据库中的枚举类型都在类中添加相应的枚举类型和字符串互相转换的方法。
 * <p>日期类型使用String类型。
 * <p>所有属性都设为private，并实现get和set方法。
 */
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
	private String headPortrait;
	
	/**
	 * <p>根据会员编号和登录密码初始化会员对象，其他默认为空对象，基本类型默认为本类中对应的空常量值。
	 * @param memberNo 会员编号
	 * @param cipher 登录密码
	 */
	public Member(String memberNo, String cipher) {
		this.memberNo = memberNo;
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
	 * <p>根据会员编号初始化会员对象，其他默认为空对象，基本类型默认为本类中对应的空常量值。
	 * @param memberNo 会员编号
	 */
	public Member(String memberNo) {
		this(memberNo, null);
	}
	
	/** 
	 * <p>所有属性默认为空对象，基本类型默认为本类中对应的空常量值。
	 */
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
	
	/**
	 * 根据会员性别枚举返回对应存储在数据库里的字符串。
	 * @return 性别对应的字符串，如果性别为空，则返回空对象
	 */
	public String switchSexToString() {
		if(sex == null)
			return null;
		switch (sex) {
		case MALE:   return "male";
		case FEMALE: return "female";
		default: return null;
		}
	}
	
	/**
	 * 根据会员性别字符串改变对应对象的性别枚举属性。
	 * @param sex 需要转换的性别字符串，为空对象或不合法则不改变对象的属性
	 */
	public void switchSexToEnum(String sex) {
		if(sex == null)
			return;
		switch (sex) {
		case "male":   this.sex = Gender.MALE;
			break;
		case "female": this.sex = Gender.FEMALE;
			break;
		default: return;
		}
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
	
	/**
	 * 根据会员状态枚举返回对应存储在数据库里的字符串。
	 * @return 状态对应的字符串，如果状态为空，则返回空对象
	 */
	public String switchStateToString() {
		if(state == null)
			return null;
		switch (state) {
		case OFFLINE: return "offline";
		case ONLINE:  return "online";
		case FROZEN:  return "frozen";
		default: return null;
		}
	}
	
	public String switchStateToChinese() {
		if(state == null)
			return null;
		switch (state) {
		case OFFLINE: return "离线";
		case ONLINE:  return "在线";
		case FROZEN:  return "冻结";
		default: return null;
		}
	}
	
	/**
	 * <p>根据会员状态字符串改变对应对象的状态枚举属性。
	 * @param state 需要转换的状态字符串，为空对象或不合法则不改变对象的属性
	 */
	public void switchStateToEnum(String state) {
		if(state == null)
			return;
		switch (state) {
		case "offline": this.state = MemberState.OFFLINE;
			break;
		case "online":  this.state = MemberState.ONLINE;
			break;
		case "frozen":  this.state = MemberState.FROZEN;
			break;
		default: return;
		}
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

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}
}