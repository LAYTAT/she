package team.ecust.she.model;

/**闲置物品类 对应IdleGoods表
*/
public final class IdleGoods {
	public final static int NULL_SALE_PRICE=-1;
	public final static int NULL_ORIGINAL_PRICE=-1;
	public final static int NULL_DEGREE=-1;
	/**ENUM state: 'ON_SALE','workoff','cancel'*/
	public enum IdlegoodsState{
		ON_SALE,
		WORKOFF,
		CANCEL
	}
	
	private String idleGoodsNo;
	private String memberNo;
	private String idleGoodsName;
	private int salePrice;
	private int originalPrice;
	private int degree;
	private String uploadTime;
	private String note;
	private IdlegoodsState state;
	
	public IdleGoods(String idleGoodsNo,String memberNo ){
		this.idleGoodsNo=idleGoodsNo;
		this.memberNo=memberNo;
		this.idleGoodsName=null;
		this.salePrice=NULL_SALE_PRICE;
		this.originalPrice=NULL_ORIGINAL_PRICE;
		this.degree=NULL_DEGREE;
		this.uploadTime=null;
		this.note=null;
		this.state=null;	
	}
	public IdleGoods(String idleGoodsNo){
		this(idleGoodsNo,null);
	}

	public String getIdleGoodsNo() {
		return idleGoodsNo;
	}

	public void setIdleGoodsNo(String idleGoodsNo) {
		this.idleGoodsNo = idleGoodsNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getIdleGoodsName() {
		return idleGoodsName;
	}

	public void setIdleGoodsName(String idleGoodsName) {
		this.idleGoodsName = idleGoodsName;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public IdlegoodsState getState() {
		return state;
	}

	public void setState(IdlegoodsState state) {
		this.state = state;
	}
	public String switchIdleGoodsStateToString(){
		if(state ==null)
			return null;
		switch(state){
		case ON_SALE: return "on sale";
		case WORKOFF: return "workoff";
		case CANCEL: return "cancel";
		default: return null;
		}
	}
	public void switchIdleGoodsStateToEnum(String state){
		if(state==null)
			return;
		switch(state){
		case "on sale": this.state=IdlegoodsState.ON_SALE;
		break;
		case "workoff": this.state=IdlegoodsState.WORKOFF;
		break;
		case "cancel": this.state = IdlegoodsState.CANCEL;
		break;
		default:return;
		
		}
	}
	
}
