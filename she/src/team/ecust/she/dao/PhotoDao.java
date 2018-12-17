package team.ecust.she.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import team.ecust.she.model.*;

import team.ecust.she.common.ImageTool;

public final class PhotoDao extends AbstractDao  {
	
/**@param String: idlegoodsNo
 * @param String:photoFileAbsolutePath: 
 * */
	public boolean UploadPhoto(Picture pic) {
		String sql = "insert into picture(idleGoodsNo,photoNo,photo) values (?,?,?)";
		PreparedStatement state = getPreparedStatement(sql);
		InputStream in = null;///
		try {
			state.setString(1, pic.getIdleGoodsNo());
			state.setString(2, pic.getPhotoNo());
			ImageTool tool = new ImageTool();
			tool.setImagePath(pic.getPhoto());
			tool.setImagePath(tool.getAbsolutePath());
			in = tool.readImage();
			state.setBinaryStream(3, in, in.available());
			state.executeUpdate();
			return true;
		} catch (SQLException e1) {	
			setMessage("数据中断传输");
			return false;
		} catch (IOException e2) {
			setMessage("数据流出现问题");
			return false;
		} finally {
			closeStatement(state);
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					setMessage("数据流关闭失败");
					return false;
				}
		}

	}
}
