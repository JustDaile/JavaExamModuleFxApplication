package info.justdaile.application.options;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

public class ButtonOption extends EvaluableOption {

	private Button button;
	
	public ButtonOption(String text, boolean isCorrect) {
		super(isCorrect);
		this.button = new Button(text);
		
		this.button.setOnAction(e -> {
			this.selectionChanged();
		});
		
		this.button.setFont(new Font(16));
		this.setAlignment(Pos.CENTER);
		HBox.setHgrow(this.button, Priority.ALWAYS);
		
		this.getChildren().add(this.button);
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
	public void select(){
		super.select();
		this.setStyle("-fx-base: #EEEEFF");
	}
	
	@Override
	public void deselect(){
		super.deselect();
		this.setStyle("-fx-base: #EEEEEE");
	}
		
	@Override
	public void selectionChanged(){
		super.toggle();
	}
	
	@Override
	public String toString(){
		return this.button.getText();
	}
	
}
