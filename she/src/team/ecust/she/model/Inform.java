package team.ecust.she.model;


/**系统通知类 对应数据库里的通知表
 */
public final class Inform {
	
	/**'系统通知类型的枚举: 'credit','newmsg','cfmorder','orderinfo','comment'*/
	public enum InformType{
		CREDIT,
		NEW_MSG,
		CFM_ORDER,
		ORDER_INFO,
		COMMENT
	}
	/**系统通知状态的枚举。*/
	public enum InformState {
		READ,
		TOBEREAD
		}
	/**系统通知编号*/
	private String informNo;
	/**会员账号*/
	private String memberNo;
	/**系统通知时间*/
	private String sentTime;
	/**系统通知类型*/
	private InformType type;
	/**系统通知状态*/
	private InformState state;
	/**系统通知内容*/
	private String content;
	
		
	/**
	 * @param informNo系统通知编号 <br>memberNo会员账号*/
	public Inform(String informNo, String memberNo){
		this.informNo=informNo;
		this.memberNo=memberNo;
		this.sentTime=null;
		this.type=null;
		this.state=null;		
	}
	
	public Inform(){
		this(null,null);
	}
	
	public Inform(String informNo){
		this(informNo,null);
	}

	public String getInformNo() {
		return informNo;
	}


	public void setInformNo(String informNo) {
		this.informNo = informNo;
	}


	public String getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}


	public String getSentTime() {
		return sentTime;
	}


	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}


	public InformType getInformType() {
		return type;
	}


	public void setInformType(InformType type) {
		this.type = type;
	}


	public InformState getState() {
		return state;
	}


	public void setState(InformState state) {
		this.state = state;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	/**@return 系统通知类型对应的字符*/
	public String switchInformTypeToString(){
		if(type == null) return null;
		switch (type){
		case CREDIT: 			return "credit";
		case NEW_MSG: 		return "newmsg";
		case CFM_ORDER: 	return "cfmorder";
		case ORDER_INFO:   return "orderInfo";
		case COMMENT:    return "comment";
		default: return null;
		}
	}
	
	public String switchInformTypeToChinese(){
		if(type == null) return null;
		switch (type){
		case CREDIT: 			return "信用分变化";
		case NEW_MSG: 		return "新消息";
		case CFM_ORDER: 	return "订单确认";
		case ORDER_INFO:   return "订单信息";
		case COMMENT:    return "用户评价";
		default: return null;
		}
	}
	
	/**@param type 需要转换的系统通知类型String**/
	public void switchTypeStringToEnum(String type){
		if(type == null)
			return;
		switch (type) {
		case "credit": this.type = InformType.CREDIT;
			break;
		case "new message":  this.type = InformType.NEW_MSG;
			break;
		case "cfmorder":  this.type = InformType.CFM_ORDER;
			break;
		case "orderInfo":  this.type = InformType.ORDER_INFO;
		break;
		case "comment":  this.type = InformType.COMMENT;
		break;
		default: return;
		}		
	}
	/**@return 系统通知状态对应的字符*/
	public String switchInformStateToString(){
		if(state == null) return null;
		switch (state){
		case TOBEREAD: 			return "toberead";
		case READ: 		return "read";
		default: return null;
		}
	}
	
	/**@param type 需要转换的系统通知状态String**/
	public void switchStateStringToEnum(String state){
		if(state == null)
			return;
		switch (state) {
		case "toberead": this.state = InformState.TOBEREAD;
			break;
		case "read":  this.state = InformState.READ;
			break;
		default: return;
		}		
	}
}