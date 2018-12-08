package team.ecust.she.view;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.MatteBorder;

import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class DemandGoodsList extends JPanel {
	
	private JLabel setNamelLabel;   
	private JLabel setPriceLabel;
	private JLabel setVarietyLabel;
	private JLabel setDegreeLabel;
	private JTextArea noteTextField;


	/**
	 * @param setlText 传入string设置显示物品名称
	 */
	public void setSetNamelLabel(String setlText) {
		this.setNamelLabel.setText(setlText);
	}

	/**
	 * @param setlText 传入string设置显示最高价格
	 */
	public void setSetPriceLabel(String setlText) {
		this.setPriceLabel.setText(setlText);
	}

	/**
	 * @param setlText 传入string设置显示种类
	 */
	public void setSetVarietyLabel(String setlText) {
		this.setVarietyLabel.setText(setlText);
	}

	/**
	 * @param setlText 传入string设置显示最旧新旧程度
	 */
	public void setSetDegreeLabel(String setlText) {
		this.setDegreeLabel.setText(setlText);
	}
	/**
	 * @param setlText 传入string设置显示备注
	 */
	public void setNoteText(String setlText) {
		this.noteTextField.setText(setlText);
	}
	
	
	/**
	 * Create the panel.
	 */
	public DemandGoodsList(String DemandGoodsNo) {
		//setPreferredSize(new Dimension(1368, 646));
		//int width =1980;
		//int height =1000;
	    int width = getWidth();
       int height =getHeight();		
		int subPanelHeight =200;  
		setBorder(new EmptyBorder((height-subPanelHeight)/2, width, (height-subPanelHeight)/2, width));
		
	    JPanel mainPanel = new JPanel();
	    mainPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.textHighlight));
	    mainPanel.setPreferredSize(new Dimension(1000, 200));
	    add(mainPanel);
	    mainPanel.setLayout(new GridLayout(0, 4, 4, 10));
	    
	    JPanel subPanel1 = new JPanel();
	    mainPanel.add(subPanel1);
	    subPanel1.setLayout(new GridLayout(5, 0, 5, 5));
	    
	    JLabel nameLabel = new JLabel("需求物品名称:");
	    nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
	    subPanel1.add(nameLabel);
	    
	    setNamelLabel = new JLabel("New label");
	    subPanel1.add(setNamelLabel);
	    
	    JLabel pricelabel = new JLabel("可接受最高售价:");
	    pricelabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
	    subPanel1.add(pricelabel);
	    
	    setPriceLabel = new JLabel("New label");
	    subPanel1.add(setPriceLabel);
	    
	    JPanel subPanel2 = new JPanel();
	    mainPanel.add(subPanel2);
	    subPanel2.setLayout(new GridLayout(5, 0, 5, 5));
	    
	    JLabel VarietyLabel = new JLabel("需求物品种类:");
	    VarietyLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
	    subPanel2.add(VarietyLabel);
	    
	    setVarietyLabel = new JLabel("New label");
	    subPanel2.add(setVarietyLabel);
	    
	    JLabel label_1 = new JLabel("可接受最旧程度:");
	    label_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
	    subPanel2.add(label_1);
	    
	    setDegreeLabel = new JLabel("New label");
	    subPanel2.add(setDegreeLabel);
	    

	   
	    JPanel subPanel7 = new JPanel();
	    mainPanel.add(subPanel7);
	    //subPanel7.setLayout(new GridLayout(1, 1, 0, 0));
	    subPanel7.setLayout(new BorderLayout(0,0));
	    
//	    JTextArea noteTextPane = new JTextArea();
//	    noteTextPane.setEditable(false);
//	    subPanel7.add(noteTextPane);
	    
	    JScrollPane noteScrollPane = new JScrollPane();
	    noteScrollPane.setToolTipText("备注:");
	    subPanel7.add(noteScrollPane,BorderLayout.CENTER);
	    
	    JLabel lblNewLabel_1 = new JLabel("卖家备注:");
	    lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
	    noteScrollPane.setColumnHeaderView(lblNewLabel_1);
	    
	    noteTextField = new JTextArea();
	    noteTextField.setLineWrap(true);
	    noteTextField.setRows(16);
	    noteTextField.setFont(new Font("微软雅黑", Font.PLAIN, 13));
	    noteTextField.setEditable(false);
	    noteScrollPane.setViewportView(noteTextField);
	    noteTextField.setText("测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容");
	    noteTextField.setColumns(6);
	    
//	    noteScrollPane.setViewportView(noteTextPane);
	    
	    JPanel subPanel8 = new JPanel();
	    mainPanel.add(subPanel8);
	    subPanel8.setLayout(new GridLayout(5, 0, 5, 5));
	    
	    JPanel panel = new JPanel();
	    subPanel8.add(panel);
	    
	    JPanel panel_1 = new JPanel();
	    subPanel8.add(panel_1);
	    
	    JButton button_1 = new JButton("联系买家");
	    button_1.setForeground(Color.WHITE);
	    button_1.setFont(new Font("微软雅黑", Font.PLAIN, 25));
	    button_1.setBackground(SystemColor.textHighlight);
	    subPanel8.add(button_1);
	    
	    JPanel panel_2 = new JPanel();
	    subPanel8.add(panel_2);
	    
	    JButton button = new JButton("买家信息");
	    button.setForeground(Color.WHITE);
	    button.setFont(new Font("微软雅黑", Font.PLAIN, 25));
	    button.setBackground(SystemColor.textHighlight);
	    subPanel8.add(button);
	 
	}

	
}
