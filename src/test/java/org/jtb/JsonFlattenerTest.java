package org.jtb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.json.JSONObject;
import org.jtb.jsonflattener.JsonFlattener;

public class JsonFlattenerTest extends TestCase {
  public JsonFlattenerTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(JsonFlattenerTest.class);
  }

  public void testEncodeFlat() {
    JSONObject input = new JSONObject("{\"foo\": \"bar\"}");
    JSONObject encoded = new JSONObject(JsonFlattener.encode(input));

    assertTrue(encoded.has("foo"));
    assertEquals("bar", encoded.getString("foo"));
  }

  public void testDecodeFlat() {
    JSONObject input = new JSONObject("{\"foo\": \"bar\"}");
    JSONObject decoded = JsonFlattener.decodeToObject(input);

    assertTrue(decoded.has("foo"));
    assertEquals("bar", decoded.getString("foo"));
  }

  public void testEncode2Level() {
    JSONObject input = new JSONObject("{\"foo\": {\"bar\": \"baz\"}}");
    JSONObject encoded = new JSONObject(JsonFlattener.encode(input));

    assertTrue(encoded.has("foo.bar"));
    assertEquals("baz", encoded.getString("foo.bar"));
  }

  public void testDecode2Level() {
    JSONObject input = new JSONObject("{\"foo.bar\": \"baz\"}");
    JSONObject decoded = JsonFlattener.decodeToObject(input);

    assertTrue(decoded.has("foo"));
    assertTrue(decoded.getJSONObject("foo").has("bar"));
    assertEquals("baz", decoded.getJSONObject("foo").getString("bar"));
  }

  public void testEncodeFlatArray() {
    JSONObject input = new JSONObject("{ \"array\": [ \"foo\", \"bar\", \"baz\" ]}");
    JSONObject encoded = new JSONObject(JsonFlattener.encode(input));

    assertTrue(encoded.has("array.0"));
    assertEquals("foo", encoded.getString("array.0"));

    assertTrue(encoded.has("array.1"));
    assertEquals("bar", encoded.getString("array.1"));

    assertTrue(encoded.has("array.2"));
    assertEquals("baz", encoded.getString("array.2"));
  }

  public void testDecodeFlatArray() {
    JSONObject input = new JSONObject("{\"array.0\": \"foo\", \"array.1\": \"bar\", \"array.2\": \"baz\"}");
    JSONObject decoded = JsonFlattener.decodeToObject(input);

    assertTrue(decoded.has("array"));
    assertEquals("foo", decoded.getJSONArray("array").getString(0));
    assertEquals("bar", decoded.getJSONArray("array").getString(1));
    assertEquals("baz", decoded.getJSONArray("array").getString(2));
  }

}
