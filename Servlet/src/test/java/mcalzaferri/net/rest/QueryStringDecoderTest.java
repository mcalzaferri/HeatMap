package mcalzaferri.net.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.cloud.datastore.StructuredQuery.OrderBy;

public class QueryStringDecoderTest {
	public static final String validQueryString = "field1,field2&field1[gt]=10&field1[lt]=20&field2=abc&orderBy=+field1,-field2&limit=10";
	public QueryStringDecoder decoder;
	
	@Before
	public void init() {
		decoder = new QueryStringDecoder(validQueryString);
	}
	
	@Test
	public void fieldsShouldBeCorrectlyDecoded() {
		assertEquals("field1", decoder.getFields().get(0));
		assertEquals("field2", decoder.getFields().get(1));
	}
	
	@Test
	public void filtersShouldBeCorrectlyDecoded() {
		assertFilterEquals("field1", QueryStringDecoder.FilterOperation.GT, "10", decoder.getFilters().get(0));
		assertFilterEquals("field1", QueryStringDecoder.FilterOperation.LT, "20", decoder.getFilters().get(1));
		assertFilterEquals("field2", QueryStringDecoder.FilterOperation.EQ, "abc", decoder.getFilters().get(2));
	}
	
	@Test
	public void ordersShouldBeCorrectlyDecoded() {
		assertOrderByEquals("field1", OrderBy.Direction.ASCENDING, decoder.getOrders().get(0));
		assertOrderByEquals("field2", OrderBy.Direction.DESCENDING, decoder.getOrders().get(1));
	}
	
	@Test
	public void limitsShouldBeCorrectlyDecoded() {
		assertEquals(10, decoder.getLimit().intValue());
	}
	
	private void assertFilterEquals(String expectedProperty, QueryStringDecoder.FilterOperation expectedOperation, String expectedValue, QueryStringDecoder.Filter actual) {
		assertEquals(expectedProperty, actual.getProperty());
		assertEquals(expectedOperation.name(), actual.getFilterOperation().name());
		assertEquals(expectedValue, actual.getValue());
	}
	
	private void assertOrderByEquals(String expectedProperty, OrderBy.Direction expectedDirection, OrderBy actual) {
		assertEquals(expectedDirection, actual.getDirection());
		assertEquals(expectedProperty, actual.getProperty());
	}

}
