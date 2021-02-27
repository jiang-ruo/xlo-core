package xlo.database.dao;

import xlo.database.dbc.ConnectDb;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author XiaoLOrange
 * @time 2021.01.19
 * @title
 */

public class QuickDao<T> extends BaseDao<T> {

	private String driver;
	private String link;
	private String user;
	private String pwd;

	public QuickDao(String driver, String link, String user, String pwd){
		this.driver = driver;
		this.link = link;
		this.user = user;
		this.pwd = pwd;
	}

	@Override
	public Connection getConn() {
		return ConnectDb.getConn(driver, link, user, pwd);
	}

	@Override
	public boolean closeConn(Connection conn) {
		try {
			conn.close();
			return true;
		} catch (SQLException throwables) {
//			throwables.printStackTrace();
			return false;
		}
	}
}
