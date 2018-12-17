package team.ecust.she.model;


/**闲置与需求匹配信息类,对应数据库里的匹配表matchinfo*/
public final class MatchInfo {
	/**需求物品编号*/
	private String demandGoodsNo;
	/**闲置物品编号*/
	private String idleGoodsNo;
	/**匹配时间*/
	private String matchTime; 
	
	/**@param demandGoodsNo 需求物品编号
	 * @param idleGoodsNo 闲置物品编号*/
   public MatchInfo(String demandGoodsNo, String idleGoodsNo){
	   this.demandGoodsNo=demandGoodsNo;
	   this.idleGoodsNo=idleGoodsNo;
	   this.matchTime=null;
   }

	/**
	 * @return demandGoodsNo
	 */
	public String getDemandGoodsNo() {
		return demandGoodsNo;
	}

	/**
	 * @param demandGoodsNo 
	 */
	public void setDemandGoodsNo(String demandGoodsNo) {
		this.demandGoodsNo = demandGoodsNo;
	}

	/**
	 * @return idleGoodsNo
	 */
	public String getIdleGoodsNo() {
		return idleGoodsNo;
	}

	/**
	 * @param idleGoodsNo 
	 */
	public void setIdleGoodsNo(String idleGoodsNo) {
		this.idleGoodsNo = idleGoodsNo;
	}

	/**
	 * @return  matchTime
	 */
	public String getMatchTime() {
		return matchTime;
	}

	/**
	 * @param  matchTime 
	 */
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
}
