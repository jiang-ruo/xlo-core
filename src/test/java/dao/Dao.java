package dao;

import xlo.database.dao.BaseDao;
import xlo.database.dao.CoreDao;
import xlo.database.dbc.ConnectDb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author XiaoLOrange
 * @time 2021.01.14
 * @title
 */

public class Dao extends BaseDao<Pojo> {

	public void doo(){
		String sql = "select * from log";
		List<Pojo> ls = super.QueryT_LL(sql);
		for (Pojo l: ls){
			System.out.println(l);
		}
	}

	@Override
	public Connection getConn() {
		return ConnectDb.getConn(ConnectDb.Oracle, ConnectDb.Oracle_link, "javatask", "qaz123456");
	}

	@Override
	public boolean closeConn(Connection conn) {
		try {
			conn.close();
			return true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
	}
}
