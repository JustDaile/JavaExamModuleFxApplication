package info.justdaile.application.options;

import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class UnselectableCodeOption extends EvaluableOption {

	public UnselectableCodeOption(String text) {
		super(false);
		TextArea area = new TextArea(text);
		area.setEditable(false);
		area.setMaxHeight(100);
		
		HBox box = new HBox(area);
		box.maxWidth(500);
		box.setAlignment(Pos.TOP_LEFT);
		area.setStyle("-fx-base: #9999CE;");
		HBox.setHgrow(area, Priority.ALWAYS);

		this.getChildren().add(box);
	}

	@Override
	public boolean eval() {
		return true;
	}

	@Override
	public void selectionChanged() {
		// Ignore
	}

}
