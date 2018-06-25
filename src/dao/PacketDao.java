package dao;

import java.util.ArrayList;
import java.util.List;

import model.Gamepack;
import model.Packet;
import model.PacketMana;
import azul.BaseDao;

public class PacketDao extends BaseDao {

	public PacketDao(){
		init();
	}
	
	public ArrayList getList(int pageno,int pagesize,String pageSql){
		ArrayList list = null;
		String sql = pageSql+" limit "+(pageno-1)*pagesize +","+ pagesize;
		System.out.println(sql);
		ArrayList<ArrayList> list1 = getObjectList(sql);
		
		System.out.println("结果数据："+list1.size());
		
		if (list1.size()>0) {
			list = new ArrayList();
			for (ArrayList object : list1) {
				PacketMana pMana = new PacketMana();
				pMana.setId((Integer) object.get(0));
				pMana.setFileName((String) object.get(1));
				pMana.setPacketId((String) object.get(2));
				pMana.setGameName((String) object.get(3));
				pMana.setPreVersion((String) object.get(4));
				pMana.setNewVersion((String) object.get(5));
				pMana.setDUrl((String) object.get(6));
				list.add(pMana);
			}
		}
		
		return list;
	}
	
	public boolean checkByPacketId(String packetId) {
		boolean ret = false;
		Packet packet = null;
		packet = getRecordByPackId(packetId);
		if(packet!=null)
			ret = true;
		return ret;
	}
	
	public Packet getRecordByPackId(String packetId){
		Packet packet = null;
		String sql = String.format(
				"select * from %s where packet_id='%s'",
				baseTableName, packetId);
		List list = getList(sql);
		if (list.size() > 0) {
			packet = (Packet) list.get(0);
		}
		
		return packet;
	}
}
