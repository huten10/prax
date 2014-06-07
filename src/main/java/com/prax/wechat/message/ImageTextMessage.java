package com.prax.wechat.message;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * 图文消息（只能回复）
 * @author marker
 * @date 2013-8-25 上午8:53:43
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class ImageTextMessage extends Message {

	// 图文消息个数，限制为10条以内
	private String articleCount;
	// 图文消息的数据
	private List<MessageItem> items = new ArrayList<MessageItem>(3);
 
	
	
	/**
	 * 默认构造
	 * */
	public ImageTextMessage() {
		this.head = new MessageHead();
		this.head.setMsgType(Message.MSG_TYPE_IMAGE_TEXT);
	}
	
	@Override
	public void write(Document document) {
		Element root = document.createElement(Elements.ROOT);
		head.write(root, document);
		Element articleCountElement = document.createElement(Elements.ARTICLE_COUNT);
		articleCountElement.setTextContent(this.articleCount);
		
		Element articlesElement = document.createElement(Elements.ARTICLES);
		int size = Integer.parseInt(this.articleCount);
		for(int i = 0; i<size; i++){
			MessageItem currentItem = items.get(i);//获取当前
			Element itemElement = document.createElement(Elements.ITEM);
			Element titleElement = document.createElement(Elements.TITLE);
			titleElement.setTextContent(currentItem.getTitle());
			Element descriptionElement = document.createElement(Elements.DESCRITION);
			descriptionElement.setTextContent(currentItem.getDescription());
			Element picUrlElement = document.createElement(Elements.PIC_URL);
			picUrlElement.setTextContent(currentItem.getPicUrl());
			Element urlElement = document.createElement(Elements.URL);
			urlElement.setTextContent(currentItem.getUrl());
			itemElement.appendChild(titleElement);
			itemElement.appendChild(descriptionElement);
			itemElement.appendChild(picUrlElement);
			itemElement.appendChild(urlElement);
			
			articlesElement.appendChild(itemElement);
		}
		
 

		root.appendChild(articleCountElement);
		root.appendChild(articlesElement);
		
		document.appendChild(root);
	}

	@Override
	public void read(Document document) {
		// TODO Auto-generated method stub
		
	}
  
	
	public void addItem(MessageItem item){
		this.items.add(item);
		this.reflushArticleCount();
	}
	
	public void removeItem(int index){
		this.items.remove(index);
		this.reflushArticleCount();
	}
	
	
	
	/**
	 * 刷新当前文章条数
	 * */
	private void reflushArticleCount(){
		this.articleCount = ""+this.items.size();
	}
	
	
	
	
}
