package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.model.Inform;

public final class InformDao extends AbstractDao {
	public InformDao() {
		
	}
	
	public boolean updateInformState(String memberNo) {
		return update("update inform set state = 'read' where memberNo = '" + memberNo + "'");
	}
	
	public Inform[] getUnreadInforms(String memberNo) {
		String sql = "select*from Inform where memberNo = '" + memberNo + "' and state = 'toberead'";
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
		Inform inform[] = new Inform[rows];
		try {
			for(int i = 0; i < rows; i++) {
				inform[i] = new Inform();
				inform[i].setInformNo(result.getString(1));
				inform[i].setMemberNo(result.getString(2));
				inform[i].setSentTime(result.getString(3));
				inform[i].switchTypeStringToEnum(result.getString(4));
				inform[i].setContent(result.getString(5));
				inform[i].switchStateStringToEnum(result.getString(6));
				if(!result.next())
					break;
			}
		} catch (SQLException e) {
			setMessage("数据中断传输");
			return null;
		} finally {
			closeStatement(state);
		}
		if(updateInformState(memberNo))
			return inform;
		else
			return null;
	}
	
	public boolean insertNewInform(Inform inform) {
		String sql = "insert into Inform (informNo,memberNo,sentTime,type,content,state)values(?,?,?,?,?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		try {
			state.setString(1, inform.getInformNo());
			state.setString(2, inform.getMemberNo());
			state.setString(3, inform.getSentTime());
			state.setString(4, inform.switchInformTypeToString());
			state.setString(5, inform.getContent());
			state.setString(6, inform.switchInformStateToString());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closePreparedStatement(state);
		}
		return false;
	}
	
	public boolean existInform(String informNo) {
		String sql = "select informNo from Inform where informNo = '" + informNo + "'";
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