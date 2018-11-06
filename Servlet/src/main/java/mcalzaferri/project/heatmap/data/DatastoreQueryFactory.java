package mcalzaferri.project.heatmap.data;

import java.util.Collection;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import mcalzaferri.net.rest.QueryStringDecoder;
import mcalzaferri.project.heatmap.data.config.EntityDefinition;
import mcalzaferri.project.heatmap.data.config.FieldNotFoundException;

public class DatastoreQueryFactory {
	
	
	private com.google.cloud.datastore.EntityQuery.Builder queryBuilder;
	private String queryString;
	private StructuredQuery.Filter filter;
	private DatastoreKeyFactory keyFactory;
	private RequestedRessource requestedRes;
	private EntityDefinition entityConfig;

	private DatastoreQueryFactory(DatastoreKeyFactory keyFactory) {
		this.keyFactory = keyFactory;
		init();
	}
	
	public static DatastoreQueryFactory newFactory(DatastoreKeyFactory keyFactory) {
		return new DatastoreQueryFactory(keyFactory);
	}
	
	private void init() {
		queryBuilder = Query.newEntityQueryBuilder();
		filter = null;
		queryString = null;
		requestedRes = null;
		entityConfig = null;
	}
	
	public DatastoreQueryFactory addPropertyFilter(PropertyFilter filter) {
		return addFilter(filter);
	}
	
	public DatastoreQueryFactory addFilter(StructuredQuery.Filter newFilter) {
		if(newFilter != null) {
			if(this.filter == null) {
				this.filter = newFilter;
			}else {
				this.filter = CompositeFilter.and(this.filter, newFilter);
			}
		}
		return this;
	}
	
	public DatastoreQueryFactory addFilter(QueryStringDecoder.Filter newFilter) throws FieldNotFoundException {
		if(newFilter != null) {
			addFilter(newFilter.getAsPropertyFilter(entityConfig));
		}
		return this;
	}
	
	public DatastoreQueryFactory addDecoderFilters(Collection<QueryStringDecoder.Filter> filters) throws FieldNotFoundException {
		if(filters != null) {
			for(QueryStringDecoder.Filter filter : filters) {
				addFilter(filter);
			}
		}
		return this;
	}
	
	public DatastoreQueryFactory addOrder(OrderBy order) {
		queryBuilder.addOrderBy(order);
		return this;
	}
	
	public DatastoreQueryFactory addOrders(Collection<OrderBy> orders) {
		if(orders != null) {
			for(OrderBy order : orders) {
				addOrder(order);
			}
		}
		return this;
	}
	
	public DatastoreQueryFactory setKind(String kind) {
		queryBuilder.setKind(kind);
		return this;
	}
	
	public DatastoreQueryFactory setRequestedRessource(RequestedRessource res) {
		this.requestedRes = res;
		return this;
	}
	
	public DatastoreQueryFactory setEntityDefinition(EntityDefinition def) {
		this.entityConfig = def;
		return this;
	}
	
	public DatastoreQueryFactory setQueryString(String queryString) {
		this.queryString = queryString;
		return this;
	}
	
	public DatastoreQueryFactory setLimit(Integer limit) {
		if(limit != null && limit > 0) {
			queryBuilder.setLimit(limit);
		}
		return this;
	}
	
	private void applyQueryStringOperations() throws FieldNotFoundException {
		if(queryString != null) {
			QueryStringDecoder decoder = new QueryStringDecoder(queryString);
			addDecoderFilters(decoder.getFilters());
			addOrders(decoder.getOrders());
			setLimit(decoder.getLimit());
		}
	}
	
	private void applyAncestorFilters() {
		if(requestedRes != null) {
			setKind(requestedRes.getName());
			if(requestedRes.getId() != null) {
				addFilter(PropertyFilter.eq("__key__", keyFactory.getRessourceKey(requestedRes)));
			}
			if(requestedRes.getParent() != null) {
				addFilter(PropertyFilter.hasAncestor(keyFactory.getRessourceKey(requestedRes.getParent())));
			}
		}
	}
	
	public Query<Entity> build() throws FieldNotFoundException{
		applyQueryStringOperations();
		applyAncestorFilters();
		if(filter != null)
			queryBuilder.setFilter(filter);
		
		Query<Entity> result = queryBuilder.build();
		init();
		return result;
	}
	
}
