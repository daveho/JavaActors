package edu.ycp.cs365.actor;

public class Message {
	private final Object content;
	private final ActorId senderId, recipientId;
	
	public Message(Object content, ActorId senderId, ActorId recipientId) {
		this.content = content;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}
	
	public Object getContent() {
		return content;
	}
	
	public ActorId getSenderId() {
		return senderId;
	}
	
	public ActorId getRecipientId() {
		return recipientId;
	}
}
