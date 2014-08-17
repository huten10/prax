/*
 * Project EBam
 * Copyright Information
 * (C) 2009-2011, SunGard Inc. All rights reserved.
 * 3F, No.210 Liangjing Road, Zhangjiang High-Tech Park, Shanghai, 201203, China.
 * 
 * This document is protected by copyright. No part of this
 * document may be reproduced in any form by any means without
 * prior written authorization of SunGard.
 * 
 */
package com.prax.core.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.prax.framework.base.model.DomainEntity;

/*
 * Changed by			Reference Number		Description
 * ----------			----------------		------------------------
 */

/**
 * @author Alvin.Hu
 * 
 */
@Entity
@Table(name = "PXProfile")
public class Profile extends DomainEntity {
	private static final long serialVersionUID = 1L;

	private String name;

	private String mobile;

	private Boolean subscribe;

	private String openId;

	private String nickName;

	private int sex;

	private String language;

	private String city;

	private String province;

	private String country;

	private String headImgUrl;

	private Date subscribeTime;

	private String email;

	private String company;

	private String address;
	
	private String qrcode;

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column
	public Boolean getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

	@Column
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Lob 
	@Column
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Column
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(length = 256)
	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	@Column
	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	@Column
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
}
