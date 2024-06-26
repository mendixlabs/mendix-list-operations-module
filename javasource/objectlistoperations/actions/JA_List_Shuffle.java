// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package objectlistoperations.actions;

import java.util.ArrayList;
import java.util.Collections;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;

/**
 * This java action takes a list of objects and randomly changes positions of all elements inside.
 * Element can have the same position after shuffling.
 * Returns new shuffled list.
 */
public class JA_List_Shuffle extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private java.util.List<IMendixObject> ProvidedList;

	public JA_List_Shuffle(IContext context, java.util.List<IMendixObject> ProvidedList)
	{
		super(context);
		this.ProvidedList = ProvidedList;
	}

	@java.lang.Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		// BEGIN USER CODE
		
		if (ProvidedList == null || ProvidedList.isEmpty()) {
		    return new ArrayList<IMendixObject>();
		}

		// Utilize Collections.reverse for in-place reversal.
		// Create a copy of the provided list to avoid altering the original list.
		java.util.List<IMendixObject> resultList = new ArrayList<IMendixObject>(ProvidedList);
		Collections.shuffle(resultList);

		return resultList;
		
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_List_Shuffle";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
