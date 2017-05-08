package info.justdaile.application.events;

import javafx.event.Event;

public class PopupOptionEvent {

	private final Event e;
	private final int action;
	
	public PopupOptionEvent(Event e, int action) {
		this.e = e;
		this.action = action;
	}

	public int getActionType() {
		return action;
	}
	
	public Event getEvent(){
		return e;
	}

}
