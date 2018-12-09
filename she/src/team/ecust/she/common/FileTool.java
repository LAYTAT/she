package team.ecust.she.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * <p>这是对文本文件进行部分常用操作的类。
 * <p>包含系统文件的名称列举。
 * <p>不包含错误信息的处理和提示，只是会防止异常产生。
 */
public final class FileTool {
	/**软件信息文件的名称*/
	public final static String SOFTWARE_INFO = "software";
	/**登录配置文件的名称*/
	public final static String LOGIN_SETTING = "login";
	public final static String FILE_NAME = "filename";
	
	/**文件的相对路径或绝对路径*/
	private String filepath;
	
	/**
	 * <p>初始化需要操作的文件对象的路径。
	 * <p>这里初始化的文件路径均默认在src/team/ecust/she/resource/file/目录下。
	 * <p>可以在参数里添加子目录，但必须保证子目录存在。
	 * @param filename 指定文件的名称，可以为空，则对应文件路径也为空
	 */
	public FileTool(String filename) {
		this.setFilePath("src/team/ecust/she/resource/file/" + filename);
	}
	
	public void createFolderIfNotExist() {
		File file = null;
		try {
			file = new File(filepath);
			if(!file.exists())
				file.mkdir();
		} catch (NullPointerException e) {
			System.err.println("->文件对象路径为null");
		}
	}
	
	/**
	 * <p>设置操作的文件路径为指定的文件路径。
	 * <p>如果指定的文件路径为目录，则文件路径不会改变。
	 * @param filepath 文件的路径，相对路径从src目录开始，需要包含文件名，不需要后缀
	 */
	public void setFilePath(String filepath) {
		if(filepath.endsWith("/"))
			return;
		else
			this.filepath = filepath;
	}
	
	/**
	 * <p>获取当前操作的文件路径。
	 * @return 当前操作的文件路径
	 */
	public String getFilePath() {
		return filepath;
	}
	
	/**
	 * <p>获取当前文件的总共行数。
	 * <p>如果文件不存在或无效路径都返回0
	 * @return 行数
	 */
	public int getAllLines() {
		File file = null;
        try {
			file = new File(filepath);//创建文件对象
		} catch (NullPointerException e) {
			System.err.println("->该文件路径为null");
			return 0;
		}
        BufferedReader reader = null;//文件读取对象
        try {
            reader = new BufferedReader(new FileReader(file));
            for(int i = 1; ; i++) {
            	if(reader.readLine() == null)//达到文件末端
            		return i;
            }
        } catch (FileNotFoundException e1) {  
        	System.err.println("->该文件不存在：" + filepath);
        } catch (IOException e2) {
        	System.err.println("->读取该文件时出现IO错误：" + filepath);
		} finally {  
            if (reader != null)
                try {
                	reader.close();
                } catch (IOException e3) {
                	System.err.println("->文件流关闭失败");
                }
        }
        return 0;
	}

	/**
	 * <p>读取并返回指定文件指定行的内容，不包含换行符。
	 * <p>也就是换行符只是一个空串。
	 * <p>指定行不存在或指定文件不存在都返回空对象。
	 * @param row 指定的行数，行从1开始有效
	 * @return 指定行的内容
	 */
	public String readTheLine(int row) {
		if(row <= 0)
			return null;
        File file = null;
        try {
			file = new File(filepath);//创建文件对象
		} catch (NullPointerException e) {
			System.err.println("->该文件路径为null");
			return null;
		}
        BufferedReader reader = null;//文件读取对象
        try {
        	String data = "";//行内容
            reader = new BufferedReader(new FileReader(file));
            for(int i = 1; i <= row; i++) {
            	data = reader.readLine();//读取下一行
            	if(data == null)//达到文件末端
            		return null;
            }
            return data;//返回指定行内容，包括只有换行符的内容
        } catch (FileNotFoundException e1) {  
        	System.err.println("->该文件不存在：" + filepath);
        } catch (IOException e2) {
        	System.err.println("->读取该文件时出现IO错误：" + filepath);
		} finally {  
            if (reader != null)
                try {
                	reader.close();
                } catch (IOException e3) {
                	System.err.println("->文件流关闭失败");
                }
        }
        return null;
	}
	
	/**
	 * <p>读取整个文件的内容，可能行与行之间会出现很多空行。
	 * <p>文件不存在则返回null。
	 * @return 指定文件的内容
	 */
	public String readAllLines() {
		File file = null;
		try {
			file = new File(filepath);
		} catch (NullPointerException e) {
			System.err.println("->该文件路径为null");
			return null;
		}
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			StringBuffer data = new StringBuffer("");//存储整个文本数据
			int single = reader.read();//存储单个字符
			while(single != -1) {
				data.append((char)single);
				single = reader.read();
			}
			return data.toString();
		} catch (FileNotFoundException e1) {
			System.err.println("->该文件不存在：" + filepath);
		} catch (IOException e2) {
			System.err.println("->读取该文件时出现IO错误：" + filepath);
		} finally {  
            if (reader != null)
                try {
                	reader.close();
                } catch (IOException e3) {
                	System.err.println("->文件输入流关闭失败");
                }
        }
		return null;
	}
	
	/**
	 * <p>从指定行开始读取并返回文件的全部内容。
	 * <p>行为负数则获取整个文件，包含换行符。
	 * <p>指定文件不存在或指定行不存在则返回空串""。
	 * @param begin 指定起始的行
	 * @return 从指定行开始的整个文件的内容
	 */
	@Deprecated
	public String readAllLines(int begin) {
		begin = begin > 0 ? begin : 1;
		StringBuffer data = new StringBuffer("");//存储文本
		String temp = null;//临时文本
		for(int i = begin; ;i++) {
			temp = readTheLine(i);
			if(temp == null)
				break;
			data.append(temp);
		}
		return data.toString();
	}
	
	/**
	 * <p>用指定内容覆盖指定文件的全部内容。
	 * <p>data为null则不更改并返回false。
	 * <p>更改成功返回true，修改失败则返回false。
	 * @param data 指定内容
	 * @return 修改的结果
	 */
	public boolean writeAllLines(String data) {
		if(data == null)
			return false;
		File file = null;
		try {
			file = new File(filepath);
		} catch (NullPointerException e) {
			System.err.println("->该文件路径为null");
			return false;
		}
		FileWriter writer = null;
        try {
            writer = new FileWriter(file.getPath());
            writer.write(data);
            return true;
        } catch (IOException e1) {  
        	System.err.println("->写入该文件时出现IO错误：" + filepath);
        } finally {
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e2) {
					System.err.println("->文件输出流关闭失败");
				}
		}
        return false;
	}
	
	/**
	 * <p>用指定内容覆盖指定文件指定行的内容，会自动加上换行符。
	 * <p>数据不能为空对象，指定行必须先存在，否则会返回false。
	 * <p>更改成功返回true，修改失败则返回false。
	 * @param data 指定内容
	 * @param row 指定行
	 */
	public boolean writeTheLine(String data, int row) {
		if(data == null || row <= 0)
			return false;
		StringBuffer buffer = new StringBuffer();//临时存储修改的文本
		String temp = null;
		for(int i = 1; ; i++) {
			if(i == row)
				buffer.append(data.concat("\n"));//修改指定行信息
			else {//其余照抄
				if((temp = readTheLine(i)) != null) {
					if(temp.equals("\n"))//该行只含有换行符
						buffer.append(temp);
					else
						buffer.append(temp.concat("\n"));
				}
				else break;
			}
		}
		return writeAllLines(buffer.toString());
	}
	
	/**
	 * <p>往指定文件末尾添加指定数据。
	 * <p>数据为空对象或空行则不加，并返回false。
	 * <p>指定文件不存在则在指定路径创建新文件并写入，要保证文件名存在。
	 * <p>更改成功返回true，修改失败则返回false。
	 * @param data 指定数据
	 */
	public boolean append(String data) {
		if(data == null || data.isEmpty())
			return false;
		File file = null;
		try {
			file = new File(filepath);
		} catch (NullPointerException e) {
			System.err.println("->该文件路径为null");
			return false;
		}
		FileOutputStream write = null;
		try {
			if(file.exists())
				write = new FileOutputStream(file, true);
			else
				write = new FileOutputStream(file);
			write.write(data.getBytes());
			return true;
		} catch (FileNotFoundException e1) {
			System.err.println("->该路径不是文件：" + filepath);
		} catch (IOException e2) {
			System.err.println("->扩展该文件时出现IO错误：" + filepath);
		} finally {
			if(write != null)
				try {
					write.close();
				} catch (IOException e3) {
					System.err.println("->文件输出流关闭失败");
				}
		}
		return false;
	}
}