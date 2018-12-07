package team.ecust.she.model;



public final class DemandGoods {
/**新旧程度的空常量*/
	public final static int NULL_DEGREE=-1;
	public final static float NULL_PRICE=-1;
/**需求物品的状态枚举:'done','cancel','matched','tobematched'*/
	public enum DemandGoodsState {
		DONE,
		CANCEL,
		MATCHED,
		TO_BE_MATCHED
	}
private String demandGoodsNo;
private String memberNo;
private String demandGoodsName;
private float price;
private int degree;
private String uploadTime;
private String note;
private DemandGoodsState state;
	

/**
 * 根据闲置物品编号初始化对象,其他的对象默认为空
 * @param demandGoodsNo
 * */
public DemandGoods(String demandGoodsNo){
	this.setDemandGoodsNo(demandGoodsNo);
	this.setMemberNo(null);
	this.setDemandGoodsName(null);
	this.setPrice(NULL_PRICE);
	this.setState(null);
	this.setUploadTime(null);
	this.setNote(null);
	this.setDegree(NULL_DEGREE);
}


public String getDemandGoodsNo() {
	return demandGoodsNo;
}


public void setDemandGoodsNo(String demandGoodsNo) {
	this.demandGoodsNo = demandGoodsNo;
}


public String getMemberNo() {
	return memberNo;
}


public void setMemberNo(String memberNo) {
	this.memberNo = memberNo;
}


public String getDemandGoodsName() {
	return demandGoodsName;
}


public void setDemandGoodsName(String demandGoodsName) {
	this.demandGoodsName = demandGoodsName;
}


public float getPrice() {
	return price;
}


public void setPrice(float price) {
	this.price = price;
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


public DemandGoodsState getState() {
	return state;
}


public void setState(DemandGoodsState state) {
	this.state = state;
}

public int getDegree() {
	return degree;
}


public void setDegree(int degree) {
	this.degree = degree;
}

public void switchDemandGoodsStateToEnum(String state){
	if(state ==null)
		return;
	switch(state){
	case "done": 
		this.state=DemandGoodsState.DONE;	
	case "cancel":
		this.state=DemandGoodsState.CANCEL;
	case "matched":
		this.state=DemandGoodsState.MATCHED;
	case "tobematched":
		this.state=DemandGoodsState.TO_BE_MATCHED;
	}
}
public String switchDemandGoodsStateToString(){
	if(state==null)
		return null;
		switch(state){
		case DONE: return "done";
		case CANCEL:return "cancel";
		case MATCHED: return "matched";
		case TO_BE_MATCHED: return "tobematched";
		default:return null;
	}
}





}
