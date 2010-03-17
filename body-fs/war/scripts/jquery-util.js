/**
 * These are some handuful JavaScript functions take from jquery 1.4
 */

/**
 * This function parses the input string into JSON object. This method uses
 * native JSON parse for new browsers and use new function as a fallback to
 * support old browsers.
 * 
 * @param data input JSON string
 * @return JSON object
 */
function parseJSON(data) {
	if (!data) {
		return {};
	}
	if (window.JSON && window.JSON.parse) {
		data = window.JSON.parse(data);
	} else {
		data = (new Function("return " + data))();
	}
	return data;
}

/**
 * This function first checks the native JSON stringify function if not exists
 * then uses the zk's toJSON method
 * 
 * @param data
 * @return string representation of the given object
 */
function toJSON(data) {
	if (!data) {
		return "{}";
	}
	if (window.JSON.stringify) {
		return JSON.stringify(data);
	}
	return $.toJSON(data);
}