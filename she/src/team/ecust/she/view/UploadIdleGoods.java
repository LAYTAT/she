package team.ecust.she.view;

import javax.swing.JPanel;


import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.MatteBorder;

import com.wis.pack.component.Photo;

import team.ecust.she.common.ImageTool;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import javax.swing.JEditorPane;
import team.ecust.she.controller.UploadIdleGoodsController;;

@SuppressWarnings("serial")
public final class UploadIdleGoods extends JPanel {
	

	/**@param 种类**/
	private JComboBox<String> varietyComboBox;
	//各项信息
	private JTextField goodsNameTextField;
	private JTextField priceTextField;
	private JTextField originalPriceTextField;
	private JTextField degreeTextField;
	private JEditorPane note;
	
	//四张图片
	private JFileChooser chosePhoto1;
	private JFileChooser chosePhoto2;
	private JFileChooser chosePhoto3;
	private JFileChooser chosePhoto4;
	
	//四张图片对应四个文件
	private File photoFile1;
	private File photoFile2;
	private File photoFile3;
	private File photoFile4;
	
	//四张图片是否被选	
	private boolean isPhoto1Chosed=false;
	private boolean isPhoto2Chosed=false;
	private boolean isPhoto3Chosed=false;
	private boolean isPhoto4Chosed=false;
	
	//两个标签
	private JTextField goodsLabelTextField1;
	private JTextField goodsLabelTextField2;
	

	/**
	 * @return  String 闲置物品名称
	 */
	public String getIdleGoodsName() {
		return goodsNameTextField.getText().toString();
	}
	/**
	 * @return  String 闲置物品预期售价
	 */
	public String getIdleGoodsSalePrice() {
		return priceTextField.getText().toString();
	}
	/**
	 * @return  String 闲置物品原价
	 */
	public String getIdleGoodsOriginalPrice() {
		return originalPriceTextField.getText().toString();
	}
	/**
	 * @return  String 闲置物品新旧程度
	 */
	public String getIdleGoodsDgree() {
		return degreeTextField.getText().toString();
	}
	/**
	 * @return  String 图片1的路径
	 */
	public String getPhoto1Path() {
		return chosePhoto1.getSelectedFile().getAbsolutePath();
	}
	/**
	 * @return  String 图片2的路径
	 */
	public String getPhoto2Path() {
		return chosePhoto2.getSelectedFile().getAbsolutePath();
	}
	/**
	 * @return  String 图片3的路径
	 */
	public String getPhoto3Path() {
		return chosePhoto3.getSelectedFile().getAbsolutePath();
	}
	/**
	 * @return  String 图片4的路径
	 */
	public String getPhoto4Path() {
		return chosePhoto4.getSelectedFile().getAbsolutePath();
	}
	/**
	 * @return String 闲置物品的卖家备注*/
	public String getIdleGoodsNote() {
		return note.getText().toString();
	}
	/**
	 * @return String 闲置物品的标签1*/
	public String getGoodsLabel1(){
		return goodsLabelTextField1.getText().toString();
	}
	/**
	 * @return String 闲置物品的标签1*/
	public String getGoodsLabel2(){
		return goodsLabelTextField2.getText().toString();
	}
	
	/**@return 闲置物品种类 **/
	public String getVarietySelectedIndexString(){
		switch(varietyComboBox.getSelectedIndex()){
				case 0: return "书籍类";
				case 1: return "电子产品";
				case 2: return "日常用品";
				case 3: return "食品";
				case 4: return "纪念品";
				case 5: return "其他";
				default: return null;		}
}
	/**
	 * @return boolean 图片一是否被选*/
	public boolean isPhoto1Chosed() {
		return isPhoto1Chosed;
}
	/**
	 * @return boolean 图片2是否被选*/
	public boolean isPhoto2Chosed() {
		return isPhoto2Chosed;
}
	/**
	 * @return boolean 图片3是否被选*/
	public boolean isPhoto3Chosed() {
		return isPhoto3Chosed;
	}

	/**
	 * @return boolean 图片4是否被选*/
	public boolean isPhoto4Chosed() {
		return isPhoto4Chosed;
	}


	
	/**
	 * 
	 * Create the panel.
	 */
	public UploadIdleGoods() {
	//display();
		
	}

	public void display()
	{
		//int width =1980;
		//int height =1000;
	    int width = getWidth();
        int height =getHeight();			
		int subPanelWidth = 1200;
		int subPanelHeight =850;  
		
		
//        setLayout(new GridLayout(2, 1));
//        JLabel fill =new JLabel();
//		add(fill);
		setBorder(new EmptyBorder((height-subPanelHeight)/2, (width-subPanelWidth)/2, (height-subPanelHeight)/2, (width-subPanelWidth)/2));

		
	    JPanel panel = new JPanel();
	    panel.setPreferredSize(new Dimension(1200, 850));
	    add(panel);
	    panel.setLayout(new GridLayout(2, 3, 0, 0));
	    
	    JPanel mainPanel = new JPanel();
	   // panel_8.setPreferredSize(new Dimension(1000, 750));
	    panel.add(mainPanel);
	    mainPanel.setLayout(new GridLayout(7, 1, 10, 2));
	    
	    JLabel label = new JLabel(" 请在下方输入闲置物品的各项信息:");
	    label.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.textHighlight));
	    mainPanel.add(label);
	    label.setFont(Fonts.BOLD_PROMPT_TEXT.getFont());

	    
	    JPanel varietyPanel = new JPanel();
	    mainPanel.add(varietyPanel);
	    varietyPanel.setLayout(new GridLayout(0, 2, 0, 0));
	    
	    JLabel varietyLabel = new JLabel(" 物品种类");
	    varietyLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
	    varietyPanel.add(varietyLabel);
	    
	    varietyComboBox = new JComboBox<String>();
	    varietyComboBox.setForeground(Color.WHITE);
	    varietyComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 20));
	    varietyComboBox.setBackground(SystemColor.textHighlight);
	    varietyPanel.add(varietyComboBox);
	    
	    varietyComboBox.addItem("书籍类");
	    varietyComboBox.addItem("电子产品");
	    varietyComboBox.addItem("日常用品");
	    varietyComboBox.addItem("食品");
	    varietyComboBox.addItem("纪念品");
	    varietyComboBox.addItem("工具类");
	    varietyComboBox.addItem("其他");
	    
	    JPanel salePricePanel = new JPanel();
	    mainPanel.add(salePricePanel);
	    salePricePanel.setLayout(new GridLayout(0, 2, 0, 0));
	    
	    priceTextField = new JTextField();
	    priceTextField.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		priceTextField.selectAll();
	    	}
	    });
	    
	    JLabel salePriceLabel = new JLabel(" 预期售价");
	    salePriceLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
	    salePricePanel.add(salePriceLabel);
	    priceTextField.setText("xxx(人民币)");
	    priceTextField.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());
	    priceTextField.setColumns(10);
	    salePricePanel.add(priceTextField);
	    
	    JPanel originalPricePanel = new JPanel();
	    mainPanel.add(originalPricePanel);
	    originalPricePanel.setLayout(new GridLayout(0, 2, 0, 0));
	    
	    JLabel originalPriceLabel = new JLabel(" 购入价格");
	    originalPriceLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
	    originalPricePanel.add(originalPriceLabel);
	    
	    originalPriceTextField = new JTextField();
	    originalPriceTextField.setText("xxx(人民币)");
	    originalPriceTextField.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		originalPriceTextField.selectAll();
	    	}
	    });
	    originalPriceTextField.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());
	    originalPriceTextField.setColumns(10);
	    originalPricePanel.add(originalPriceTextField);
	    
	    JPanel degreePanel = new JPanel();
	    mainPanel.add(degreePanel);
	    degreePanel.setLayout(new GridLayout(0, 2, 0, 0));
	    
	    JLabel degreeLabel = new JLabel(" 新旧程度");
	    degreeLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
	    degreePanel.add(degreeLabel);
	    
	    degreeTextField = new JTextField();
	    degreeTextField.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		degreeTextField.selectAll();
	    	}
	    });
	    degreeTextField.setText("填写数字:1-9");
	    degreeTextField.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());
	    degreeTextField.setColumns(10);
	    degreePanel.add(degreeTextField);
	    
	    JPanel goodsNamePanel = new JPanel();
	    mainPanel.add(goodsNamePanel);
	    goodsNamePanel.setLayout(new GridLayout(0, 2, 0, 0));
	    
	    JLabel gooodsNameLabel = new JLabel(" 物品名称");
	    gooodsNameLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
	    goodsNamePanel.add(gooodsNameLabel);
	    
	    goodsNameTextField = new JTextField();
	    goodsNameTextField.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		goodsNameTextField.selectAll();
	    	}
	    });
	    goodsNameTextField.setText("方便大家搜索到你的闲置");
	    goodsNameTextField.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());
	    goodsNameTextField.setColumns(10);
	    goodsNamePanel.add(goodsNameTextField);
	    
	    JPanel goodsLabelMainPanel = new JPanel();
	    mainPanel.add(goodsLabelMainPanel);
	    goodsLabelMainPanel.setLayout(new GridLayout(0, 2, 0, 4));
	    
	    JPanel goodsLabelSubPanel1 = new JPanel();
	    goodsLabelMainPanel.add(goodsLabelSubPanel1);
	    goodsLabelSubPanel1.setLayout(new GridLayout(0, 1, 0, 0));
	    
	    JLabel labelLabel = new JLabel(" 物品标签");
	    labelLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
	    goodsLabelSubPanel1.add(labelLabel);
	    
	    JPanel goodsLabelSubPanel2 = new JPanel();
	    goodsLabelMainPanel.add(goodsLabelSubPanel2);
	    goodsLabelSubPanel2.setLayout(new GridLayout(0, 2, 4, 10));
	    
	    goodsLabelTextField1 = new JTextField();
	    goodsLabelTextField1.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		goodsLabelTextField1.selectAll();
	    	}
	    });
	    goodsLabelTextField1.setText("标签1");
	    goodsLabelTextField1.setFont(new Font("微软雅黑", Font.PLAIN, 25));
	    goodsLabelSubPanel2.add(goodsLabelTextField1);
	    goodsLabelTextField1.setColumns(10);
	    
	    goodsLabelTextField2 = new JTextField();
	    goodsLabelTextField2.setText("标签2");
	    goodsLabelTextField2.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		goodsLabelTextField2.selectAll();
	    	}
	    });
	    goodsLabelTextField2.setFont(new Font("微软雅黑", Font.PLAIN, 25));
	    goodsLabelSubPanel2.add(goodsLabelTextField2);
	    goodsLabelTextField2.setColumns(10);
	    
	    JPanel filePanel1 = new JPanel();
	    panel.add(filePanel1);
	    filePanel1.setLayout(null);
	    
	    
	    JLabel toChosePhotoLabel = new JLabel("点击按钮选择上传大于一张小于四张闲置物品图片:");
	    toChosePhotoLabel.setBounds(26, -18, 600, 97);
	    toChosePhotoLabel.setFont(Fonts.BOLD_PROMPT_TEXT.getFont());
	    filePanel1.add(toChosePhotoLabel);
	    
	    
	    JPanel photoPanel1 = new JPanel();
	    photoPanel1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.BLACK));
	    photoPanel1.setBounds(38, 83, 261, 285);
	    filePanel1.add(photoPanel1);
	    photoPanel1.setLayout(new GridLayout(1, 1, 0, 0));;
	    
	    
	    JPanel PhotoPanel2 = new JPanel();
	    PhotoPanel2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	    PhotoPanel2.setBounds(325, 83, 261, 285);
	    filePanel1.add(PhotoPanel2);
	    PhotoPanel2.setLayout(new GridLayout(1, 1, 0, 0));
	    
	    JButton photoButton2 = new JButton("照片2");
	    photoButton2.setForeground(Color.WHITE);
	    photoButton2.setBackground(SystemColor.textHighlight);
	    photoButton2.addMouseListener(new MouseAdapter() {
	    	
	    	public void mouseClicked(MouseEvent e) {
	    		PhotoPanel2.removeAll();
	    		
	    		chosePhoto2= new JFileChooser();
	    		chosePhoto2.showOpenDialog(chosePhoto2);
	    		File file = chosePhoto2.getSelectedFile();
	    		if(file == null || !file.exists() || !file.isFile())
	    			return;
	    		Photo photo2=new Photo(file.getAbsolutePath());
	    		photo2.setScaleFunction(true);
	    		photo2.setDragFunction(true);
	    		photo2.setRecoverFunction(true);
	    		PhotoPanel2.add(photo2);
	    		
				photoFile2 = chosePhoto2.getSelectedFile();
				if(photoFile2 == null || !photoFile2.exists() || photoFile2.isDirectory()) {
					(new PromptBox()).open("未选择图片");
					return;
				}
				
				String temp = photoFile2.getAbsolutePath();
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
					photo2.setPhoto(tool.getImage());
					photoButton2.setText("点击重新选择");
					isPhoto2Chosed=true;
					PhotoPanel2.validate();
	    		
	    	}}
	    });
	    photoButton2.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());;
        photoButton2.setBounds(352, 381, 211, 43);
	    filePanel1.add(photoButton2);
	    
	    JButton photoButton1 = new JButton("照片1");
	    photoButton1.setBounds(62, 381, 211, 43);
	    filePanel1.add(photoButton1);
	    photoButton1.setForeground(Color.WHITE);
	    photoButton1.setBackground(SystemColor.textHighlight);
	    photoButton1.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		photoPanel1.removeAll();
	    		
	    		chosePhoto1= new JFileChooser();
	    		chosePhoto1.showOpenDialog(chosePhoto1);
	    		File file = chosePhoto1.getSelectedFile();
	    		if(file == null || !file.exists() || !file.isFile())
	    			return;
	    		Photo photo1 =new Photo(file.getAbsolutePath());
	    		photo1.setScaleFunction(true);
	    		photo1.setDragFunction(true);
	    		photo1.setRecoverFunction(true);
	    		photoPanel1.add(photo1);
	    		
				photoFile1 = chosePhoto1.getSelectedFile();
				if(photoFile1 == null || !photoFile1.exists() || photoFile1.isDirectory()) {
					(new PromptBox()).open("未选择图片");
					return;
				}
				
				String temp = photoFile1.getAbsolutePath();
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
					photo1.setPhoto(tool.getImage());
					photoButton1.setText("点击重新选择");
					isPhoto1Chosed=true;
					photoPanel1.validate();
	    	}}
	    });
	    photoButton1.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());
    
	    JPanel notePanel = new JPanel();
	    panel.add(notePanel);
	    notePanel.setLayout(null);
	    
	    JLabel noteLabel = new JLabel(" 备注:");
	    noteLabel.setBounds(0, 0, 88, 34);
	    noteLabel.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 120, 215)));
	    noteLabel.setFont(Fonts.BOLD_PROMPT_TEXT.getFont());
	    notePanel.add(noteLabel);
	    note = new JEditorPane();
	    note.setBounds(94, 41, 506, 285);
	    note.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		note.selectAll();
	    	}
	    });
	    note.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
	    note.addFocusListener(new FocusAdapter() {
	    	@Override
	    	public void focusGained(FocusEvent e) {
	    		note.selectAll();
	    	}
	    });
	    note.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 120, 215)));
	    notePanel.add(note);
	    note.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());
	    note.setText("让大家更了解你的闲置物品(255字内)\r\n");
	    note.setPreferredSize(new Dimension(300, 300));
	    JButton confirmUploadButton = new JButton("确认上传");
	    confirmUploadButton.addMouseListener(new UploadIdleGoodsController<UploadIdleGoods>(this));//UploadIdleGoodsController
	    confirmUploadButton.setForeground(Color.WHITE);
	    confirmUploadButton.setBackground(Color.PINK);
	    confirmUploadButton.setFont(Fonts.BOLD_PROMPT_TEXT.getFont());
	    confirmUploadButton.setBounds(94, 342, 506, 43);
	    notePanel.add(confirmUploadButton);
	    
	    

	    JPanel filePanel2 = new JPanel();
	    panel.add(filePanel2);
	    filePanel2.setLayout(null);
	    
	    
	    JPanel photoPanel3 = new JPanel();
	    photoPanel3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	    photoPanel3.setBounds(38, 42, 261, 285);
	    filePanel2.add(photoPanel3);
	    photoPanel3.setLayout(new GridLayout(1, 1, 0, 0));
	    JButton photoButton3 = new JButton("照片3");
	    photoButton3.addMouseListener(new MouseAdapter() {
	    	
	    	public void mouseClicked(MouseEvent e) {
//	    	    chosePhoto3 =new JFileChooser();
//	    		chosePhoto3.showOpenDialog(chosePhoto3);
//	    		Photo photo3 =new Photo(chosePhoto3.getSelectedFile().getAbsolutePath());
//	    		photo3.setScaleFunction(true);
//	    		photo3.setDragFunction(true);
//	    		photo3.setRecoverFunction(true);
//	    		photoPanel3.add(photo3);
//	    		photoButton3.setText("点击重新选择");
//	    		photoPanel3.validate();
	    	//	photoPanel3.validate();
	    		photoPanel3.removeAll();
	    		
	    		chosePhoto3 = new JFileChooser();
	    		chosePhoto3.showOpenDialog(chosePhoto3);
	    		File file = chosePhoto3.getSelectedFile();
	    		if(file == null || !file.exists() || !file.isFile())
	    			return;
	    		Photo photo3 =new Photo(file.getAbsolutePath());
	    		photo3.setScaleFunction(true);
	    		photo3.setDragFunction(true);
	    		photo3.setRecoverFunction(true);
	    		photoPanel3.add(photo3);
	    		
				photoFile3 = chosePhoto3.getSelectedFile();
				if(photoFile3 == null || !photoFile3.exists() || photoFile3.isDirectory()) {
					(new PromptBox()).open("未选择图片");
					return;
				}
				
				String temp = photoFile3.getAbsolutePath();
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
					photo3.setPhoto(tool.getImage());
					photoButton3.setText("点击重新选择");
					photoPanel3.validate();
					isPhoto3Chosed = true;
				}
	    	}
	    });
	   photoButton3.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());;
	    photoButton3.setBackground(SystemColor.textHighlight);
	    photoButton3.setForeground(Color.WHITE);
	    photoButton3.setBounds(66, 342, 207, 43);
	    filePanel2.add(photoButton3);
	    
	    
	    JPanel photoPanel4 = new JPanel();
	    photoPanel4.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	    photoPanel4.setBounds(325, 42, 261, 285);
	    filePanel2.add(photoPanel4);
	    photoPanel4.setLayout(new GridLayout(1, 1, 0, 0));
	    
	    JButton photoButton4 = new JButton("照片4");
	    photoButton4.addMouseListener(new MouseAdapter() {
	    	
	    	public void mouseClicked(MouseEvent e) {
	    		photoPanel4.removeAll();
	    		
	    		chosePhoto4= new JFileChooser();
	    		chosePhoto4.showOpenDialog(chosePhoto4);
	    		File file = chosePhoto4.getSelectedFile();
	    		if(file == null || !file.exists() || !file.isFile())
	    			return;
	    		Photo photo4=new Photo(file.getAbsolutePath());
	    		photo4.setScaleFunction(true);
	    		photo4.setDragFunction(true);
	    		photo4.setRecoverFunction(true);
	    		photoPanel4.add(photo4);
	    		
				photoFile4 = chosePhoto4.getSelectedFile();
				if(photoFile4 == null || !photoFile4.exists() || photoFile4.isDirectory()) {
					(new PromptBox()).open("未选择图片");
					return;
				}
				
				String temp = photoFile4.getAbsolutePath();
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
					photo4.setPhoto(tool.getImage());
					photoButton4.setText("点击重新选择");
					photoPanel4.validate();
					isPhoto4Chosed=true;
	    		
				}
	    	}
	    });
	    photoButton4.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());;
	    photoButton4.setForeground(Color.WHITE);
	    photoButton4.setBackground(SystemColor.textHighlight);
	    photoButton4.setBounds(353, 342, 207, 43);
	    filePanel2.add(photoButton4);

	    }    




	public enum Fonts {
		/*登陆框主要字体*/
		LOGIN_MAIN_FONT( new Font("微软雅黑", Font.BOLD,20) ),
		LOGIN_LABEL_FONT( new Font("等线", Font.PLAIN, 18)),
		BOLD_PROMPT_TEXT(new Font("微软雅黑", Font.BOLD,25)),
		PLAIN_PROMPT_TEXT(new Font("微软雅黑", Font.PLAIN,25));
		
		/**提示框文字的字体*/
		//PROMPT_BOX(new Font("微软雅黑", Font.BOLD, 30)),
		
		/**顶栏标题的字体*/
		//SHE_TITLE(new Font("黑体", Font.PLAIN, 28)),
		
		/**顶栏昵称的字体*/
		//TOP_BAR_NICKNAME(new Font("宋体", Font.PLAIN, 20)),
		
		
		
		/**枚举的字体变量*/
		private Font font;
		
		/**
		 * 枚举的构造函数，用于初始化字体变量。
		 * @param color 需要设置的字体对象
		 */
		private Fonts(Font font) {
			this.font = font;
		}
		
		/**
		 * 获取枚举的字体对象。
		 * @return 当前枚举常量的字体对象
		 */
		public Font getFont() {
			return font;
		}
	}
}
