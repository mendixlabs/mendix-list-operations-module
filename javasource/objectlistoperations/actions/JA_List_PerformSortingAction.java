// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package objectlistoperations.actions;

import java.util.List;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import objectlistoperations.impl.Sorting;
import objectlistoperations.impl.SortingValidation;
import objectlistoperations.impl.SortingValidation.ValidationFeedback;

/**
 * This Java action takes an object and a list (which the object is also part of) and changes that object's position in the list, based on a specific 'sorting' attribute being used to sort the list.
 * Commits changed objects if "Commit" parameter set to "true".
 * Returns changed objects if any in a new list otherwise returns empty list.
 */
public class JA_List_PerformSortingAction extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private IMendixObject ObjectToChange;
	private java.util.List<IMendixObject> ProvidedList;
	private java.lang.String SortAttributeName;
	private objectlistoperations.proxies.Enum_SortingAction Action;
	private java.lang.Boolean Commit;

	public JA_List_PerformSortingAction(IContext context, IMendixObject ObjectToChange, java.util.List<IMendixObject> ProvidedList, java.lang.String SortAttributeName, java.lang.String Action, java.lang.Boolean Commit)
	{
		super(context);
		this.ObjectToChange = ObjectToChange;
		this.ProvidedList = ProvidedList;
		this.SortAttributeName = SortAttributeName;
		this.Action = Action == null ? null : objectlistoperations.proxies.Enum_SortingAction.valueOf(Action);
		this.Commit = Commit;
	}

	@java.lang.Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		// BEGIN USER CODE
		IContext context = this.getContext();
		
		ValidationFeedback Valid = SortingValidation.performSortingActionValidation(ProvidedList, ObjectToChange, context, SortAttributeName, Action, Commit);
		if (!Valid.Valid) {
			throw new Exception(Valid.ValidationMessage);	
		}

		List<IMendixObject> resultList = Sorting.objectSortPerformAction(ProvidedList, ObjectToChange, context, SortAttributeName, Action, Commit);	
		
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
		return "JA_List_PerformSortingAction";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
