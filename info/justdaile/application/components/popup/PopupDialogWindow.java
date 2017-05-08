package info.justdaile.application.components.popup;

import info.justdaile.application.events.PopupOptionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class PopupDialogWindow extends PopupWindow{

	private String message;
	
	public PopupDialogWindow(String title, String message) {
		super(title);
		this.message = message;
		this.setResizable(false);
	}
	
	@Override
	public Node init(){
		final VBox box = new VBox(15);
		Text message = new Text(this.message);
		message.wrappingWidthProperty().bind(box.widthProperty());
		message.setTextAlignment(TextAlignment.CENTER);
		box.getChildren().addAll(message, this.initButtons());
		box.setAlignment(Pos.CENTER);
		box.setPadding(new Insets(15, 15, 15, 15));
		return box;
	}
	
	@Override
	public void onAction(PopupOptionEvent event){
		switch(event.getActionType()){
		
		case PopupWindow.CLOSE_WINDOW_ACTION:
			this.close();
			break;
		
		}
	}
	
	public abstract Node initButtons();

}
