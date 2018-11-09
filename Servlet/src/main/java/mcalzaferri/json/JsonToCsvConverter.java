package mcalzaferri.json;

import java.math.BigDecimal;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonToCsvConverter {
	private static final String SEPARATORCHAR = ",";
	private static  final String NEWLINECHAR = "\r\n";
	private StringBuilder csvBuilder;
	private boolean firstRow;
	private boolean firstColumn;
	
	public String toCsv(JsonArray jsonArray) {
		csvBuilder = new StringBuilder();
		firstRow = true;
		firstColumn = true;
		appendJsonArray(jsonArray);
		return csvBuilder.toString();
		
	}
	
	private void appendJsonElement(JsonElement jsonElement) {
		if(jsonElement.isJsonObject()){
			appendJsonObject(jsonElement.getAsJsonObject());
		}else if(jsonElement.isJsonPrimitive()){
			appendJsonPrimitive(jsonElement.getAsJsonPrimitive());
		}
	}
	
	private void appendJsonArray(JsonArray jsonArray) {
		for(JsonElement jsonElement : jsonArray) {
			appendJsonElement(jsonElement);
			newLine();
		}
	}
	
	private void appendJsonObject(JsonObject jsonObject) {
		if(firstRow) {
			appendDescriptionLine("", jsonObject);
			firstRow = false;
			newLine();
		}
		for(Entry<String,JsonElement> entry : jsonObject.entrySet()) {
			appendJsonElement(entry.getValue());
		}
	}
	
	private void appendDescriptionLine(String prefix, JsonObject jsonObject) {
		for(Entry<String,JsonElement> entry : jsonObject.entrySet()) {
			appendDescription(prefix + entry.getKey(), entry.getValue());
		}
	}
	
	private void appendDescription(String property, JsonElement entry) {
		if(entry.isJsonPrimitive()) {
			appendColumn(property);
		}else if(entry.isJsonObject()){
			appendDescriptionLine(property + ".", entry.getAsJsonObject());
		}
	}
	
	private void appendJsonPrimitive(JsonPrimitive jsonPrimitive) {
		if(jsonPrimitive.isBoolean()) {
			appendColumn(Boolean.toString(jsonPrimitive.getAsBoolean()));
		}else if(jsonPrimitive.isString()) {
			appendColumn(jsonPrimitive.getAsString());
		}else {
			BigDecimal bigDec = jsonPrimitive.getAsBigDecimal();
			
			try {
				long value = bigDec.longValueExact();
				appendColumn(Long.toString(value));
			}catch(ArithmeticException aex) {
				appendColumn(Double.toString(jsonPrimitive.getAsNumber().doubleValue()));
			}
			
		}
	}
	
	private void newLine() {
		csvBuilder.append(NEWLINECHAR);
		firstColumn = true;
	}
	
	private void appendColumn(String value) {
		if(firstColumn) {
			firstColumn = false;
		}else {
			csvBuilder.append(SEPARATORCHAR);
		}
		csvBuilder.append(value);
	}
	
}
