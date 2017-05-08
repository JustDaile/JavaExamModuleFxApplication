package info.justdaile.application.components;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AnswerView extends VBox{

	public AnswerView(String question, String answer, boolean isCorrect){
		Text questionView = new Text("Question: " + question + "\n");
		Text answerView = new Text("Answered : " + answer + "\n");
		TextFlow flow = new TextFlow(questionView, answerView);
		CheckBox correctView = new CheckBox();
		correctView.setText("correct");
		if(isCorrect){
			correctView.fire();
		}
		correctView.setDisable(true);
		this.setPrefWidth(500);
		this.setMinWidth(500);
		this.setMaxWidth(500);
		this.getChildren().addAll(flow, correctView);
	}
	
}
