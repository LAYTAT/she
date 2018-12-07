package team.ecust.she.model;


/**聊天消息类,对应数据库里的message表**/
public final class Message {
	/**聊天消息状态的枚举。*/
	public enum  MsgState {
		READ,
		TO_BE_READ,
		TIPOFF
		}
	/**聊天消息编号*/
	private String messageNo;
	/**聊天消息发送者编号*/
	private String senderNo;
	/**聊天消息接收者编号*/
	private String receiverNo;
	/**聊天消息发送时间*/
	private String sentTime;
	/**聊天消息内容*/
	private String content;
	/**聊天消息状态*/
	private MsgState state;
	
	public Message(String messageNo, String senderNo, String receiverNo)
	{
		this.messageNo=messageNo;
		this.senderNo=senderNo;
		this.receiverNo=receiverNo;
		this.sentTime=null;
		this.content=null;
		this.state=null;
	}
	
	public Message(String messageNo)
	{
		this(messageNo, null, null);
	}
	
	/**
	 * @return the messageNo
	 */
	public String getMessageNo() {
		return messageNo;
	}

	/**
	 * @param messageNo the messageNo to set
	 */
	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}

	/**
	 * @return the senderNo
	 */
	public String getSenderNo() {
		return senderNo;
	}

	/**
	 * @param senderNo the senderNo to set
	 */
	public void setSenderNo(String senderNo) {
		this.senderNo = senderNo;
	}

	/**
	 * @return the receiverNo
	 */
	public String getReceiverNo() {
		return receiverNo;
	}

	/**
	 * @param receiverNo the receiverNo to set
	 */
	public void setReceiverNo(String receiverNo) {
		this.receiverNo = receiverNo;
	}

	/**
	 * @return the sentTime
	 */
	public String getSentTime() {
		return sentTime;
	}

	/**
	 * @param sentTime the sentTime to set
	 */
	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the state
	 */
	public MsgState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(MsgState state) {
		this.state = state;
	}

	/**@return 聊天消息状态对应的字符*/
	public String switchMsgStateToString(){
		if(state == null) return null;
		switch (state){
		case TO_BE_READ: 			return "toberead";
		case READ: 		return "read";
		case TIPOFF: return "tipoff";
		default: return null;
		}
	}
	
	/**@param type 需要转换的聊天消息状态String**/
	public void switchStateStringToEnum(String state){
		if(state == null)
			return;
		switch (state) {
		case "toberead": this.state = MsgState.TO_BE_READ;
			break;
		case "read":  this.state = MsgState.READ;
			break;
		case "tipoff":  this.state = MsgState.TIPOFF;
		break;
		default: return;
		}		
	}
	
	
}
