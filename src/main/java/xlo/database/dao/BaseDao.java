package xlo.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2020.11.26 BaseDao
 * @time 2021.01.14 因为每次执行sql使用的是不同的Connection，所以无法用于事务
 * @title
 */

public abstract class BaseDao<T> extends CoreDao<T>{



	/**
	 * 当调用不需要传入Connection的方法时，该方法会从getConn方法中获取一个连接
	 * @return
	 */
	public abstract Connection getConn();

	/**
	 * 调用完getConn方法后会调用closeConn方法关闭连接
	 * @param conn
	 * @return
	 */
	public abstract boolean closeConn(Connection conn);


	/**
	 * 执行sql语句
	 * @param sql
	 * @param params 向数据库中传参
	 * @return 成功受影响的数据条数
	 */
	public int Update(String sql, Object... params){
		Connection conn = getConn();

		int result = Update(conn, sql, params);

		closeConn(conn);
		return result;
	}

	/**
	 * 单返回值查询，查询后的结果仅有1行1列。
	 * @param sql
	 * @param params
	 * @return 返回Object对象
	 */
	public Object SingleValueQuery(String sql, Object... params) {
		Connection conn = getConn();
		if(conn == null) throw new NullPointerException();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object obj = null;
		try {
			pstmt = conn.prepareStatement(sql);
			inputParam(pstmt, params);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				obj = rs.getObject(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				closeConn(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return obj;
	}



	/**
	 * 查询
	 * @param sql
	 * @param params 向数据库中传参
	 * @return 返回结果集ArrayList<Map<String, Object>>
	 * ArrayList<行>
	 * Map<字段名,对应的值>
	 */
	public ArrayList<Map<String, Object>> QueryM(String sql, Object... params) {
		return (ArrayList<Map<String, Object>>) SupportedQueryM(ArrayList.class, sql, params);
	}

	/**
	 * 查询
	 * @param sql
	 * @param params
	 * @return 返回结果集ArrayList<T>
	 */
	public ArrayList<T> QueryT(String sql, Object... params){
		return (ArrayList) SupportedQueryT(null, ArrayList.class, sql, params);
	}

	/**
	 * 查询
	 * @param <E> 泛型
	 * @param claee 返回类型
	 * @param sql
	 * @param params
	 * @return
	 */
	public <E> ArrayList<E> QueryT(Class claee, String sql, Object... params){
		return (ArrayList) SupportedQueryT(claee, ArrayList.class, sql, params);
	}

	/**
	 * 查询
	 * @param sql
	 * @param params 向数据库中传参
	 * @return 返回结果集LinkedList<Map<String, Object>>
	 * LinkedList<行>
	 * Map<字段名,对应的值>
	 */
	public LinkedList<Map<String, Object>> QueryM_LL(String sql, Object... params) {
		return (LinkedList<Map<String, Object>>) SupportedQueryM(LinkedList.class, sql, params);
	}

	/**
	 * 查询
	 * @param sql
	 * @param params
	 * @return 返回结果集LinkedList<T>
	 */
	public LinkedList<T> QueryT_LL(String sql, Object... params){
		return (LinkedList) SupportedQueryT(null, LinkedList.class, sql, params);
	}

	/**
	 * 查询
	 * @param <E> 泛型
	 * @param claee 返回类型
	 * @param sql
	 * @param params
	 * @return
	 */
	public <E> LinkedList<E> QueryT_LL(Class claee, String sql, Object... params){
		return (LinkedList) SupportedQueryT(claee, LinkedList.class, sql, params);
	}

	/**
	 * 将Query方法共用的代码放到该方法中，作为补充。
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> SupportedQueryM(Class clazz, String sql, Object... params){
		Connection conn = getConn();

		List<Map<String, Object>> rows = SupportedQueryM(conn, clazz, sql, params);

		closeConn(conn);

		return rows;
	}

	/**
	 * 将Query方法共用的代码放到该方法中，作为补充。
	 * @param claee 返回值类型，为空则返回泛型T
	 * @param clazz 输出的容器类型：arraylist或linkedlist
	 * @param sql
	 * @param params
	 * @return
	 */
	List SupportedQueryT(Class claee, Class clazz, String sql, Object... params){
		Connection conn = getConn();

		List rows = SupportedQueryT(conn, claee, clazz, sql, params);

		closeConn(conn);

		return rows;
	}

}
