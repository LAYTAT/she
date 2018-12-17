package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;


import team.ecust.she.model.IdleLabel;

public final class IdleLabelDao extends AbstractDao  {
	
	public boolean insertNewIdleLabel(IdleLabel idleLabel) {
		String sql = "insert into idlelabel (idleGoodsNo,goodsVarietyNo) values(?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, idleLabel.getIdleGoodsNo());
			state.setString(2, idleLabel.getGoodsVarietyNo());
			System.out.println("idleGoodsNo="+idleLabel.getIdleGoodsNo());
			System.out.println("GoodsVarietyNo="+idleLabel.getGoodsVarietyNo());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closePreparedStatement(state);
		}
		return false;
	}
	
	
}
