package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.model.Order;

public final class OrderDao extends AbstractDao {

	public OrderDao() {
		
	}
	/**根据订单号查询订单*/
	public Order getOrderByOrder(String orderNo) {
		String sql = "select*from orders where orderNo='" + orderNo + "'";
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
		Order order = new Order(orderNo);
		try {
			order.setMemberNo(result.getString(2));
			order.setIdleGoodsNo(result.getString(3));
			order.setPrice(result.getFloat(4));
			order.setDeadline(result.getString(5));
			order.setOrderTime(result.getString(6));
			order.setEffectiveTime(result.getString(7));
			order.switchStateToEnum(result.getString(8));
			return order;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closeStatement(state);
		}
		return null;
	}
	/**是否存在订单*/
	public boolean existItemByOrder(String orderNo) {
		String sql = "select*from orders where orderNo = '" + orderNo + "'";
		Statement state = getStatement();
		ResultSet result = getResult(state, sql);//执行查询语句
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
	
	public boolean existItemByMember(String memberNo) {
		String sql = "select*from orders where idleGoodsNo in(select idleGoodsNo from idlegoods where memberNo = '"+memberNo+"')or memberNo = '"+memberNo+"'";
		Statement state = getStatement();
		ResultSet result = getResult(state, sql);//执行查询语句
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
	

	
	public Order[] getOrderbyMemberNo(String memberNo){
		String sql="select*from orders where idleGoodsNo in(select idleGoodsNo from idlegoods where memberNo = '"+memberNo+"')or memberNo = '"+memberNo+"'";
		Statement state = getStatement();
		ResultSet result = getResult(state, sql);//执行查询语句
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
		  Order order[] = new Order[rows];
		  try {
			  for(int i = 0; i < rows; i++) {
				  order[i] = new Order(result.getString(1));
				  order[i].setMemberNo(memberNo);
				  order[i].setIdleGoodsNo(result.getString(3));
				  order[i].setPrice(Float.valueOf(result.getString(4)).floatValue());
				  order[i].setDeadline(result.getString(5));
				  order[i].setOrderTime(result.getString(6));
				  order[i].setEffectiveTime(result.getString(7));
				  order[i].switchStateToEnum((result.getString(8)));
				  if(!result.next())
					  break;
			  }
		  } catch (SQLException e) {
			  setMessage("数据中断传输");
			  return null;
		  } finally {
			  closeStatement(state);
		  }
		  return order;
	}
	
	public boolean insertNewOrder(Order order){
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, order.getOrderNo());
			state.setString(2,order.getMemberNo());
			state.setString(3,order.getIdleGoodsNo());
			state.setFloat(4, order.getPrice());
			state.setString(5,order.getDeadline());
			state.setString(6, order.getOrderTime());
			state.setString(7, null);
			state.setString(8, "order");
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closePreparedStatement(state);
		}
		return false;
	}
	
	/**根据订单编号和对应的状态更新订单状态**/
	public boolean updateOrderState(String state,String orderNo){
		return update("update orders set state = '"+state+"' where orderNo = '" + orderNo + "'");
	}

	public boolean deleteOrder(String orderNo) {
		return update("delete from orders where orderNo = '" + orderNo + "'");
	}
	
	public boolean updateOrderEffectiveTime(String time,String orderNo){
		return update("update orders set effectivetime = '"+time+"' where orderNo = '" + orderNo + "'");
	}
	
}
