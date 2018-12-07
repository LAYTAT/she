package team.ecust.she.common;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;

/**
 * <p>图片对象获取和操作的类。
 * <p>主要用于获取各种图片对象，和进行字节流的转换。
 */
public final class ImageTool {
	/**本地图片的存储路径，可以是绝对或相对路径*/
	private String imagepath;
	
	/**
	 * <p>根据文件名初始化图片路径。
	 * <p>文件默认在/team/ecust/she/resource/image/download/目录下。
	 * <p>参数可以加上该目录下的子目录。
	 * @param filename 指定图片的文件名，需要后缀名
	 */
	public ImageTool(String imagename) {
		imagepath = "/team/ecust/she/resource/image/download/" + imagename;
	}
	
	/**
	 * <p>给绝对路径初始化的构造函数。
	 * <p>什么都不做。
	 */
	public ImageTool() {
		
	}
	
	/**
	 * <p>获取图片的文件路径
	 * @return 图片的文件路径
	 */
	public String getImagePath() {
		return imagepath;
	}
	
	/**
	 * <p>设置读取图片或存储图片的路径，可以为绝对路径。
	 * <p>相对路径一般使用这个路径：/team/ecust/she/resource/image/download/。
	 * @param imagepath 要设置的路径参数
	 */
	public void setImagePath(String imagepath) {
		this.imagepath = imagepath;
	}
	
	/**
	 * <p>获取当前路径的绝对路径。
	 * <p>如果路径为空，则返回空。
	 * @return 当前路径的绝对路径
	 */
	public String getAbsolutePath() {
		try {
			File file = new File(imagepath);
			return file.getAbsolutePath();
		} catch (NullPointerException e) {
			System.err.println("->该文件路径为null");
		}
		return null;
	}
	
	/**
	 * <p>根据当前图片路径获取输入流。
	 * <p>如果当前路径并不能获取有效的文件，则返回null。
	 * @return 对应图片的输入流或null
	 */
	public FileInputStream readImage() {
		addSrcIfNotExist();
		try {
			return (new FileInputStream(new File(imagepath)));
		} catch (FileNotFoundException e1) {
			System.err.println("->找不到对应文件");
		} catch (NullPointerException e2) {
			System.err.println("->文件参数为空");
		}
		return null;
	}
	
	/**
	 * <p>将从数据库获取的图像输出流保存到指定的文件路径里，需要指定文件类型。
	 * <p>文件路径对应的文件可以不存在，则会新建文件。
	 * <p>文件路径可以为相对和绝对，相对路径会自动规范。
	 * <p>输入输出流会自动关闭。
	 * @param in 数据库获取的数据流
	 * @return 只有成功保存了才返回true
	 */
	public boolean saveImage(InputStream in) {
		if(in == null)
			return false;
		addSrcIfNotExist();
		OutputStream out = null;
		try {
			byte[] b = new byte[in.available()];
			in.read(b);
			out = new FileOutputStream(imagepath);
			out.write(b);
			out.flush();
			out.close();
			in.close();
			return true;
		} catch (IOException e) {
			System.err.println("->文件参数为空");
		} finally {
			try {
				if(out != null)
					out.close();
				if(in != null)
					in.close();
			} catch (IOException e) {
				System.err.println("->流关闭失败");
			}
		}
		return false;
	}
	
	/**
	 * <p>判断指定路径的图片是否已经超过指定的字节大小。
	 * <p>指定路径的图片为无效时则返回false。
	 * <p>不对参数进行判断。
	 * <p>会自动判断是否合法的相对路径并自动加上，即以src开头。
	 * <p>指定文件可以不存在或为目录，返回false。
	 * @param size 指定的最大大小，单位为字节
	 * @return 判断结果，超过大小返回true，否则返回false
	 */
	public boolean exceedSize(int size) {
		if(imagepath == null)
			return false;
		addSrcIfNotExist();//一定要添加
		File image = new File(imagepath);
		if(!image.exists())
			return false;
		if(!image.isFile())
			return false;
		if(image.length() > size)
			return true;
		return false;
	}
	
	/**
	 * <p>如果图片路径为相对路径且以src开头，则移除之。
	 */
	private void removeSrcIfExist() {
		if(imagepath == null)
			return;
		if(imagepath.startsWith("src"))
			imagepath = imagepath.substring(3, imagepath.length());
	}
	
	/**
	 * <p>如果图片路径为相对路径且未以src开头，则添加之。
	 */
	private void addSrcIfNotExist() {
		if(imagepath == null)
			return;
		if(imagepath.startsWith("/"))
			imagepath = "src" + imagepath;
	}
	
	/**
	 * <p>根据图片路径获取图片对象。
	 * <p>如果图片不存在则返回空对象。
	 * <p>会依据相对路径与绝对路径自动调整。
	 * @return 有效路径的图片或空对象
	 */
	//@Deprecated
	public Image getImage() {
		if(imagepath == null)
			return null;
		removeSrcIfExist();//一定要移除
		if(imagepath.startsWith("/"))
			return Toolkit.getDefaultToolkit().getImage(ImageTool.class.getResource(imagepath));
		else
			return Toolkit.getDefaultToolkit().getImage(imagepath);
	}
	
	/**
	 * <p>根据图片路径获取图片对象。
	 * <p>如果图片不存在则返回空对象。
	 * <p>会依据相对路径与绝对路径自动调整。
	 * @return 有效路径的图片或空对象
	 */
	//@Deprecated
	public ImageIcon getImageIcon() {
		if(imagepath == null)
			return null;
		removeSrcIfExist();//一定要移除
		if(imagepath.startsWith("/"))
			return (new ImageIcon(ImageTool.class.getResource(imagepath)));
		else
			return (new ImageIcon(imagepath));
	}
}