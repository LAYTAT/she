package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.model.Message;

public class MessageDao extends AbstractDao {

	public MessageDao() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean updateMessageState(String memberNo) {
		return update("update message set state = 'read' where receiverNo = '" + memberNo + "'");
	}
	
	public boolean hasUnreadMessages(String memberNo) {
		String sql = "select*from message where receiverNo='" + memberNo + "' and state = 'toberead'";
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
	
	public Message[] getUnreadMessages(String memberNo) {
		String sql = "select*from message where receiverNo = '" + memberNo + "' and state = 'toberead'";
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
		Message messages[] = new Message[rows];
		try {
			for(int i = 0; i < rows; i++) {
				messages[i] = new Message(result.getString(1));
				messages[i].setSenderNo(result.getString(2));
				messages[i].setReceiverNo(result.getString(3));
				messages[i].setSentTime(result.getString(4));
				messages[i].setContent(result.getString(5));
				if(!result.next())
					break;
			}
		} catch (SQLException e) {
			setMessage("数据中断传输");
			return null;
		} finally {
			closeStatement(state);
		}
		if(updateMessageState(memberNo))
			return messages;
		else
			return null;
	}
	
	/**不会传入状态*/
	public boolean insertNewMessage(Message message) {
		String sql = "insert into message (messageNo,senderNo,receiverNo,sentTime,content)values(?,?,?,?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, message.getMessageNo());
			state.setString(2, message.getSenderNo());
			state.setString(3, message.getReceiverNo());
			state.setString(4, message.getSentTime());
			state.setString(5, message.getContent());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closePreparedStatement(state);
		}
		return false;
	}
	
	public boolean existItem(Message message) {
		String sql = "select messageNo from message where messageNo = '" + message.getMessageNo() +
				"' and senderNo = '" + message.getSenderNo() + "' and receiverNo = '" + message.getReceiverNo() + "'";
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
}
