package team.ecust.she.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JPanel;

import team.ecust.she.common.FileTool;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**展示软件信息文本里相关信息的界面，有惊喜哟。*/
@SuppressWarnings("serial")
public class SoftwareInfo extends JPanel {
	/**相关字体颜色常量*/
	private final static Font TITLE_FONT = new Font("微软雅黑", Font.PLAIN, 28);
	private final static Font TEXT_FONT = new Font("等线", Font.PLAIN, 24);
	private final static Color TITLE_COLOR = new Color(50, 200, 250);
	private final static Color TEXT_COLOR = new Color(200, 250, 50);
	
	/**该界面要展示的存储一行数据的类。*/
	private class RowWord {
		/**一行文字*/
		public String word;
		/**文字的左上角坐标*/
		public Point location;
		/**文字颜色*/
		public Color color;
		/**文字字体*/
		public Font font; 
		/**初始化。*/
		public RowWord() {
			
		}
	}
	
	/**存储所有行文字对象*/
	private Vector<RowWord> vector;
	
	/**创建面板并初始化基本信息。*/
	public SoftwareInfo() {
		setBackground(Color.black);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D painter = (Graphics2D)g.create();
		if(vector != null && vector.size() != 0) {//只有添加了行数据才显示
			RowWord word = vector.lastElement();
			if(word.location.y < -TITLE_FONT.getSize())//根据实际调整
				return;
			word = vector.firstElement();
			if(word.location.y > getHeight())
				return;
			for(int i = 0; i < vector.size(); i++) {
				word = vector.elementAt(i);
				if(word.location.y < -TITLE_FONT.getSize()) 
					continue;
				if(word.location.y > getHeight())
					continue;
				painter.setColor(word.color);
				painter.setFont(word.font);
				painter.drawString(word.word, word.location.x, word.location.y);
			}
		}
	}
	
	/**真正显示整个面板信息。*/
	public void display() {
		int width = getWidth();
		int height = getHeight();
		if(width == 0 || height == 0)//未加入容器中或者显示了也没作用
			return;
		
		vector = new Vector<RowWord>();//以10个为单位分配
		String text = null;
		FileTool file = new FileTool(FileTool.SOFTWARE_INFO);//新建软件信息文件对象
		
		for(int i = 1; ; i++) {//添加数据
			text = file.readTheLine(i);
			if(text == null)//读完了数据
				break;
			RowWord word = new RowWord();
			if(text.startsWith("@")) {//统一做英文宽度处理，文件则需要在每一行的两侧加上相同等量于中文的空格
				word.color = TITLE_COLOR;
				word.font = TITLE_FONT;
				word.word = text.substring(1);
				word.location = new Point((width-TITLE_FONT.getSize()*(text.length()-1)/2)/2, (int) (height + 1.5*(i-1)*TITLE_FONT.getSize()));
			} else {
				word.color = TEXT_COLOR;
				word.font = TEXT_FONT;
				word.word = text;
				word.location = new Point((width-TITLE_FONT.getSize()*text.length()/2)/2, (int) (height + 1.5*(i-1)*TITLE_FONT.getSize()));
			}
			vector.add(word);
		}
		
		(new Timer()).schedule(new TimerTask() {//自动滚动
			@Override
			public void run() {
				adjust(-1);
				repaint();
			}
		}, 100, 30);
		
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() == MouseWheelEvent.WHEEL_BLOCK_SCROLL) 
					adjust(-10);
				else if(e.getWheelRotation() == -MouseWheelEvent.WHEEL_BLOCK_SCROLL)
					adjust(10);
				repaint();
			}
		});
	}
	
	/**
	 * 同步的调整文字位置的方法。
	 * @param sensibility 滚动灵敏度和方向，+往下，-往上
	 */
	private synchronized void adjust(int sensibility) {
		if(vector != null && vector.size() != 0) {
			RowWord word = vector.lastElement();
			if(word.location.y < -TITLE_FONT.getSize() && sensibility < 0)//根据实际调整
				return;
			word = vector.firstElement();
			if(word.location.y > getHeight() && sensibility > 0)
				return;
			for(int i = 0; i < vector.size(); i++) {
				word = vector.elementAt(i);
				word.location.setLocation(word.location.x, word.location.y + sensibility);
			}
		}
	}
}