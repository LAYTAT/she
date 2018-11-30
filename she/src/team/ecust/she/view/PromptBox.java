package team.ecust.she.view;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**提示框类，实际上是一个窗体，需要使用open方法打开才能显示。*/
@SuppressWarnings("serial")
public class PromptBox extends JFrame {
	/**默认的显示时间{@value}，单位为毫秒*/
	public final static int DEFAULT_DELAY_MS = 1000;
	
	/**提示信息的提示枚举类型。*/
	public enum Tips {
		/**错误图标*/
		ERROR,
		/**信息图标*/
		INFO,
		/**成功图标*/
		TICK,
		/**离线图标*/
		OFFLINE,
	}
	
	/**显示时间属性，单位为毫秒，不设置则为默认值*/
	private int delay;
	/**提示类型属性，不设置则为信息图标*/
	private Tips icon;
	
	/**
	 * 新建对象时，指定图标和显示时间。
	 * @param icon 在本类中的提示枚举中选择合适的图标
	 * @param delayms 提示框的显示时间，单位为毫秒
	 */
	public PromptBox(Tips icon, int delayms) {
		this.icon = icon;
		this.delay = delayms;
	}
	/**
	 * 新建对象时，指定图标，显示时间默认。
	 * @param icon 在本类中的提示枚举中选择合适的图标
	 */
	public PromptBox(Tips icon) {
		this(icon, DEFAULT_DELAY_MS);
	}
	/**
	 * 新建对象时，图标默认，指定显示时间。
	 * @param delayms 提示框的显示时间，单位为毫秒
	 */
	public PromptBox(int delayms) {
		this(Tips.INFO, delayms);
	}
	/**新建对象时，图标和显示时间都默认。*/
	public PromptBox() {
		this(Tips.INFO, DEFAULT_DELAY_MS);
	}
	
	/**
	 * 获取当前设置的图标的相对路径，如果提示枚举没设置或为空对象则返回默认的信息图标路径。
	 * @return 对应图标的相对路径
	 */
	private String getIconPath() {
		if(icon == null)
			icon = Tips.INFO;
		switch (icon) {
		case ERROR:
			return "/team/ecust/she/resource/image/error.png";
		case INFO:
			return "/team/ecust/she/resource/image/info.png";
		case TICK:
			return "/team/ecust/she/resource/image/tick.png";
		case OFFLINE:
			return "/team/ecust/she/resource/image/offline.png";
		default:
			return "/team/ecust/she/resource/image/info.png";
		}
	}
	
	/**
	 * 如果消息为空对象或空串，消息会变为"空消息"，根据设置的属性执行显示动作，并在屏幕中心位置打开提示框后显示图标和消息。
	 * 这个方法是同步的。
	 * @param message 传入要显示的消息
	 */
	public synchronized void open(String message) {
		if(message == null || message.equals(""))
			message = "空消息";
		
		getContentPane().setBackground(Colors.PROMPT_BOX_BACKGROUND.getColor());
		setUndecorated(true);//去除标题栏
		int fitWidth = 2*message.length()*Fonts.PROMPT_BOX.getFont().getSize();//计算窗体的最佳宽度
		setBounds((Index.SCREEN_WIDTH-fitWidth)/2, (Index.SCREEN_HEIGHT-80)/2, fitWidth, 80);//设置窗体的大小位置
		setAlwaysOnTop(true);//设为最顶层显示
		setOpacity(0.1f);//透明度必须最后设置
		setVisible(true);
		
		Timer dynamic = new Timer();
		dynamic.schedule(new TimerTask()
		{
			@Override
			public void run() {
				if(getOpacity() < 0.8f)
				{
					setOpacity(getOpacity() + 0.05f);
					repaint();
				}
				else {
					dynamic.schedule(new TimerTask()
					{
						@Override
						public void run() {
							dynamic.schedule(new TimerTask() {
								@Override
								public void run() {
									if(getOpacity() > 0.1f)
									{
										setOpacity(getOpacity() - 0.05f);
										repaint();
									}
									else {
										dispose();
										dynamic.cancel();
									}
								}
							}, 50, 50);
						}
					}, delay);
				}
			}
		}, 50, 50);
		
		JLabel label = new JLabel(new ImageIcon(PromptBox.class.getResource(getIconPath())));
		label.setText(message);
		label.setFont(Fonts.PROMPT_BOX.getFont());
		label.setForeground(Colors.PROMPT_BOX_FOREGROUND.getColor());
		getContentPane().add(label);
	}
}