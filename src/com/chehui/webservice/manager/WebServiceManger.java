package com.chehui.webservice.manager;

import chehui.maichetong.bankservice.BankService;
import chehui.maichetong.bankservice.BankServiceSoap;
import chehui.maichetong.baseinfoservice.BaseInfoService;
import chehui.maichetong.baseinfoservice.BaseInfoServiceSoap;
import chehui.maichetong.messageservice.MessageService;
import chehui.maichetong.messageservice.MessageServiceSoap;
import chehui.maichetong.selleroperationservice.SellerOperationService;
import chehui.maichetong.selleroperationservice.SellerOperationServiceSoap;
import chehui.maichetong.useroperationservice.UserOperationService;
import chehui.maichetong.useroperationservice.UserOperationServiceSoap;

import com.chehui.manage.comm.BaseManager;
;
/****
 * webservice管理类
 * @author zhangtengteng
 *
 */
public class WebServiceManger extends BaseManager {
	private BaseInfoService  baseInfoService;
	private BankService  bankService;
	private MessageService messageService;
	private SellerOperationService sellerOperationService;
	private UserOperationService userOperationService;
	private volatile static WebServiceManger instance = null;
	public static WebServiceManger getInstance() {
		if (instance == null) {
			instance = new WebServiceManger();
		}

		return instance;
	}
	
	
	public BankServiceSoap getBankService(){
		if(bankService==null){
			bankService=new BankService();
		}
		return bankService.getBankServiceSoap12();
	}
	
	public BaseInfoServiceSoap getBaseInfoService(){
		if(baseInfoService==null){
			baseInfoService=new BaseInfoService();
		}
		return baseInfoService.getBaseInfoServiceSoap();
	}
	
	public MessageServiceSoap getMessageService(){
		if(messageService==null){
			messageService=new MessageService();
		}
		return messageService.getMessageServiceSoap12();
	}
	
	public SellerOperationServiceSoap getSellerOperationService(){
		if(sellerOperationService==null){
			sellerOperationService=new SellerOperationService();
		}
		
		return sellerOperationService.getSellerOperationServiceSoap12();
	}
	
	public UserOperationServiceSoap getUserOperationService(){
		if(userOperationService==null){
			userOperationService=new UserOperationService();
		}
		
		return userOperationService.getUserOperationServiceSoap12();
	}

}
