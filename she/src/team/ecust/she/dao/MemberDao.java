package team.ecust.she.dao;

import java.sql.Connection;

import team.ecust.she.model.Member;

/**会员的数据库访问对象，对象的属性为空对象时会忽略改变或查询。*/
public final class MemberDao extends AbstractDao implements IDao<Member> {

	/**
	 * 根据会员对象提供的信息，图像数据、枚举数据、个人简介等除外，判断该条记录是否存在表中。
	 * 属性为空对象不做判断，参数为空返回false。
	 * @param k 需要判断的会员对象
	 * @return 判断的结果，存在返回true，不存在返回false
	 */
	@Override
	public boolean existItem(Member k) {
		if(k == null)
			return false;
		StringBuffer sql = new StringBuffer("select*from Member where ");
		if(k.getMemberNo() != null) {
			sql.append("memberNo = '");
			sql.append(k.getMemberNo());
			sql.append("' and ");
		}
		return false;
	}

	@Override
	public boolean saveItems(Member[] k) {
		if(!isViable()) {//数据库驱动未成功加载
			setMessage("数据库驱动加载失败");
			return false;
		}
		Connection conn = getConnection();
		closeConnection(conn);
		if(conn == null)
			setMessage("连接失败");
		else
			setMessage("连接成功");
		return false;
	}

	@Override
	public boolean removeItems(Member[] k) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateItems(Member[] k) {
		// TODO Auto-generated method stub
		return false;
	}
}