package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.model.GoodsVariety;

public final class GoodVareityDao extends AbstractDao   {
	
	public boolean updateHotDegree(String keyWord) {
		String sql = "update goodsvariety set degree = degree + 1 "
				+ " where keyWord like '%" + keyWord + "%' or description like '%" + keyWord + "%'";
		return update(sql);
	}
	
	/**@return 检查keyword是否存在 存在则返回*/
	public boolean existGoodVareity(String keyword) {
		String sql = "select goodsVarietyNo from goodsvariety where KeyWord = '" + keyword + "'";
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
	/**插入新的种类, 传入idlegoods对象,以及传1,2,3哪个keyword*/
	public boolean insertNewGoodsVareity(GoodsVariety goodsVariety) {
		String sql = "insert into goodsvariety(goodsVarietyNo,keyWord)values(?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		try {

				state.setString(1, goodsVariety.getGoodsVarietyNo());
				state.setString(2, goodsVariety.getKeyWord());
				state.executeUpdate();
			return true;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closePreparedStatement(state);
		}
		return false;
	}
	
	  public String getGoodsVarietyNoByKeyword(String keyword) {
		  String sql = "select goodsVarietyNo from goodsvariety where keyWord = '" + keyword + "'";
		  Statement state = getStatement();
		  ResultSet result = getResult(state, sql);
		//  System.out.println("result.getString(1);="+result.getString(1);
		  if(result == null) {//查询出现异常 
		       closeStatement(state);
		      // System.out.println("已有标签goodVarietyNo:null");	  
		       return null;
		  }
		  try {	   
			  result.next();
			  String goodVarietyNo=result.getString(1);
			  return goodVarietyNo;
		  } catch (SQLException e) {
			  setMessage("结果出错");
			  //System.out.println("结果出错");	  
			  closeStatement(state);
			  return null;
		  } finally {
			  closeStatement(state);
		  }

	  }
	
	
}
