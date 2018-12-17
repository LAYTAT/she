package team.ecust.she.model;


public final class Order {
	/**订单价格的空常量值*/
	public final static float NULL_PRICE=-1f;
	
	/**订单状态的枚举:<br>对应''order','undetermined','succeed','fail'*/
	public enum OrderState{
		ORDER,
		UNDERTERMINED,
		SUCCEED,
		FAIL
	}
	
	private String orderNo;
	private String memberNo;
	private String idleGoodsNo;
	private float price;
	private String deadline;
	private String orderTime;
	private String effectiveTime;
	private OrderState state;
	
	/**
	 * 根据订单号, 会员账号, 闲置编号初始化订单对象，其他默认为空对象，基本类型默认为本类中对应的空常量值。
	 * @param orderNo 订单编号
	 * @param memberNo 会员编号
	 * @param idleGoodsNo 闲置物品编号
	 */
	public Order(String orderNo,String memberNo,String idlegoodsNo){
		this.orderNo=orderNo;
		this.memberNo=memberNo;
		this.idleGoodsNo=idlegoodsNo;
		this.price=NULL_PRICE;
		this.deadline=null;
		this.orderTime=null;
		this.effectiveTime=null;
		this.state=null;
	}
	
	/**
	 * 根据订单编号初始化订单对象，其他默认为空对象，基本类型默认为本类中对应的空常量值。
	 * @param orderNo 订单编号
	 */
	public Order(String orderNo) {
		this(orderNo,null, null);
	}
	

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the memberNo
	 */
	public String getMemberNo() {
		return memberNo;
	}

	/**
	 * @param memberNo the memberNo to set
	 */
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	/**
	 * @return the idleGoodsNo
	 */
	public String getIdleGoodsNo() {
		return idleGoodsNo;
	}

	/**
	 * @param idleGoodsNo the idleGoodsNo to set
	 */
	public void setIdleGoodsNo(String idleGoodsNo) {
		this.idleGoodsNo = idleGoodsNo;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the deadline
	 */
	public String getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the orderTime
	 */
	public String getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return the effectiveTime
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}

	/**
	 * @param effectiveTime the effectiveTime to set
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	/**
	 * @return the state
	 */
	public OrderState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(OrderState state) {
		this.state = state;
	}
	
	/**
	 * @return 状态对应的字符串，如果状态为空，则返回空对象
	 */
	public String switchStateToString() {
		if(state == null)
			return null;
		switch (state) {
		case ORDER: return "order";
		case UNDERTERMINED: return "undetermined";
		case SUCCEED: return "succeed";
		case FAIL: return "fail";
		default: return null;
		}
	}
	
	public String switchStateToChinese() {
		if(state == null)
			return null;
		switch (state) {
		case ORDER: return "已下单";
		case UNDERTERMINED: return "待完成";
		case SUCCEED: return "交易成功";
		case FAIL: return "交易失败";
		default: return null;
		}
	}
	
	/**
	 * @param state 需要转换的状态字符串，为空对象或不合法则不改变对象的属性
	 */
	public void switchStateToEnum(String state) {
		if(state == null)
			return;
		switch (state) {
		case "order": this.state = OrderState.ORDER;
			break;
		case "undetermined":  this.state = OrderState.UNDERTERMINED;
			break;
		case "succeed":  this.state = OrderState.SUCCEED;
			break;
		case "fail":  this.state = OrderState.FAIL;
		break;
		default: return;
		}
	}	
}