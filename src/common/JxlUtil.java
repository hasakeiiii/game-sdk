package common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

//����jxl����Execl
public class JxlUtil {

	public JxlUtil() {

	}

	// ��excel���ݶ���map�ṹ��
	public HashMap<String, ArrayList> readExcel(String fileName) {
		HashMap<String, ArrayList> map = null;
		Workbook rwb = null;
		try {
			rwb = Workbook.getWorkbook(new File(fileName));
			Sheet[] sheets = rwb.getSheets();
			// �Ѷ�ȡ����sheetҳ,����sheetName������ŵ�map��
			map = new HashMap<String, ArrayList>();
			for (int i = 0; i < sheets.length; i++) {
				Sheet rs = sheets[i];
				String sheetName = rs.getName();
				// System.out.println("sheetName:" + sheetName);
				// ȡ������,����
				int row = rs.getRows();
				if (row > 0) {
					int col = rs.getColumns();
					// System.out.println("row:" + row);
					ArrayList<String> list = new ArrayList<String>();
					for (int j = 0; j < row; j++) {
						// ÿһ����¼�洢Ϊһ��List����,ÿ�������������ַ�"ZvZ"�����?
						//String textStr = (j + 1) + "ZvZ";
						StringBuffer textStr = new StringBuffer("");
						for (int k = 0; k < col; k++) {
							String text = rs.getCell(k, j).getContents().trim();
							textStr.append(text);
						}
						//��ȡ�����һ���ָ���?
						String temp=textStr.toString();
						if(!"".equals(temp)){
							temp=temp.substring(0,temp.length()-3);
						}
						list.add(temp);
						// System.out.println(textStr);
					}
					map.put(sheetName, list);
				}
			}
		} catch (Exception e) {
			System.out.println("JxlUtil.readExcel error:" + e);
		} finally {
			try {
				if (rwb != null)
					rwb.close();
			} catch (Exception e) {
				System.out.println("JxlUtil.readExcel.rwb close error:" + e);
			}
		}
		return map;
	}

	// ��map�ṹ������д��excel
	@SuppressWarnings("unchecked")
	public boolean writeExcel(String fileName, HashMap<String, ArrayList> map) {
		boolean result = false;
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(new File(fileName));
			// Method 2����WritableWorkbookֱ��д�뵽�����?
			// OutputStream os = new FileOutputStream(targetfile);
			// jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
			int sheetNo = 0;
			Iterator<Entry<String, ArrayList>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String,ArrayList> entry = it.next(); 
				String sheetName = entry.getKey(); // �õ�sheetName
				ArrayList<String> list = entry.getValue();
				WritableSheet ws = wwb.createSheet(sheetName, sheetNo);
				int m = 0;
				for (String text : list) {
					// System.out.println(text);
					String[] temp = azul.JspUtil.split(text,"ZvZ");
					// ѭ����n��0��ʼ��д����,��1��ʼ������ԭʼ�ļ�д��
					for (int n = 1; n < temp.length; n++) {
						// ���Label����,��Ԫ��λ����(column, row)�������±궼�Ǵ�0��ʼ
						Label label = new Label(n - 1, m, temp[n]);
						ws.addCell(label);
					}
					// д����
					// for (int n = 0; n < temp.length; n++) {
					// Label label = new Label(n, m, temp[n]);
					// ws.addCell(label);
					// }
					m++;
				}
				sheetNo++;
			}
			wwb.write();
			result = true;
		} catch (Exception e) {
			System.out.println("JxlUtil.writeExcel error:" + e);
		} finally {
			try {
				if (wwb != null)
					wwb.close();
			} catch (Exception e) {
				System.out.println("JxlUtil.writeExcel.wwb close error:" + e);
			}
		}
		return result;
	}

	// �༭excel�ļ�,��Ҫ�޸ĵ�������map��ʽ����,����ΪsheetName,����list��ʽΪ:rowZvZcolZvZtext��ʽ
	@SuppressWarnings("unchecked")
	public boolean editExcel(String fileName, HashMap<String, ArrayList> map) {
		boolean result = false;
		Workbook rw = null;
		WritableWorkbook wwb = null;
		try {
			// ����ֻ����Excel�������Ķ���
			rw = jxl.Workbook.getWorkbook(new File(fileName));
			// ������д���Excel����������
			wwb = Workbook.createWorkbook(new File(fileName), rw);
			/*�򵥵��޸�
			// ��ȡ��һ�Ź�����
			jxl.write.WritableSheet ws = wwb.getSheet(0);
			// ��õ�һ����Ԫ�����
			jxl.write.WritableCell wc = ws.getWritableCell(0, 0);
			// �жϵ�Ԫ�������? ������Ӧ��ת��
			if (wc.getType() == CellType.LABEL) {
				Label label = (Label) wc;
				label.setString("The value has been modified.");
			}
			*/
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				String sheetName = (String) it.next(); // �õ�sheetName
//				 System.out.println(keyId);
//				 ��õ�Ԫ�����
				jxl.write.WritableSheet ws = wwb.getSheet(sheetName);
				ArrayList<String> list = (ArrayList<String>) map.get(sheetName);
				for (String text : list) {
					// System.out.println(text);
					String[] temp = azul.JspUtil.split(text,"ZvZ");
					// ѭ����n��0��ʼ��д����,��1��ʼ������ԭʼ�ļ�д��
					for (int n = 0; n < temp.length; n++) {
						jxl.write.WritableCell wc = ws.getWritableCell(Integer.valueOf(temp[1]), Integer.valueOf(temp[0]));
						// ���Label����,��Ԫ��λ����(column, row)�������±궼�Ǵ�0��ʼ
						Label label = (Label) wc;
						label.setString(temp[2]);
					}
				}
			}
			wwb.write();
			result = true;
		} catch (Exception e) {
			System.out.println("JxlUtil.editExcel error:" + e);
		} finally {
			try {
				if (rw != null)
					rw.close();
				if (wwb != null)
					wwb.close();
			} catch (Exception e) {
				System.out.println("JxlUtil.editExcel.wwb close error:" + e);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public void getText(HashMap map) {
		Iterator<Entry<String, ArrayList>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String,ArrayList> entry = it.next(); 
			ArrayList<String> list = entry.getValue();
			for (String text : list) {
				System.out.println(text);
			}
		}
	}

	public static void main(String[] args) {
		JxlUtil ju = new JxlUtil();
		HashMap map=ju.readExcel("c:/AAA.xls");
		Iterator<Entry<String, ArrayList>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String,ArrayList> entry = it.next();  
			String keyId = entry.getKey();
			//System.out.println(keyId);
			ArrayList<String> list = entry.getValue();
			for (String text : list) {
				System.out.println(text);
			}
		}
		//ju.writeExcel("d:/1.xls", ju.readExcel("d:/book1.xls"));
		//System.out.println(ju.editExcel("d:/1.xls",new HashMap()));
	}

}
