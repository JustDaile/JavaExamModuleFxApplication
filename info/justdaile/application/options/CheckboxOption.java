package info.justdaile.application.options;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CheckboxOption extends EvaluatableOption {
	
	private String text;
	private CheckBox checkbox;

	public CheckboxOption(String text, boolean isCorrect) {
		super(isCorrect);
		this.text = text;
		this.checkbox = new CheckBox();
		this.checkbox.setOnAction(e -> {
			this.selectionChanged();
		});
		
		Text textbox = new Text(text);
		textbox.setWrappingWidth(500);
		
		HBox box = new HBox(this.checkbox, textbox);
		box.maxWidth(500);
		box.setAlignment(Pos.TOP_LEFT);
		
		this.getChildren().add(box);
	}
	
	@Override
	public void select() {
		this.checkbox.selectedProperty().set(true);
	}

	@Override
	public void deselect() {
		this.checkbox.selectedProperty().set(false);
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
			this.setStyle("-fx-base: #00FF00");
		}else{
			this.setStyle("-fx-base: #FF0000");
		}
	}
	
	@Override
	public void selectionChanged() {
		// ignored
	}

	
}
