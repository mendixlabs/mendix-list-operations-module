// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package objectlistoperations.actions;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import objectlistoperations.impl.Misc;
import com.mendix.systemwideinterfaces.core.IMendixObject;

/**
 * This Java action takes list ("Provided List" parameter), gets a value of specified attribute (name of this attribute should be provided in "Attribute Name" parameter) of each list member and compiles a single string containing all gathered values. Each value in the string optionally can be separated with specified separator string (use "Separator" parameter to specify string that will be put between values in the string outcome). Additionally, before joining, each value can be enclosed with a string ("Wrap values with" parameter).
 * Returns new string.
 */
public class JA_List_JoinAttributeToASingleString extends CustomJavaAction<java.lang.String>
{
	private java.util.List<IMendixObject> ProvidedList;
	private java.lang.String AttributeName;
	private java.lang.String Separator;
	private java.lang.String WrapValuesWith;

	public JA_List_JoinAttributeToASingleString(IContext context, java.util.List<IMendixObject> ProvidedList, java.lang.String AttributeName, java.lang.String Separator, java.lang.String WrapValuesWith)
	{
		super(context);
		this.ProvidedList = ProvidedList;
		this.AttributeName = AttributeName;
		this.Separator = Separator;
		this.WrapValuesWith = WrapValuesWith;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		// BEGIN USER CODE
		
		if (ProvidedList == null || ProvidedList.isEmpty()) {
		    return "";
		}

		validateAttributeName(AttributeName);

		IContext context = getContext();
		String adjustedSeparator = Separator != null ? Separator : "";
		String result = ProvidedList.stream()
		        .map(obj -> formatAttributeValue(context, obj, AttributeName, WrapValuesWith))
		        .filter(Objects::nonNull)
		        .collect(Collectors.joining(adjustedSeparator));

		return result;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_List_JoinAttributeToASingleString";
	}

	// BEGIN EXTRA CODE
	private void validateAttributeName(String attributeName) throws IllegalArgumentException {
	    if (!Misc.isStringNotEmpty(attributeName)) {
	        throw new IllegalArgumentException("\"Attribute name\" parameter is required");
	    }
	    if (!"id".equalsIgnoreCase(attributeName.strip()) && !ProvidedList.get(0).hasMember(attributeName)) {
	        throw new IllegalArgumentException("Incorrect attribute name provided in \"Attribute name\" parameter. Could not find attribute on entity provided in a list or user does not have access to its content.");
	    }
	}

	private String formatAttributeValue(IContext context, IMendixObject obj, String attributeName, String wrapWith) {
	    Object attrValue = "id".equalsIgnoreCase(attributeName.strip()) ? obj.getId().toLong() : obj.getValue(context, attributeName);
	    if (attrValue == null) {
	        return null;
	    }
	    String stringValue = attrValue instanceof BigDecimal ? ((BigDecimal) attrValue).stripTrailingZeros().toPlainString() : attrValue.toString();
	    return wrapWith != null && !wrapWith.isBlank() ? wrapWith + stringValue + wrapWith : stringValue;
	}
	// END EXTRA CODE
}