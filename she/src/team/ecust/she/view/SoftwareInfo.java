package team.ecust.she.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import team.ecust.she.common.TextFile;
import team.ecust.she.common.TextFile.FileName;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**展示软件信息文本里相关信息的界面，有惊喜哟。*/
@SuppressWarnings("serial")
public class SoftwareInfo extends JPanel {
	/**字体颜色常量*/
	private final static Font TITLE_FONT = new Font("微软雅黑", Font.PLAIN, 28);
	private final static Font TEXT_FONT = new Font("等线", Font.PLAIN, 24);
	private final static Color TITLE_COLOR = new Color(250, 250, 250);
	private final static Color TEXT_COLOR = new Color(200, 200, 200);
	
	/**存储所有标签*/
	private Vector<JLabel> vector = null;
	private boolean dynamic = true;
	
	/**该界面要展示的一行数据对象类。*/
	private class RowWord {
		/**一行文字*/
		public String word;
		/**文字的左上角坐标*/
		public Point location;
		/***/
		public RowWord(String word, int x, int y) {
			this.word = word;
			location.x = x;
			location.y = y;
		}
	}
	
	/**创建面板。*/
	public SoftwareInfo() {
		vector = new Vector<JLabel>();//以10为单位分配
		setBackground(Color.black);
		/*
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(dynamic) {
					dynamic = false;
					(new Timer()).schedule(new TimerTask() {
						@Override
						public void run() {
							if(vector.lastElement().getLocation().y + 100 < 0)
								cancel();
							int size = vector.size();
							for(int i = 0; i < size; i++) {
								JLabel label = vector.elementAt(i);
								label.setLocation(label.getLocation().x, label.getLocation().y - 1);
							}
						}
					}, 100, 20);
				}
			}
		});*/
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(TEXT_FONT);
		g.setColor(TEXT_COLOR);
		
		g.drawString(g.toString(), 100, 100);
	}
	
	/**真正展示。*/
	public void display() {
		int width = getWidth();
		int height = getHeight();
		
		String text = null;
		TextFile file = new TextFile(FileName.SOFTWARE_INFO);
		
		int i;
		for(i = 1; ; i++) {
			text = file.getOneRowData(i);
			if(text == null)
				break;
			JLabel label = null;
			if(text.startsWith("@")) {
				label = new JLabel(text.substring(1));
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setForeground(TITLE_COLOR);
				label.setFont(TITLE_FONT);
				label.setLocation((width-text.length()*TITLE_FONT.getSize())/2, height+30*(i-1));
			} else {
				label = new JLabel(text);
				label.setForeground(TEXT_COLOR);
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setFont(TEXT_FONT);
				label.setLocation((width-text.length()*TEXT_FONT.getSize())/2, height+30*(i-1));
			}
			add(label);
			vector.add(label);
		}
		setLayout(new GridLayout(i, 0, 0, 0));
	}
}