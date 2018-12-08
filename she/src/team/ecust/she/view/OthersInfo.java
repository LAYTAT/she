package team.ecust.she.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**其他人的信息界面类。*/
@SuppressWarnings("serial")
public class OthersInfo extends JPanel {

	/**初始化。 */
	public OthersInfo() {
		//delete after testing
			display();
		//..........
	}

	public void display() {
		//int width = getWidth();
		//int height = getHeight();
		int width = 1680;
		int height = 1020;
		//...........
			setSize(width, height);
		//.............
		setLayout(new BorderLayout());
	}
}