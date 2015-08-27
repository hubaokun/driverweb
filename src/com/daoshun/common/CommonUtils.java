/**
 * 
 */
package com.daoshun.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author wangcl
 * 
 */
public class CommonUtils {

	/**
	 * 读取配置文件
	 */
	public static Properties properties = new Properties();
	static {
		try {
			String path = "config.properties";
			InputStream inStream = CommonUtils.class.getClassLoader().getResourceAsStream(path);
			if (inStream == null) {
				inStream = CommonUtils.class.getClassLoader().getResourceAsStream("/" + path);
			}
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 文件访问路径
	public static String getFileRootUrl() {
		return properties.getProperty("fileRootUrl");
	}

	public static String getLocationPath() {
		return properties.getProperty("uploadFilePath");
	}

	public static String getWebRootUrl() {
		return properties.getProperty("webRootUrl");
	}

	public static String getAliSet() {
		return properties.getProperty("aliset");
	}

	/**
	 * 检查路径是否存在，不存在则创建路径
	 * 
	 * @param path
	 */
	public static void checkPath(String path) {
		String[] paths = null;
		if (path.contains("\\")) {
			path=path.replace("/", "\\");
			paths = path.split(File.separator + File.separator);
		} else {
			paths = path.split(File.separator);
		}
		
		if (paths == null || paths.length == 0) {
			return;
		}
		String pathdir = "";
		for (String string : paths) {
			pathdir = pathdir + string + File.separator;
			File file = new File(pathdir);
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}

	/**
	 * 判断参数是否为空
	 * 
	 */
	public static void validateEmpty(String value) throws ErrException {
		if (value == null || value.length() == 0) {
			System.out.println("参数为空");
			throw new ErrException();
		}
	}
	/**
	 * 判断参数是否为空
	 * 
	 */
	public static void validateEmptytoMsg(String value,String msg) throws ErrException {
		if (value == null || value.length() == 0) {
			System.out.println("参数为空");
			throw new ErrException(msg);
		}
	}
	/**
	 * 判断字符串是否为数字
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number)  
    {  
        if(number!=null){
        	for (int i = 0; i < number.length(); i++)  
            {  
                if(Character.isDigit(number.charAt(i)))  
                {  
                    return true;  
                }  
            }  
        }
		
        return false;  
    }  
	/**
	 * 判断字符是否是合法的日期格式
	 * @param str  日期字符串
	 * @return true 是  false 否
	 * @author 卢磊
	 */
	public static boolean validDateFormat(String str) throws ErrException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    try{
	        Date date = (Date)formatter.parse(str);
	        return str.equals(formatter.format(date));
	    }catch(Exception e){
	       System.out.println("日期格式有误!");
	       throw new ErrException();
	    }
	}
	/**
	 * 判断String是否为空
	 * 
	 */
	public static boolean isEmptyString(String value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * String -> int
	 * 
	 * @param String
	 * @return int
	 */
	public static int parseInt(String string, int def) {
		if (isEmptyString(string))
			return def;
		int num = def;
		try {
			num = Integer.parseInt(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> long
	 * 
	 * @param String
	 * @return long
	 */
	public static long parseLong(String string, long def) {
		if (isEmptyString(string))
			return def;
		long num;
		try {
			num = Long.parseLong(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> double
	 * 
	 * @param String
	 * @return long
	 */
	public static double parseDouble(String string, double def) {
		if (isEmptyString(string))
			return def;
		double num;
		try {
			num = Double.parseDouble(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> float
	 * 
	 * @param String
	 * @return float
	 */
	public static float parseFloat(String string, float def) {
		if (isEmptyString(string))
			return def;
		float num;
		try {
			num = Float.parseFloat(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	public static String getCurrentTimeFormat(String format) {
		return getTimeFormat(new Date(), format);
	}

	/**
	 * @param date
	 * @param string
	 * @return
	 */
	public static String getTimeFormat(Date date, String string) {
		SimpleDateFormat sdFormat;
		if (isEmptyString(string)) {
			sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdFormat = new SimpleDateFormat(string);
		}
		try {
			return sdFormat.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static int hasNext(List<?> a) {
		if (a != null && a.size() > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * MD5加密
	 * 
	 * @param 需要加密的String
	 * @return 加密后String
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 格式化时间- String转换Date "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param 需格式化的时间
	 * @return 格式化后的时间
	 */
	public static Date getDateFormat(String date, String format) {
		if (isEmptyString(date))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param Title
	 * @param listContent
	 * @return
	 */
	public static String exportExcel(String fileName, String[] Title, List<?> listContent, String[] data) {
		// 以下开始输出到EXCEL
		String newfilename = "";
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
			String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			/** **********创建工作表************ */
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);
			/** ************设置单元格字体************** */
			// WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行
			// 用于正文居左
			NumberFormat nf = new jxl.write.NumberFormat("0.00");
			WritableCellFormat wcf_left = new WritableCellFormat(nf);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行
			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			int h = 0;
			for (int i = 0; i < Title.length; i++) {
				if (i <= 3) {
					sheet.addCell(new Label(i, 0, Title[i], wcf_center));
				} else if (h < data.length) {
					int j = CommonUtils.parseInt(data[h], -1) + 4;
					sheet.addCell(new Label(i, 0, Title[j], wcf_center));
					h++;
				}
			}
			/** ***************以下是EXCEL正文数据********************* */
			Field[] fields = null;
			int i = 1;
			for (Object obj : listContent) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				int k = 0;
				for (int b = 0; b < (data.length + 4); b++) {
					Field v = null;
					if (b <= 3) {
						v = fields[b];
					} else if (k < data.length) {
						int a = CommonUtils.parseInt(data[k], -1);
						v = fields[a + 4];
						k++;
					}
					if (v != null) {
						v.setAccessible(true);
						Object va = v.get(obj);
						if (va == null) {
							va = "";
						}
						// System.out.println(va.getClass());
						if (va.getClass() == Double.class) {
							sheet.addCell(new jxl.write.Number(j, i, (Double) va, wcf_left));
						} else {
							sheet.addCell(new Label(j, i, va.toString(), wcf_left));
						}
					}
					j++;
				}
				i++;
			}
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newfilename;
	}

	public static void downloadExcel(String filepath, String downloadname, HttpServletResponse response) {
		try {
			// String file= filepath;
			File file = new File(filepath);
			InputStream is = new FileInputStream(file);
			response.reset(); // 必要地清除response中的缓存信息
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(downloadname.getBytes(), "ISO_8859_1") + CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmm") + ".xls");
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("applicationnd.ms-excel");// 根据个人需要,这个是下载文件的类型
			javax.servlet.ServletOutputStream out = response.getOutputStream();
			byte[] content = new byte[1024];
			int length = 0;
			while ((length = is.read(content)) != -1) {
				out.write(content, 0, length);
			}
			out.write(content);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param 文件
	 * @param 服务器路径
	 * @param 文件名
	 * @return boolean
	 */
	public static String uploadApp(File imageFile, String realpath, String fileName) {
		String path = null;
		if (imageFile != null) {

			// d:upload/temp/
			String tempPath = realpath;

			// 取得后缀名
			String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

			// 时间戳加两位随机数据
			String severPicName = CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + extension;

			// 上传文件保存路径
			File savefile = new File(new File(tempPath), severPicName);
			if (!savefile.getParentFile().exists())
				savefile.getParentFile().mkdirs();

			// 复制文件到指定目录
			try {
				FileUtils.copyFile(imageFile, savefile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 保存路径
			path = severPicName;

		}
		return path;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的路径
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static void deleteFile(String sPath) {
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}

	/**
	 * 给指定用户发送短信
	 * 
	 * @param phone
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @return
	 */
	public static String sendSms(String phone, String content) {
		// http://202.91.244.252/qd/SMSSendYD?usr=***&pwd=***&mobile=***&sms=***&extdsrcid=***
		StringBuilder url = new StringBuilder();
		url.append("http://202.91.244.252/qd/SMSSendYD?usr=");
		url.append(Constant.SMS_USER);
		url.append("&pwd=");
		url.append(Constant.SMS_PWD);
		url.append("&mobile=");
		url.append(phone);
		url.append("&sms=");
		try {
			url.append(java.net.URLEncoder.encode(content, "GBK"));
		} catch (UnsupportedEncodingException e) {
		}
		String result = HttpRequest.sendGet(url.toString(), null);
		// System.out.println(result);
		return result;
	}
	public static String sendSms2(String phone, String content) {
		// http://202.91.244.252/qd/SMSSendYD?usr=***&pwd=***&mobile=***&sms=***&extdsrcid=***
		StringBuilder url = new StringBuilder();
		url.append("http://120.26.69.248/msg/HttpSendSM?account=002006&pswd=Sd123456789&mobile=13958109962&needstatus=false&msg=");
		/*url.append(Constant.SMS_USER);
		url.append("&pwd=");
		url.append(Constant.SMS_PWD);
		url.append("&mobile=");
		url.append(phone);
		url.append("&sms=");*/
		try {
			url.append(java.net.URLEncoder.encode(content, "utf-8"));
		} catch (UnsupportedEncodingException e) {
		}
		String result = HttpRequest.sendGet(url.toString(), null);
		// System.out.println(result);
		return result;
	}
	
	/**
	 * 上传图片
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public static String uploadImg(File imageFile, String fileName) throws Exception {
		String path = null;
		if (imageFile != null) {
			// d:upload/temp/
			String tempPath = getLocationPath() + getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
			checkPath(tempPath);
			// 取得后缀名
			String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
			// 时间戳加两位随机数据
			String severPicName2 = getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + "origin" + extension;
			String severPicName = severPicName2.replace("origin", "");
			// 上传文件保存路径
			File savefile = new File(new File(tempPath), severPicName);
			if (!savefile.getParentFile().exists())
				savefile.getParentFile().mkdirs();
			// 复制文件到指定目录
			FileUtils.copyFile(imageFile, savefile);
			// 上传文件保存路径
			File savefile2 = new File(new File(tempPath), severPicName2);
			if (!savefile2.getParentFile().exists())
				savefile2.getParentFile().mkdirs();
			// 复制文件到指定目录
			FileUtils.copyFile(imageFile, savefile2);
			// 压缩图片
			// ImageUtil.scale4(tempPath + severPicName, tempPath + severPicName, 999999999, 640);
			// 保存路径
			path = (tempPath + severPicName).replace(File.separator, "/");
		}
		return path;
	}
	
	
	/** 
     * 将数转为24进制 
     * @param num 
     * @param base 
     * @return 
     */  
    public static String to24Base(long num){  
    	
    	int base= 24;
        StringBuffer str = new StringBuffer("");  
        String digths = "ABCDEFGHJKLMNPQRSTUVWXYZ";  
        Stack<Character> s = new Stack<Character>();  
        
        while(num > base){  
        	int idx=(int)(num%base);
            s.push(digths.charAt(idx));  
            num/=base;  
        }  
        while(!s.isEmpty()){  
            str.append(s.pop());  
        }  
        return str.toString();  
    } 
    
    public static String getInviteCode(String phone)
    {
    	  	
    	String first=phone.substring(0, 3);
    	Integer index=Integer.parseInt(first);
    	List<String> list = Arrays.asList("130","131","132","133","134","135","136","137","138","139","150","151","152","153","154","155","156","157","158","159","170","171","172","173","174","175","176","177","178","179","180","181","182","183","184","185","186","187","188","189");
    	 Iterator<String> iter = list.iterator();  
    	 
    	Integer end=Integer.parseInt(phone.substring(3, 11));
    	int i=0;
    	
    	for (String elem : list)
    	{
    		 if(elem.equals(first))
    		 {    		
    			 break;
    		 }
    		 else
    		 {
    			 i++;
    		 } 
    	}
    	
    	 long num=((long)i+1)*100000000+end;   	 
    	String code=to24Base(num);
//    	System.out.println("to24Base:"+phone+"-->"+code);
    	return code;
    }

    
    public static void main(String[] args) { 
    	/*String phone="18758234668";
    	String code=CommonUtils.getInviteCode(phone);
    	System.out.print(code);*/
    	sendSms2("","【小巴科技】你的验证码是232323");
    }
    
}
