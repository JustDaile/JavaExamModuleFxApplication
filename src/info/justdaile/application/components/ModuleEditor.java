package info.justdaile.application.components;

import info.justdaile.application.components.popup.PopupWindow;
import info.justdaile.application.events.PopupOptionEvent;
import info.justdaile.application.json.Module;
import info.justdaile.application.json.ModuleQuestion;
import info.justdaile.application.strings.Buttons;
import info.justdaile.application.strings.Descriptions;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ModuleEditor extends VBox {
	
	private Module module;
	
	public ModuleEditor(final Module module){
		this.module = module;
		Label header = new Label("Module Navigation");
		header.setFont(new Font(16));
		HBox headerBox = new HBox(header);
		this.getChildren().add(headerBox);
		headerBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(header, Priority.ALWAYS);
		VBox questionContainer = new VBox(5);
		this.updateQuestions(questionContainer);
		this.getChildren().add(questionContainer);
		Button addBtn = new Button(Buttons.ADD_QUESTION_BUTTON);
		HBox addBox = new HBox(addBtn);
		addBox.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().add(addBox);
		addBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(addBox, Priority.ALWAYS);
		addBtn.setOnAction(e -> {
			this.module.addQuestion(new ModuleQuestion(Descriptions.DEFAULT_QUESTION_TEXT, false));
			this.updateQuestions(questionContainer);
		});
		addBtn.setTooltip(new Tooltip(Descriptions.ADD_QUESTION_BUTTON));
	}
		
	private void updateQuestions(VBox questionContainer){
		Platform.runLater(() -> {
			questionContainer.getChildren().clear();
			for(int i = 0; i < this.module.size(); i++){
				ModuleQuestion question = this.module.getQuestion(i);
				final TextField input = new TextField(question.getQuestion());
				final Button removeBtn = new Button(Buttons.REMOVE_QUESTION_BUTTON);
				final Button editBtn = new Button(Buttons.EDIT_QUESTION_BUTTON);
				final HBox row = new HBox(15, input, removeBtn, editBtn);
				row.setPadding(new Insets(10, 10, 0, 10));
				row.setAlignment(Pos.CENTER);
				HBox.setHgrow(input, Priority.ALWAYS);
				questionContainer.getChildren().add(row);
				removeBtn.setOnAction(e -> {
					int questionIndex = questionContainer.getChildren().indexOf(row);
					module.removeQuestion(questionIndex);
					this.updateQuestions(questionContainer);
				});
				removeBtn.setTooltip(new Tooltip(Descriptions.REMOVE_QUESTION_BUTTON));
				editBtn.setOnAction(e -> {
					new PopupWindow(question.getQuestion()) {
						@Override
						public Node init() {
							this.setResizable(true);
							OptionEditor editor = new OptionEditor(module, questionContainer.getChildren().indexOf(row));
							ScrollPane pane = new ScrollPane(editor);
							pane.setPrefSize(500, 600);
							pane.fitToWidthProperty().set(true);
							return pane;
						}
						@Override
						public void onAction(PopupOptionEvent event) {
							// unused
						}
					}.show();
				});
				editBtn.setTooltip(new Tooltip(Descriptions.EDIT_OPTION_BUTTON));
				input.addEventHandler(EventType.ROOT, e -> {
					if(e.getEventType() == KeyEvent.KEY_RELEASED){
						module.getQuestion(questionContainer.getChildren().indexOf(row)).setQuestion(input.getText());
					}
				});
			}
		});
	}
	
	public Module getModule(){
		return this.module;
	}
	
}
