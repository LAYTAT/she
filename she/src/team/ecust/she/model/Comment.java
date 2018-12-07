package team.ecust.she.model;

/**<p>评价模型类, 对应数据库里的评价表
*/
public final class Comment {
/**评论内容的默认值*/
//public final static String NULL_COMMENT="对方暂未评价.";

/**评分对应的空常量值*/
public final static int NULL_MARK = -1;

/**评论状态的枚举*/
public enum CommentState {
	NORMAL,
	TIPOFF
}



private String orderNo;
private String memberNo;
private String commentTime;
private int mark;
private String content;
private CommentState state;

/**
 * 根据会员编号和登录密码初始化会员对象，其他默认为空对象，基本类型默认为本类中对应的空常量值。
 * @param memberNo 会员编号
 * @param orderNo 订单编号
 */
public Comment(String orderNo, String memberNo){
	this.setOrderNo(orderNo);
	this.setMemberNo(memberNo);
	this.setCommentTime(null);
	this.setMark(NULL_MARK);
	this.setContent(null);
	this.setState(null);
	}

public Comment(String orderNo){
	this(orderNo, null);
}

public Comment(){
	this(null,null);
}

public String getOrderNo() {
	return orderNo;
}

public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}

public String getMemberNo() {
	return memberNo;
}

public void setMemberNo(String memberNo) {
	this.memberNo = memberNo;
}

public String getCommentTime() {
	return commentTime;
}

public void setCommentTime(String commentTime) {
	this.commentTime = commentTime;
}

public int getMark() {
	return mark;
}

public void setMark(int mark) {
	this.mark = mark;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public CommentState getState() {
	return state;
}

public void setState(CommentState state) {
	this.state = state;
}


/**
 *根据评论状态枚举返回对应存储在数据库里的字符串
 *@return 状态对应的字符串, 如果状态为空,则返回空对象
 * */
public String switchCommentStateToString(){
	if(state ==null)
		return null;
	switch (state){
	case NORMAL: return "normal";
	case TIPOFF: return "tipoff";
	default: return null;
	}
}

/**根据评论状态字符串改变对象的状态枚举属性.
 * @param state 需要转换的状态字符串, 为空对象或者不合法则不改变对象的属性了
 */
public void switchStringToCommentStataEnum(String state){
	if (state == null)
		return;
	 switch(state){
	 case "normal": this.state=CommentState.NORMAL;
	 case "tipoff": this.state= CommentState.TIPOFF;
	 }
}


}

