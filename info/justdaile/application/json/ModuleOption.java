package info.justdaile.application.json;

import org.json.JSONObject;

import info.justdaile.application.strings.JSONKeys;

public class ModuleOption implements JSONConvertable<JSONObject>{
	
	private String type;
	private String text;
	private boolean isValid;
	
	public ModuleOption(String type, String text, boolean isValid) {
		this.type = type;
		this.text = text;
		this.isValid = isValid;
	}
	
	public boolean isValidOption() {
		return isValid;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getText(){
		return this.text;
	}

	public static ModuleOption fromJSONObject(JSONObject object) {
		return new ModuleOption(object.getString(JSONKeys.MODULE_QUESTION_OPTION_TYPE_KEY), object.getString(JSONKeys.MODULE_QUESTION_OPTION_TEXT_KEY), object.getBoolean(JSONKeys.MODULE_QUESTION_OPTION_VALID_KEY));
	}

	@Override
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		object.put(JSONKeys.MODULE_QUESTION_OPTION_TYPE_KEY, this.type);
		object.put(JSONKeys.MODULE_QUESTION_OPTION_TEXT_KEY, this.text);
		object.put(JSONKeys.MODULE_QUESTION_OPTION_VALID_KEY, this.isValid);
		return object;
	}

}
