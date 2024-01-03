package objectlistoperations.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaPrimitive.PrimitiveType;

public final class Misc {

	/**
	 * Checks if a given string is not null and not empty after trimming.
	 *
	 * @param value The string to check.
	 * @return true if the string is neither null nor empty after trimming whitespace, false otherwise.
	 */
	public static Boolean isStringNotEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}

	/**
	 * Checks if a boolean value is defined as true or false.
	 * This method is somewhat redundant as a boolean in Java is always true or false,
	 * but it could be used as a placeholder for more complex logic.
	 *
	 * @param commit The boolean value to check.
	 * @return true because in Java a boolean is always either true or false.
	 */
	public static boolean isBooleanDefined(boolean commit) {
        return commit == true || commit == false;
    }

	/**
	 * Validates if the provided index is within the bounds of the provided list.
	 *
	 * @param index The index to check.
	 * @param providedList The list against which to check the index.
	 * @return true if the index is not null and is within the bounds of the list, false otherwise.
	 */
	public static boolean isObjectListIndexValid(Long index, List<IMendixObject> providedList) {
        return index != null && index >= 0 && index < providedList.size();
    }

	/**
	 * Creates a comparator for IMendixObjects that compares based on the long value of a specified attribute.
	 *
	 * @param context The context of the current session.
	 * @param sortAttributeName The name of the attribute to sort by, which should be of type Long.
	 * @return A comparator that compares two IMendixObjects based on the long value of the specified attribute.
	 */
	public static Comparator<IMendixObject> compareBySortOrder(IContext context, String sortAttributeName) {
	    return Comparator.comparing((IMendixObject o) -> {
	        Object value = o.getValue(context, sortAttributeName);
	        // Assuming the sort attribute is either Long or can be defaulted to 0 if null.
	        return (value instanceof Long) ? (Long) value : (Integer) value;
	    });
	}

	/**
     * Gets the minimum and maximum values of a sorting attribute from a list of IMendixObject.
     *
     * @param providedList     The list of IMendixObjects to process.
     * @param sortAttributeName The name of the sorting attribute.
     * @param context          The context of the current session.
     * @return An array where the first element is the minimum value and the second element is the maximum value.
     */
    public static Long[] getMinMaxSortingAttribute(List<IMendixObject> providedList, String sortAttributeName, IContext context, PrimitiveType primitiveType) {
        Long min = providedList.stream()
                .map(obj -> primitiveType == PrimitiveType.Long ? (Long) obj.getValue(context, sortAttributeName) : ((Integer) obj.getValue(context, sortAttributeName)).longValue())// Replace Long with the actual type of your sorting attribute
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);

        Long max = providedList.stream()
                .map(obj -> primitiveType == PrimitiveType.Long ? (Long) obj.getValue(context, sortAttributeName) : ((Integer) obj.getValue(context, sortAttributeName)).longValue()) // Replace Long with the actual type of your sorting attribute
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);

        return new Long[]{min, max};
    }

    /**
     * Checks if a value is between or equal to the minimum and maximum values specified.
     *
     * @param value The value to check.
     * @param minMax An array where the first element is the minimum value and the second element is the maximum value.
     * @return true if the value is between or equal to min and max, false otherwise.
     */
    public static boolean isValueBetweenMinMax(Long value, Long[] minMax) {
        // Ensure minMax has the correct length and the value isn't null
        if (minMax == null || minMax.length != 2 || value == null) {
            return false;
        }
        
        Long min = minMax[0];
        Long max = minMax[1];

        // Ensure min and max aren't null
        if (min == null || max == null) {
            return false;
        }

        // Check if the value is between or equal to min and max
        return value >= min && value <= max;
    }
}
