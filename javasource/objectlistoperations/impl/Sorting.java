package objectlistoperations.impl;

import java.util.ArrayList;
import java.util.List;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaPrimitive.PrimitiveType;

import objectlistoperations.proxies.Enum_SortingAction;

public final class Sorting {
	
	public static List<IMendixObject> objectSortPerformAction(List<IMendixObject> providedList, IMendixObject objectToChange, IContext context, String sortAttributeName, Enum_SortingAction action, boolean commit) throws Exception {
	    List<IMendixObject> resultList = new ArrayList<IMendixObject>();
	    
	    // Sort the provided list of objects using a custom comparator based on the provided sort attribute name.
	    List<IMendixObject> sortedList = new ArrayList<IMendixObject>(providedList);
	    sortedList.sort(Misc.compareBySortOrder(context, sortAttributeName));
	    
	    // Identify the object to change within the provided list.
	    IMendixObject objectToChangeList = getObjectFromList(objectToChange, sortedList);

	    switch(action) {
	        case Step_towards_start:
	        case Step_towards_end: {
	            // If the action is to move the object one step towards the start or the end,
	            // call the objectSortStepAction method to handle the repositioning.
	            resultList = Sorting.objectSortStepAction(sortedList, objectToChangeList, context, sortAttributeName, action, commit);
	            break;
	        }
	        case Move_to_start: {
	            // Check if the object is not already at the start of the list.
	            if (sortedList.indexOf(objectToChangeList) != 0) {
	                // Get a sublist from the start to the current position of the object to change.
	                resultList = sortedList.subList(0, sortedList.indexOf(objectToChangeList) + 1);
	                
	                // Move the specified object to the start of the list using the moveObject method.
	                Sorting.moveObject(resultList, objectToChangeList, context, sortAttributeName, commit, true);    
	            }
	            break;
	        }
	        case Move_to_end: {
	            // Check if the object is not already at the end of the list.
	            if (sortedList.indexOf(objectToChangeList) < sortedList.size() - 1) {
	                // Get a sublist from the current position of the object to change to the end of the list.
	                resultList = sortedList.subList(sortedList.indexOf(objectToChangeList), sortedList.size());
	                
	                // Move the specified object to the end of the list using the moveObject method.
	                Sorting.moveObject(resultList, objectToChangeList, context, sortAttributeName, commit, false);
	            }
	            break;
	        }
	        default:
	            break;
	    }

	    // Return the list of objects that were affected by the sort action.
	    return resultList;
	}

	private static List<IMendixObject> objectSortStepAction(List<IMendixObject> providedList, IMendixObject objectToChangeList, IContext context, String sortAttributeName, Enum_SortingAction direction, boolean commit) throws Exception {
	    List<IMendixObject> resultList = new ArrayList<>();

	 // Find the current index of the object to be moved.
	    int currentIndex = providedList.indexOf(objectToChangeList);

	    // Determine if it's possible to move the object in the specified direction.
	    boolean canMakeAStep = 
	            (direction == Enum_SortingAction.Step_towards_end && currentIndex < providedList.size() - 1)
	            || (direction == Enum_SortingAction.Step_towards_start && currentIndex > 0);

	    if (canMakeAStep) {
	        // Calculate the target index based on the direction.
	        int targetIndex = direction == Enum_SortingAction.Step_towards_end ? currentIndex + 1 : currentIndex - 1;

	        // Swap the sort values of the object to move and its neighbor in the target direction.
	        swapValues(providedList, context, sortAttributeName, currentIndex, targetIndex);

	        // Add the moved objects to the result list.
	        resultList.add(providedList.get(currentIndex));
	        resultList.add(providedList.get(targetIndex));
	        
	        // Commit the changes to the database if required.
	        if (commit) {
	            Core.commit(context, resultList);
	        }
	    }

	    // Return the list of objects affected by the step action.
	    return resultList;
	}

	private static void swapValues(List<IMendixObject> providedList, IContext context, String sortAttributeName, int index1, int index2) throws Exception {
	    IMendixObject firstObject = providedList.get(index1);
	    IMendixObject secondObject = providedList.get(index2);
	    Object value1 = firstObject.getValue(context, sortAttributeName);
	    Object value2 = secondObject.getValue(context, sortAttributeName);

	    firstObject.setValue(context, sortAttributeName, value2);
	    secondObject.setValue(context, sortAttributeName, value1);
	}
	
	private static void moveObject(List<IMendixObject> providedList, IMendixObject objectToMove, IContext context, String sortAttributeName, boolean commit, boolean moveToStart) throws Exception {
	    if (providedList.isEmpty()) {
	        return;
	    }

	    // Determine the target index based on the move direction.
	    int index = moveToStart ? 0 : providedList.size() - 1;

	    // Update the sorting attribute for all affected objects.
	    updateSortAttribute(providedList, objectToMove, context, sortAttributeName, index);
	    
	    // Commit the changes to the database if required.
	    if (commit) {
	        Core.commit(context, providedList);
	    }
	}

	private static void updateSortAttribute(List<IMendixObject> providedList, IMendixObject objectToMove, IContext context, String sortAttributeName, int index) throws Exception {
	    Object setValue = providedList.get(index).getValue(context, sortAttributeName);
	    
	    // Determine the value increment based on move direction.
	    int increment = index == 0 ? 1 : -1;

	    // Update the sort attribute for all objects in the list.
	    providedList.forEach(obj -> {
	        Object currentValue = obj.getValue(context, sortAttributeName);
	        // Adjust the sort value based on its data type.
	        if (currentValue instanceof Integer) {
	            obj.setValue(context, sortAttributeName, (Integer) currentValue + increment);
	        } else if (currentValue instanceof Long) {
	            obj.setValue(context, sortAttributeName, (Long) currentValue + increment);
	        }
	    });

	    // Set the sort attribute of the object to move to its new value.
	    objectToMove.setValue(context, sortAttributeName, setValue);
	}
	
	public static List<IMendixObject> moveToPosition(List<IMendixObject> providedList, IMendixObject objectToChangeSingle, IContext context, String sortAttributeName, Long newPosition, boolean commit, PrimitiveType primitiveType) throws Exception {
		// Early return for an empty or null provided list.
	    if (providedList == null || providedList.isEmpty()) {
	        return new ArrayList<IMendixObject>();
	    }

	    // Sort the provided list of objects using a custom comparator based on the provided sort attribute name.
	    List<IMendixObject> sortedList = new ArrayList<IMendixObject>(providedList);
	    sortedList.sort(Misc.compareBySortOrder(context, sortAttributeName));

	    // Find the object in the list and its current index.
	    IMendixObject objectToChangeList = getObjectFromList(objectToChangeSingle, sortedList);
	    IMendixObject objectForcedToMove= findObjectByAttributeValue(sortAttributeName, newPosition, sortedList, context, primitiveType);
	    int oldIndex = sortedList.indexOf(objectToChangeList);
	    int newIndex = sortedList.indexOf(objectForcedToMove);

	    // No operation needed if the old and new indexes are the same.
	    if (oldIndex == newIndex) {
	        return new ArrayList<IMendixObject>();
	    }

	    // Determine the range of affected objects and perform the move.
	    int startIndex = Math.min(oldIndex, newIndex);
	    int endIndex = Math.max(oldIndex, newIndex) + 1;
	    List<IMendixObject> resultList = new ArrayList<>(sortedList.subList(startIndex, endIndex));
	    boolean moveTowardsStart = oldIndex > newIndex;

	    // Move the object to the new index within the range of affected objects.
	    Sorting.moveObject(resultList, objectToChangeList, context, sortAttributeName, commit, moveTowardsStart);

	    return resultList;
	}

	public static List<IMendixObject> listSanitize(List<IMendixObject> providedList, IContext context, String sortAttributeName, boolean commit, long startingIndex) throws Exception {

		List<IMendixObject> resultList = new ArrayList<IMendixObject>();

		// Early return an empty list if the provided list is null or empty.
	    if (providedList == null || providedList.isEmpty()) {
	        return resultList;
	    }

	    // Sort the provided list of objects using a custom comparator based on the provided sort attribute name.
	    List<IMendixObject> sortedList = new ArrayList<IMendixObject>(providedList);
	    sortedList.sort(Misc.compareBySortOrder(context, sortAttributeName));
	    
	    // Initialize the sort value to start sequencing from.
	    Long sortValue = startingIndex;

	    // Iterate through each object in the list to ensure its sort attribute is sequential.
	    for(IMendixObject obj : sortedList) {
	        // Check if the current object's sort value needs updating.
	        if (!obj.getValue(context, sortAttributeName).equals(sortValue)) {
	            // Update the object's sort attribute to the current sort value.
	            obj.setValue(context, sortAttributeName, sortValue);
	            // Add the object to the result list as it's been modified.
	            resultList.add(obj);
	        }
	        // Increment the sort value for the next object.
	        sortValue += 1;
	    }
	    
	    // Commit the changes to the database if required and changes have been made.
	    if (commit && !resultList.isEmpty()) {
	        Core.commit(context, resultList);
	    }
	    
	    // Return the list of modified objects.
	    return resultList;
	}
	
	public static IMendixObject getObjectFromList(IMendixObject objToChange, List<IMendixObject> providedList) {
	    // Use stream to filter through the affectedList to find the first object matching the ID of objToChange.
	    return providedList.stream()
	            .filter(obj -> obj.getId().equals(objToChange.getId()))
	            .findFirst() // This will return the first matching object if present, wrapped in an Optional.
	            .orElse(null); // If no matching object is found, return null.
	}
	
	public static IMendixObject findObjectByAttributeValue(String sortAttributeName, Long value, List<IMendixObject> providedList, IContext context, PrimitiveType primitiveType) {
        return providedList.stream()
                .filter(obj -> {
                    Object attributeValue = obj.getValue(context, sortAttributeName);
                    return primitiveType == PrimitiveType.Integer
                            ? ((Integer) attributeValue).longValue() == value
                            : attributeValue.equals(value);
                })
                .findFirst() // This will return the first matching object if present, wrapped in an Optional.
                .orElse(null); // If no matching object is found, return null.
    }
}
