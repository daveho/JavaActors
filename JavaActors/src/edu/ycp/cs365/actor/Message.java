package edu.ycp.cs365.actor;

/**
 * A message sent from one {@link Actor} to another.
 */
public class Message {
	private final Object content;
	private final ActorId senderId, recipientId;
	
	/**
	 * Constructor.
	 * 
	 * @param content     the content of the message
	 * @param senderId    the {@link ActorId} of the sender
	 * @param recipientId the {@link ActorId} of the recipient
	 */
	public Message(Object content, ActorId senderId, ActorId recipientId) {
		this.content = content;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}
	
	/**
	 * @return the message content
	 */
	public Object getContent() {
		return content;
	}
	
	/**
	 * @return the {@link ActorId} of the sender
	 */
	public ActorId getSenderId() {
		return senderId;
	}
	
	/**
	 * @return the {@link ActorId} of the recipient
	 */
	public ActorId getRecipientId() {
		return recipientId;
	}
}
