package model;

public class FilterCell implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5141110087749857079L;
	public Integer id;
	public Integer mcc;
	public Integer mnc;
	public Integer cell_id;
	public Integer lac;
	public String addr;
	public Integer bfilte;
	
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setBfilte(Integer bfilte){
		this.bfilte=bfilte;
	}
	public Integer getBfilte(){
		return this.bfilte;
	}
	
	public void setMcc(Integer mcc){
		this.mcc = mcc;
	}
	public Integer getMcc(){
		return this.mcc;
	}
	
	public void setMnc(Integer mnc){
		this.mnc = mnc;
	}
	public Integer getMnc(){
		return this.mnc;
	}
	
	public void setCellId(Integer cell_id){
		this.cell_id = cell_id;
	}
	public Integer getCellId(){
		return this.cell_id;
	}
	
	public void setLac(Integer lac){
		this.lac = lac;
	}
	public Integer getLac(){
		return this.lac;
	}

	public void setAddr(String addr){
		this.addr=addr;
	}
	public String getAddr( ){
		return this.addr;
	}
}

