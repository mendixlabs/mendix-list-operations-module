// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package objectlistoperations.actions;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;

/**
 * This java action returns an integer.Specifically, a position number (index) of object in the list. It uses object internal id to find first object in the list containing same id. In case there are multiple same objects, it will return the first one.
 * 
 * First object in the list is positions at index "0".
 * If there is no object in the list, it will return "-1".
 * If list is empty, it will return "-1".
 * If object is "Empty", it will throw an error.
 */
public class JA_List_GetIndexOfObject extends CustomJavaAction<java.lang.Long>
{
	private java.util.List<IMendixObject> ProvidedList;
	private IMendixObject ObjectToLookFor;

	public JA_List_GetIndexOfObject(IContext context, java.util.List<IMendixObject> ProvidedList, IMendixObject ObjectToLookFor)
	{
		super(context);
		this.ProvidedList = ProvidedList;
		this.ObjectToLookFor = ObjectToLookFor;
	}

	@java.lang.Override
	public java.lang.Long executeAction() throws Exception
	{
		// BEGIN USER CODE

		if (ObjectToLookFor == null) {
		    throw new IllegalArgumentException("ObjectToLookFor parameter should be a valid object of the same type as the provided list");
		}

		if (ProvidedList == null || ProvidedList.isEmpty()) {
		    return (long) -1;
		}

		return (long) ProvidedList.indexOf(ObjectToLookFor);
	
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_List_GetIndexOfObject";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}