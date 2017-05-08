package info.justdaile.application.json;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.justdaile.application.strings.JSONKeys;

public class Module implements JSONConvertable<JSONObject>{
	
	private String title;
	private JSONArrayList<ModuleQuestion> questions = new JSONArrayList<ModuleQuestion>();
	
	public Module(String title, ModuleQuestion...moduleQuestions){
		this.title = title;
		this.questions.addAll(Arrays.asList(moduleQuestions));
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addQuestion(ModuleQuestion question){
		this.questions.add(question);
	}
	
	public void removeQuestion(int index) {
		this.questions.remove(index);
	}
	
	public ModuleQuestion getQuestion(int index){
		return this.questions.get(index);
	}

	public int size() {
		return this.questions.size();
	}

	public ArrayList<ModuleQuestion> getQuestions() {
		return this.questions;
	}

	public static Module fromJSONObject(JSONObject object) {
		Module module = new Module(object.getString(JSONKeys.MODULE_TITLE_KEY));
		try{
			JSONArray questions = object.getJSONArray(JSONKeys.MODULE_QUESTIONS_KEY);
			for(int i = 0; i < questions.length(); i++){
				module.addQuestion(ModuleQuestion.fromJSONObject(questions.getJSONObject(i)));
			}
		}catch(JSONException j){
			System.err.println("invalid module file in module directory! " + j.getMessage());
		}
		return module;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put(JSONKeys.MODULE_TITLE_KEY, this.title);
		object.put(JSONKeys.MODULE_QUESTIONS_KEY, this.questions.toJSON());
		return object;
	}

}
