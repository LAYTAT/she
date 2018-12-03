package team.ecust.she.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;

/**
 * 这是对文本文件进行部分常用操作的类
 * 主要包含以下方法：
 * <p>1.读取指定行的信息；
 * <p>2.读取整个文件；
 * <p>3.将指定数据覆盖整个文件；
 * <p>4.将指定数据覆盖指定行。
 */
public class TextFile
{
	/**所有文件的枚举，选择以对指定的文件进行读写。*/
	public enum FileName {
		/**软件信息文件*/
		SOFTWARE_INFO,
		/**登录配置文件*/
		LOGIN_CONFIGURE,
	}
	
	/**文件名对象*/
	private FileName filename;
	
	/**
	 * 初始化文件操作对象。
	 * @param filename 指定文件名的枚举，可以为空，该枚举在在本类中
	 */
	public TextFile(FileName filename) {
		this.filename = filename;
	}
	
	/**
	 * 获取设置的文件枚举对象对应的相对路径。
	 * @return 如果枚举设置为空，任何操作将是无效的，正常则返回文件的相对路径
	 */
	private String getFilePath() {
		if(filename == null)
			return null;
		switch (filename) {
		case SOFTWARE_INFO:
			return "src/team/ecust/she/resource/file/software";
		case LOGIN_CONFIGURE:
			return "src/team/ecust/she/resource/file/login";
		default:
			return null;
		}
	}
	
	/**（1）读取并返回指定文件指定行的内容，行是从1开始计数的，指定行不存在或为空或指定文件不存在都返回null。*/
	public String getOneRowData(int row) {
		if(row <= 0)
			return null;
        File file = null;
        try {
			file = new File(getFilePath());//创建文件对象
		} catch (NullPointerException e) {
			(new PromptBox(Tips.ERROR)).open("文件不存在");
			return null;
		}
        BufferedReader reader = null;  //文件读取对象
        try {
        	String data = null;                              //行内容
            reader = new BufferedReader(new FileReader(file));//获取文件内容
            for(int i = 1; ; i++) {
            	data = reader.readLine();//读取下一行
            	if(data == null) {//达到文件末端
            		return null;
            	} else if(i == row) {//返回指定行内容，包括只有换行符的内容
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
	
	/**（2）从指定行开始读取并返回文件的全部内容，行为负数则获取整个文件，包含换行符，指定文件不存在或指定行为空则返回空串""。*/
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
	
	/**（3）用指定内容覆盖指定文件的全部内容，data为null则不更改并返回false，更改成功返回true，修改失败则返回false*/
	public boolean setAllData(String data) {
		if(data == null)
			return false;
		File file = null;
		try {
			file = new File(getFilePath());
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
	
	/**（4）用指定内容覆盖指定文件指定行的内容，自动加上换行符，指定行必须先存在，更改成功返回true，修改失败则返回false*/
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
}