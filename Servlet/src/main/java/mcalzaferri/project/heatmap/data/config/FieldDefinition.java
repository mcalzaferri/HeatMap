package mcalzaferri.project.heatmap.data.config;

import java.util.Date;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.gson.JsonObject;

public class FieldDefinition {
	public String name;
	public String type;
	public boolean required;
	
	public ValueType getValueType() {
		return ValueType.valueOf(type);
	}
	
	public Value<?> parse(String input){
		switch(getValueType()) {
		case DOUBLE:
			return DoubleValue.of(Double.parseDouble(input));
		case LONG:
			return LongValue.of(Long.parseLong(input));
		case TIMESTAMP:
			return TimestampValue.of(Timestamp.of(new Date(Long.parseLong(input))));
		case BOOLEAN:
			return BooleanValue.of(Boolean.parseBoolean(input));
		default:
			return StringValue.of(input);
		}
	}
	public Value<?> parse(JsonObject jsonObject){
		switch(getValueType()) {
		case DOUBLE:
			return DoubleValue.of(jsonObject.getAsDouble());
		case LONG:
			return LongValue.of(jsonObject.getAsLong());
		case TIMESTAMP:
			return TimestampValue.of(Timestamp.of(new Date(jsonObject.getAsLong())));
		case LAT_LNG:
			return LatLngValue.of(LatLng.of(jsonObject.get("latitude").getAsDouble()
					,jsonObject.get("longitude").getAsDouble()));
		case BOOLEAN:
			return BooleanValue.of(jsonObject.getAsBoolean());
		default:
			return StringValue.of(jsonObject.getAsString());
		}
	}
}
