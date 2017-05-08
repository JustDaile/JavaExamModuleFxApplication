package info.justdaile.application.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class QuestionDialog extends FlowPane{

	private TextFlow outputArea = new TextFlow();
	
	public QuestionDialog(){		
		Insets padding = new Insets(5, 5, 5, 5);
		outputArea.setMinHeight(50);
		outputArea.setTextAlignment(TextAlignment.CENTER);
		outputArea.setPadding(padding);
		outputArea.setPrefWidth(500);
		outputArea.setMinWidth(500);
		outputArea.setMaxWidth(500);
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(outputArea);
		HBox.setHgrow(outputArea, Priority.ALWAYS);
	}
	
	public void addText(Text text) {
		outputArea.getChildren().add(text);
	}

	public void clear() {
		outputArea.getChildren().clear();
	}
	
}
