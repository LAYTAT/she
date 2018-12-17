package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.model.Member;


/**
 * <p>教务处信息的数据库访问对象，对象的属性为对应的空对象时会忽略改变或查询。
 */
public final class EcustDao extends AbstractDao {
		/***/
		public  Member getEcustinfo(String memberNo){
			String sql = "select * from ecust where studentNo="+"'"+memberNo+"'";
			Statement state = getStatement();
			ResultSet result=getResult(state, sql);
			if(result == null) {//查询出现异常 
				closeStatement(state);
				return null;
			}
			try {
				result.next();
			} catch (SQLException e) {
				setMessage("结果出错");
				return null;
			}
			Member member = new Member(memberNo);
			try {			
				member.setRealName(result.getString(3));
				member.switchSexToEnum(result.getString(4));
				member.setMajor(result.getString(5));			
				return member;
				
				} catch (SQLException e) {
				setMessage("数据中断传输");
				} finally {
				closeStatement(state);
			}
		  return member;
//			Ecust ecust=new Ecust(member.getMemberNo());
//			member.setMajor(ecust.getMajor());
//			member.setRealName(ecust.getStudentName());		
//			member.switchSexToEnum(ecust.switchEcustSexToString());	
//			return member;
		}
		
		
		
		/**检查身份证号是否与学号匹配*/
		public boolean identityCheck(String ID,String studentID){
			String sql="select identityCard from ecust where studentNo='"+studentID+"'";
			Statement state = getStatement();
			ResultSet result = getResult(state, sql);
			if(result==null){
				closeStatement(state);
				return false;
			}
			try{
				result.next();
			} catch(SQLException e){
				setMessage("结果出错");
				return false;
			}
			try{
				String realID = result.getString(1);
					if(realID.equals(ID))
						return true;
					setMessage("身份证号码与学号不匹配!");
			} catch(SQLException e){
				setMessage("数据传输中断!");
			}finally{
				closeStatement(state);
			}
			return false;
		}
		

		/**@param Member 会员对象 用于插入8个得到的信息数据: 会员号, 密码,昵称,真实姓名,性别,专业,邮箱,注册时间*/
		public boolean insertMembership(Member member)
		{
			String sql1="insert into Member (memberNo,cipher,nickname,realName,sex,major,mailbox,registerTime)values(?,?,?,?,?,?,?,?)";
			PreparedStatement state = getPreparedStatement(sql1);
			try{
				state.setString(1, member.getMemberNo());
				state.setString(2,member.getCipher() );
				state.setString(3, member.getNickname());
				state.setString(4, member.getRealName());
				state.setString(5,member.switchSexToString());
				state.setString(6,member.getMajor() );
				state.setString(7,member.getMailbox() );
				state.setString(8, member.getRegisterTime());
				state.executeUpdate();
				return true;
			} catch (SQLException e) {
				setMessage("数据传输中断");
			} finally {
				closePreparedStatement(state);
			}
			return false;
		}

}
