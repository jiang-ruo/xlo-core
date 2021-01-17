package xlo.database.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.*;

/**
 * @author XiaoLOrange
 * @time 2020.08.27	使用Map接收传入的参数时需要传入表中各个字段的名称。
 * @time 2020.10.10	添加了使用LinkedList接收返回值的方法
 * 					能够与Sqlite和Oracle数据库连接
 * 					添加对与反射调用的支持(对于传入的类对象没有任何限制，不需要特殊的构造函数，也不需要set方法)
 * @time 2020.10.20	并将获取连接和关闭连接设为接口。
 * @time 2020.10.30 在使用一段时间后删除了基本没有使用的旧版方法。
 * 					替换为两个泛型方法，增加了BaseDao的灵活性
 * @time 2020.11.26 将BaseDao更名为PreBaseDao，作为BaseDao的父类，
 * 					添加了更为灵活的能够传入Connection的方法
 * @time 2020.12.01 修改PreBaseDao使其能够使用单例
 * @time 2020.01.14 备注，计划：将setFilter改为注解结果映射，没映射采用原pojo值。
 * @title
 *
 * @rule @Orm注解标记Field在数据库中对应的字段名
 * @rule @Obj注解标记Field作为一个Pojo对象获取
 */

public class CoreDao<T> {

	private Class<T> Ttype;

	public CoreDao(){
		try {
			ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
			Ttype = (Class<T>) pt.getActualTypeArguments()[0];
		}catch (ClassCastException e){}
	}

	/**
	 * 执行sql语句
	 * @param conn 数据库连接
	 * @param sql
	 * @param params 向数据库中传参
	 * @return 成功受影响的数据条数
	 */
	public int Update(Connection conn, String sql, Object... params){
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			inputParam(pstmt, params);
			result = pstmt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 初始化批处理缓冲区
	 * 仅支持一条语句批量执行，
	 * 同一语句的不同参数
	 * @param conn
	 * @param sql
	 * @return
	 */
	public PreparedStatement bufferedStart(Connection conn, String sql){
		PreparedStatement pstmt = null;
		boolean result = true;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstmt;
	}

	/**
	 * 向缓冲区中添加执行参数
	 * 1次仅能添加1条语句的参数
	 * @param pstmt
	 * @param params
	 * @return
	 */
	public boolean BufferedAdd(PreparedStatement pstmt, Object... params) {
		boolean result = true;
		try {
			inputParam(pstmt, params);
			pstmt.addBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * 执行缓冲内容
	 * @param pstmt
	 * @return 返回每天语句影响的记录数
	 */
	public int[] executeBuffered(PreparedStatement pstmt) {
		int[] result = null;
		try {
			result = pstmt.executeBatch();
			pstmt.clearBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param pstmt
	 * 关闭批处理缓冲区
	 */
	public void bufferedClose(PreparedStatement pstmt) {
		try {
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public ArrayList<Map<String, Object>> QueryM(Connection conn, String sql, Object... params){
		return (ArrayList<Map<String, Object>>) SupportedQueryM(conn, ArrayList.class, sql, params);
	}

	/**
	 * 查询
	 * @param conn
	 * @param sql
	 * @param params
	 * @return 返回结果集ArrayList<T>
	 */
	public ArrayList<T> QueryT(Connection conn, String sql, Object... params){
		return (ArrayList) SupportedQueryT(conn, null, ArrayList.class, sql, params);
	}

	/**
	 * 查询
	 * @param conn
	 * @param <E> 泛型
	 * @param claee 返回类型
	 * @param sql
	 * @param params
	 * @return
	 */
	public <E> ArrayList<E> QueryT(Connection conn, Class claee, String sql, Object... params){
		return (ArrayList) SupportedQueryT(conn, claee, ArrayList.class, sql, params);
	}

	/**
	 * 查询
	 * @param conn
	 * @param sql
	 * @param params 向数据库中传参
	 * @return 返回结果集LinkedList<Map<String, Object>>
	 * LinkedList<行>
	 * Map<字段名,对应的值>
	 */
	public LinkedList<Map<String, Object>> QueryM_LL(Connection conn, String sql, Object... params) {
		return (LinkedList<Map<String, Object>>) SupportedQueryM(conn, LinkedList.class, sql, params);
	}

	/**
	 * 查询
	 * @param conn
	 * @param sql
	 * @param params
	 * @return 返回结果集LinkedList<T>
	 */
	public LinkedList<T> QueryT_LL(Connection conn, String sql, Object... params){
		return (LinkedList) SupportedQueryT(conn, null, LinkedList.class, sql, params);
	}

	/**
	 * 查询
	 * @param conn
	 * @param <E> 泛型
	 * @param claee 返回类型
	 * @param sql
	 * @param params
	 * @return
	 */
	public <E> LinkedList<E> QueryT_LL(Connection conn, Class claee, String sql, Object... params){
		return (LinkedList) SupportedQueryT(conn, claee, LinkedList.class, sql, params);
	}

	/**
	 * 可以直接传入一个连接执行数据库查询操作
	 * @param conn
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> SupportedQueryM(Connection conn, Class clazz, String sql, Object... params){
		List<Map<String, Object>> rows = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			inputParam(pstmt, params);
			rs = pstmt.executeQuery();

			rows = getResultM(clazz, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rows;
	}

	/**
	 * 传入连接进行查询
	 * @param conn
	 * @param claee
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	List SupportedQueryT(Connection conn, Class claee, Class clazz, String sql, Object... params){
		List rows = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			inputParam(pstmt, params);
			rs = pstmt.executeQuery();

			rows = getResultT(claee, clazz, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rows;
	}

	/**
	 * 向sql语句中添加参数
	 * setObject怀疑setObject方法会比XLO-0.4之前的版本使用setInt,setString等方法慢
	 * @throws SQLException
	 */
	void inputParam(PreparedStatement pstmt, Object[] params) throws SQLException {
		if(params == null) return;
		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
	}

	/**
	 * 将结果集转换为List集。
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> getResultM(Class<T> clazz, ResultSet rs) throws SQLException{
		List<Map<String, Object>> rows = null;
		if(clazz == ArrayList.class){
			rows = new ArrayList<Map<String, Object>>();
		}else if(clazz == LinkedList.class){
			rows = new LinkedList<Map<String, Object>>();
		}else{
			throw new Error("class错误");
		}

		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		String[] columns = new String[cols];
		for (int i = 0; i < cols; i++) {
			columns[i] = rsmd.getColumnLabel(i + 1);
		}

		Map<String, Object> row = null;
		while(rs.next()){
			row = new HashMap<String, Object>();
			for (int i = 0; i < columns.length; i++) {
				row.put(columns[i], rs.getObject(columns[i]));
			}
			rows.add(row);
		}

		return rows;
	}

	/**
	 * 将结果集转换为List集。
	 * @param claee 返回值类型，为空则返回泛型T
	 * @param clazz 输出的容器类型：arraylist或linkedlist
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private List getResultT(Class claee, Class clazz, ResultSet rs) throws SQLException {
		List rows = null;
		if(clazz == ArrayList.class){
			rows = new ArrayList();
		}else if(clazz == LinkedList.class){
			rows = new LinkedList();
		}else{
			throw new Error("class错误");
		}

		Class entityClass = claee == null ? Ttype : claee;
		if(entityClass == null) throw new ClassCastException("used but don't set T");

		Object obj = null;
		PojoBuilder builder = new PojoBuilder();
		while (rs.next()){
			//新建一个T对象(使用Unsafe方法，越过构造方法直接构造对象)
			builder.init();
			obj = builder.create(entityClass, rs);

			rows.add(obj);
		}

		return rows;
	}
}
