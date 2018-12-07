package team.ecust.she.model;


/**闲置物品标签类
*
* */
public final class IdleLabel {
	private String idleGoodsNo;
	private String goodsVarietyNo;
	/**@param String idleGoodsNo 闲置物品编号
	 * */
	public IdleLabel(String idleGoodsNo){
		this.idleGoodsNo=idleGoodsNo;
		this.goodsVarietyNo=null;
	}
	public String getIdleGoodsNo() {
		return idleGoodsNo;
	}
	public void setIdleGoodsNo(String idleGoodsNo) {
		this.idleGoodsNo = idleGoodsNo;
	}
	public String getGoodsVarietyNo() {
		return goodsVarietyNo;
	}
	public void setGoodsVarietyNo(String goodsVarietyNo) {
		this.goodsVarietyNo = goodsVarietyNo;
	}
	
	
}
