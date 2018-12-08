
package team.ecust.she.model;


/**图像模型类，对应数据库里的图像表。*/
public final class Picture {
	private String idleGoodsNo;
	private String photoNo;
	private String photo;
	
	/**
	 * 根据订单编号和照片编号初始化会员对象，其他默认为空对象，基本类型默认为本类中对应的空常量值。
	 * @param idleGoodsNo 闲置编号
	 * @param photoNo 图片编号
	 */
	public Picture(String idleGoodsNo,String photoNo){
		this.idleGoodsNo=idleGoodsNo;
		this.photoNo=photoNo;
		this.photo=null;
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
	 * @return the photoNo
	 */
	public String getPhotoNo() {
		return photoNo;
	}

	/**
	 * @param photoNo the photoNo to set
	 */
	public void setPhotoNo(String photoNo) {
		this.photoNo = photoNo;
	}

	/**
	 * @return the photo file path
	 */
	public String  getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo File Path to set
	 */
	public void setPhoto(String  photo) {
		this.photo = photo;
	}
	
	
}
