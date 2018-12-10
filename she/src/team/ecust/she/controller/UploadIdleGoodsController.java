package team.ecust.she.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
/**上传闲置物品*/
import team.ecust.she.common.RandomNo;
import team.ecust.she.dao.IdleGoodsDao;
import team.ecust.she.dao.IdleLabelDao;
import team.ecust.she.dao.PhotoDao;
import  team.ecust.she.dao.GoodVareityDao;
import team.ecust.she.model.GoodsVariety;
import team.ecust.she.model.IdleGoods;
import team.ecust.she.model.IdleLabel;
import team.ecust.she.model.Picture;
//import team.ecust.she.view.EditInfo;
import team.ecust.she.view.Index;
import team.ecust.she.view.PromptBox;
import team.ecust.she.view.PromptBox.Tips;
import team.ecust.she.view.UploadIdleGoods;


public class UploadIdleGoodsController <K extends JComponent> extends MouseAdapter{
		private UploadIdleGoods uploadIdleGoods;
		
		public UploadIdleGoodsController(K k){
			uploadIdleGoods=(UploadIdleGoods) k;
		}
		
		public synchronized static void dolt(UploadIdleGoods uploadIdleGoods){
			Index index = Index.getInstance();
			//String variety=uploadIdleGoods.getVarietySelectedIndexString();
			String price=uploadIdleGoods.getIdleGoodsSalePrice();
		//	float priceFloat=Float.valueOf(price).floatValue();
			//System.out.println("price="+price);
			String originalPrice=uploadIdleGoods.getIdleGoodsOriginalPrice();
		//	float originalPriceFloat=Float.valueOf(originalPrice).floatValue();
			String goodsName=uploadIdleGoods.getIdleGoodsName();
			String degree=uploadIdleGoods.getIdleGoodsDgree();
		//	int degreeInt=Integer.valueOf(degree).intValue();
			String note=uploadIdleGoods.getIdleGoodsNote();
			String label1=uploadIdleGoods.getGoodsLabel1();
			String label2=uploadIdleGoods.getGoodsLabel2();
			//注册时间:
			SimpleDateFormat sp1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//SimpleDateFormat("yyyy-MM-dd hh"+"'"+"mm""ss");
			String uploadIdleTime=sp1.format(new Date());
			//生成10位闲置物品编号:
			String idleGoodsNo=RandomNo.getDefaultRandomNo().getRandomNo(10);

			//生成10位goodsVarietyNo:
			//String goodsVarietyNo=RandomNo.getDefaultRandomNo().getRandomNo(10);
//			System.out.println("idleGoodsNo"+idleGoodsNo);
//			System.out.println("goodsVarietyNo"+goodsVarietyNo);

					if(price.length() == 0 ||price.equals("xxx(人民币)")||!price.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$")) {
		    		
		    			new PromptBox().open("请输入预期价格!(小数位最多两位)");
		    			return;
		    		}
					else if(originalPrice.length() == 0 || originalPrice.equals("xxx(人民币)") || !originalPrice.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"))
		    		{
		    			new PromptBox().open("请输入原价!(小数位最多两位)");
		    			return;
		    		}		
					else if(degree.length() == 0 || degree.equals("填写数字:1-9")|| degree.length() >1|| !degree.matches("[1-9]"))
		    		{
		    			new PromptBox().open("请输入正确的新旧程度!");
		    			return;
		    		}
					else if(goodsName.length() == 0 || goodsName.equals("方便大家搜索到你的闲置") || goodsName.length()>=20)
		    		{
		    			new PromptBox().open("请输入一个20字内的物品名称名称!");
		    			return;
		    		}
					else if(note.length()==0|| note.length()>=255|| note.equals("让大家更了解你的闲置物品(255字内)\r\n")){
		    			new PromptBox().open("请输入正确的备注!");
		    			return;
		    		}
					else if((!label1.equals("") && !label2.equals("")) && (label1.equals(label2)||label2.equals(uploadIdleGoods.getVarietySelectedIndexString())||label1.equals(uploadIdleGoods.getVarietySelectedIndexString()))){
						new PromptBox().open("请输入不一样的标签!");
						return;
					}

					else if(!uploadIdleGoods.isPhoto1Chosed() && !uploadIdleGoods.isPhoto2Chosed() && !uploadIdleGoods.isPhoto3Chosed() && !uploadIdleGoods.isPhoto4Chosed() )
					{
						new PromptBox(Tips.ERROR).open("请至少选择一张图片!");
						return;
					}
					else {
					
					//传送
						IdleGoods idleGoods= new IdleGoods(idleGoodsNo);
						idleGoods.setDegree(Integer.valueOf(degree).intValue());
						idleGoods.setMemberNo(index.getMemberNo());
						idleGoods.setIdleGoodsName(goodsName);
						idleGoods.setSalePrice(Float.valueOf(price).floatValue());
						idleGoods.setOriginalPrice(Float.valueOf(originalPrice).floatValue());
						idleGoods.setUploadTime(uploadIdleTime);
						idleGoods.setNote(note);		
						idleGoods.setKeyWord1(uploadIdleGoods.getGoodsLabel1());
						idleGoods.setKeyWord2(uploadIdleGoods.getGoodsLabel2());
						idleGoods.setKeyWord3(uploadIdleGoods.getVarietySelectedIndexString());
						
						IdleLabelDao idleLabelDao=new IdleLabelDao();
						GoodVareityDao goodVareityDao =new GoodVareityDao();
						IdleGoodsDao idleGoodsDao=new IdleGoodsDao();
//						System.out.println("idleGoods.getKeyWord1():"+idleGoods.getKeyWord1());
//						System.out.println("idleGoods.getKeyWord2():"+idleGoods.getKeyWord2());
//						System.out.println("idleGoods.getKeyWord3():"+idleGoods.getKeyWord3());
									
						//上传闲置基本信息到idlegoods表
						if(idleGoodsDao.insertNewIdleGoods(idleGoods)){
							
							IdleLabel idleLabel1=new IdleLabel(idleGoodsNo);	//闲置物品的标签1
							IdleLabel idleLabel2=new IdleLabel(idleGoodsNo);	//闲置物品的标签2
							IdleLabel idleLabel3=new IdleLabel(idleGoodsNo);	//闲置物品的标签3		
								//上传闲置标签1到idlegoodsLabel表
							if(!goodVareityDao.existGoodVareity(idleGoods.getKeyWord1()))  // 上传物品的标签一是否存在
								{   //不存在则将keyword1添加到goodsvarity表里,并且将对应的的goodsvareityNo存到idlelabel表里				
									String goodsVarietyNo1=RandomNo.getDefaultRandomNo().getRandomNo(10);	
									
									GoodsVariety goodsVariety1=new  GoodsVariety(goodsVarietyNo1);
									goodsVariety1.setKeyWord(idleGoods.getKeyWord1());
									goodVareityDao.insertNewGoodsVareity(goodsVariety1);								
									
									idleLabel1.setGoodsVarietyNo(goodsVarietyNo1);
									idleLabelDao.insertNewIdleLabel(idleLabel1);												
								}
							else if(goodVareityDao.existGoodVareity(idleGoods.getKeyWord1()))  
									{//如果存在就得到goodvarietyNo存到idlelabel里面								
									idleLabel1.setGoodsVarietyNo(goodVareityDao.getGoodsVarietyNoByKeyword(idleGoods.getKeyWord1()));
									if(!idleLabelDao.insertNewIdleLabel(idleLabel1)){
										new PromptBox(Tips.ERROR).open("数据库传输出现了错误!");
										System.out.println("数据库传输出1现错误!");
										return;
										}
									}
							//上传闲置标签2idlegoodsLabel表
							 if(!goodVareityDao.existGoodVareity(idleGoods.getKeyWord2()))  // 上传物品的标签2是否存在
							{   //不存在则将keyword2添加到goodsvarity表里,并且将对应的的goodsvareityNo存到idlelabel表里				
								String goodsVarietyNo2=RandomNo.getDefaultRandomNo().getRandomNo(10);	
								
								GoodsVariety goodsVariety2=new  GoodsVariety(goodsVarietyNo2);
								goodsVariety2.setKeyWord(idleGoods.getKeyWord2());
								goodVareityDao.insertNewGoodsVareity(goodsVariety2);								
								
								idleLabel2.setGoodsVarietyNo(goodsVarietyNo2);
								idleLabelDao.insertNewIdleLabel(idleLabel2);
																
							}
							 else if(goodVareityDao.existGoodVareity(idleGoods.getKeyWord2()))  
								{//如果存在就得到goodvarietyNo存到idlelabel里面								
								idleLabel2.setGoodsVarietyNo(goodVareityDao.getGoodsVarietyNoByKeyword(idleGoods.getKeyWord2()));
								System.out.println("goodVareityDao.getGoodsVarietyNoByKeyword(idleGoods.getKeyWord2())="+goodVareityDao.getGoodsVarietyNoByKeyword(idleGoods.getKeyWord2()));
								System.out.println("idleGoods.getKeyWord2()="+idleGoods.getKeyWord2());
								if(!idleLabelDao.insertNewIdleLabel(idleLabel2)){
									new PromptBox(Tips.ERROR).open("数据库传输出现错误!");
									System.out.println("数据库传输出2现错误!");
									return;
									}
								}
							//上传闲置标签3idlegoodsLabel表
							  if(!goodVareityDao.existGoodVareity(idleGoods.getKeyWord3()))  // 上传物品的标签3是否存在
								{   //不存在则将keyword3添加到goodsvarity表里,并且将对应的的goodsvareityNo存到idlelabel表里				
									String goodsVarietyNo3=RandomNo.getDefaultRandomNo().getRandomNo(10);	
									
									GoodsVariety goodsVariety3=new  GoodsVariety(goodsVarietyNo3);
									goodsVariety3.setKeyWord(idleGoods.getKeyWord3());
									goodVareityDao.insertNewGoodsVareity(goodsVariety3);								
									
									idleLabel3.setGoodsVarietyNo(goodsVarietyNo3);
									idleLabelDao.insertNewIdleLabel(idleLabel3);						
								}	
							  else if(goodVareityDao.existGoodVareity(idleGoods.getKeyWord3()))  
									{//如果存在就得到goodvarietyNo存到idlelabel里面								
									idleLabel3.setGoodsVarietyNo(goodVareityDao.getGoodsVarietyNoByKeyword(idleGoods.getKeyWord3()));
									if(!idleLabelDao.insertNewIdleLabel(idleLabel3)){
										new PromptBox(Tips.ERROR).open("数据库传输出现错误!");
										System.out.println("数据库传输3出现错误!");
										return;
										}
									}
								 	//上传图片
									PhotoDao photoDao=new PhotoDao();
									  if(uploadIdleGoods.isPhoto1Chosed()){
										  String photoFilePath1=uploadIdleGoods.getPhoto1Path();
										  Picture picture1=new Picture(idleGoodsNo,"1");
										  picture1.setPhoto(photoFilePath1);
										  if(! photoDao.UploadPhoto(picture1)){
											  new PromptBox(Tips.ERROR).open("图片传输失败!");
											  return;
										  	}		  
										}
									    if(uploadIdleGoods.isPhoto2Chosed()){
									    	String photoFilePath2=uploadIdleGoods.getPhoto2Path();
									    	Picture picture2=new Picture(idleGoodsNo,"2");
									    	picture2.setPhoto(photoFilePath2);
									    	if(! photoDao.UploadPhoto(picture2)){
												  new PromptBox(Tips.ERROR).open("图片传输失败!");
												  return;
											  	}		 
									    
										}
									    if(uploadIdleGoods.isPhoto3Chosed()){
											String photoFilePath3=uploadIdleGoods.getPhoto3Path();
											Picture picture3=new Picture(idleGoodsNo,"3");
											picture3.setPhoto(photoFilePath3);
											if(! photoDao.UploadPhoto(picture3)){
												  new PromptBox(Tips.ERROR).open("图片传输失败!");
												  return;
											  	}		
									    }
									    if(uploadIdleGoods.isPhoto4Chosed()){
											String photoFilePath4=uploadIdleGoods.getPhoto4Path();
											Picture picture4=new Picture(idleGoodsNo,"4");
											picture4.setPhoto(photoFilePath4);
											if(! photoDao.UploadPhoto(picture4)){
												  new PromptBox(Tips.ERROR).open("图片传输失败!");
												  return;
											  	}		
									    } 	 
									
								 
							new PromptBox(Tips.TICK).open("上传成功!");
							UploadIdleGoods up=new UploadIdleGoods();
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
		    		dolt(uploadIdleGoods);

		    	}
		
}
