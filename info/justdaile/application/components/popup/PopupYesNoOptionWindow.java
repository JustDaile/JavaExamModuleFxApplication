package info.justdaile.application.components.popup;

import info.justdaile.application.events.PopupOptionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public abstract class PopupYesNoOptionWindow extends PopupOptionWindow{

	public static final int POSITIVE_ACTION = 0;
	public static final int NEGATIVE_ACTION = 1;
	
	public static final String YES_BUTTON = "Yes";
	public static final String NO_BUTTON = "No";

	private String positiveBtnText;
	private String negativeBtnText;
	
	public PopupYesNoOptionWindow(String title, String message) {
		super(title, message);
		this.positiveBtnText = PopupYesNoOptionWindow.YES_BUTTON;
		this.negativeBtnText = PopupYesNoOptionWindow.NO_BUTTON;
	}

	public PopupYesNoOptionWindow(String title, String message, String positiveBtnText, String negativeBtnText) {
		super(title, message);
		this.positiveBtnText = positiveBtnText;
		this.negativeBtnText = negativeBtnText;
	}

	@Override
	public Node initButtons() {
		HBox buttonBox = new HBox(15);
		buttonBox.setAlignment(Pos.TOP_CENTER);
		Insets padding = new Insets(0, 15, 15, 15);
		Button yes = new Button(this.positiveBtnText);
		yes.setAlignment(Pos.TOP_CENTER);
		Button no = new Button(this.negativeBtnText);
		no.setAlignment(Pos.TOP_CENTER);
		buttonBox.setPadding(padding);
		yes.setOnAction(e -> this.onAction(new PopupOptionEvent(e, PopupYesNoOptionWindow.POSITIVE_ACTION)));
		no.setOnAction(e -> this.onAction(new PopupOptionEvent(e, PopupYesNoOptionWindow.NEGATIVE_ACTION)));
		buttonBox.getChildren().addAll(yes, no);
		return buttonBox;
	}

}
