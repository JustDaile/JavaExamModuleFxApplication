package info.justdaile.application.json;

import org.json.JSONObject;

import info.justdaile.application.strings.JSONKeys;

public class UserDataAnswers implements JSONConvertable<JSONObject>{
	
	private String question, answer;
	private boolean isCorrect;
	
	public UserDataAnswers(String question, String answer, boolean isCorrect){
		this.question = question;
		this.answer = answer;
		this.isCorrect = isCorrect;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject answer = new JSONObject();
		answer.put(JSONKeys.MODULE_QUESTION_KEY, this.question);
		answer.put(JSONKeys.USER_ANSWER_KEY, this.answer);
		answer.put(JSONKeys.USER_QUESTION_CORRECT_KEY, this.isCorrect);
		return answer;
	}

}
