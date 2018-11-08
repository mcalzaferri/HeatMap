package mcalzaferri.net.rest;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import mcalzaferri.project.heatmap.data.config.EntityDefinition;
import mcalzaferri.project.heatmap.data.config.FieldDefinition;
import mcalzaferri.project.heatmap.data.config.FieldNotFoundException;

public class QueryStringDecoder {
	private String[] tokens;
	private List<OrderBy> orders;
	private List<Filter> filters;
	private List<String> fields;
	private Integer limit;
	
	public QueryStringDecoder(String queryString) {
		tokens = queryString.split("&");
		orders = new ArrayList<>();
		filters = new ArrayList<>();
		fields = new ArrayList<>();
		limit = null;
		decode();
	}
	
	private void decode() {
		for(String token : tokens) {
			if(token.startsWith("orderBy")) {
				addOrderBy(token);
			}else if(token.startsWith("limit=")) {
				addLimit(token);
			}else if(token.contains("=")) {
				addFilter(token);
			}else {
				addField(token);
			}
		}
	}
	
	private void addOrderBy(String token) {
		String subToken = token.substring(token.indexOf("=") + 1);
		OrderBy newOrderBy;
		String property;
		for(String tokenPart : subToken.split(",")) {
			property = tokenPart.substring(1);
			if(tokenPart.startsWith("+")) {
				newOrderBy = new OrderBy(property, OrderBy.Direction.ASCENDING);
			}else {
				newOrderBy = new OrderBy(property, OrderBy.Direction.DESCENDING);
			}
			orders.add(newOrderBy);
		}
	}
	
	private void addFilter(String token) {
		Filter newFilter = new Filter(token);
		if(filters.contains(newFilter)) {
			filters.set(filters.indexOf(newFilter), newFilter);
		}else {
			filters.add(newFilter);
		}
	}
	
	private void addField(String token) {
		for(String field : token.split(",")) {
			if(!fields.contains(field)) {
				fields.add(field);
			}
		}
	}
	
	private void addLimit(String token) {
		limit = Integer.parseInt(token.substring(token.indexOf("=") + 1));
	}
	
	public List<OrderBy> getOrders() {
		return orders;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public List<String> getFields() {
		return fields;
	}
	
	public Integer getLimit() {
		return limit;
	}


	public class Filter{
		private String token;
		private FilterOperation filterOperation;
		private String property;
		private String value;
		
		public Filter(String token) {
			this.token = token;
			decodeProperty();
			decodeFilterOperation();
			decodeValue();
		}
		private void decodeProperty() {
			if(token.contains("["))
				property = token.substring(0, token.indexOf("["));
			else
				property = token.substring(0,token.indexOf("="));
		}
		private void decodeFilterOperation() {
			if(token.contains("[")) {
				filterOperation = FilterOperation.valueOf(FilterOperation.class, 
						token.substring(token.indexOf("[") + 1, token.indexOf("]")).toUpperCase());
			}else {
				filterOperation = FilterOperation.EQ;
			}
		}
		private void decodeValue() {
			value = token.substring(token.indexOf("=") + 1);
		}
		public FilterOperation getFilterOperation() {
			return filterOperation;
		}
		public String getProperty() {
			return property;
		}
		public String getValue() {
			return value;
		}
		public PropertyFilter getAsPropertyFilter(EntityDefinition entitiyDef) throws FieldNotFoundException {
			for(FieldDefinition field : entitiyDef.fields) {
				if(field.name.equals(property)) {
					switch(filterOperation) {
					case GT:
						return PropertyFilter.gt(property, field.parse(value));
					case GE:
						return PropertyFilter.ge(property, field.parse(value));
					case EQ:
						return PropertyFilter.eq(property, field.parse(value));
					case LE:
						return PropertyFilter.le(property, field.parse(value));
					case LT:
						return PropertyFilter.lt(property, field.parse(value));
					}
				}
			}
			throw new FieldNotFoundException("No such property: " + property);
		}
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Filter) {
				return equals((Filter)obj);
			}
			return super.equals(obj);
		}
		private boolean equals(Filter obj) {
			return this.property.equals(obj.property) && this.filterOperation.equals(obj.filterOperation);
		}
		
	}
	public enum FilterOperation{
		GT,
		GE,
		EQ,
		LE,
		LT
	}
}
