package team.ecust.she.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.MatchInfo;

public final class MatchInfoDao extends AbstractDao {
	

	public MatchInfo[] getMatchInfoByMemebr(String memberNo) {
		String sql = "select*from matchinfo where demandGoodsNo in(select demandGoodsNo from demandgoods where memberNo = '"+memberNo+"')";
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
		MatchInfo[] infos = new MatchInfo[rows];
		try {
			for(int i = 0; i < rows; i++) {
				infos[i] = new MatchInfo(result.getString(1), result.getString(2));
				infos[i].setMatchTime(result.getString(3));
			 	if(!result.next())
			 		break;
			}
	  	} catch (SQLException e) {
	  		setMessage("数据中断传输");
		  	return null;
	  	} finally {
	  		closeStatement(state);
	  	}
		return infos;
	}
	
	public boolean existItemByMember(String memberNo) {
		String sql = "select*from matchinfo where demandGoodsNo in(select demandGoodsNo from demandgoods where memberNo = '"+memberNo+"')";
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
	
	public boolean isNewDemandGoodsMatched(DemandGoods demandGoods){
		String sql="select DISTINCT i.idleGoodsNo from demandgoods d,idlegoods i where i.idleGoodsNo IN (  select idleGoodsNo from idlegoods where idleGoodsNo IN ( select idleGoodsNo"
				+ " from idlelabel where goodsVarietyNo IN (	select goodsVarietyNo from goodsvariety where keyWord IN (select keyWord from goodsvariety where goodsVarietyNo IN ( "
				+ "SELECT goodsVarietyNo from demandlabel where demandGoodsNo = '"+demandGoods.getDemandGoodsNo()+"'"  //需求物品编号
				+ ") ) ) ) ) AND d.memberNo <> i.memberNo "
				+"AND d.demandGoodsNo='"+demandGoods.getDemandGoodsNo()+"'" //需求物品编号
				+ "AND i.state='onsale' "
				+ "AND d.price>= i.salePrice "
				+	"AND d.degree <= i.degree"
				;
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

	public boolean isNewIdleFGoodsMatched(IdleGoods idleGoods){
		String sql="select DISTINCT d.demandGoodsNo from demandgoods d,idlegoods i where d.demandGoodsNo IN ( select demandGoodsNo from demandgoods	where demandGoodsNo IN (	select demandGoodsNo	from demandlabel	where goodsVarietyNo IN (select goodsVarietyNo from goodsvariety where keyWord IN ( select keyWord from goodsvariety 	where goodsVarietyNo IN (  SELECT goodsVarietyNo  from demandlabel 	 where idleGoodsNo "
				+"= '"+idleGoods.getIdleGoodsNo()+"'"+") )	) ) )"
				+"AND i.idleGoodsNo='"+idleGoods.getIdleGoodsNo()+"' "
				+"AND d.memberNo <> i.memberNo "
				+"AND d.state='tobematched' "
				+"AND d.price>= i.salePrice "
				+"AND d.degree <= i.degree   "
				;
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
	
	
	
	public boolean insertNewMatchInfo(String demand,String idle){
		{	
			//上传时间
			SimpleDateFormat sp1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//SimpleDateFormat("yyyy-MM-dd hh"+"'"+"mm""ss");
			String uploadTime=sp1.format(new java.util.Date());
			
			String sql = "insert into matchinfo values(?,?,?)";
			PreparedStatement state = getPreparedStatement(sql);
			try {
				state.setString(1, demand);
				state.setString(2, idle);
				state.setString(3, uploadTime);
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
	/**得到并插入新上传的闲置物品的匹配信息:*/
	public MatchInfo[] insertNewIdleMatchDemandInfo(IdleGoods idleGoods){
		String sql="select DISTINCT d.demandGoodsNo from demandgoods d,idlegoods i where d.demandGoodsNo IN ( select demandGoodsNo from demandgoods	where demandGoodsNo IN (	select demandGoodsNo	from demandlabel	where goodsVarietyNo IN (select goodsVarietyNo from goodsvariety where keyWord IN ( select keyWord from goodsvariety 	where goodsVarietyNo IN (  SELECT goodsVarietyNo  from demandlabel 	 where idleGoodsNo "
				+"= '"+idleGoods.getIdleGoodsNo()+"'"+") )	) ) )"
				+"AND i.idleGoodsNo='"+idleGoods.getIdleGoodsNo()+"' "
				+"AND d.memberNo <> i.memberNo "
				+"AND d.state='tobematched' "
				+"AND d.price>= i.salePrice "
				+"AND d.degree <= i.degree   "
				;
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
		MatchInfo matchInfos[] = new MatchInfo[rows];
		try {
			for(int i = 0; i < rows; i++) {
				matchInfos[i]=new MatchInfo(result.getString(1), idleGoods.getIdleGoodsNo());
				matchInfos[i].setMatchTime(idleGoods.getUploadTime());
				insertNewMatchInfo(matchInfos[i].getDemandGoodsNo(),matchInfos[i].getIdleGoodsNo());
				if(!result.next())
					break;
			}
		} catch (SQLException e) {
			setMessage("数据中断传输");
			return null;
		} finally {
			closeStatement(state);
		}
			return matchInfos;
	}
	
	/**得到并插入新上传的需求物品的匹配信息:*/
	public MatchInfo[] insertNewDemandMatchIdleInfo(DemandGoods demandGoods){
		String sql="select DISTINCT i.idleGoodsNo from demandgoods d,idlegoods i where i.idleGoodsNo IN (  select idleGoodsNo from idlegoods where idleGoodsNo IN ( select idleGoodsNo"
				+ " from idlelabel where goodsVarietyNo IN (	select goodsVarietyNo from goodsvariety where keyWord IN (select keyWord from goodsvariety where goodsVarietyNo IN ( "
				+ "SELECT goodsVarietyNo from demandlabel where demandGoodsNo = '"+demandGoods.getDemandGoodsNo()+"'"  //需求物品编号
				+ ") ) ) ) ) AND d.memberNo <> i.memberNo "
				+"AND d.demandGoodsNo='"+demandGoods.getDemandGoodsNo()+"'" //需求物品编号
				+ "AND i.state='onsale' "
				+ "AND d.price>= i.salePrice "
				+	"AND d.degree <= i.degree"
				;
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
		MatchInfo matchInfos[] = new MatchInfo[rows];
		try {
			for(int i = 0; i < rows; i++) {
				matchInfos[i]=new MatchInfo( demandGoods.getDemandGoodsNo(),result.getString(1));
				matchInfos[i].setMatchTime(demandGoods.getUploadTime());
				insertNewMatchInfo(matchInfos[i].getDemandGoodsNo(),matchInfos[i].getIdleGoodsNo());
				if(!result.next())
					break;
			}
		} catch (SQLException e) {
			setMessage("数据中断传输");
			return null;
		} finally {
			closeStatement(state);
		}
			return matchInfos;
	}
	
	public MatchInfoDao() {
		
	}
	
	public MatchInfo[] getMatchInfoByMemebrNo(String memberNo) {
		String sql = "select*from matchinfo where demandGoodsNo in(select demandGoodsNo from demandgoods where memberNo = '"+memberNo+"')";
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
		MatchInfo[] infos = new MatchInfo[rows];
		try {
			for(int i = 0; i < rows; i++) {
				infos[i] = new MatchInfo(result.getString(1), result.getString(2));
				infos[i].setMatchTime(result.getString(3));
			 	if(!result.next())
			 		break;
			}
	  	} catch (SQLException e) {
	  		setMessage("数据中断传输");
		  	return null;
	  	} finally {
	  		closeStatement(state);
	  	}
		return infos;
	}
	
	public boolean existItemByMemberNo(String memberNo) {
		String sql = "select*from matchinfo where demandGoodsNo in(select demandGoodsNo from demandgoods where memberNo = '"+memberNo+"')";
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