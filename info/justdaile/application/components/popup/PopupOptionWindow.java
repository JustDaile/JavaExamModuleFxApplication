package info.justdaile.application.components.popup;

import info.justdaile.application.events.PopupOptionEvent;
import javafx.scene.Node;

public abstract class PopupOptionWindow extends PopupDialogWindow{
			
	public PopupOptionWindow(String title, String message){
		super(title, message);
		this.setResizable(false);
	}
	
	@Override
	public void onAction(PopupOptionEvent event){
		super.onAction(event);
		this.onOptionPressed(event.getActionType());
	}
	
	@Override
	public abstract Node initButtons();
	public abstract void onOptionPressed(int actionType);

}