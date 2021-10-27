package xlo.database.dao;

import xlo.database.annotation.Orm;

/**
 * @author XiaoLOrange
 * @time 2021.01.14
 * @title
 */

public class Pojo {

	@Orm("log_id")
	private int sd;
	private String log_time;

	@Override
	public String toString() {
		return sd + ":" + log_time;
	}
}
