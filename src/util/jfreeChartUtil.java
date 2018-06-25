package util;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.TextAnchor;


public class jfreeChartUtil {
	public static Font defaultFont = new Font("宋体",Font.BOLD+Font.ITALIC,20);
	public static Color defaultBKColor = new Color(0xDD,0xEE,0xF2);
	 public static void saveFile(JFreeChart chart,String fileName,int width,int height) 
	   {
		   try {
				 OutputStream os;
				 os = new FileOutputStream(fileName);
				 try {
					 //使用一个面向application的工具类，将chart转换成JPEG格式的图片。第3个参数是宽度，第4个参数是高度。
					ChartUtilities.writeChartAsJPEG(os, chart, width, height);
					os.close();//关闭输出流
				 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			        
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//图片是文件格式的，故要用到FileOutputStream用来输出。
	   }
	   
	  
	   public static StandardChartTheme standardChartTheme()
	   {
		   StandardChartTheme standardChartTheme=null;  
	       
	       standardChartTheme=new StandardChartTheme("CN");  
		   //设置标题字体  
		   standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
		   //设置图例的字体  
		   standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
		   //设置轴向的字体  
		   standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
		   
		   return standardChartTheme;
	   }
	   public static void createPieTest()
	   {
		   DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
	       dpd.setValue("管理人员", 25);  //输入数据
	       dpd.setValue("市场人员", 25);
	       dpd.setValue("开发人员", 45);
	       dpd.setValue("其他人员", 10);
	       
	       JFreeChart chart = createPie(dpd, "拇指游玩人员组织数据图",standardChartTheme());
	      
			  // 图像属性部分
	       
	       saveFile(chart,"company.jpeg",400,300);
		      
	       ChartFrame chartFrame=new ChartFrame("拇指游玩人员组织数据图",chart); 
	       //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
	       chartFrame.pack(); //以合适的大小展现图形
	       chartFrame.setVisible(true);//图形是否可见
	   }
	   
	   
	   public static JFreeChart createPie(DefaultPieDataset dpd,String title,StandardChartTheme ChartTheme) 
	   {		
		   //应用主题样式  
		   ChartFactory.setChartTheme(ChartTheme); 
		
	       JFreeChart chart=ChartFactory.createPieChart(title,dpd,true,true,false); 
	       chart.setBackgroundPaint(defaultBKColor);
	       PiePlot pie = (PiePlot) chart.getPlot(); 
	       pie.setBackgroundPaint(defaultBKColor); 
	       //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
	       return chart;
	      
	   }
	   
	   public static void createBarTest()
	   {
		   DefaultCategoryDataset dataset=new DefaultCategoryDataset();
	       dataset.setValue(10,"","管理人员");
	       dataset.setValue(20,"","市场人员");
	       dataset.setValue(40,"","开发人员");
	       dataset.setValue(15,"","其他人员");
		   /*dataset.addValue(510, "深圳", "苹果");
		   dataset.addValue(320, "深圳", "香蕉");
		   dataset.addValue(580, "深圳", "橘子");
		   dataset.addValue(390, "深圳", "梨子");*/
	       JFreeChart chart = createBar(dataset,"某公司组织结构图", "人员数量","人员分布",standardChartTheme());
	       ChartFrame chartFrame=new ChartFrame("某公司组织结构图",chart); 
	       
	       //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
	       chartFrame.pack(); //以合适的大小展现图形
	       chartFrame.setVisible(true);//图形是否可见
	   }
	   
	   public static JFreeChart createBar(CategoryDataset dataset,String title,String ytitle,String xtitle,StandardChartTheme ChartTheme) //柱形报表
	   {
		   ChartFactory.setChartTheme(ChartTheme); 
		   
	       JFreeChart chart=ChartFactory.createBarChart("hi", xtitle, 
	    		   ytitle, dataset, PlotOrientation.VERTICAL, false, false, false); //创建一个JFreeChart
	       chart.setTitle(new TextTitle(title,defaultFont));//可以重新设置标题，替换“hi”标题
	       CategoryPlot plot=(CategoryPlot)chart.getPlot();//获得图标中间部分，即plot
	       CategoryAxis categoryAxis=plot.getDomainAxis();//获得横坐标
	       categoryAxis.setLabelFont(defaultFont);//设置横坐标字体
	       
		   chart.setBackgroundPaint(defaultBKColor);
		   plot.setBackgroundPaint(defaultBKColor); 
		      
	       //categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);//设置角度
	       return chart;
	   }
	   
	   public static void setTick(JFreeChart chart,double tick)
	   {
		   CategoryPlot plot = chart.getCategoryPlot();
	       NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	       double unit=tick;//0.1d;//刻度的长度
		   NumberTickUnit ntu= new NumberTickUnit(unit);
		   rangeAxis.setTickUnit(ntu);
	   }
	   
	   public static void setYFormat(JFreeChart chart,NumberFormat formatter)
	   {
		   CategoryPlot plot = chart.getCategoryPlot();
	       NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	       rangeAxis.setNumberFormatOverride(formatter);
	      
	   }
	   
	   //NumberFormat formatter
	   public static void createLineTest()
	   {
		   DebuUtil.log("createLineTest11111111111111111111");
		   DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	       dataset.setValue(0.90,"请求成功率","2015-03-01");//Y 数据，类型，X 数据
	       dataset.setValue(0.88,"请求成功率","2015-03-02");
	       dataset.setValue(0.70,"请求成功率","2015-03-03");
	       dataset.setValue(0.70,"请求成功率","2015-03-04");
	       dataset.setValue(0.70,"请求成功率","2015-03-05");
	       dataset.setValue(0.70,"请求成功率","2015-03-05");
	       dataset.setValue(0.80,"短信成功率","2015-03-01");//Y 数据，类型，X 数据
	       dataset.setValue(0.85,"短信成功率","2015-03-02");
	       dataset.setValue(0.70,"短信成功率","2015-03-03");
	       dataset.setValue(0.75,"短信成功率","2015-03-04");
	       dataset.setValue(0.70,"短信成功率","2015-03-05");
	       dataset.setValue(0.75,"短信成功率","2015-03-06");
	       DebuUtil.log("createLineTest11111111111111111111");
	       JFreeChart chart = createLine(dataset,"转化率", "","",standardChartTheme());
	       DebuUtil.log("createLineTest22222222222222222");
	       setTick(chart,0.1d);
	       setYFormat(chart,new DecimalFormat("0.00%"));	  
	       saveFile(chart,"company.jpeg",800,300);
	       DebuUtil.log("createLineTest333333333333333333333");
	       /*ChartFrame chartFrame=new ChartFrame("拇指游玩流水走势图",chart); 
	       
	       //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
	       chartFrame.pack(); //以合适的大小展现图形
	       chartFrame.setVisible(true);//图形是否可见*/
	   }
	   
	   public static void createLineByMap(String filepah)
	   {
		   DebuUtil.log("createLineTest11111111111111111111");
		   DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	       dataset.setValue(0.90,"请求成功率","2015-03-01");//Y 数据，类型，X 数据
	       dataset.setValue(0.88,"请求成功率","2015-03-02");
	       dataset.setValue(0.70,"请求成功率","2015-03-03");
	       dataset.setValue(0.70,"请求成功率","2015-03-04");
	       dataset.setValue(0.80,"短信成功率","2015-03-01");//Y 数据，类型，X 数据
	       dataset.setValue(0.85,"短信成功率","2015-03-02");
	       dataset.setValue(0.70,"短信成功率","2015-03-03");
	       dataset.setValue(0.75,"短信成功率","2015-03-04");
	       DebuUtil.log("createLineTest11111111111111111111");
	       JFreeChart chart = createLine(dataset,"转化率", "","",standardChartTheme());
	       DebuUtil.log("createLineTest22222222222222222");
	       setTick(chart,0.1d);
	       setYFormat(chart,new DecimalFormat("0.00%"));	  
	       saveFile(chart,filepah,400,300);
	       DebuUtil.log("createLineTest333333333333333333333");
	       /*ChartFrame chartFrame=new ChartFrame("拇指游玩流水走势图",chart); 
	       
	       //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
	       chartFrame.pack(); //以合适的大小展现图形
	       chartFrame.setVisible(true);//图形是否可见*/
	   }
	   
	   public static void fillDataset(DefaultCategoryDataset dataset,java.util.Map map,String type)
	   {
		   Iterator it =  map.entrySet().iterator();
		    while(it.hasNext())
		    {          
		          Entry  obj = (Entry) it.next();
		          //Float.valueOf((String)obj.getValue())
		          float value = Float.valueOf((String)obj.getValue());
		          dataset.setValue(value,type,(String)obj.getKey());             
		     }
	   }
	   
	   public static JFreeChart createLine(DefaultCategoryDataset dataset,String title,String ytitle,String xtitle,StandardChartTheme ChartTheme)
	   {
		   
		   ChartFactory.setChartTheme(ChartTheme);
		   
           //定义图标对象
		  JFreeChart chart = ChartFactory.createLineChart(null,// 报表题目，字符串类型
				  xtitle,//"采集时间", // 横轴xtitle
				  ytitle,//"血糖值", // 纵轴 ytitle
		                dataset, // 获得数据集
		                PlotOrientation.VERTICAL, // 图标方向垂直
		                true, // 显示图例
		                true, // 不用生成工具
		                false // 不用生成URL地址
		                );
		  //整个大的框架属于chart  可以设置chart的背景颜色
		  chart.setTitle(new TextTitle(title,defaultFont));//可以重新设置标题，替换“hi”标题
		  // 生成图形
		  CategoryPlot plot = chart.getCategoryPlot();
		  chart.setBackgroundPaint(defaultBKColor);
	      plot.setBackgroundPaint(defaultBKColor); 
	       
		  // 图像属性部分
		  /*plot.setBackgroundPaint(Color.white);
		  plot.setDomainGridlinesVisible(true);  //设置背景网格线是否可见
		  plot.setDomainGridlinePaint(Color.BLACK); //设置背景网格线颜色
		  plot.setRangeGridlinePaint(Color.GRAY);
		  plot.setNoDataMessage("没有数据");//没有数据时显示的文字说明。 
		  */
		  // 数据轴属性部分
		  NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		  rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		  //double unit=0.1d;//刻度的长度
		 // NumberTickUnit ntu= new NumberTickUnit(unit);
		  //rangeAxis.setTickUnit(ntu);
		  
		  rangeAxis.setAutoRangeIncludesZero(true); //自动生成
		  rangeAxis.setUpperMargin(0.20);
		  rangeAxis.setLabelAngle(Math.PI/2.0); 
		  //rangeAxis.setAutoRange(false);
		  //rangeAxis.setNumberFormatOverride(new DecimalFormat("0.00%"));
		  //XYPlot xyplot = (XYPlot)chart.getPlot();
		  
		  //((NumberAxis) ((XYPlot)chart.getPlot()).getRangeAxis()).setNumberFormatOverride(new DecimalFormat("0"));
		  // 数据渲染部分 主要是对折线做操作
		  LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
		                .getRenderer();
		  renderer.setBaseItemLabelsVisible(true);
		  renderer.setSeriesPaint(0, Color.black);//设置折线的颜色
		  renderer.setBaseShapesFilled(true);
		  renderer.setBaseItemLabelsVisible(false);//是否显示当然值     
		  renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		  renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  

		  renderer.setBaseItemLabelFont(defaultFont);  //设置提示折点数据形状
		  plot.setRenderer(renderer);
		  //区域渲染部分
		  /*double lowpress = 4.5; 
          double uperpress = 8;   //设定正常血糖值的范围
          IntervalMarker inter = new IntervalMarker(lowpress, uperpress);  
          inter.setLabelOffsetType(LengthAdjustmentType.EXPAND); //  范围调整——扩张
          inter.setPaint(Color.LIGHT_GRAY);// 域顏色  
      
          inter.setLabelFont(new Font("SansSerif", 41, 14));  
          inter.setLabelPaint(Color.RED);  
          inter.setLabel("正常血糖值范围");    //设定区域说明文字
          plot.addRangeMarker(inter,Layer.BACKGROUND);  //添加mark到图形   BACKGROUND使得数据折线在区域的前端
          */
         return chart;
    }
}
