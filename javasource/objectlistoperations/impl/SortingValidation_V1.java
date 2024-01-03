package objectlistoperations.impl;

import java.util.ArrayList;
import java.util.List;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaPrimitive.PrimitiveType;

import objectlistoperations.proxies.Enum_SortingAction;

public final class SortingValidation_V1 {
	public static class ValidationFeedback{
		public Boolean Valid;
		public String ValidationMessage;
	}
	
	public static ValidationFeedback performValidation(List<IMendixObject> AffectedList, IMendixObject ObjectToChange, IContext context, String SortAttributeName, Enum_SortingAction Action, boolean Commit) throws Exception{
		// Validations
		Boolean Valid = true;
		String ExceptionMessage = "The following errors were found: ";
		
		// Commit value is not empty (this should never be possible as Booleans must always be True or False)
		if(Commit != true && Commit != false){
			ExceptionMessage += "commit boolean is undefined; ";
			Valid = false;
		}
		
		//Action specified
		if(Action == null){
			ExceptionMessage += "action is undefined; ";
			Valid = false;
		}
			
		// Object is given
		if(ObjectToChange == null){
			ExceptionMessage += "an object to sort has not been provided; ";
			Valid = false;
		}else{
			// SortAttributeName is specified	
			if(SortAttributeName == null){
				ExceptionMessage += "a sorting attribute has not been specified; ";
				Valid = false;
			}else {
				// Provided SortAttributeName is an attribute of the affected object
				if(ObjectToChange.hasMember(SortAttributeName) == false) {
					ExceptionMessage += "the object to sort provided does not have an attribute "+SortAttributeName+"; ";
					Valid = false;
				}else{
					// Sort attribute has a value
					if(ObjectToChange.getMember(context, SortAttributeName).getValue(context) == null) {
						ExceptionMessage += "the object to sort provided does not already have a value for its sorting attribute; ";
						Valid = false;
					}else{
						// Sort attribute is of type integer
						//if(ObjectToChange.getMember(context, SortAttributeName).getValue(context).getClass() != java.lang.Integer.class) {
						if(ObjectToChange.getMetaObject().getMetaPrimitive(SortAttributeName).getType() != PrimitiveType.Integer) {
							ExceptionMessage += "the attribute specified, "+SortAttributeName+", is not an integer; ";
							Valid = false;
						}
					}
				}
				// Affected list has at least 2 objects
				if(AffectedList.size() < 2) { 
					ExceptionMessage += "there are not enough objects in the provided list to provide a sorting operation; ";
					Valid = false;
				}else {

					if(Sorting.getObjectFromList(ObjectToChange,AffectedList) == null){
						ExceptionMessage += "the provided list does not contain the object to apply changes to; ";
						Valid = false;
					}
					
					List<IMendixObject> ValidList = new ArrayList<IMendixObject>(AffectedList); // duplicate the input list
					// Assume all objects are valid. Then, remove any which do not have the specified attribute or that do but the attribute is not an integer
					ValidList.removeIf(o -> (o.hasMember(SortAttributeName) == false) || o.getMetaObject().getMetaPrimitive(SortAttributeName).getType() != PrimitiveType.Integer || o.getValue(context, SortAttributeName) == null); // remove any objects which have the sort attribute or 
					if(ValidList.size() != AffectedList.size()) { //if not all objects are valid
						ExceptionMessage += "the provided list contains at least 1 object which does not have an attribute "+SortAttributeName+"; where this attribute is not an integer or where the attribute currently has an empty value; ";
						Valid = false;
					}
				}
			}
		}
		
		ValidationFeedback ValidationFeedback = new ValidationFeedback();
		ValidationFeedback.ValidationMessage = ExceptionMessage;
		ValidationFeedback.Valid = Valid;
		
		return ValidationFeedback;
	}
		
	public static ValidationFeedback performSanitizingValidation(List<IMendixObject> AffectedList, IContext context, String SortAttributeName, boolean Commit) throws Exception{
		// Validations
		Boolean Valid = true;
		String ExceptionMessage = "The following errors were found: ";
		
		// Commit value is not empty (this should never be possible as Booleans must always be True or False)
		if(Commit != true && Commit != false){
			ExceptionMessage += "commit boolean is undefined; ";
			Valid = false;
		}
		
		// Assume all objects are valid. Then, remove any which do not have the specified attribute or that do but the attribute is not an integer
		List<IMendixObject> ValidList = new ArrayList<IMendixObject>(AffectedList); // duplicate the input list
		ValidList.removeIf(o -> (o.hasMember(SortAttributeName) == false) || o.getMetaObject().getMetaPrimitive(SortAttributeName).getType() != PrimitiveType.Integer); // remove any objects which have the sort attribute or 
		if(ValidList.size() != AffectedList.size()) { //if not all objects are valid
			ExceptionMessage += "the provided list contains at least 1 object which does not have an attribute "+SortAttributeName+" or where that attribute is not an integer ; ";
			Valid = false;
		}
		
		ValidationFeedback ValidationFeedback = new ValidationFeedback();
		ValidationFeedback.ValidationMessage = ExceptionMessage;
		ValidationFeedback.Valid = Valid;
		
		return ValidationFeedback;
	}
	
	public static ValidationFeedback performIndexValidation(List<IMendixObject> AffectedList, IMendixObject ObjectToChange, IContext context, String SortAttributeName, Integer Index, boolean Commit) throws Exception{
		// Validations
		Boolean Valid = true;
		String ExceptionMessage = "The following errors were found: ";
		
		// Commit value is not empty (this should never be possible as Booleans must always be True or False)
		if(Commit != true && Commit != false){
			ExceptionMessage += "commit boolean is undefined; ";
			Valid = false;
		}
			
		// Object is given
		if(ObjectToChange == null){
			ExceptionMessage += "an object to sort has not been provided; ";
			Valid = false;
		}else{
			// SortAttributeName is specified	
			if(SortAttributeName == null){
				ExceptionMessage += "a sorting attribute has not been specified; ";
				Valid = false;
			}else {
				// Provided SortAttributeName is an attribute of the affected object
				if(ObjectToChange.hasMember(SortAttributeName) == false) {
					ExceptionMessage += "the object to sort provided does not have an attribute "+SortAttributeName+"; ";
					Valid = false;
				}else{
					// Sort attribute has a value
					if(ObjectToChange.getMember(context, SortAttributeName).getValue(context) == null) {
						ExceptionMessage += "the object to sort provided does not already have a value for its sorting attribute; ";
						Valid = false;
					}else{
						// Sort attribute is of type integer
						//if(ObjectToChange.getMember(context, SortAttributeName).getValue(context).getClass() != java.lang.Integer.class) {
						if(ObjectToChange.getMetaObject().getMetaPrimitive(SortAttributeName).getType() != PrimitiveType.Integer) {
							ExceptionMessage += "the attribute specified, "+SortAttributeName+", is not an integer; ";
							Valid = false;
						}
					}
				}
				// Affected list has at least 2 objects
				if(AffectedList.size() < 2) { 
					ExceptionMessage += "there are not enough objects in the provided list to provide a sorting operation; ";
					Valid = false;
				}else {

					if(Sorting.getObjectFromList(ObjectToChange,AffectedList) == null){
						ExceptionMessage += "the provided list does not contain the object to apply changes to; ";
						Valid = false;
					}
					
					List<IMendixObject> ValidList = new ArrayList<IMendixObject>(AffectedList); // duplicate the input list
					// Assume all objects are valid. Then, remove any which do not have the specified attribute or that do but the attribute is not an integer
					ValidList.removeIf(o -> (o.hasMember(SortAttributeName) == false) || o.getMetaObject().getMetaPrimitive(SortAttributeName).getType() != PrimitiveType.Integer || o.getValue(context, SortAttributeName) == null); // remove any objects which have the sort attribute or 
					if(ValidList.size() != AffectedList.size()) { //if not all objects are valid
						ExceptionMessage += "the provided list contains at least 1 object which does not have an attribute "+SortAttributeName+"; where this attribute is not an integer or where the attribute currently has an empty value; ";
						Valid = false;
					}
				}
				if(Index == null) {
					ExceptionMessage += "no index is provided; ";
					Valid = false;
				}else {
					if(Index < 1 || Index > AffectedList.size()) {
						ExceptionMessage += "the provided index lies outside the size of the provided list; ";
						Valid = false;
					}
				}
			}
		}
		
		ValidationFeedback ValidationFeedback = new ValidationFeedback();
		ValidationFeedback.ValidationMessage = ExceptionMessage;
		ValidationFeedback.Valid = Valid;
		
		return ValidationFeedback;
	}
}