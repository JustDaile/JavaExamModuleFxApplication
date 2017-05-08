package info.justdaile.application.options;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class InputOption extends EvaluatableOption {

	private String expect[];
	private TextField text;
	
	public InputOption(String expect) {
		super(true);
		this.expect = expect.split("/");
		this.text = new TextField();
		this.text.setPrefWidth(500);
		HBox textBox = new HBox(this.text);
		textBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(this.text, Priority.ALWAYS);
		this.text.minWidthProperty().bind(textBox.widthProperty());
		this.getChildren().add(textBox);
	}
	
	public void reveal() {
		if(this.eval()){
			this.text.setStyle("-fx-base: #00FF00");
		}else{
			this.text.setStyle("-fx-base: #FF0000");
		}
	}
	
	@Override
	public boolean eval(){
		for(int i = 0; i < expect.length; i++){
			if(expect[i].contentEquals(text.getText())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString(){
		return this.text.getText();
	}

	@Override
	public void selectionChanged() {
		// Ignore
	}

}
