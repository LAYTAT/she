package team.ecust.she.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**<p>数据库对象的抽象类，所有DAO的父类。
 * <p>含有消息属性，需要显示操作结果的信息时，可以通过相应的get和set方法来传递。
 */
public abstract class AbstractDao {
	/**MySQL驱动器选项{@value}*/
	private final static String DRIVER   = "com.mysql.jdbc.Driver";
	/**服务器数据库的URL{@value}*/
	private final static String URL      = "jdbc:mysql://localhost:3306/she";
	/**服务器配置时的用户名{@value}*/
	private final static String USER     = "root";
	/**服务器配置时的密码{@value}*/
	private final static String PASSWORD = "326623";
	
	/**存储数据库加载状态，不要初始化*/
	private static boolean VIABLE;
	/**数据库的静态连接对象*/
	private static Connection connection;
	
	/**加载驱动，只执行一次，优先任何块执行*/
	static {
		driveMySQL();
	}
	
	/**存储错误异常消息，需要初始化为空字符串*/
	private String message = "";
	
	/**
	 * 返回驱动加载情况，子类对象创建时必先依此判断。
	 * @return 数据库加载状态，成功加载返回true，失败返回false
	 */
	protected static boolean isViable() {
		return VIABLE;
	}
	
	/**
	 * 加载数据库驱动并返回执行结果。
	 * @return 数据库驱动的加载状态，成功加载返回true，失败返回false
	 */
	private static boolean driveMySQL() {
		try {
			Class.forName(DRIVER);
			return (VIABLE = true);
		} catch (ClassNotFoundException e1) {
			System.err.println("->找不到MySQL的加载驱动类");
		} catch (ExceptionInInitializerError e2) {
			System.err.println("->初始化驱动类时出现错误，加载失败");
		} catch (LinkageError e3) {
			System.err.println("->连接驱动失败");
		}
		return (VIABLE = false);
	}
	
	/**
	 * 从本类的静态连接成员获取数据库连接对象。
	 * 发生异常时会更新消息。
	 * @return 正常连接的对象或异常连接的空对象
	 */
	protected Connection getConnection() {
		try {
			if(connection == null || connection.isClosed())
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			return connection;
		} catch (SQLTimeoutException e1) {
			message = "连接超时";
		} catch (SQLException e2) {
			message = "连接错误或其它错误";
		}
		return null;
	}
	
	/**
	 * 关闭数据库的连接对象，为空则返回true。
	 * 一般不关闭。
	 * @param conn 需要关闭的连接对象
	 * @return 关闭的结果，成功关闭返回true，否则返回false
	 */
	protected boolean closeConnection(Connection conn) {
		if(conn == null)
			return true;
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			message = "关闭连接错误";
			return false;
		}
	}
	
	/**
	 * 根据SQL语句执行数据查询并返回结果集。
	 * 发生异常时会更新消息并返回空值。
	 * 该方法执行后不会关闭连接。
	 * @param sql 需要执行的SQL语句
	 * @return 只有成功查询了数据库数据才返回结果集，其它情况都返回null
	 */
	protected ResultSet getResult(String sql) {
		Connection conn = getConnection();
		if(conn == null)
			return null;
		Statement state = null;
		try {
			state = conn.createStatement();
			ResultSet result = state.executeQuery(sql);
			state.close();
			return result;
		} catch (SQLTimeoutException e1) {
			message = "查询连接超时";
		} catch (SQLException e2) {
			message = "查询连接错误或其它错误";
		}
		return null;
	}
	
	/**
	 * 根据SQL语句更新数据库表的数据，包括增加、删除、修改语句。
	 * <p>发生异常时会更新消息并返回错误。
	 * <p>该方法执行后不会关闭连接。
	 * @param sql 需要执行的SQL语句
	 * @return 只有成功更新了数据库表的数据才返回true，其它情况都返回false
	 */
	protected boolean update(String sql) {
		Connection conn = getConnection();
		if(conn == null)
			return false;
		PreparedStatement state = null;
		try {
			state = conn.prepareStatement(sql);
			state.executeUpdate();
			state.close();
			return true;
		} catch (SQLTimeoutException e1) {
			message = "更新连接超时";
		} catch (SQLException e2) {
			message = "更新连接错误或其它错误";
		}
		return false;
	}

	/**
	 * 如果这个消息上次更新后没有被读过，即没有调用该方法，返回上次的消息，否则返回空串。
	 * 这个方法是同步的。
	 * @return 返回实时消息内容
	 */
	public synchronized String getMessage() {
		String message = this.message;
		this.message = "";
		return message;
	}

	/**
	 * 设置这个对象的消息属性为message。
	 * 这个方法是同步的。
	 * @param message 需要设置的消息内容
	 */
	protected synchronized void setMessage(String message) {
		this.message = message;
	}
}