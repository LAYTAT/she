package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import team.ecust.she.model.DemandLabel;

public final class DemandGoodsLabelDao  extends AbstractDao{
			
	public boolean insertNewDemandLabel(DemandLabel dLabel) {
		String sql = "insert into demandlabel (demandGoodsNo,goodsVarietyNo) values(?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, dLabel.getDemandGoodsNo());
			state.setString(2, dLabel.getGoodsVarietyNo());
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
