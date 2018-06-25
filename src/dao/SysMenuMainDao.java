package dao;

import azul.CacheDao;

public class SysMenuMainDao extends CacheDao {

	private static final long serialVersionUID = 6771591998751487653L;
	public String msg = "";
	public SysMenuMainDao() {
		init();
	}
	public String sort(String op, int id, int sort) {
		msg = "-1";
		String sql = "";
		String sql2 = "";
		try {
			if (op.equals("up")) {
				sql = "update sys_menu_main set sort=sort+1 where sort=" + (sort - 1);
				sql2 = "update sys_menu_main set sort=sort-1 where sys_menu_main_id=" + id;
			} else {
				sql = "update sys_menu_main set sort=sort-1 where sort=" + (sort + 1);
				sql2 = "update sys_menu_main set sort=sort+1 where sys_menu_main_id=" + id;
			}
			int bln = executeUpdate(sql);
			if (bln == 1) {
				bln = executeUpdate(sql2);
				if (bln == 1) {
					msg = "1";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(sql);
			System.err.println(sql2);
		}
		return msg;
	}
}