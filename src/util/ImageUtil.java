package util;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	//得到图片的宽度和高度,适用于jpg ,gif ,png ,bmp
	public static int[] getSize(String fromFile) {
		int[] result = new int[] { 0, 0 };
		try {
			Image src = javax.imageio.ImageIO.read(new File(fromFile));
			result[0]=src.getWidth(null);
			result[1]=src.getHeight(null);
		} catch (Exception e) {
			System.out.println("ImageUtil.getSize error:" + e);
			System.out.println("fromFile:" + fromFile);
		}
		return result;
	}

	//根据输出高度和宽度的限制得出当前图片最好显示效果的尺寸
	public static int[] getScaleSize(String fromFile, int maxWidth, int maxHeight) {
		int[] result = new int[] { 0, 0 };
		try {
			int[] tempArr = getSize(fromFile);
			int width=tempArr[0];
			int height=tempArr[1];
			double radio = 0.0;
			if (width > height) {
				
				radio = (double)maxWidth / width;
			} else if (width < height) {
				radio = (double)maxHeight / height;
			} else if (width == height) {
				if(maxWidth>maxHeight){
					radio = (double)maxWidth/width;
				}
				else if(maxWidth<maxHeight){
					
				}
				else{

				}
			}
			result[0] = (int) (width * radio);
			result[1] = (int) (height * radio);
		} catch (Exception e) {
			System.out.println("ImageUtil.getScaleSize error:" + e);
			System.out.println("fromFile:" + fromFile+"   maxWidth:"+maxWidth+"   maxHeight:"+maxHeight);
		}
		return result;
	}

	// 倒转图像
	public static void transform(String fromFile, String toFile) {
		try {
			Image src = javax.imageio.ImageIO.read(new File(fromFile));
			int width=src.getWidth(null);
			int height=src.getHeight(null);
			BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.dispose();
			AffineTransform transform = new AffineTransform(1, 0, 0, -1, 0, height - 1);
			BufferedImageOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			BufferedImage filteredImage = new BufferedImage(width,height, image.getType());
			op.filter(image, filteredImage);
			ImageIO.write(filteredImage, "JPEG", new File(toFile));
		} catch (Exception e) {
			System.out.println("ImageUtil.transform error:" + e);
			System.out.println("fromFile:" + fromFile + "" + "   toFile:" + toFile);
		}
	}

	public static void resize(String fromFile, String toFile, int width, int height) {
		try {
            File srcfile = new File(fromFile);   
            if (!srcfile.exists()) {   
                return;   
            }   
            Image src = ImageIO.read(srcfile);   
            BufferedImage tag = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);    
            tag.getGraphics().drawImage(   src.getScaledInstance(width, height,Image.SCALE_SMOOTH), 0, 0, null);   
            FileOutputStream out = new FileOutputStream(toFile);   
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
            encoder.encode(tag);   
            out.close();   

		} catch (Exception e) {
			System.out.println("ImageUtil.resize error:" + e);
			System.out.println("fromFile:" + fromFile + "" + "   toFile:" + toFile + "   width:" + width+"   height:"+height);
		}
	}
	
	//按照比例缩放图片
	public static void zoom(String fromFile, String toFile, float factor) {
		try {
			Image src = javax.imageio.ImageIO.read(new File(fromFile));
			int width=src.getWidth(null);
			int height=src.getHeight(null);
			BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.dispose();
			AffineTransform transform = AffineTransform.getScaleInstance(factor, factor);
			BufferedImageOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			BufferedImage filteredImage = new BufferedImage((int) (width * factor), (int) (height*factor), image.getType());
			op.filter(image, filteredImage);
			ImageIO.write(filteredImage, "JPEG", new File(toFile));
		} catch (Exception e) {
			System.out.println("ImageUtil.zoom error:" + e);
			System.out.println("fromFile:" + fromFile + "" + "   toFile:" + toFile + "   factor:" + factor);
		}
	}

	/*******************************************************************************  
	 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法  
	 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))  
	 */  
    public static boolean zoom(String fromFile, String toFile,int width, int height, boolean gp) {    
        try {    
            //获得源文件    
            File file = new File(fromFile);    
            if (!file.exists()) { 
            	System.out.println("ImageUtil.zoom file not exsits,fromFile:" + fromFile); 
                return false;    
            }
            //如果文件夹不存在就创建
            FileUtil.buildPath(toFile);
            Image img = ImageIO.read(file);    
            // 判断图片格式是否正确    
            if (img.getWidth(null) == -1) {   
                System.out.println(" can't read,retry!" + "<BR>");    
                return false;    
            } else {    
                int newWidth; int newHeight;    
                // 判断是否是等比缩放    
                if (gp == true) {    
                    // 为等比缩放计算输出的图片宽度及高度    
                    double rate1 = ((double) img.getWidth(null)) / (double) width;    
                    double rate2 = ((double) img.getHeight(null)) / (double) height;    
                    // 根据缩放比率大的进行缩放控制    
                    double rate = rate1 > rate2 ? rate1 : rate2;    
                    newWidth = (int) (((double) img.getWidth(null)) / rate);    
                    newHeight = (int) (((double) img.getHeight(null)) / rate);    
                } else {    
                    newWidth = width; // 输出的图片宽度    
                    newHeight = height; // 输出的图片高度    
                }    
               BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);    
                  
               /*  
                * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的  
                * 优先级比速度高 生成的图片质量比较好 但速度慢  
                */    
               tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);   
               FileOutputStream out = new FileOutputStream(toFile);   
               // JPEGImageEncoder可适用于其他图片类型的转换    
               JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
               encoder.encode(tag);    
               out.close();    
            }    
        } catch (IOException ex) {    
        	System.out.println("ImageUtil.zoom error:" + ex);
			System.out.println("fromFile:" + fromFile + "" + "   toFile:" + toFile);   
        }    
        return true;    
   }
	
	// 按照输入的坐标裁剪图片
	public static void cut(String fromFile, String toFile, int[] points) {
		for (int point : points) {
			if (point < 0) {
				System.out.println("ImageUtil.cut error:points<0");
				return;
			}
		}
		String format = fromFile.substring(fromFile.lastIndexOf(".") + 1);
		FileInputStream fileInputStream = null;
		ImageInputStream imageInputStream = null;
		try {
			fileInputStream = new FileInputStream(fromFile);
			Iterator it = ImageIO.getImageReadersByFormatName(format);
			ImageReader reader = (ImageReader) it.next();
			imageInputStream = ImageIO.createImageInputStream(fileInputStream);
			reader.setInput(imageInputStream, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(points[0], points[1], points[2], points[3]);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, format, new File(toFile));
		} catch (IOException e) {
			System.out.println("ImageUtil.cut error:" + e);
			System.out.println("fromFile:" + fromFile + "" + "   toFile:" + toFile);
			azul.JspUtil.p(points);
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
				if (imageInputStream != null)
					imageInputStream.close();
			} catch (Exception e) {
				System.out.println("ImageUtil.cut close Stream error:" + e);
			}
		}
	}

	// 按照输入的坐标裁剪图片
	public static void cut(String fromFile, String toFile, String[] tempPoints) {
		int[] points=new int[4];
		for (int i = 0; i < tempPoints.length; i++) {
			points[i]=Integer.valueOf(tempPoints[i]);
		}
		String format = fromFile.substring(fromFile.lastIndexOf(".") + 1);
		FileInputStream fileInputStream = null;
		ImageInputStream imageInputStream = null;
		try {
			fileInputStream = new FileInputStream(fromFile);
			Iterator it = ImageIO.getImageReadersByFormatName(format);
			ImageReader reader = (ImageReader) it.next();
			imageInputStream = ImageIO.createImageInputStream(fileInputStream);
			reader.setInput(imageInputStream, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(points[0], points[1], points[2], points[3]);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, format, new File(toFile));
		} catch (IOException e) {
			System.out.println("ImageUtil.cut error:" + e);
			System.out.println("fromFile:" + fromFile + "" + "   toFile:" + toFile);
			azul.JspUtil.p(points);
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
				if (imageInputStream != null)
					imageInputStream.close();
			} catch (Exception e) {
				System.out.println("ImageUtil.cut close Stream error:" + e);
			}
		}
	}
	
	public static void imageMark(String markImg, String fromFile, String toFile, int x, int y) {
		FileOutputStream out = null;
		try {
			File file = new File(fromFile);
			Image src = ImageIO.read(file);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);

			// 水印文件
			File pressFile = new File(markImg);
			Image press_src = ImageIO.read(pressFile);
			int press_width = press_src.getWidth(null);
			int press_height = press_src.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
			g.drawImage(press_src, width - press_width - x, height - press_height - y, press_width, press_height, null);
			g.dispose();
			out = new FileOutputStream(fromFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			ImageIO.write(image, "JPEG", new File(toFile));
		} catch (Exception e) {
			System.out.println("ImageUtil.imageMark error:" + e);
			System.out.println("markImg:" + markImg + "   fromFile:" + fromFile + "" + "   toFile:" + toFile + "   x:" + x + "   y:" + y);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	//将输入文件夹下所有图片按照比例生成缩略图，位置在原文件夹下的ThumbDB目录，名称与原图片一致
	public static void getScalePic(String path,int width,int height){
		File[] list=FileUtil.getFiles(path,"jpg,gif,bmp");
		for (int i = 0; i < list.length; i++) {
			System.out.println(list[i].getPath());
			zoom(list[i].getPath(),list[i].getParentFile().getPath()+"\\ThumbDB\\"+list[i].getName(),width,height,true);
		}
	}
	
	public static void main(String[] args) {
		//int[] arr=iu.getImgSize("c:\\1.jpg");
		//int[] arr=getImgSize("D:\\web\\111.bmp");
		//System.out.println(arr[0]+"  "+arr[1]);
		String a = "c:\\b.jpg";
		String b = "c:\\b.gif";
		String c = "c:\\c.png";
		String d = "c:\\d.bmp";
		String o = "c:\\ooo.jpg";
		//azul.JspUtil.p(getImgSize(d));
		//System.out.println(getScaleHeight(a,300));
		//azul.JspUtil.p("111111111111111");
		//azul.JspUtil.p(getScaleHeight(d,300));
		//zoom(a, "c:\\o.jpg",1.5f);
		//azul.JspUtil.p(getScaleSize(a,300,300));
		//azul.JspUtil.p(getScaleSize(b,300,300));
		//azul.JspUtil.p(getScaleSize(c,300,300));
		//azul.JspUtil.p(getScaleSize(d,300,300));
		//zoom(a,o,500,500,false);
		//imageMark("c:\\o.gif",d,o,10,10);
		//zoom(a,o,500,500,true);
		//resize(a,"c:\\ooo.jpg",400,300);
		//resize(b,"c:\\ooo.gif",400,300);
		//resize(c,"c:\\ooo.png",400,300);
		//resize(d,"c:\\ooo.bmp",400,300);
		//cut(a,"c:\\o.jpg",new int[]{100,100,200,200});
		//cut(b,"c:\\o.gif",new int[]{100,100,200,200});
		//cut(c,"c:\\o.png",new int[]{100,100,200,200});
		//cut(d,"c:\\o.bmp",new int[]{100,100,200,200});
		getScalePic("E:\\workspace\\dingxue\\ninxia\\",72,48);
		System.out.println("ok");
		
		//System.out.println(iu.getScaleHeight("E:\\tomcat5\\webapps\\solaradmin\\images\\2008052223595462.jpg",400));
	}

}
