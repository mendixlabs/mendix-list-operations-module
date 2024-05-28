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
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;

/**
 * Returns new list of objects containing objects from the provided list + object specified separately.
 * Specified object will have position determined by Object index parameter.
 * 
 * Object index with value "0" will put specified object to the very first position in the list.
 * Object index with value "1" will put specified object to the second position in the list, etc.
 * Object index with value "-1" will put specified object to the very last position in the list.
 * Object index with value "-2" will put specified object to the position right before very last position in the list, etc.
 * If Object index boundaries surpass list size the error won't be given. Instead specified object will be put to the very beginning of the list if the index is negative and too low. Or specified object will be put to the very end of the list if the index is positive and surpasses Provided list size.
 */
public class JA_List_InsertObjectAtIndex extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private java.util.List<IMendixObject> ProvidedList;
	private IMendixObject ObjectToInsertInList;
	private java.lang.Long ObjectIndex;

	public JA_List_InsertObjectAtIndex(IContext context, java.util.List<IMendixObject> ProvidedList, IMendixObject ObjectToInsertInList, java.lang.Long ObjectIndex)
	{
		super(context);
		this.ProvidedList = ProvidedList;
		this.ObjectToInsertInList = ObjectToInsertInList;
		this.ObjectIndex = ObjectIndex;
	}

	@java.lang.Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		// BEGIN USER CODE
		// Validate parameters
        if (ObjectToInsertInList == null) {
            throw new IllegalArgumentException("The \"Object to insert in list\" parameter cannot be empty or null.");
        }
        if (ObjectIndex == null) {
            throw new IllegalArgumentException("The \"Object index\" cannot be empty or null.");
        }

        // Create a new list from objectList or an empty list if objectList is null
        ArrayList<IMendixObject> resultList = ProvidedList != null ? new ArrayList<IMendixObject>(ProvidedList) : new ArrayList<IMendixObject>();

        // Convert long index to int for list operations, handling large values
        int actualIndex;
        if (ObjectIndex < 0) {
            Long positiveIndex = Math.abs(ObjectIndex);
            actualIndex = positiveIndex > resultList.size() ? 0 : (int)(resultList.size() - positiveIndex);
        } else {
            actualIndex = ObjectIndex > Integer.MAX_VALUE || ObjectIndex > resultList.size() ? resultList.size() : ObjectIndex.intValue();
        }

        // Add the object at the adjusted index
        resultList.add(actualIndex, ObjectToInsertInList);

        // Return the new list with the modifications
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
		return "JA_List_InsertObjectAtIndex";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
