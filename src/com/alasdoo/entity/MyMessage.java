package com.alasdoo.entity;

/**
 * Holds the type and the messageText
 * @author turzait
 *
 */
public class MyMessage {

	String messageText;
	EMessageType type;
	
	public MyMessage(EMessageType type,String messageText){
		this.type = type;
		this.messageText = messageText;		
	}


	public EMessageType getType() {
		return type;
	}


	public String getMessageText() {
		return messageText;
	}
	

}
