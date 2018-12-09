package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.GoodsVariety;

public final class DemandGoodsDao extends AbstractDao {
	public DemandGoodsDao() {
		
	}
	
	public DemandGoods[] getDemandGoodsByMember(String memberNo) {
		String sql = "select demandGoodsNo,demandGoodsName,price,degree,uploadTime,note,state from DemandGoods where memberNo = '" + memberNo + "'";
		  Statement state = getStatement();
		  ResultSet result = getResult(state, sql);
		  if(result == null) {//查询出现异常 
		       closeStatement(state);
		       return null;
		  }
		  int rows = 0;
		  try {
			  result.last();
			  rows = result.getRow();
			  result.first();
		  } catch (SQLException e) {
			  setMessage("结果出错");
			  closeStatement(state);
			  return null;
		  }
		  DemandGoods goods[] = new DemandGoods[rows];
		  try {
			  for(int i = 0; i < rows; i++) {
				  goods[i] = new DemandGoods(result.getString(1));
				  goods[i].setDemandGoodsName(result.getString(2));
				  goods[i].setPrice(result.getFloat(3));
				  goods[i].setDegree(result.getInt(4));
				  goods[i].setUploadTime(result.getString(5));
				  goods[i].setNote(result.getString(6));
				  goods[i].switchDemandGoodsStateToEnum(result.getString(7));
				  if(!result.next())
					  break;
			  }
		  } catch (SQLException e) {
			  setMessage("数据中断传输");
			  return null;
		  } finally {
			  closeStatement(state);
		  }
		  return goods;
	}
	
	public GoodsVariety[] getGoodsVarietyByGoods(String demandGoodsNo) {
		  String sql = "select c.keyWord from DemandGoods a join DemandLabel b on a.demandGoodsNo = b.demandGoodsNo" +
				  " join GoodsVariety c on b.goodsVarietyNo = c.goodsVarietyNo" +
				  " where a.demandGoodsNo = '" + demandGoodsNo + "'";
		  Statement state = getStatement();
		  ResultSet result = getResult(state, sql);
		  if(result == null) {//查询出现异常 
		       closeStatement(state);
		       return null;
		  }
		  int rows = 0;
		  try {
			  result.last();
			  rows = result.getRow();
			  result.first();
		  } catch (SQLException e) {
			  setMessage("结果出错");
			  closeStatement(state);
			  return null;
		  }
		  GoodsVariety variety[] = new GoodsVariety[rows];
		  try {
			  for(int i = 0; i < rows; i++) {
				  variety[i] = new GoodsVariety("");
				  variety[i].setKeyWord(result.getString(1));
				  if(!result.next())
					  break;
			  }
		  } catch (SQLException e) {
			  setMessage("数据中断传输");
			  return null;
		  } finally {
			  closeStatement(state);
		  }
		  return variety;
	  }
	
	public boolean insertNewDemandGoods(DemandGoods goods) {
		String sql = "insert into DemandGoods (demandGoodsNo,memberNo,demandGoodsName,price,degree,uploadTime,note)values(?,?,?,?,?,?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, goods.getDemandGoodsNo());
			state.setString(2, goods.getMemberNo());
			state.setString(3, goods.getDemandGoodsName());
			state.setFloat(4, goods.getPrice());
			state.setFloat(5, goods.getDegree());
			state.setString(6, goods.getUploadTime());
			state.setString(7, goods.getNote());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closePreparedStatement(state);
		}
		return false;
	}
	
	
	public boolean existItem(String demandGoodsNo) {
		String sql = "select demandGoodsNo from DemandGoods where demandGoodsNo = '" + demandGoodsNo + "'";
		Statement state = getStatement();
		ResultSet result = getResult(state, sql);
		if(result == null) {//查询出现异常 
			closeStatement(state);
			return false;
		}
		try {
			while(result.next())//含有记录则为true
				return true;
		} catch (SQLException e) {
			setMessage("查询结果出错");//可能提前关闭了发送对象
		} finally {
			closeStatement(state);
		}
		return false;
	}
}