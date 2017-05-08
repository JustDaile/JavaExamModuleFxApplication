package info.justdaile.application.json;

import java.util.Arrays;

import org.json.JSONObject;

import info.justdaile.application.strings.JSONKeys;

public class UserDataModule implements JSONConvertable<JSONObject>{

	private String title;
	private JSONArrayList<UserDataAnswers> answers = new JSONArrayList<UserDataAnswers>();
	
	public UserDataModule(String title, UserDataAnswers...answerSaveData){
		this.title = title;
		answers.addAll(Arrays.asList(answerSaveData));
	}
	
	public void addAnswer(UserDataAnswers answer){
		this.answers.add(answer);
	}

	@Override
	public JSONObject toJSON() {
		JSONObject created = new JSONObject();
		created.put(JSONKeys.MODULE_TITLE_KEY, this.title);
		created.put(JSONKeys.USER_ANSWERS_KEY, this.answers.toJSON());
		return created;
	}

}
