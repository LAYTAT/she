package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import com.wis.pack.component.Photo;

import team.ecust.she.common.ImageTool;
import team.ecust.she.view.EditInfo;
import team.ecust.she.view.PromptBox;

public class OpenFileChooser <K extends JComponent> extends MouseAdapter {
	private Photo photo;
	private EditInfo info;
	
	public OpenFileChooser(K k, Photo photo) {
		this.info = (EditInfo)k;
		this.photo = photo;
	}
	
	public synchronized static void doIt(EditInfo k, Photo photo) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(fileChooser);
		
		File file = fileChooser.getSelectedFile();
		if(file == null || !file.exists() || file.isDirectory()) {
			(new PromptBox()).open("未选择图片");
			return;
		}
		
		String temp = file.getAbsolutePath();
		if(!temp.endsWith(".png") && !temp.endsWith(".jpg") && !temp.endsWith(".gif")
				&& !temp.endsWith(".PNG") && !temp.endsWith(".JPG") && !temp.endsWith(".GIF")) {
			(new PromptBox()).open("不支持此图片格式");
			return;
		}
		
		ImageTool tool = new ImageTool();
		tool.setImagePath(temp);
		if(tool.exceedSize(1000000))
			(new PromptBox()).open("图片过大");
		else {
			k.setHeadPortrait(temp);
			photo.setPhoto(tool.getImage());
			photo.repaint();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(info, photo);
	}
}