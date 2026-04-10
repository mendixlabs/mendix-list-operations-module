package objectlistoperations.impl;

import java.util.List;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaPrimitive.PrimitiveType;

import objectlistoperations.proxies.Enum_SortingAction;

public final class SortingValidation {
	public static class ValidationFeedback {
		public PrimitiveType MxPrimitiveType = PrimitiveType.Integer;
        public Boolean Valid = true;
        public String ValidationMessage;
    }

    private static boolean isActionDefined(Enum_SortingAction action) {
        return action != null;
    }

    private static boolean isSortAttributeNameValid(IMendixObject mxObject, String sortAttributeName, IContext context) {
    	if (!mxObject.hasMember(sortAttributeName) || mxObject.getMember(context, sortAttributeName).getValue(context) == null) {
    		return false;
    	}
    	PrimitiveType primitiveType = mxObject.getMetaObject().getMetaPrimitive(sortAttributeName).getType();
        return primitiveType == PrimitiveType.Integer || primitiveType == PrimitiveType.Long;
    }

    private static boolean isListAttributeValid(List<IMendixObject> list, String attributeName, IContext context) {
        return list.stream().allMatch(o -> isSortAttributeNameValid(o, attributeName, context));
    }

    public static ValidationFeedback performSortingActionValidation(List<IMendixObject> providedList, IMendixObject objectToChange, IContext context, String sortAttributeName, Enum_SortingAction action, boolean commit) throws Exception {
        ValidationFeedback feedback = new ValidationFeedback();
        StringBuilder message = new StringBuilder("The following errors were found: ");

        if (!Misc.isBooleanDefined(commit)) {
            message.append("commit boolean is undefined; ");
            feedback.Valid = false;
        }

        if (!isActionDefined(action)) {
            message.append("action is undefined; ");
            feedback.Valid = false;
        }

        if (!Misc.isStringNotEmpty(sortAttributeName)) {
            message.append("sorting attribute has not been provided; ");
            feedback.Valid = false;
        }

        if (objectToChange == null) {
            message.append("an object to sort has not been provided; ");
            feedback.Valid = false;
        } else if (!isSortAttributeNameValid(objectToChange, sortAttributeName, context)) {
        	message.append("the provided object does not have attribute ")
		           .append(sortAttributeName)
		           .append(" or that attribute is not an integer or long; ");
            feedback.Valid = false;
        } else if (providedList != null && !providedList.isEmpty() && providedList.size() >= 2) {
        	if (Sorting.getObjectFromList(objectToChange, providedList) == null) {
                message.append("the provided list does not contain the object to apply changes to; ");
                feedback.Valid = false;
        	}
            if (!isListAttributeValid(providedList, sortAttributeName, context)) {
                message.append("the provided list contains at least one object which does not have the attribute ")
                       .append(sortAttributeName)
                       .append(" or that attribute is not an integer or long; ");
                feedback.Valid = false;
            }
        } else {
            message.append("there are not enough objects in the provided list to provide a sorting operation; ");
            feedback.Valid = false;
        }

        feedback.ValidationMessage = message.toString();
        return feedback;
    }
		
    public static ValidationFeedback performSanitizingValidation(List<IMendixObject> providedList, IContext context, String sortAttributeName, boolean commit, Long startingIndex) throws Exception {
        ValidationFeedback feedback = new ValidationFeedback();
        StringBuilder message = new StringBuilder("The following errors were found: ");

        if (!Misc.isBooleanDefined(commit)) {
            message.append("commit boolean is undefined; ");
            feedback.Valid = false;
        }

        if (startingIndex == null) {
            message.append("starting index parameter has not been specified; ");
            feedback.Valid = false;
        }

        if (!Misc.isStringNotEmpty(sortAttributeName)) {
            message.append("sorting attribute has not been provided; ");
            feedback.Valid = false;
        }

        if (providedList != null && !providedList.isEmpty()) {
            if (!isListAttributeValid(providedList, sortAttributeName, context)) {
                message.append("the provided list contains at least one object which does not have the attribute ")
                       .append(sortAttributeName)
                       .append(" or where that attribute is not an integer or long; ");
                feedback.Valid = false;
            }
        }

        feedback.ValidationMessage = message.toString();
        return feedback;
    }
	
	public static ValidationFeedback performPositionValidation(List<IMendixObject> providedList, IMendixObject objectToChange, IContext context, String sortAttributeName, Long newPositionValue, boolean commit) throws Exception {
        ValidationFeedback feedback = new ValidationFeedback();
        StringBuilder message = new StringBuilder("The following errors were found: ");

        if (!Misc.isBooleanDefined(commit)) {
            message.append("commit boolean is undefined; ");
            feedback.Valid = false;
        }

        if (!Misc.isStringNotEmpty(sortAttributeName)) {
            message.append("sorting attribute has not been provided; ");
            feedback.Valid = false;
        }

		if (newPositionValue == null) {
            message.append("new index has not been provided; ");
            feedback.Valid = false;
		}

		// Object is given
        if (objectToChange == null) {
            message.append("an object to sort has not been provided; ");
            feedback.Valid = false;
        } else if (!isSortAttributeNameValid(objectToChange, sortAttributeName, context)) {
        	message.append("the provided object does not have attribute ")
		           .append(sortAttributeName)
		           .append(" or that attribute is not an integer or long; ");
            feedback.Valid = false;
        } else if (providedList != null && !providedList.isEmpty() && providedList.size() >= 2) {
        	feedback.MxPrimitiveType = objectToChange.getMetaObject().getMetaPrimitive(sortAttributeName).getType();
        	if (Sorting.getObjectFromList(objectToChange, providedList) == null) {
                message.append("the provided list does not contain the object to apply changes to; ");
                feedback.Valid = false;
        	}
            if (!isListAttributeValid(providedList, sortAttributeName, context)) {
                message.append("the provided list contains at least one object which does not have the attribute ")
                       .append(sortAttributeName)
                       .append(" or that attribute is not an integer or long; ");
                feedback.Valid = false;
            } else {
                Long[] minMax = Misc.getMinMaxSortingAttribute(providedList, sortAttributeName, context, feedback.MxPrimitiveType);
    			if (newPositionValue != null && !Misc.isValueBetweenMinMax(newPositionValue, minMax)) {
                    message.append("the new position lies outside of the positions of the object inside the provided list; ");
                    feedback.Valid = false;
            }
			}
        } else {
            message.append("there are not enough objects in the provided list to provide a sorting operation; ");
            feedback.Valid = false;
        }

        feedback.ValidationMessage = message.toString();
        return feedback;
	}
}