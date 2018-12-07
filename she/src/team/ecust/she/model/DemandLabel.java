package team.ecust.she.model;


/**需求物品标签
 *
 * */
public final class DemandLabel {
private String demandGoodsNo;
private String goodsVarietyNo;


/**@param String demandGoodsNo 需求物品编号
 * */
public DemandLabel(String demandGoodsNo){
	this.demandGoodsNo=demandGoodsNo;
	this.goodsVarietyNo=null;
}

public String getDemandGoodsNo() {
	return demandGoodsNo;
}

public void setDemandGoodsNo(String demandGoodsNo) {
	this.demandGoodsNo = demandGoodsNo;
}

public String getGoodsVarietyNo() {
	return goodsVarietyNo;
}

public void setGoodsVarietyNo(String goodsVarietyNo) {
	this.goodsVarietyNo = goodsVarietyNo;
}

}
