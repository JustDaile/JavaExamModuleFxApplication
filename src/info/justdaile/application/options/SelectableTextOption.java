package info.justdaile.application.options;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SelectableTextOption extends EvaluatableOption {
	
	private Text text;
	private static Font font = new Font(18);
	
	public SelectableTextOption(String text, boolean isCorrect) {
		super(isCorrect);
		this.text = new Text(text);	
		this.text.setFont(SelectableTextOption.font);
		this.text.setTextAlignment(TextAlignment.LEFT);
		this.text.setStyle("-fx-fill: #000000;");
		
		this.getChildren().add(this.text);
	}

	@Override
	public void selectionChanged() {
		this.toggle();
		if(this.isSelected()){
			this.text.setStyle("-fx-fill: #99FF99;");
		}else{
			this.text.setStyle("-fx-fill: #000000;");
		}
	}
	
	public void reveal() {
		if(this.isValid()){
			if(this.isSelected()){
				this.text.setStyle("-fx-fill: #00FF00;");
			}else{
				this.text.setStyle("-fx-fill: #FF0000;");
			}
		}else{
			if(this.isSelected()){
				this.text.setStyle("-fx-fill: #FF0000;");
			}
		}
	}
	
	@Override
	public boolean eval(){
		return (this.isSelected() && this.isValid() || !this.isSelected() && !this.isValid());
	}
	
	@Override
	public String toString(){
		return this.text.getText();
	}

}
