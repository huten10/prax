/**
 * 
 */
package com.prax.wechat.listener;

import com.prax.core.user.entity.Profile;
import com.prax.core.user.entity.User;
import com.prax.framework.base.model.UCN;
import com.prax.framework.context.Context;
import com.prax.wechat.UserInfo;
import com.prax.wechat.message.EventMessage;
import com.prax.wechat.message.Message;
import com.prax.wechat.message.TextMessage;

/**
 * @author Huanan
 * 
 */
public class UserSubscribe extends AbstractMessageListener<EventMessage> {

	public UserSubscribe() {
		this.matchers.put("event", EventMessage.SUBSCRIBE);
	}

	public Message onMessage(EventMessage msg) {
		TextMessage reMsg = new TextMessage();
		reMsg.setFromUserName(msg.getToUserName());
		reMsg.setToUserName(msg.getFromUserName());
		reMsg.setCreateTime(msg.getCreateTime());

		reMsg.setContent("【菜单】\n" + "1. 功能菜单\n" + "2. 图文消息测试\n" + "3. 图片消息测试\n");

		UserInfo userInfo = operations.getUserInfo(msg.getFromUserName());
		createUser(userInfo);

		return msg;
	}

	private void createUser(UserInfo userInfo) {
		UCN domain = Context.getCurrentDomain();
		User user = userService.getByExtenralId(userInfo.getOpenid(), new String[] { "profile" });
		if (user == null) {
			user = new User();
			user.setDomainUuid(domain.getUuid());
			user.setExtenralId(userInfo.getOpenid());
			user.setLogin("WX_" + System.currentTimeMillis());
			user.setName(userInfo.getNickname());

			Profile profile = new Profile();
			user.setProfile(profile);
			profile.setDomainUuid(domain.getUuid());
			profile.setCity(userInfo.getCity());
			profile.setCountry(userInfo.getCountry());
			profile.setHeadImgUrl(userInfo.getHeadimgurl());
			profile.setLanguage(userInfo.getLanguage());
			profile.setNickName(userInfo.getNickname());
			profile.setOpenId(userInfo.getOpenid());
			profile.setProvince(userInfo.getProvince());
			profile.setSex(userInfo.getSex());
			profile.setSubscribeTime(userInfo.getSubscribe_time());
			profile.setSubscribe(userInfo.isSubscribe());
		}
		user.getProfile().setSubscribe(Boolean.TRUE);

		if (user.getUuid() != null)
			userService.update(user);
		else
			userService.add(user);
	}

}
