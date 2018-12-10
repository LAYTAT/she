package team.ecust.she.controller;

import team.ecust.she.view.PromptBox.Tips;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import team.ecust.she.common.RandomNo;
import team.ecust.she.dao.DemandGoodsDao;
import team.ecust.she.dao.DemandGoodsLabelDao;
import team.ecust.she.dao.GoodVareityDao;
import team.ecust.she.model.DemandGoods;
import team.ecust.she.model.DemandLabel;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.view.Index;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.UploadDemandGoods;
import java.text.SimpleDateFormat;


public class UploadDemandController <K extends JComponent> extends MouseAdapter{
	private UploadDemandGoods uploadDemandGoods;
	
	public UploadDemandController(K k){
		uploadDemandGoods=(UploadDemandGoods) k;
	}
	
	public synchronized static void doIt(UploadDemandGoods uploadDemandGoods){
		Index index = Index.getInstance();
		
		//基本信息
		String demandGoodsNo=RandomNo.getDefaultRandomNo().getRandomNo(10);
		String memberNo=index.getMemberNo();
		String goodsName=uploadDemandGoods.getDemandGoodsName();
		String oldestDegree=uploadDemandGoods.getDemandGoodsDgree();
		String highestPrice=uploadDemandGoods.getDemandGoodsSalePrice();
		String label1=uploadDemandGoods.getLabel1();
		String label2=uploadDemandGoods.getLabel2();
		String label3=uploadDemandGoods.getVarietySelectedIndex();
		String note=uploadDemandGoods.getDemandGoodsNote();
		//上传时间:
		SimpleDateFormat sp1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				//SimpleDateFormat("yyyy-MM-dd hh"+"'"+"mm""ss");
		String uploadDemandTime=sp1.format(new java.util.Date());
		
		if(highestPrice.length() == 0 ||highestPrice.equals("xxx(人民币)")||!highestPrice.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$")) {
    		
			new PromptBox().open("请输入预期最高价格!(小数位最多两位)");
			return;
		}
		else if(oldestDegree.length() == 0 || oldestDegree.equals("填写数字:1-9")|| oldestDegree.length() >1|| !oldestDegree.matches("[1-9]"))
		{
			new PromptBox().open("请输入正确的最旧程度!");
			return;
		}
		else if(goodsName.length() == 0 || goodsName.equals("方便大家了解你的需求") || goodsName.length()>=20)
		{
			new PromptBox().open("请输入一个20字内的需求物品名称!");
			return;
		}
		else if(label1.equals(label2)||label2.equals(label3)||label1.equals(label3)){
			new PromptBox().open("请输入不一样的标签!");
			return;
		}
		else if(note.length()==0|| note.length()>=255|| note.equals("让大家更了解你的需求物品的要求(255字内)\r\n")){
			new PromptBox().open("请输入正确的备注!");
			return;
		}
		else{
				DemandGoodsDao demandGoodsDao=new DemandGoodsDao();
				DemandGoodsLabelDao demandGoodsLabelDao=new DemandGoodsLabelDao();
				GoodVareityDao goodVareityDao=new GoodVareityDao();
				
				//传送需求物品内容:
				DemandGoods demandGoods=new DemandGoods(demandGoodsNo);
				demandGoods.setDegree(Integer.valueOf(oldestDegree).intValue());
				demandGoods.setDemandGoodsName(goodsName);
				demandGoods.setMemberNo(memberNo);
				demandGoods.setPrice(Float.valueOf(highestPrice).floatValue());
				demandGoods.setUploadTime(uploadDemandTime);
				demandGoods.setNote(note);
				
				
				if(demandGoodsDao.insertNewDemandGoods(demandGoods)){		
					//传送需求标签1内容:
					if(label1.length()!=0){
						DemandLabel demandLabel1=new DemandLabel(demandGoodsNo);		
						if(!goodVareityDao.existGoodVareity(label1)) // 上传物品的标签一是否存在
						{   		
							String goodsVarietyNo1=RandomNo.getDefaultRandomNo().getRandomNo(10);	
							
							GoodsVariety goodsVariety1=new  GoodsVariety(goodsVarietyNo1);
							goodsVariety1.setKeyWord(label1);
							goodVareityDao.insertNewGoodsVareity(goodsVariety1);								
							demandLabel1.setGoodsVarietyNo(goodsVarietyNo1);
							demandGoodsLabelDao.insertNewDemandLabel(demandLabel1);												
						}
						else if(goodVareityDao.existGoodVareity(label1))  
							{					
							demandLabel1.setGoodsVarietyNo(goodVareityDao.getGoodsVarietyNoByKeyword(label1));
							if(!demandGoodsLabelDao.insertNewDemandLabel(demandLabel1)){
								new PromptBox(Tips.ERROR).open("数据库传输出现了错误!");
								System.out.println("数据库传输出1现错误!");
								return;
								}
							}
						}
					
					if(label2.length()!=0){
						DemandLabel demandLabel2=new DemandLabel(demandGoodsNo);		
						if(!goodVareityDao.existGoodVareity(label2)) 
						{   		
							String goodsVarietyNo2=RandomNo.getDefaultRandomNo().getRandomNo(10);	
							
							GoodsVariety goodsVariety2=new  GoodsVariety(goodsVarietyNo2);
							goodsVariety2.setKeyWord(label1);
							goodVareityDao.insertNewGoodsVareity(goodsVariety2);								
							demandLabel2.setGoodsVarietyNo(goodsVarietyNo2);
							demandGoodsLabelDao.insertNewDemandLabel(demandLabel2);												
						}
						else if(goodVareityDao.existGoodVareity(label2))  
							{						
							demandLabel2.setGoodsVarietyNo(goodVareityDao.getGoodsVarietyNoByKeyword(label2));
							if(!demandGoodsLabelDao.insertNewDemandLabel(demandLabel2)){
								new PromptBox(Tips.ERROR).open("数据库传输出现了错误!");
								System.out.println("数据库传输出2现错误!");
								return;
								}
							}
						}
					
					if(label3.length()!=0){
						DemandLabel demandLabel3=new DemandLabel(demandGoodsNo);		
						if(!goodVareityDao.existGoodVareity(label3)) 
						{ 		
							String goodsVarietyNo3=RandomNo.getDefaultRandomNo().getRandomNo(10);	
							
							GoodsVariety goodsVariety3=new  GoodsVariety(goodsVarietyNo3);
							goodsVariety3.setKeyWord(label1);
							goodVareityDao.insertNewGoodsVareity(goodsVariety3);								
							demandLabel3.setGoodsVarietyNo(goodsVarietyNo3);
							demandGoodsLabelDao.insertNewDemandLabel(demandLabel3);												
						}
						else if(goodVareityDao.existGoodVareity(label3))  
							{				
							demandLabel3.setGoodsVarietyNo(goodVareityDao.getGoodsVarietyNoByKeyword(label3));
							if(!demandGoodsLabelDao.insertNewDemandLabel(demandLabel3)){
								new PromptBox(Tips.ERROR).open("数据库传输出现了错误!");
								System.out.println("数据库传输出3现错误!");
								return;
								}
							}
						}
					
					new PromptBox(Tips.TICK).open("上传成功!");
					UploadDemandGoods up=new UploadDemandGoods();
					index.showInCard(up);
					up.display();
					}
				else{
					new PromptBox(Tips.ERROR).open("上传失败!");
					return;
				}
		
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		doIt(uploadDemandGoods);

	}
}		
				
				
				
				
				
			
		
		

