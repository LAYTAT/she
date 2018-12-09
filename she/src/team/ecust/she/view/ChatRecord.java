package team.ecust.she.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JPanel;

import team.ecust.she.common.FileTool;

@SuppressWarnings("serial")
public class ChatRecord extends JPanel {
	private final static Color TIME_TITLE = new Color(250, 250, 250);
	private final static Color CHAT_TEXT = new Color(200, 200, 200);
	private final static Font TIME_FONT = new Font("黑体", Font.PLAIN, 24);
	private final static Font CHAT_FONT = new Font("宋体", Font.PLAIN, 24);
	
	private Vector<RowWord> vector;
	
	public void addRecord(String word) {
		
		repaint();
	}
	
	/**该界面要展示的存储一行数据的类。*/
	private class RowWord {
		/**一行文字*/
		public String word;
		/**文字的左上角y坐标*/
		public int y;
		/**文字颜色*/
		public Color color;
		/**文字字体*/
		public Font font; 
		/**初始化。*/
		public RowWord(String text) {
			word = text;
		}
	}
	
	public ChatRecord() {
		setBackground(Colors.CENTER_CARD_BACKGROUND.getColor());
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D painter = (Graphics2D)g.create();
		if(vector != null && vector.size() != 0) {//只有添加了行数据才显示
			RowWord word = null;
			for(int i = 0; i < vector.size(); i++) {
				word = vector.elementAt(i);
				if(word.y < -30 || word.y > getHeight())
					continue;
				painter.setColor(word.color);
				painter.setFont(word.font);
				painter.drawString(word.word, 10, word.y);
			}
		}
	}
	
	public void display(String objectNo) {
		if(vector != null)
			vector.clear();
		
		int width = getWidth();
		int height = getHeight();
		if(width == 0 || height == 0)//未加入容器中或者显示了也没作用
			return;
		vector = new Vector<RowWord>();//以10个为单位分配
		String text = null;
		FileTool file = new FileTool(Index.getInstance().getMemberNo() + "/" + objectNo);//新建软件信息文件对象
		
		//int rows = file.getAllLines();
		//if(rows*30 > height)//最好动态载入
		for(int i = 0; ; i++) {
			text = file.readTheLine(i + 1);
			if(text == null)
				break;
			RowWord word = new RowWord(text);
			if(text.startsWith("[") && text.endsWith("]")) {
				word.color = TIME_TITLE;
				word.font = TIME_FONT;
			} else {
				word.color = CHAT_TEXT;
				word.font = CHAT_FONT;
			}
			word.y = 30*i;
			vector.add(word);
		}
		
		if(vector.size() != 0 && vector.lastElement().y > getHeight() - 30)//微调
			adjust(getHeight() - vector.lastElement().y - 30);
			
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() == MouseWheelEvent.WHEEL_BLOCK_SCROLL) 
					adjust(15);
				else if(e.getWheelRotation() == -MouseWheelEvent.WHEEL_BLOCK_SCROLL)
					adjust(-15);
				repaint();
			}
		});
		repaint();
	}
	
	/**
	 * 同步的调整文字位置的方法。
	 * @param sensibility 滚动灵敏度和方向，+往下，-往上
	 */
	private synchronized void adjust(int sensibility) {
		if(vector != null && vector.size() != 0) {
			RowWord word = vector.lastElement();
			if(word.y < getHeight() - 30 && sensibility < 0)//根据实际调整
				return;
			word = vector.firstElement();
			if(word.y > 30 && sensibility > 0)//微调
				return;
			for(int i = 0; i < vector.size(); i++) {
				word = vector.elementAt(i);
				word.y += sensibility;
			}
		}
	}
}