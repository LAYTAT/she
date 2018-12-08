package team.ecust.she.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public final class UploadDemandGoods extends JPanel {
	
	//需求上传所需要的信息
	/**需求物品名称*/
	private JTextField goodsNameTextField;
	/**需求物品最旧程度*/
	private JTextField degreeTextField;
	/**需求物品最高价格**/
	private JTextField priceTextField;
	/**需求物品相关备注*/
	private JEditorPane note;
	/** 需求物品种类**/
	private JComboBox<String> varietyComboBox;
	

	/**
	 * @return  String 需求物品名称
	 */
	public String getDemandGoodsName() {
		return goodsNameTextField.getText().toString();
	}
	/**
	 * @return  String 需求物品预期最高售价
	 */
	public String getDemandGoodsSalePrice() {
		return priceTextField.getText().toString();
	}
	/**
	 * @return  String 闲置物品最旧程度
	 */
	public String getDemandGoodsDgree() {
		return degreeTextField.getText().toString();
	}
	/**
	/**
	 * @return String 需求物品的买家备注*/
	public String getDemandGoodsNote() {
		return note.getText().toString();
	}
	/**@return 闲置物品种类 **/
	public String getVarietySelectedIndex(){
		switch(varietyComboBox.getSelectedIndex()){
				case 0: return "书籍类";
				case 1: return "电子产品";
				case 2: return "日常用品";
				case 3: return "食品";
				case 4: return "纪念品";
				case 5: return "其他";
				default: return null;
						}
	}
	
	/**
	 * Create the panel.
	 */
	public UploadDemandGoods() {
			//display();
	}
	public void display(){

		//int width =1980;
		//int height =1000;
	    int width = getWidth();
       int height =getHeight();			
		int subPanelWidth = 850;
		int subPanelHeight =850;  
		
		
//        setLayout(new GridLayout(2, 1));
//        JLabel fill =new JLabel();
//		add(fill);
		setBorder(new EmptyBorder((height-subPanelHeight)/2, (width-subPanelWidth)/2, (height-subPanelHeight)/2, (width-subPanelWidth)/2));

		
	    JPanel panel = new JPanel();
	    panel.setPreferredSize(new Dimension(850, 850));
	    add(panel);
	    panel.setLayout(new GridLayout(0, 1, 0, 0));
	    
	    JPanel mainPanel = new JPanel();
	   // panel_8.setPreferredSize(new Dimension(1000, 750));
	    panel.add(mainPanel);
	    mainPanel.setLayout(new GridLayout(5, 1, 45, 25));
	    
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
	    
	    JLabel salePriceLabel = new JLabel(" 可接受的最高售价");
	    salePriceLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
	    salePricePanel.add(salePriceLabel);
	    priceTextField.setText("xxx(人民币)");
	    priceTextField.setFont(Fonts.PLAIN_PROMPT_TEXT.getFont());
	    priceTextField.setColumns(10);
	    salePricePanel.add(priceTextField);
	    

	    
	    JPanel degreePanel = new JPanel();
	    mainPanel.add(degreePanel);
	    degreePanel.setLayout(new GridLayout(0, 2, 0, 0));
	    
	    JLabel degreeLabel = new JLabel(" 可接受的最旧程度");
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
	    goodsNamePanel.add(goodsNameTextField);;

	    JPanel notePanel = new JPanel();
	    panel.add(notePanel);
	    notePanel.setLayout(null);
	    
	    JLabel noteLabel = new JLabel(" 备注:");
	    noteLabel.setBounds(0, 36, 88, 34);
	    noteLabel.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 120, 215)));
	    noteLabel.setFont(Fonts.LOGIN_MAIN_FONT.getFont());
	    notePanel.add(noteLabel);
	    note = new JEditorPane();
	    note.setBounds(48, 69, 802, 268);
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
	    note.setText("让大家更了解你的需求物品的要求(300字内)\r\n");
	    note.setPreferredSize(new Dimension(300, 300));
	    
	    JButton confirmUploadButton = new JButton("确认上传");
	    confirmUploadButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    confirmUploadButton.setForeground(Color.WHITE);
	    confirmUploadButton.setBackground(Color.PINK);
	    /**判断是否填写了内容*/
	    confirmUploadButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		if(priceTextField.getText().length() == 0 || priceTextField.getText().equals("xxx(人民币)") )
	    		{
	    			new PromptBox().open("请输入可接受最高售价!");
	    			
	    		}
	    		else if(goodsNameTextField.getText().length() == 0 || goodsNameTextField.getText().equals("方便大家搜索到你的需求") )
	    		{
	    			new PromptBox().open("请输入需求物品名称!");
	    		}
	    		else if(degreeTextField.getText().length() == 0 || degreeTextField.getText().equals("填写数字:1-9")|| degreeTextField.getText().length() >1|| !degreeTextField.getText().toString().matches("[0-9]"))
	    		{
	    			new PromptBox().open("请输入可接受最旧程度!");
	    		}
	    		else if(note.getText().length()==0|| note.getText().length()>=300|| note.getText().equals("让大家更了解你的闲置物品(300字内)\r\n")){
	    			new PromptBox().open("请输入正确的备注!");
	    		}
	    			
	    	}
	    });
	    confirmUploadButton.setFont(Fonts.BOLD_PROMPT_TEXT.getFont());
	    confirmUploadButton.setBounds(163, 349, 573, 60);
	    notePanel.add(confirmUploadButton);
	    
	    

	    

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
