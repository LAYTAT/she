package team.ecust.she.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <p>数据库对象的抽象类，所有DAO的父类。
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
	
	/**加载MySQL数据库的驱动，只需要且只执行一次，优先任何块执行。*/
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e1) {
			System.err.println("->找不到MySQL的驱动类！");
		} catch (ExceptionInInitializerError e2) {
			System.err.println("->初始化驱动类时出现错误！");
		} catch (LinkageError e3) {
			System.err.println("->连接驱动时出现错误！");
		}
	}
	
	/**数据库的静态连接对象，用于保持与数据库的连接*/
	private static Connection connection;
	
	/**存储错误异常消息，需要初始化为空字符串*/
	private String message = "";
	
	/**
	 * <p>从本类的静态连接成员获取数据库连接对象。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * @return 正常连接的对象或异常连接的空对象
	 */
	protected Connection getConnection() {
		try {
			if(connection == null || connection.isClosed())
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			return connection;
		} catch (SQLTimeoutException e1) {
			setMessage("连接过程超时");
		} catch (SQLException e2) {
			setMessage("连接出现错误");
		}
		return null;
	}
	
	/**
	 * <p>关闭数据库的连接对象，对象为空则返回true。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>一般不关闭静态连接对象。
	 * @param conn 需要关闭的连接对象
	 * @return 关闭的结果，成功关闭返回true，否则返回false
	 */
	protected boolean closeConnection(Connection conn) {
		if(conn == null)
			return true;
		try {
			if(!conn.isClosed())
				conn.close();
			return true;
		} catch (SQLException e) {
			setMessage("无法关闭连接");
		}
		return false;
	}
	
	/**
	 * <p>获取查询sql语句的发送对象，如果连接为空或关闭了则返回空对象。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>该方法不会自动关闭连接对象。
	 * @param conn 指定的数据库的连接对象
	 * @return 成功返回的对象，或异常的空对象
	 */
	protected Statement getStatement(Connection conn) {
		if(conn == null) {
			setMessage("连接对象为空");
			return null;
		}
		Statement state = null;
		try {
			if(!conn.isClosed())
				state = conn.createStatement();
			return state;
		} catch (SQLException e) {
			setMessage("发送出现错误");
		}
		return null;
	}
	
	/**
	 * <p>使用默认的静态连接对象获取发送查询sql语句的发送对象。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>该方法不会自动关闭连接对象。
	 * <p>一般先使用这个方法，再处理查询结果，之后关闭这个对象。
	 * @return 成功返回的对象，或异常的空对象
	 */
	protected Statement getStatement() {
		return getStatement(getConnection());
	}
	
	/**
	 * <p>关闭发送对象，对象为空则返回true。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>使用完要使用该方法手动关闭发送对象。
	 * @param state 需要关闭的发送对象
	 * @return 关闭的结果，成功关闭返回true，否则返回false
	 */
	protected boolean closeStatement(Statement state) {
		if(state == null)
			return true;
		try {
			if(!state.isClosed())
				state.close();
			return true;
		} catch (SQLException e) {
			setMessage("无法阻断发送");
		}
		return false;
	}
	
	/**
	 * <p>根据SQL语句执行数据查询并返回结果集。
	 * <p>正常返回的结果集不会为空对象。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>state不会自动关闭，使用完记得关闭。
	 * @param state 发送语句的对象
	 * @param sql 需要执行的SQL语句
	 * @return 只有成功查询了数据库数据才返回结果集，其它情况都返回null
	 */
	protected ResultSet getResult(Statement state, String sql) {
		if(state == null) {
			setMessage("发送对象为空");
			return null;
		}
		try {
			if(!state.isClosed())
				return state.executeQuery(sql);
			setMessage("发送对象已关");
		} catch (SQLTimeoutException e1) {
			setMessage("查询过程超时");
		} catch (SQLException e2) {
			setMessage("查询出现错误");
		}
		return null;
	}
	
	/**
	 * <p>获取更新sql语句的发送对象，如果连接为空或关闭了则返回空对象。
	 * <p>只对sql语句做简单的判断：是否为空。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>该方法不会自动关闭连接对象。
	 * @param conn 指定的数据库的连接对象
	 * @param sql 要执行的sql语句
	 * @return 成功返回的对象，或异常的空对象
	 */
	protected PreparedStatement getPreparedStatement(Connection conn, String sql) {
		if(conn == null) {
			setMessage("连接对象为空");
			return null;
		}
		if(sql == null || sql.equals("")) {
			setMessage("更新语句为空");
			return null;
		}
		PreparedStatement state = null;
		try {
			if(!conn.isClosed())
				state = conn.prepareStatement(sql);
			return state;
		} catch (SQLException e) {
			setMessage("发送出现错误");
		}
		return null;
	}
	
	/**
	 * <p>使用默认的静态连接对象获取发送更新sql语句的发送对象。
	 * <p>只对sql语句做简单的判断：是否为空。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>该方法不会自动关闭连接对象。
	 * <p>一般先使用这个方法，再执行更新步骤，之后关闭这个对象。
	 * @param sql 要执行的sql语句
	 * @return 成功返回的对象，或异常的空对象
	 */
	protected PreparedStatement getPreparedStatement(String sql) {
		return getPreparedStatement(getConnection(), sql);
	}
	
	/**
	 * <p>关闭发送对象，对象为空则返回true。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>使用完要使用该方法手动关闭发送对象。
	 * @param state 需要关闭的发送对象
	 * @return 关闭的结果，成功关闭返回true，否则返回false
	 */
	protected boolean closePreparedStatement(PreparedStatement state) {
		if(state == null)
			return true;
		try {
			if(!state.isClosed())
				state.close();
			return true;
		} catch (SQLException e) {
			setMessage("无法阻断发送");
		}
		return false;
	}
	
	/**
	 * <p>根据SQL语句更新数据库表的数据，包括增加、删除、修改语句。
	 * <p>发生异常时会更新消息，也就是获取到空对象时不用覆盖消息。
	 * <p>该方法执行后不会自动关闭连接。
	 * <p>一般使用该方法执行简单的sql语句。
	 * @param sql 需要执行的SQL语句
	 * @return 只有成功更新了数据库表的数据才返回true，其它情况都返回false
	 */
	protected boolean update(String sql) {
		Connection conn = getConnection();
		if(conn == null) {
			setMessage("连接对象为空");
			return false;
		}
		PreparedStatement state = null;
		try {
			if(!conn.isClosed()) {
				state = conn.prepareStatement(sql);
				state.executeUpdate();
				state.close();
				return true;
			}
			setMessage("连接对象已关");
		} catch (SQLTimeoutException e1) {
			setMessage("更新过程超时");
		} catch (SQLException e2) {
			setMessage("更新出现错误");
		}
		return false;
	}

	/**
	 * <p>如果这个消息上次更新后没有被读过，即没有调用该方法，返回上次的消息，否则返回空串。
	 * <p>这个方法是同步的。
	 * @return 返回实时消息内容
	 */
	public synchronized String getMessage() {
		String message = this.message;
		this.message = "";
		return message;
	}

	/**
	 * <p>设置这个对象的消息属性为message。
	 * <p>但如果这个消息没有读过，则不会更新消息内容。
	 * <p>也就是会返回最底层实现的消息。
	 * <p>这个方法是同步的。
	 * @param message 需要设置的消息内容
	 */
	protected synchronized void setMessage(String message) {
		if(this.message == null || this.message.equals(""))
			this.message = message;
	}
}