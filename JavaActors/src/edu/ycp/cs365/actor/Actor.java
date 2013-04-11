// Java actors framework
// Copyright (C) 2013, David Hovemeyer <david.hovemeyer@gmail.com>
//
// Permission is hereby granted, free of charge, to any person obtaining a 
// copy of this software and associated documentation files (the "Software"),
// to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included 
// in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
// THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
// OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
// ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
// OTHER DEALINGS IN THE SOFTWARE.

package edu.ycp.cs365.actor;

/**
 * Actor superclass: all actor classes must extend this class
 * and override the {@link Actor#react(Message)} method.
 * 
 * @author David Hovemeyer
 */
public abstract class Actor {
	private ActorGroup group;
	private int index;
	
	/**
	 * Constructor.
	 */
	public Actor() {
	}
	
	/**
	 * React to a {@link Message}.
	 * 
	 * @param message the message to which the actor should react
	 */
	public abstract void react(Message message);
	
	/**
	 * Send a message.
	 * 
	 * @param recipient the {@link ActorId} of the recipient {@link Actor}
	 * @param contents  the contents of the message
	 */
	public void send(ActorId recipient, Object contents) {
		group.send(new ActorId(this.index), recipient, contents);
	}
	
	/**
	 * Spawn a new {@link Actor}.
	 * 
	 * @param actor the new actor
	 * @return the {@link ActorId} of the new actor
	 */
	public ActorId spawn(Actor actor) {
		return group.addActor(actor);
	}

	/**
	 * Actors can call this method if they receive a message
	 * with unexpected content.
	 * 
	 * @param content the unexpected content
	 */
	protected void unexpectedMessageContent(Object content) {
		throw new IllegalArgumentException("Unexpected message type: " + content.getClass().getSimpleName());
	}
	
	void setGroup(ActorGroup group) {
		this.group = group;
	}
	
	void setIndex(int index) {
		this.index = index;
	}
}
