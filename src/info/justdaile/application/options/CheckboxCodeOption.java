package info.justdaile.application.options;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CheckboxCodeOption extends EvaluableOption {

	private String text;
	private CheckBox checkbox;
	
	public CheckboxCodeOption(String text, boolean isCorrect) {
		super(isCorrect);
		this.text = text;
		this.checkbox = new CheckBox();
		this.checkbox.setOnAction(e -> {
			this.selectionChanged();
		});
		
		TextArea area = new TextArea(text);
		area.setEditable(false);
		area.setMaxHeight(100);
		
		HBox box = new HBox(this.checkbox, area);
		box.maxWidth(500);
		box.setAlignment(Pos.TOP_LEFT);
		area.setStyle("-fx-base: #9999CE;");
		HBox.setHgrow(area, Priority.ALWAYS);

		this.getChildren().add(box);
	}

	@Override
	public boolean isSelected() {
		return this.checkbox.isSelected();
	}

	@Override
	public String toString(){
		return this.text;
	}
	
	@Override
	public void reveal() {
		if(this.isValid()){
			this.checkbox.setStyle("-fx-base: #00FF00");
		}else{
			this.checkbox.setStyle("-fx-base: #FF0000");
		}
	}
	
	@Override
	public void selectionChanged() {
		// ignored
	}

	@Override
	public boolean eval(){
		return (this.isSelected() && this.isValid() || !this.isSelected() && !this.isValid());
	}


	@Override
	public void select() {
		this.checkbox.selectedProperty().set(true);
	}

	@Override
	public void deselect() {
		this.checkbox.selectedProperty().set(false);
	}
	
}
