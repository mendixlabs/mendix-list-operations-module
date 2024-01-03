[![Support](https://img.shields.io/badge/Support-Community%20(no%20active%20support)-orange.svg)](https://docs.mendix.com/appstore/overview/#432-community-supplied-content)
[![Studio](https://img.shields.io/badge/Studio%20version-8.18.1%2B-blue.svg)](https://marketplace.mendix.com/link/studiopro/)
![GitHub release](https://img.shields.io/github/release/mendixlabs/mendix-list-operations-module)
![GitHub issues](https://img.shields.io/github/issues/mendixlabs/mendix-list-operations-module)

# Mendix List Operations Module

Enhance and streamline list manipulation within your Mendix applications with the Mendix List Operations module. This module provides a suite of Java actions designed to handle sorting, filtering, and modifying lists with ease and efficiency.

![screenshot](/assets/ListOperations index.png)

## Java Actions

### Sorting Actions

- **JA_List_MoveObjectToIndex** - Moves an object to a specified index within the list.
- **JA_List_PerformSortingAction** - Performs a sorting action on a specified object in the list.
- **JA_List_SanitizeSorting** - Prepares a list for sorting actions by setting a numeric order.

### Filtering Actions

- **JA_List_FilterChanged** - Filters the list for objects that have been modified.
- **JA_List_FilterIfAttributeHasChanged** - Filters the list for objects where specified attributes have changed.

### List Manipulation Actions

- **JA_List_FlatListAssociations** - Flattens hierarchical associations into a single list.
- **JA_List_GetObjectByIndex** - Retrieves an object from the list based on its index.
- **JA_List_GetRandomObject** - Randomly selects an object from the list.
- **JA_List_InsertObjectAtIndex** - Inserts an object at a specified index within the list.
- **JA_List_JoinAttributeToASingleString** - Compiles a string from the values of a specified attribute for each object in the list.
- **JA_List_Reverse** - Reverses the order of the objects in the list.
- **JA_List_Shuffle** - Randomly shuffles the positions of objects within the list.


## How to Update Deprecated Java Actions

If you're transitioning from older versions of the actions, please see our guide on manually updating to the newer versions, ensuring a smooth transition.

## Dependencies

- No dependencies on other modules or libraries

## License

Apache 2
