package com.zmy.knowledge.contacts;

import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ContactBean implements Comparator<ContactBean> {
	@Override
	public String toString() {
		return "ContactBean{" +
				"contactId=" + contactId +
				", desplayName='" + desplayName + '\'' +
				", phoneNum='" + phoneNum + '\'' +
				", sortKey='" + sortKey + '\'' +
				", photoId=" + photoId +
				", lookUpKey='" + lookUpKey + '\'' +
				", selected=" + selected +
				", formattedNumber='" + formattedNumber + '\'' +
				", pinyin='" + pinyin + '\'' +
				'}';
	}

	private int contactId;
	private String desplayName;
	private String phoneNum;
	private String sortKey;
	private Long photoId;
	private String lookUpKey;
	private int selected = 0;
	private String formattedNumber;
	private String pinyin;

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getDesplayName() {
		return desplayName;
	}

	public void setDesplayName(String desplayName) {
		this.desplayName = desplayName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public String getLookUpKey() {
		return lookUpKey;
	}

	public void setLookUpKey(String lookUpKey) {
		this.lookUpKey = lookUpKey;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public String getFormattedNumber() {
		return formattedNumber;
	}

	public void setFormattedNumber(String formattedNumber) {
		this.formattedNumber = formattedNumber;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}



	@Override
	public int compare(ContactBean o1, ContactBean o2) {
	return 	Collator.getInstance(Locale.CHINESE).compare(o1.getSortKey(), o2.getSortKey());
	}

	public static List sort(List strList)
	{
		ContactBean comp = new ContactBean();
		Collections.sort(strList,comp);
		return strList; //返回排序后的列表
	}

}
