package info.justdaile.application.components;

import info.justdaile.application.components.popup.PopupDialogWindow;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ConfirmWindow extends PopupDialogWindow{
	
	public ConfirmWindow(String title, String message) {
		super(title, message);
	}

	@Override
	public Node initButtons() {
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		Button confirm = new Button("Confirm");
		confirm.setOnAction(e -> {
			this.close();
		});
		buttonBox.getChildren().addAll(confirm);
		return buttonBox;
	}

}
