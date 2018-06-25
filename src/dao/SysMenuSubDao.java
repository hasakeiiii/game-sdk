package dao;

import azul.CacheDao;

public class SysMenuSubDao extends CacheDao {
	private static final long serialVersionUID = -6102680630045997024L;
	public String msg = "";
	public SysMenuSubDao() {
		init();
	}
	public String sort(String op,int sys_menu_main_id, int id, int sort) {
		msg = "-1";
		String sql = "";
		String sql2 = "";
		try {
			if (op.equals("up")) {
				sql = "update sys_menu_sub set sort=sort+1 where sort=" + (sort - 1)+" and sys_menu_main_id="+sys_menu_main_id;
				sql2 = "update sys_menu_sub set sort=sort-1 where sys_menu_sub_id=" + id+" and sys_menu_main_id="+sys_menu_main_id;
			} else {
				sql = "update sys_menu_sub set sort=sort-1 where sort=" + (sort + 1)+" and sys_menu_main_id="+sys_menu_main_id;
				sql2 = "update sys_menu_sub set sort=sort+1 where sys_menu_sub_id=" + id+" and sys_menu_main_id="+sys_menu_main_id;
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