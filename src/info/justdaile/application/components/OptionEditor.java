package info.justdaile.application.components;

import info.justdaile.application.json.Module;
import info.justdaile.application.json.ModuleOption;
import info.justdaile.application.strings.ButtonTypes;
import info.justdaile.application.strings.Descriptions;

import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class OptionEditor extends VBox {

	private Module module;
	private int questionIndex;
	private Insets padding = new Insets(10, 10, 10, 10);
	private CheckBox fixed;
	public static int WIDTH = 500;
	public static int HEIGHT = 500;
	
	public OptionEditor(final Module module, int questionIndex){
		this.module = module;
		this.questionIndex = questionIndex;
		this.setMinSize(WIDTH, HEIGHT);
		Label header = new Label("Question Navigation");
		header.setFont(new Font(16));
		HBox headerBox = new HBox(header);
		this.getChildren().add(headerBox);
		headerBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(header, Priority.ALWAYS);
		HBox responseBox = new HBox(15);
		Label responseLabel = new Label("Response");
		TextField response = new TextField(this.module.getQuestion(questionIndex).getResponse());
		response.setTooltip(new Tooltip(Descriptions.RESPONSE_FIELD));
		response.addEventHandler(EventType.ROOT, e -> {
			if(e.getEventType() == KeyEvent.KEY_RELEASED){
				this.module.getQuestion(questionIndex).assignResponse(response.getText());
			}
		});
		this.fixed = new CheckBox("fixed");
		this.fixed.setTooltip(new Tooltip(Descriptions.FIXED_DESCRIPTION));
		this.fixed.setOnAction(e -> {
			this.module.getQuestion(questionIndex).setFixed(this.fixed.isSelected());
		});
		HBox.setHgrow(response, Priority.ALWAYS);
		responseBox.getChildren().add(responseLabel);
		responseBox.getChildren().add(response);
		responseBox.getChildren().add(this.fixed);
		responseBox.setAlignment(Pos.CENTER);
		this.getChildren().add(responseBox);
		VBox optionContainer = new VBox(5);
		this.updateOptions(optionContainer);
		Button addBtn = new Button("+");		
		HBox addBox = new HBox(addBtn);
		addBox.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().add(addBox);
		addBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(addBox, Priority.ALWAYS);
		addBtn.setOnAction(e -> {
			this.module.getQuestion(questionIndex).addOption(new ModuleOption(ButtonTypes.BUTTON, Descriptions.DEAULT_OPTION_TEXT, false));
			this.updateOptions(optionContainer);
		});
		addBtn.setTooltip(new Tooltip(Descriptions.ADD_OPTION_BUTTON));
		this.getChildren().add(optionContainer);
		this.setAlignment(Pos.TOP_CENTER);
		this.setPadding(padding);
	}
	
	private void updateOptions(VBox optionContainer){
		Platform.runLater(() -> {
			optionContainer.getChildren().clear();
			for(int i = 0; i < this.module.getQuestion(this.questionIndex).options(); i++){
				int index = i; // effectively final
				Parent input;
				ModuleOption option = this.module.getQuestion(this.questionIndex).getOption(i);
				this.fixed.setSelected(this.module.getQuestion(this.questionIndex).isPositionFixed());
				ComboBox<String> type = new ComboBox<String>();
				for(int t = 0; t < ButtonTypes.TYPES.length; t++){
					type.getItems().add(ButtonTypes.TYPES[t]);
				}
				type.setTooltip(new Tooltip(Descriptions.OPTION_TYPE_LIST));
				String setType = option.getType();
				for(int x = 0; x < type.getItems().size(); x++){
					if(type.getItems().get(x).contentEquals(setType)){
						type.getSelectionModel().select(x);
					}
				}
				if(setType.contentEquals(ButtonTypes.CODE) || setType.contentEquals(ButtonTypes.MULTITEXT)){
					input = new TextArea(option.getText());
					((TextArea)input).setMaxSize(300, 100);
				}else{
					input = new TextField(option.getText());
				}
				CheckBox valid = null;
				if(!setType.contentEquals(ButtonTypes.MULTITEXT)){
					valid = new CheckBox();
					CheckBox c = valid;
					valid.setTooltip(new Tooltip(Descriptions.VALID_OPTION_CHECKBOX));
					valid.setSelected(option.isValidOption());
					valid.setOnAction(e -> {
						this.module.getQuestion(this.questionIndex).getOption(index).setValid(c.isSelected());
					});
				}
				Button removeBtn = new Button("x");
				final HBox row = new HBox(15);
				if(input != null){
					row.getChildren().add(input);
				}
				if(type != null){
					row.getChildren().add(type);
				}
				if(valid != null){
					row.getChildren().add(valid);
				}
				if(removeBtn != null){
					row.getChildren().add(removeBtn);
				}
				row.setPadding(new Insets(10, 10, 0, 10));
				row.setAlignment(Pos.CENTER);
				removeBtn.setOnAction(e -> {
					this.module.getQuestion(this.questionIndex).removeOption(index);
					this.updateOptions(optionContainer);
				});
				removeBtn.setTooltip(new Tooltip(Descriptions.REMOVE_OPTION_BUTTON));
				input.addEventHandler(EventType.ROOT, e -> {
					if(e.getEventType() == KeyEvent.KEY_TYPED){
						if(input instanceof TextField){
							this.module.getQuestion(this.questionIndex).getOption(index).setText(((TextField) input).getText());
						}else if(input instanceof TextArea){
							this.module.getQuestion(this.questionIndex).getOption(index).setText(((TextArea) input).getText());
						}
					}
				});
				type.setOnAction(e -> {
					this.module.getQuestion(this.questionIndex).getOption(index).setType(type.getSelectionModel().getSelectedItem());
					this.updateOptions(optionContainer);
				});
				HBox.setHgrow(input, Priority.ALWAYS);
				optionContainer.getChildren().addAll(row);
			}
		});
	}
	
}
