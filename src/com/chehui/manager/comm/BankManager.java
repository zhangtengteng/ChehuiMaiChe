package com.chehui.manager.comm;

import java.util.List;
import java.util.Stack;

import android.content.Intent;

import chehui.maichetong.bankservice.TBankCard;

import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.LogN;
import com.example.myproject.R;

/**
 * 提现管理
 * 
 * @author zhangtengteng
 * 
 */
public class BankManager {

	private static volatile BankManager instance;
	private String userName;
	private String money;
	private int balance;
	private List<TBankCard> bankCards;

	public List<TBankCard> getBankCards() {
		return bankCards;
	}

	public void setBankCards(List<TBankCard> bankCards) {
		this.bankCards = bankCards;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public static void setInstance(BankManager instance) {
		BankManager.instance = instance;
	}

	/**
	 * 创建单例类，提供静态方法调用
	 * 
	 * @return ActivityManager
	 */
	public static BankManager getInstance() {
		if (instance == null) {
			instance = new BankManager();
		}
		return instance;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}
