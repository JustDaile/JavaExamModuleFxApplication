package info.justdaile.application.options;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Pos;

public class HighlightableMultiTextOption extends MultiPartSelectableOption{

	private List<String> parts = new ArrayList<String>();
	
	public HighlightableMultiTextOption(String optionText) {
		super();
		Pattern p = Pattern.compile("%((\\w+)|[a-zA-Z0-9'\"_ .,!?;:\\]\\[\\)\\(\\{\\}]+)%|\\b\\w+\\b|\\S{1}|\\s|\\n");
		Matcher m = p.matcher(optionText);
		while(m.find()){
			String match = optionText.substring(m.start(), m.end());
			parts.add(match);
		}
		this.setAlignment(Pos.CENTER_LEFT);
		this.setStyle("-fx-background-color:  #FFFFFF");
		this.disabledProperty().addListener(e -> {
			this.setStyle("-fx-background-color:  #FFFFFF");
		});
		this.init();
	}

	@Override
	public ArrayList<EvaluatableOption> initOptions() {
		ArrayList<EvaluatableOption> a = new ArrayList<EvaluatableOption>();
		for(int i = 0; i < this.parts.size(); i++){
			String text = parts.get(i);
			boolean isCorrect = false;
			if(text.startsWith("%") && text.endsWith("%") && text.length() > 1){
				isCorrect = true;
				text = text.replaceAll("%", " ").trim();
			}
			SelectableTextOption to = new SelectableTextOption(text, isCorrect);
			to.setOnMouseClicked((e) -> {
				to.selectionChanged();
			});
			a.add(to);
		}
		return a;
	}

}
