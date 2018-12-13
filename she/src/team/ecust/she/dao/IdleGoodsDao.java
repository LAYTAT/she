package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.common.ImageTool;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.Picture;
import team.ecust.she.view.Index;

public final class IdleGoodsDao extends AbstractDao {
	public IdleGoodsDao() {
		
	}
	
	public IdleGoods[] getIdleGoodsByHotDegree() {
		return getIdleGoodsBySQL("SELECT*FROM idlegoods WHERE idleGoodsNo in "
				+ " (SELECT b.idleGoodsNo FROM( "
				+ " SELECT idleGoodsNo FROM idlelabel "
				+ " WHERE goodsVarietyNo IN( "
				+ " SELECT a.goodsVarietyNo FROM( "
				+ " SELECT goodsVarietyNo FROM goodsvariety ORDER BY degree DESC LIMIT	0, 3) "
				+ " as a) )as b)AND state='onsale' order by uploadTime DESC");
	}
	
	public IdleGoods[] getIdleGoodsByLatestGoods() {
		return getIdleGoodsBySQL("SELECT*FROM idlegoods ORDER BY uploadTime DESC LIMIT 0,50");
	}
	
	public IdleGoods[] getIdleGoodsBySQL(String sql) {
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
		  IdleGoods goods[] = new IdleGoods[rows];
		  try {
			  for(int i = 0; i < rows; i++) {
				  goods[i] = new IdleGoods(result.getString(1), result.getString(2));
				  goods[i].setIdleGoodsName(result.getString(3));
				  goods[i].setSalePrice(result.getFloat(4));
				  goods[i].setOriginalPrice(result.getFloat(5));
				  goods[i].setDegree(result.getInt(6));
				  goods[i].setUploadTime(result.getString(7));
				  goods[i].setNote(result.getString(8));
				  goods[i].switchIdleGoodsStateToEnum(result.getString(9));
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
	
	  public IdleGoods[] getIdleGoodsBySearch(String keyWord) {
		  String sql = "select distinct a.idleGoodsNo,a.memberNo,a.idleGoodsName,a.salePrice,a.originalPrice,a.degree,a.uploadTime,a.note,a.state" +
				  " from idlegoods a join idlelabel b on a.idleGoodsNo = b.idleGoodsNo"+
				  " join goodsvariety c on b.goodsvarietyNo = c.goodsvarietyNo" +
				  " where a.idleGoodsName like '%" + keyWord + "%'" +
				  " or c.keyWord like '%" + keyWord + "%'" +
				  " or c.description like '%" + keyWord + "%'";
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
		  IdleGoods goods[] = new IdleGoods[rows];
		  try {
			  for(int i = 0; i < rows; i++) {
				  goods[i] = new IdleGoods(result.getString(1), result.getString(2));
				  goods[i].setIdleGoodsName(result.getString(3));
				  goods[i].setSalePrice(result.getFloat(4));
				  goods[i].setOriginalPrice(result.getFloat(5));
				  goods[i].setDegree(result.getInt(6));
				  goods[i].setUploadTime(result.getString(7));
				  goods[i].setNote(result.getString(8));
				  goods[i].switchIdleGoodsStateToEnum(result.getString(9));
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
	  
	  public Picture[] getPictureByIdleGoods(String idleGoodsNo) {
		  String sql = "select photoNo, photo from picture where idleGoodsNo = '" + idleGoodsNo + "'";
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
		  Picture[] picture = new Picture[rows];
		  try {
			  for (int i = 0; i < rows; i++) {
				  picture[i] = new Picture("", result.getString(1));
				  ImageTool tool = new ImageTool(Index.getFileName() + ".jpg");
				  tool.setImagePath(tool.getAbsolutePath());//设置为绝对路径
				  tool.saveImage(result.getBinaryStream(2));
				  picture[i].setPhoto(tool.getImagePath());
				  if(!result.next())
					  break;
			  }
		  } catch (SQLException e) {
			  
		  } finally {
			closeStatement(state);
		}
		  return picture;
	  }
	  
	  public IdleGoods[] getIdleGoodsByMember(String memberNo) {
		  String sql = "select idleGoodsNo,idleGoodsName,salePrice,originalPrice,degree,uploadTime,note,state"
		  		+ " from IdleGoods where memberNo = '" + memberNo + "'";
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
		  IdleGoods goods[] = new IdleGoods[rows];
		  try {
			  for(int i = 0; i < rows; i++) {
				  goods[i] = new IdleGoods(result.getString(1));
				  goods[i].setIdleGoodsName(result.getString(2));
				  goods[i].setSalePrice(result.getFloat(3));
				  goods[i].setOriginalPrice(result.getFloat(4));
				  goods[i].setDegree(result.getInt(5));
				  goods[i].setUploadTime(result.getString(6));
				  goods[i].setNote(result.getString(7));
				  goods[i].switchIdleGoodsStateToEnum(result.getString(8));
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
	  
	  public IdleGoods getIdleGoodsByIdleGoods(String idleGoodsNo) {
		  String sql = "select idleGoodsNo,idleGoodsName,salePrice,originalPrice,degree,uploadTime,note,state from IdleGoods where idleGoodsNo = '" + idleGoodsNo + "'";
		  Statement state = getStatement();
		  ResultSet result = getResult(state, sql);
		  if(result == null) {//查询出现异常 
		       closeStatement(state);
		       return null;
		  }
		  try {
			  result.next();
		  } catch (SQLException e) {
			  setMessage("结果出错");
			  closeStatement(state);
			  return null;
		  }
		  try {
				  IdleGoods goods = new IdleGoods(result.getString(1));
				  goods.setIdleGoodsName(result.getString(2));
				  goods.setSalePrice(result.getFloat(3));
				  goods.setOriginalPrice(result.getFloat(4));
				  goods.setDegree(result.getInt(5));
				  goods.setUploadTime(result.getString(6));
				  goods.setNote(result.getString(7));
				  goods.switchIdleGoodsStateToEnum(result.getString(8));
				  return goods;
		  } catch (SQLException e) {
			  setMessage("数据中断传输");
		  } finally {
			  closeStatement(state);
		  }
		  return null;
	  }
	  
	  public GoodsVariety[] getGoodsVarietyByGoods(String idleGoodsNo) {
		  String sql = "select c.keyWord from IdleGoods a join IdleLabel b on a.idleGoodsNo = b.idleGoodsNo" +
				  " join GoodsVariety c on b.goodsVarietyNo = c.goodsVarietyNo" +
				  " where a.idleGoodsNo = '" + idleGoodsNo + "'";
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
	  
	 public boolean updateState(String goodsNo, String state) {
		 return update("update idlegoods set state = '" + state + "' where idlegoodsNo = '" + goodsNo + "'");
	 }
	
	public boolean updateIdleGoods(IdleGoods goods) {
		String sql = "update IdleGoods set idleGoodsName=?,salePrice=?,"
				+ "originalPrice=?,degree=?,state=?,note=? where idleGoodsNo = '" + goods.getIdleGoodsNo() + "'";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, goods.getIdleGoodsName());
			state.setFloat(2, goods.getSalePrice());
			state.setFloat(3, goods.getOriginalPrice());
			state.setInt(4, goods.getDegree());
			state.setString(5, goods.switchIdleGoodsStateToString());
			state.setString(6, goods.getNote());
			state.executeUpdate();
			return true;
		} catch (SQLException e1) {
			setMessage("数据中断传输");
		} finally {
			closeStatement(state);
		}
		return false;
	}
	
	public String getMemberNoByIdleGoods(String idleGoodsNo) {
		String sql = "select memberNo from IdleGoods where idleGoodsNo = '" + idleGoodsNo + "'";
		Statement state = getStatement();
		ResultSet result = getResult(state, sql);
		if(result == null) {//查询出现异常 
			closeStatement(state);
			return null;
		}
		try {
			result.next();
			return result.getString(1);
		} catch (SQLException e) {
			setMessage("查询结果出错");//可能提前关闭了发送对象
		} finally {
			closeStatement(state);
		}
		return null;
	}
	
	public boolean deleteIdleGoods(String idleGoodsNo) {
		if(update("delete from IdleLabel where idleGoodsNo = '" + idleGoodsNo + "'") &&
				update("delete from Picture where idleGoodsNo = '" + idleGoodsNo + "'") &&
				update("delete from matchinfo where idleGoodsNo = '" + idleGoodsNo + "'"))
			return update("delete from IdleGoods where idleGoodsNo = '" + idleGoodsNo + "'");
		return false;
	}
	
	public boolean insertNewIdleGoods(IdleGoods goods) {
		String sql = "insert into IdleGoods (idleGoodsNo,memberNo,idleGoodsName,salePrice,originalPrice,degree,uploadTime,note)values(?,?,?,?,?,?,?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, goods.getIdleGoodsNo());
			state.setString(2, goods.getMemberNo());
			state.setString(3, goods.getIdleGoodsName());
			state.setFloat(4, goods.getSalePrice());
			state.setFloat(5, goods.getOriginalPrice());
			state.setInt(6, goods.getDegree());
			state.setString(7, goods.getUploadTime());
			state.setString(8, goods.getNote());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closePreparedStatement(state);
		}
		return false;
	}
	
	public boolean existItem(String idleGoodsNo) {
		String sql = "select idleGoodsNo from IdleGoods where idleGoodsNo = '" + idleGoodsNo + "'";
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