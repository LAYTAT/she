package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.model.Comment;

public final class CommentDao extends AbstractDao  {
	public CommentDao() {
		
	}
	/**订单对应的评论是否存在*/
	public boolean existComment(String orderNo, String memberNo){
		String sql = "select * from comment where orderNo = '" + orderNo + "' and memberNo = '" + memberNo + "'";
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
	
	public boolean insertNewComment(Comment comment ){
		String sql="insert into comment values(?,?,?,?,?,?) ";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, comment.getOrderNo());
			state.setString(2, comment.getMemberNo());
			state.setString(3,comment.getCommentTime());
			state.setInt(4, comment.getMark());
			state.setString(5,comment.getContent());
			state.setString(6, "normal");	
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closePreparedStatement(state);
		}
		return false;
	}
	
	public boolean updateCommentState(Comment comment) {
		return update("update comment set state = '" + comment.switchCommentStateToString() +
				"' where orderNo = '" + comment.getOrderNo() + "' and memberNo = '" + comment.getMemberNo() + "'");
	}
	
	public Comment getComment(String orderNo, String memberNo){
		 String sql = "select orderNo,memberNo,commentTime,mark,content,state"
			  		+ " from comment where orderNo = '" + orderNo + "' and memberNo = '" + memberNo + "'";
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
				  	  Comment comment = new Comment(orderNo);
					  comment.setMemberNo(result.getString(2));
					  comment.setCommentTime(result.getString(3));
					  comment.setMark(result.getInt(4));
					  comment.setContent(result.getString(5));
					  comment.switchStringToCommentStataEnum(result.getString(6));
					  return comment;
			  } catch (SQLException e) {
				  setMessage("数据中断传输");
				  return null;
			  } finally {
				  closeStatement(state);
			  }
		  }
}