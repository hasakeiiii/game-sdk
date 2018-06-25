package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;


public class ExcelUtil {
	
	public static void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
	//	List<ArrayList> data = (List)request.getAttribute("map");
		HSSFWorkbook wb = writeXLS(0, data);
		FileOutputStream fileOut;
		String path = "E:\\";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String filename = File.separator + "数据查询".concat(sdf.format(new Date())).concat(".xls");
		try {
			String target = path.concat(filename);
			fileOut = new FileOutputStream(target);
			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(filename);
	}

	public static HSSFWorkbook writeXLS(int sheetNum, List<ArrayList> data) {
		HSSFWorkbook wb = new HSSFWorkbook();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i <= sheetNum; i++) {

			// 根据ls的值些不同的shett页
			if (i == 0) {

				HSSFSheet sheet1 = wb.createSheet("sheet1");
				sheet1.createRow(0);
				sheet1.createRow(1);
				HSSFRow rowtitle21 = sheet1.getRow(1);
				String[] titles = { "日期", "新增用户 ", "活跃用户", "次日留存率", "7日留存率", "付费用户", "付费次数",
						"付费金额", "付费转化率", "付费成功率", "ARPPU", "激活ARPU", "人均付费次数", "人均会话时长" };
				int count = titles.length;
				for(int m = 0; m < count; m++)
				{
					sheet1.setColumnWidth(m, 2000);
				}
				
				HSSFCellStyle style = wb.createCellStyle();
				HSSFFont font = wb.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
				font.setFontHeightInPoints((short) 16);
				style.setWrapText(true);
				style.setFont(font);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框   
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				
				sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
//				String titleContent="涉外营业性演出审批基本信息表";
				HSSFRow rowtitle = sheet1.getRow(0);
				rowtitle.createCell(0);
				rowtitle.setHeightInPoints(25);
				rowtitle.getCell(0).setCellStyle(style);
//				rowtitle.getCell(0).setCellValue(titleContent);
				
			
				HSSFCell[] celtitle20 = new HSSFCell[count];

				for (int j = 0; j < count; j++) {
					rowtitle21.createCell(j);
				}
				for (int k = 0; k < count; k++) {
					celtitle20[k] = rowtitle21.getCell(k);
					celtitle20[k].setCellStyle(style);
					celtitle20[k].setCellValue(titles[k]);
				}
				/*
				 * 填写内容 1、获取要写内容的行 2、创建单元格 3、向单元格写内容
				 */
				for (int s = 0; s< data.size(); s++) {
					sheet1.createRow(2+s);
					HSSFRow rowtitletemp = sheet1.getRow(2+s);
					for (int j = 0; j < count; j++) {
						rowtitletemp.createCell(j);
					}
					HSSFCell[] celcontenttemp = new HSSFCell[count];
//					Iterator<ArrayList> it = data.iterator();
					
				for(int m = 0; m < data.size(); m++){
					for (int j = 0; j < count; j++) {
						celcontenttemp[j] = rowtitletemp.getCell(j);
						HSSFCellStyle style2 = wb.createCellStyle();
						HSSFFont font2 = wb.createFont();
						font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
						font2.setFontHeightInPoints((short) 12);
						style2.setWrapText(true);
						style2.setFont(font2);
						style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
						style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
						style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
						style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框   
						style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
						celcontenttemp[j].setCellStyle(style2);
						System.out.print(data.get(m).get(j));
						celcontenttemp[j].setCellValue(data.get(m).get(j).toString());

					}
				}
				}
			} 

		}

		return wb;
	}

}
