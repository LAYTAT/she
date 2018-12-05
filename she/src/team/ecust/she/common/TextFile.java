package team.ecust.she.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;

/**
 * 这是对文本文件进行部分常用操作的类，包含错误信息的自动提示。
 * 主要包含以下方法：
 * <p>1.读取指定行的信息；
 * <p>2.读取整个文件；
 * <p>3.将指定数据覆盖整个文件；
 * <p>4.将指定数据覆盖指定行。
 * ......
 */
public class TextFile
{
	/**软件信息文件的名称*/
	public final static String SOFTWARE_INFO = "software";
	/**登录配置文件的名称*/
	public final static String LOGIN_SETTING = "login";
	
	/**文件对象的相对路径*/
	private String filepath;
	
	/**
	 * 初始化需要操作的文件对象名称。
	 * <p>这里的文件均默认在src/team/ecust/she/resource/file/目录下。
	 * @param filename 指定文件的名称，在本类的常量中选择，可以为空，则对应文件对象也为空
	 */
	public TextFile(String filename) {
		this.setFilePath("src/team/ecust/she/resource/file/" + filename);
	}
	
	/**
	 * 设置文件对象为指定操作的文件对象。
	 * <p>如果指定的文件路径为目录，则文件对象会置为空对象。
	 * @param filepath 文件的相对路径，从src目录开始，需要包含文件名，不需要后缀
	 */
	public void setFilePath(String filepath) {
		if(filepath.endsWith("/"))
			filepath = null;
		else
			this.filepath = filepath;
	}

	/**
	 * 读取并返回指定文件指定行的内容，不包含换行符，行是从1开始计数的。
	 * <p>指定行不存在或为空或指定文件不存在都返回null。
	 * @param off 指定行
	 */
	public String getOneRowData(int off) {
		if(off <= 0)
			return null;
        File file = null;
        try {
			file = new File(filepath);//创建文件对象
		} catch (NullPointerException e) {
			(new PromptBox(Tips.ERROR)).open("文件不存在");
			return null;
		}
        BufferedReader reader = null;  //文件读取对象
        try {
        	String data = null;                               //行内容
            reader = new BufferedReader(new FileReader(file));//获取文件内容
            for(int i = 1; ; i++) {
            	data = reader.readLine();//读取下一行
            	if(data == null) {//达到文件末端
            		return null;
            	} else if(i == off) {//返回指定行内容，包括只有换行符的内容
            			return data;
            		}
            }
        } catch (IOException e) {  
        	(new PromptBox(Tips.ERROR)).open("文件读取失败");
        } finally {  
            if (reader != null) {
                try {
                	reader.close();
                } catch (IOException ex) {
                	(new PromptBox(Tips.ERROR)).open("关闭文件流失败");
                }
            }  
        }
        return null;
	}
	
	/**
	 * 从指定行开始读取并返回文件的全部内容，行为负数则获取整个文件，包含换行符。
	 * <p>指定文件不存在或指定行为空则返回空串""。
	 * @param off 指定行
	 */
	public String getAllData(int off) {
		if(off <= 0)
			off = 1;
		StringBuffer buffer = new StringBuffer("");//存储文本
		String temp = null;//临时文本
		for(int i = off; ;i++) {
			temp = getOneRowData(i);
			if(temp == null)
				break;
			buffer.append(temp);
		}
		return buffer.toString();
	}
	
	/**
	 * 用指定内容覆盖指定文件的全部内容。
	 * data为null则不更改并返回false，更改成功返回true，修改失败则返回false
	 * @param data 指定内容
	 */
	public boolean setAllData(String data) {
		if(data == null)
			return false;
		File file = null;
		try {
			file = new File(filepath);
		} catch (NullPointerException e) {
			(new PromptBox(Tips.ERROR)).open("文件不存在");
			return false;
		}
        try {
            FileWriter writter = new FileWriter(file.getPath());
            writter.write(data);
            writter.close();
            return true;
        } catch (IOException e) {  
            (new PromptBox(Tips.ERROR)).open("覆盖文件失败");
            return false;
        }  
	}
	
	/**
	 * 用指定内容覆盖指定文件指定行的内容，自动加上换行符，数据不能为空对象，指定行必须先存在。
	 * <p>更改成功返回true，修改失败则返回false
	 * @param data 指定内容
	 * @param row 指定行
	 */
	public boolean setOneRowData(String data, int row) {
		if(data == null || row <= 0)
			return false;
		StringBuffer buffer = new StringBuffer("");//临时存储修改的文本
		String temp = null;
		for(int i = 1; ; i++) {
			if(i == row)
				buffer.append(data.concat("\n"));//修改指定行信息
			else {//其余照抄
				if((temp = getOneRowData(i)) != null) {
					if(temp.equals("\n"))//该行只含有换行符
						buffer.append(temp);
					else
						buffer.append(temp.concat("\n"));
				}
				else break;
			}
		}
		if(setAllData(buffer.toString())) //修改文件失败
		    return true;
		else                               //完成修改
			return false;
	}
	
	/**
	 * 往指定文件末尾添加指定数据，为空对象或空行则不加，并返回false。
	 * <p>指定文件不存在则在指定路径创建新文件并写入。
	 * <p>更改成功返回true，修改失败则返回false。
	 * @param data 指定数据
	 */
	public boolean append(String data) {
		if(data == null || data.isEmpty() || filepath == null || filepath.endsWith("/"))
			return false;
		File file = null;
		try {
			file = new File(filepath);
		} catch (NullPointerException e) {
			(new PromptBox(Tips.ERROR)).open("文件不存在");
		}
		FileOutputStream write = null;
		try {
			if(file.exists()) {
				write = new FileOutputStream(file, true);
				write.write(data.getBytes());
			} else {
				write = new FileOutputStream(file);
				write.write(data.getBytes());
			}
			write.close();
			return true;
		} catch (Exception e1) {
			(new PromptBox(Tips.ERROR)).open("文件写入失败");
			if(write != null) {
				try {
					write.close();
				} catch (Exception e2) {
					(new PromptBox(Tips.ERROR)).open("文件流关闭失败");
				}
			}
			return false;
		}
	}
}