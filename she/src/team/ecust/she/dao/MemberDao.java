package team.ecust.she.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import team.ecust.she.common.ImageTool;
import team.ecust.she.model.Member;

/**
 * <p>会员的数据库访问对象，对象的属性为对应的空对象时会忽略改变或查询。
 * <p>需要保证传入的参数都是合法的。
 * <p>需要实现基本的增删改、存在判断操作。
 */
public final class MemberDao extends AbstractDao{
	public boolean validateCipher(String memberNo, String cipher) {
		String sql = "select cipher from Member where memberNo = '" + memberNo + "'";
		Statement state = getStatement();
		ResultSet result = getResult(state, sql);
		if(result == null) {
			closeStatement(state);
			return false;
		}
		try {
			result.next();
		} catch (SQLException e) {
			setMessage("结果出错");
			return false;
		}
		try {
			String realCipher = result.getString(1);
			if(realCipher.equals(cipher))
				return true;
			setMessage("密码不正确");
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closeStatement(state);
		}
		return false;
	}
	
	public boolean loginIn(String memberNo) {
		return update("update Member set state = 'online' where memberNo = '" + memberNo + "'");
	}
	
	public Member getMemberWhoLogin(String memberNo) {
		String sql = "select nickname,realName,sex,major,address,phone,credit,"
				+ "mailbox,signature,headPortrait from Member where memberNo = '"
				+ memberNo + "'";
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
			return null;
		}
		Member member = new Member(memberNo);
		try {
			member.setNickname(result.getString(1));
			member.setRealName(result.getString(2));
			member.switchSexToEnum(result.getString(3));
			member.setMajor(result.getString(4));
			member.setAddress(result.getString(5));
			member.setPhone(result.getString(6));
			member.setCredit(result.getInt(7));
			member.setMailbox(result.getString(8));
			member.setSignature(result.getString(9));
			ImageTool picture = new ImageTool("myHeadPortrait.jpg");
			picture.saveImage(result.getBinaryStream(10));
			member.setHeadPortrait(picture.getAbsolutePath());
			return member;
		} catch (SQLException e) {
			setMessage("数据中断传输");
		} finally {
			closeStatement(state);
		}
		return null;
	}
	
	/**
	 * <p>根据会员对象提供的编号信息，判断该条记录是否存在表中。
	 * <p>该方法会判断参数以及其属性的空否
	 * <p>不存在或查询异常都会更新消息，可以使用getMessage()方法获取。
	 * <p>参数为空则返回false，不要随意改动。
	 * @param k 需要判断的会员对象
	 * @return 判断的结果，真实存在返回true，不存在返回false
	 */
	//@Override
	public boolean existItem(String memberNo) {
		if(memberNo == null) {
			setMessage("会员帐号为空");
			return false;
		}
		StringBuffer sql = new StringBuffer("select memberNo from Member where memberNo = '");
		sql.append(memberNo);
		sql.append("'");
		Statement state = getStatement();
		ResultSet result = getResult(state, sql.toString());//执行查询语句
		if(result == null)//查询出现异常 
			return false;
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
	
	/**
	 * <p>往会员表里添加会员数组记录。
	 * <p>为空的数组元素将被忽略，既不会存入表中，也不会报错！！！！
	 * <p>一个非空数组元素将只存入为非空对象的属性，空对象属性将忽略，为默认值或空值。
	 * <p>但表中的主属性和非空属性为空或者已存在则不插入，且跳过没有提示。
	 * <p>另一种实现方式是通过传可变个数参数进来，只不过那样model里的内容就要加上属性转换等。
	 * @param k[] 需要添加的会员对象数组
	 * @return 判断的结果，插入成功返回true，失败返回false
	 */
	public boolean saveItems(Member[] k) {
		if(k == null) {
			setMessage("会员数组为空");
			return false;
		}
		int number = k.length;
		StringBuffer sql = null;
		for(int i = 0; i < number; i++) {
			if(k[i] == null || k[i].getMemberNo() == null || existItem(k[i].getMemberNo()))
				continue;
			sql = new StringBuffer("insert into Member (memberNo, headPortrait) values (?, ?)");
			PreparedStatement state = getPreparedStatement(sql.toString());
			try {
				ImageTool picture = new ImageTool("login.jpg");
				InputStream in = picture.readImage();
				state.setString(1, "10161831");
				state.setBinaryStream(2, in, in.available());
				state.executeUpdate();
				closePreparedStatement(state);
				in.close();
				return true;
			} catch (SQLException e) {
				System.err.println("参数错误");
			} catch (IOException e) {
				System.err.println("输入流出错");
			}
		}
		return true;
	}
}