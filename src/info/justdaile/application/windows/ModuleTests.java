package info.justdaile.application.windows;
	
import java.util.ArrayList;
import java.util.Random;

import info.justdaile.application.ApplicationSelectLauncher;
import info.justdaile.application.Settings;
import info.justdaile.application.components.ApplicationMenuBar;
import info.justdaile.application.components.ConfirmWindow;
import info.justdaile.application.components.OptionMenu;
import info.justdaile.application.components.QuestionDialog;
import info.justdaile.application.components.popup.ModuleSelectorWindow;
import info.justdaile.application.components.popup.PopupConfirmWindow;
import info.justdaile.application.components.popup.PopupWindow;
import info.justdaile.application.components.popup.PopupYesNoOptionWindow;
import info.justdaile.application.json.Module;
import info.justdaile.application.json.ModuleQuestion;
import info.justdaile.application.json.UserDataAnswers;
import info.justdaile.application.json.UserDataModule;
import info.justdaile.application.options.ButtonOption;
import info.justdaile.application.options.CheckboxCodeOption;
import info.justdaile.application.options.CheckboxOption;
import info.justdaile.application.options.EvaluableOption;
import info.justdaile.application.options.HighlightableMultiTextOption;
import info.justdaile.application.options.IgnoredSubmitOption;
import info.justdaile.application.options.InputOption;
import info.justdaile.application.options.UnselectableCodeOption;
import info.justdaile.application.strings.ButtonTypes;
import info.justdaile.application.strings.Buttons;
import info.justdaile.application.strings.Messages;
import info.justdaile.application.strings.Titles;
import info.justdaile.application.strings.WindowText;
import info.justdaile.io.UserFileManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ModuleTests extends Application {
		
	private String moduleTitle;
	private QuestionDialog QuestionDialog = new QuestionDialog();
	private ArrayList<ModuleQuestion> questionConsume = new ArrayList<ModuleQuestion>();
	private static Random _R = new Random();
	private OptionMenu optionMenu;
	private ModuleQuestion question;
	private PopupWindow window;
	private Font font = new Font(24);
	private UserFileManager saveFile = new UserFileManager();
	private UserDataModule moduleSaveData;
	private Insets padding = new Insets(15, 15, 15, 15);
	private Label progressLabel = new Label("Progress");
	private ProgressBar progress = new ProgressBar(0);
	private int questions, answered, marks;
		
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(Titles.MODULE_TESTS_APP_TITLE);
		ApplicationMenuBar menubar = new ApplicationMenuBar(primaryStage);
		try {
			Module module = new ModuleSelectorWindow(WindowText.SELECTION_TITLE, WindowText.SELECT_MODULE_SELECT).ShowSelectorWindow();
			if(module == null){
				Platform.runLater(() -> {
					new ConfirmWindow(WindowText.ERROR_TITLE, WindowText.ERROR_NO_MODULE_SELECTED).showAndWait();
					primaryStage.close();
					Platform.exit();
				});
				return;
			}else{
				this.questionConsume.addAll(module.getQuestions());
			}
			this.moduleTitle = module.getTitle();
			this.moduleSaveData = new UserDataModule(this.moduleTitle);
			this.questions = module.size();
			this.answered = this.marks = 0;
			this.optionMenu = new OptionMenu() {
				@Override
				public void init() {
					this.clearOptions();
					if(questionConsume.size() > 0){
						question = questionConsume.remove(_R.nextInt(questionConsume.size()));
						Text questionText = new Text(question.getQuestion());
						questionText.setFont(font);
						QuestionDialog.addText(questionText);
						boolean manualSubmit = false;
						if(question.options() < 1){
							manualSubmit = true;
						}
						for(int i = 0; i < question.options(); i++){
							String optionText = question.getOption(i).getText();
							String type = question.getOption(i).getType();
							boolean valid = question.getOption(i).isValidOption();
							EvaluableOption option = null;
							int options = question.countValidOptions();
							if(options > 1){
								manualSubmit = true;
							}
							switch(type){
							case ButtonTypes.INPUT:
								option = new InputOption(optionText);
								manualSubmit = true;
								break;
							case ButtonTypes.CHECKBOX:
								option = new CheckboxOption(optionText, valid);
								manualSubmit = true;
								break;
							case ButtonTypes.CHECKCODE:
								option = new CheckboxCodeOption(optionText, valid);
								manualSubmit = true;
								break;
							case ButtonTypes.CODE:
								option = new UnselectableCodeOption(optionText);
								break;
							case ButtonTypes.MULTITEXT:
								option = new HighlightableMultiTextOption(optionText);
								manualSubmit = true;
								break;
							case ButtonTypes.BUTTON:
							default:
								option = new ButtonOption(optionText, valid){
									@Override
									public void selectionChanged(){
										super.selectionChanged();
										if(options == 1){
											optionMenu.onSubmitted(optionMenu.getOptions(EvaluableOption::isSelected));
										}
									}
								};
								break;
								
							}
							this.addOption(option);
						}
						if(manualSubmit){
							this.addOption(new IgnoredSubmitOption("Submit") {
								@Override
								public void onSubmitted() {
									optionMenu.onSubmitted(optionMenu.getOptions(EvaluableOption::isSelected));
								}
							});
						}
						this.setFixed(question.isPositionFixed());
						this.show();
						primaryStage.sizeToScene();
					}else{
						primaryStage.close();
						save();
						new PopupConfirmWindow(WindowText.RESULT_TITLE, Messages.compileMessage(Messages.TEST_RESULT_MESSAGE, marks, questions), Buttons.CONFIRM_BUTTON) {
							@Override
							public void onOptionPressed(int actionType) {
								close();
							}
						}.showAndWait();
						Platform.runLater(() -> {
							window = new PopupYesNoOptionWindow(WindowText.MODULE_COMPLETE_TITLE, WindowText.CHOOSE_ACTION, Buttons.CHANGE_MODULE_BUTTON, Buttons.SELECT_APPLICATION_BUTTON) {
								@Override
								public void onOptionPressed(int actionType) {
									switch(actionType){
									case PopupYesNoOptionWindow.POSITIVE_ACTION:
										shutdownApplication();
										new ModuleTests().start(new Stage());
										break;
									case PopupYesNoOptionWindow.NEGATIVE_ACTION:
										shutdownApplication();
										new ApplicationSelectLauncher().start(new Stage());
										break;
									case PopupWindow.CLOSE_WINDOW_ACTION:
										try {
											stop();
											Platform.exit();
										} catch (Exception e) {
											e.printStackTrace();
										}
										break;	
									}
								}
								private void shutdownApplication() {
									window.getScene().getRoot().setDisable(true);
									window.close();
									primaryStage.close();
									hide();
								}
							};
							window.show();
						});
					}
				}
				@Override
				public void onSubmitted(EvaluableOption...options) {
					this.revealOptions();
					boolean correct = this.eval();
					answered++;
					if(correct){
						marks++;
					}
					progress.setProgress((1.0f / questions) * answered);
					progressLabel.setText(marks + " out of " + answered);
					if(options.length >= 0){
						StringBuffer answerText = new StringBuffer();
						for(int i = 0; i < options.length; i++){
							EvaluableOption option = options[i];
							answerText.append("\n-> " + option.toString());
						}
						String response = question.getResponse();
						Text responseText = new Text("\n\n" + ((correct) ? Messages.compileMessage(Messages.RESPONSE, "Correct", response) : Messages.compileMessage(Messages.RESPONSE, "Incorrect", response)));
						responseText.setStyle("-fx-fill: #" + ((correct)? "0f0" : "f00"));
						if(Settings.RESPONSE_ON_INCORRECT){
							if(!correct){
								QuestionDialog.addText(responseText);
							}
						}else{
							QuestionDialog.addText(responseText);
						}
						moduleSaveData.addAnswer(new UserDataAnswers(question.getQuestion(), answerText.toString(), correct));
					}
					this.disableOptions();
					this.addOption(new ButtonOption(Buttons.NEXT_QUESTION_BUTTON, true) {
						@Override
						public void selectionChanged() {
							QuestionDialog.clear();
							init();
						}
					});
					this.show();
					primaryStage.sizeToScene();
				}
			};
			VBox root = new VBox(5);
			QuestionDialog.setPadding(this.padding);
			optionMenu.setPadding(this.padding);
			QuestionDialog.setAlignment(Pos.CENTER);
			optionMenu.setAlignment(Pos.CENTER);
			root.setAlignment(Pos.CENTER);
			root.getChildren().addAll(menubar, progressLabel, progress, QuestionDialog, optionMenu);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public final void save(){
		this.saveFile.addModule(this.moduleSaveData);
		this.saveFile.save();
	}
	
}
