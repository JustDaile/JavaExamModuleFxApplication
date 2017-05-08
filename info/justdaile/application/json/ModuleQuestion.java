package info.justdaile.application.json;

import org.json.JSONArray;
import org.json.JSONObject;

import info.justdaile.application.strings.JSONKeys;

public class ModuleQuestion implements JSONConvertable<JSONObject>{
	
	private String question, response;
	private JSONArrayList<ModuleOption> options = new JSONArrayList<ModuleOption>();
	private boolean fixed;

	public ModuleQuestion(String question, boolean fixed) {
		this.question = question;
		this.response = "";
		this.fixed = fixed;
	}
	
	public void assignResponse(String response){
		this.response = response;
	}
	
	public String getResponse(){
		return this.response;
	}
	
	public boolean isPositionFixed(){
		return this.fixed;
	}
	
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	public int countValidOptions(){
		return (int) options.stream().filter(ModuleOption::isValidOption).count();
	}

	public void addOption(ModuleOption option) {
		this.options.add(option);
	}

	public String getQuestion(){
		return this.question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public int options(){
		return this.options.size();
	}
	
	public ModuleOption getOption(int index){
		return this.options.get(index);
	}
	
	public void setOption(int optionIndex, ModuleOption moduleOption) {
		this.options.set(optionIndex, moduleOption);
	}

	public ModuleOption removeOption(int i){
		return options.remove(i);
	}
	
	public ModuleOption removeLastOption() {
		return options.remove(options.size() - 1);
	}

	public static ModuleQuestion fromJSONObject(JSONObject object) {
		ModuleQuestion question;
		if(object.has(JSONKeys.MODULE_FIX_OPTION_KEY)){
			question = new ModuleQuestion(object.getString(JSONKeys.MODULE_QUESTION_KEY), object.getBoolean(JSONKeys.MODULE_FIX_OPTION_KEY));
		}else{
			question = new ModuleQuestion(object.getString(JSONKeys.MODULE_QUESTION_KEY), false);
		}
		question.assignResponse(object.getString(JSONKeys.MODULE_QUESTION_RESPONSE_KEY));
		JSONArray options = object.getJSONArray(JSONKeys.MODULE_QUESTION_OPTIONS_KEY);
		for(int i = 0; i < options.length(); i++){
			question.addOption(ModuleOption.fromJSONObject(options.getJSONObject(i)));
		}
		return question;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put(JSONKeys.MODULE_QUESTION_KEY, this.question);
		object.put(JSONKeys.MODULE_QUESTION_OPTIONS_KEY, this.options.toJSON());
		object.put(JSONKeys.MODULE_QUESTION_RESPONSE_KEY, this.response);
		object.put(JSONKeys.MODULE_FIX_OPTION_KEY, this.isPositionFixed());
		return object;
	}
	
}
