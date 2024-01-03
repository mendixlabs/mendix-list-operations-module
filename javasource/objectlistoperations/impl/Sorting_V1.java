package objectlistoperations.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.*;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import objectlistoperations.proxies.Enum_SortingAction;

public final class Sorting_V1 {
	
	public static void objectSortPerformAction(List<IMendixObject> AffectedList,  IMendixObject ObjectToChange, IContext context, String SortAttributeName, Enum_SortingAction Action, boolean Commit) throws Exception{
		switch(Action){
		case Step_towards_start:{
			Sorting_V1.objectSortStepAction(AffectedList, ObjectToChange, context, SortAttributeName, Action, Commit);
			break;
			}
		case Step_towards_end:{
			Sorting_V1.objectSortStepAction(AffectedList, ObjectToChange, context, SortAttributeName, Action, Commit);
			break;
			}
		case Move_to_start:{
			Sorting_V1.objectMoveToStartAction(AffectedList, ObjectToChange, context, SortAttributeName, Action, Commit);
			break;
			}
		case Move_to_end:{
			Sorting_V1.objectMoveToEndAction(AffectedList, ObjectToChange, context, SortAttributeName, Action, Commit);
			break;
			}
		default:
			break;
			}
		}
	
	public static void objectSortStepAction(List<IMendixObject> AffectedList,  IMendixObject ObjectToChangeSingle, IContext context, String SortAttributeName, Enum_SortingAction Direction, boolean Commit) throws Exception{
		
		//Identify the object to change as part of the input list
		IMendixObject ObjectToChangeList = getObjectFromList(ObjectToChangeSingle, AffectedList);
		
		//Sort the list
		//Define Comparator
		Comparator<IMendixObject> compareBySortOrder = new Comparator<IMendixObject>() {
		    @SuppressWarnings("unchecked")
			@Override
			public int compare(IMendixObject o1, IMendixObject o2) {
		    	if(o1.getValue(context, SortAttributeName) == null || o2.getValue(context, SortAttributeName) == null) {
		    			return 0;
		        }else {
		        	return ((Comparable<Integer>) o1.getValue(context, SortAttributeName)).compareTo(o2.getValue(context, SortAttributeName));
		        }
		    }
		};
		//Perform sorting using Comparator
		AffectedList.sort(compareBySortOrder);
		
		//Change the values
		switch(Direction){
		case Step_towards_end:{
			if(AffectedList.indexOf(ObjectToChangeList) < AffectedList.size()-1){ //If ObjectToChange is not already the last object in the list
				IMendixObject NextObject = AffectedList.get(AffectedList.indexOf(ObjectToChangeList)+1); //Get the next object in the list
				Integer NewValue = NextObject.getValue(context, SortAttributeName); //Record the next object's value
				NextObject.setValue(context, SortAttributeName, ObjectToChangeList.getValue(context, SortAttributeName)); //Set the next object to have the lesser value
				ObjectToChangeList.setValue(context, SortAttributeName, NewValue); //Set ObjectToChange (List instance) to have the greater value
				ObjectToChangeSingle.setValue(context, SortAttributeName, NewValue); //Set ObjectToChange (Single object instance) to have the greater value
				//Optionally commit the changes
				if(Commit == true) {
					Core.commit(context, ObjectToChangeList);
					Core.commit(context, ObjectToChangeSingle);
					Core.commit(context, NextObject);
					}
				}
			break;
			}
		case Step_towards_start:{
			if(AffectedList.indexOf(ObjectToChangeList) != 0){ //If ObjectToChange is not already the first object in the list
				IMendixObject PreviousObject = AffectedList.get(AffectedList.indexOf(ObjectToChangeList)-1); //Get the next object in the list
				Integer NewValue = PreviousObject.getValue(context, SortAttributeName); //Record the previous object's value
				PreviousObject.setValue(context, SortAttributeName, ObjectToChangeList.getValue(context, SortAttributeName)); //Set the next object to have the lesser value
				ObjectToChangeList.setValue(context, SortAttributeName, NewValue); //Set ObjectToChange (List instance) to have the greater value
				ObjectToChangeSingle.setValue(context, SortAttributeName, NewValue); //Set ObjectToChange (Single object instance) to have the greater value
				//Optionally commit the changes
				if(Commit == true) {
					Core.commit(context, ObjectToChangeList);
					Core.commit(context, PreviousObject);	
					}
				}
			break;
			}
		default:{
			}		
		}
	}
	
	public static void objectMoveToStartAction(List<IMendixObject> AffectedList,  IMendixObject ObjectToChangeSingle, IContext context, String SortAttributeName, Enum_SortingAction Direction, boolean Commit) throws Exception{
		
		//Identify the object to change as part of the input list
		IMendixObject ObjectToChangeList = getObjectFromList(ObjectToChangeSingle, AffectedList);
		
		/* This section has now been made an immediate set of steps on the Java Actions themselves
		//Sort the list
		//Define Comparator
		Comparator<IMendixObject> compareBySortOrder = new Comparator<IMendixObject>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(IMendixObject o1, IMendixObject o2) {
			   	if(o1.getValue(context, SortAttributeName) == null || o2.getValue(context, SortAttributeName) == null) {
			   			return 0;
			    }else {
				   	return ((Comparable<Integer>) o1.getValue(context, SortAttributeName)).compareTo(o2.getValue(context, SortAttributeName));
				   	}
				}
			};
		//Perform sorting using Comparator
		AffectedList.sort(compareBySortOrder);
		*/
		
		List<IMendixObject> ListToAmend = AffectedList.subList(0, AffectedList.indexOf(ObjectToChangeList)+1);
		Integer SetValue = ListToAmend.get(0).getValue(context, SortAttributeName);
		ListToAmend.forEach(obj -> obj.setValue(context, SortAttributeName, ((Integer) obj.getValue(context, SortAttributeName))+1));
		ObjectToChangeList.setValue(context, SortAttributeName, SetValue);
		ObjectToChangeSingle.setValue(context, SortAttributeName, SetValue);
		
		if(Commit == true) {
			Core.commit(context, ListToAmend);
			Core.commit(context, ObjectToChangeSingle);
			}
		}
	
	public static void objectMoveToEndAction(List<IMendixObject> AffectedList,  IMendixObject ObjectToChangeSingle, IContext context, String SortAttributeName, Enum_SortingAction Direction, boolean Commit) throws Exception{
		//Identify the object to change as part of the input list
		IMendixObject ObjectToChangeList = getObjectFromList(ObjectToChangeSingle, AffectedList);
				
		//Sort the list
		//Define Comparator
		Comparator<IMendixObject> compareBySortOrder = new Comparator<IMendixObject>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(IMendixObject o1, IMendixObject o2) {
			   	if(o1.getValue(context, SortAttributeName) == null || o2.getValue(context, SortAttributeName) == null) {
			   			return 0;
			    }else {
				   	return ((Comparable<Integer>) o1.getValue(context, SortAttributeName)).compareTo(o2.getValue(context, SortAttributeName));
				   	}
				}
			};
		//Perform sorting using Comparator
		AffectedList.sort(compareBySortOrder);
		
		List<IMendixObject> ListToAmend = AffectedList.subList(AffectedList.indexOf(ObjectToChangeList), AffectedList.size());
		Integer SetValue = ListToAmend.get(ListToAmend.size()-1).getValue(context, SortAttributeName); //Get the value of the last object in the list
		ListToAmend.forEach(obj -> obj.setValue(context, SortAttributeName, ((Integer) obj.getValue(context, SortAttributeName))-1));
		ObjectToChangeList.setValue(context, SortAttributeName, SetValue);
		ObjectToChangeSingle.setValue(context, SortAttributeName, SetValue);
		
		if(Commit == true) {
			Core.commit(context, ListToAmend);
			Core.commit(context, ObjectToChangeSingle);
			}
		}
	
	public static void moveToIndex(List<IMendixObject> AffectedList,  IMendixObject ObjectToChangeSingle, IContext context, String SortAttributeName, int NewIndex, boolean Commit) throws Exception{
		//Identify the object to change as part of the input list
		IMendixObject ObjectToChangeList = getObjectFromList(ObjectToChangeSingle, AffectedList);
		
		Integer OldIndex = AffectedList.indexOf(ObjectToChangeList);
		NewIndex -= 1; //Set the input (more human-readable position) to java index
		if( OldIndex < NewIndex) {
			List<IMendixObject> SubsetList = AffectedList.subList(OldIndex, NewIndex+1); //subList min is inclusive, max is exclusive
			Sorting_V1.objectMoveToEndAction(SubsetList, ObjectToChangeSingle, context, SortAttributeName, null, Commit);
		}else if(OldIndex > NewIndex){
			List<IMendixObject> SubsetList = AffectedList.subList(NewIndex, OldIndex+1); //subList min is inclusive, max is exclusive
			Sorting_V1.objectMoveToStartAction(SubsetList, ObjectToChangeSingle, context, SortAttributeName, null, Commit);
		}else { //Therefore, OldIndex == NewIndex
			//Do nothing, no change is needed
		}
		};
	
	public static void listSanitize(List<IMendixObject> AffectedList, IContext context, String SortAttributeName, boolean Commit) throws Exception{
		//Sort the list
		//Define Comparator
		Comparator<IMendixObject> compareBySortOrder = new Comparator<IMendixObject>() {
		    @SuppressWarnings("unchecked")
			@Override
			public int compare(IMendixObject o1, IMendixObject o2) {
			   	if(o1.getValue(context, SortAttributeName) == null || o2.getValue(context, SortAttributeName) == null) {
			   			return 0;
			    }else {
				   	return ((Comparable<Integer>) o1.getValue(context, SortAttributeName)).compareTo(o2.getValue(context, SortAttributeName));
				   	}
				}
			};
		
		//Perform sorting using Comparator
		AffectedList.sort(compareBySortOrder);
		
		Integer SortValue = 1;
		for(IMendixObject Obj : AffectedList) {
			Obj.setValue(context, SortAttributeName, SortValue);
			SortValue += 1;
		}
		
		if(Commit == true) {
			Core.commit(context, AffectedList);
		}
	}	
	
	public static IMendixObject getObjectFromList(IMendixObject ObjToChange, List <IMendixObject> AffectedList) {
		List<IMendixObject> newobj =  (List<IMendixObject>) AffectedList.stream()
				.filter((obj -> obj.getId().equals(ObjToChange.getId())))
				.collect(Collectors.toList());
		return newobj.get(0);
	}
}
