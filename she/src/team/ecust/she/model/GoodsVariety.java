package team.ecust.she.model;


/**<p>物品种类模型类，对应数据库里的物品种类goodsvariety表。
 * */
public class GoodsVariety {

	private String goodsVarietyNo;
	private String keyWord;
	private String degree;
	private String description;
	
	/**是否需要传参的初始化*/
	public GoodsVariety(String goodsVarietyNo){
		this.goodsVarietyNo=goodsVarietyNo;
		this.keyWord=null;
		this.degree=null;
		this.description=null;
		
	}

	public String getGoodsVarietyNo() {
		return goodsVarietyNo;
	}

	public void setGoodsVarietyNo(String goodsVarietyNo) {
		this.goodsVarietyNo = goodsVarietyNo;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
