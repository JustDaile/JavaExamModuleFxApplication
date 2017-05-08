package info.justdaile.application.options;

import javafx.scene.control.Button;

public abstract class IgnoredSubmitOption extends EvaluatableOption {
	
	public IgnoredSubmitOption(String text) {
		super(false);
		Button button = new Button(text);
		
		button.setOnAction(e -> {
			this.onSubmitted();
		});
		
		this.getChildren().add(button);
	}
	
	@Override
	public void selectionChanged(){
		// Ignore
	}
	
	public abstract void onSubmitted();
	
}
