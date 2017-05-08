package info.justdaile.application.components.popup;

import info.justdaile.application.events.PopupOptionEvent;
import info.justdaile.application.strings.Buttons;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public abstract class PopupConfirmWindow extends PopupOptionWindow{
	
	public static final int CONFIRM_ACTION = 0;
	private String btnText;
	
	public PopupConfirmWindow(String title, String message) {
		super(title, message);
		this.btnText = Buttons.CONFIRM_BUTTON;
	}

	public PopupConfirmWindow(String title, String message, String btnText) {
		super(title, message);
		this.btnText = btnText;
	}
	
	@Override
	public Node initButtons() {
		HBox buttonBox = new HBox(15);
		buttonBox.setAlignment(Pos.TOP_CENTER);
		Insets padding = new Insets(0, 15, 15, 15);
		Button confirm = new Button(this.btnText);
		confirm.setAlignment(Pos.TOP_CENTER);
		buttonBox.setPadding(padding);
		confirm.setOnAction(e -> this.onAction(new PopupOptionEvent(e, PopupConfirmWindow.CONFIRM_ACTION)));
		buttonBox.getChildren().add(confirm);
		return buttonBox;
	}
	
}
