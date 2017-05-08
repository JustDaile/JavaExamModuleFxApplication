package info.justdaile.application.json;

import java.util.ArrayList;

import org.json.JSONArray;

public class JSONArrayList<E> extends ArrayList<E> implements JSONConvertable<JSONArray>{
	private static final long serialVersionUID = 1L;
	
	@Override
	public JSONArray toJSON() {
		JSONArray arr = new JSONArray();
		this.stream().forEach(e -> 	arr.put(((JSONConvertable<?>) e).toJSON()));
		return arr;
	}

}
