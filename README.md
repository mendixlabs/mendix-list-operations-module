[![Support](https://img.shields.io/badge/Support-Community%20(no%20active%20support)-orange.svg)](https://docs.mendix.com/appstore/overview/#432-community-supplied-content)
[![Studio](https://img.shields.io/badge/Studio%20version-9.18.0%2B-blue.svg)](https://marketplace.mendix.com/link/studiopro/)
![GitHub release](https://img.shields.io/github/release/mendixlabs/mendix-list-operations-module)
![GitHub issues](https://img.shields.io/github/issues/mendixlabs/mendix-list-operations-module)

# Mendix List Operations Module

Enhance and streamline list manipulation within your Mendix applications with the Mendix List Operations module. This module provides a suite of Java actions designed to handle sorting, filtering, and modifying lists with ease and efficiency.

![screenshot](assets/ListOperationsIndex.png)

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
- **JA_List_GetIndexOfObject** - Returns an index of specified object in the list.
- **JA_List_GetObjectByIndex** - Retrieves an object from the list based on its index.
- **JA_List_GetRandomObject** - Randomly selects an object from the list.
- **JA_List_InsertObjectAtIndex** - Inserts an object at a specified index within the list.
- **JA_List_JoinAttributeToASingleString** - Compiles a string from the values of a specified attribute for each object in the list.
- **JA_List_Reverse** - Reverses the order of the objects in the list.
- **JA_List_Shuffle** - Randomly shuffles the positions of objects within the list.


## How to Update Deprecated Java Actions

If you're transitioning from older sorting Java actions that are now deprecated to the updated ones, please note that this process requires manual intervention for each use case. Here's a guide to help you through the update:

Identify Deprecated Actions: First, identify all instances where deprecated sorting Java actions are used within your Mendix projects.

1. Understand the Changes: Familiarize yourself with the updates. Notably, a new "Starting index" parameter has been added to the Sanitize sorting action. Additionally, all updated sorting Java actions now return a list of changed objects, which is a change from the previous behavior.
2. Plan the Update: Before making changes, plan how you'll integrate the new parameters and handle the returned list of changed objects. Consider how these changes will impact your current workflow and data handling.
3. Manual Replacement: Replace each instance of the deprecated action with the updated action manually. Ensure that you:
4. Adjust for the new "Starting index" parameter in the Sanitize sorting action.
   Modify your microflows or logic to handle the list of changed objects returned by the sorting actions.
5. Testing: After updating the actions, thoroughly test your application to ensure that the new actions are functioning as expected and that your application still behaves correctly.

Please take these differences into consideration when updating your actions to a newer version. This careful approach will help minimize disruption and ensure a smooth transition to the enhanced functionalities of the updated Java actions.

## Examples and Testing

Link with deployed examples project (free node - could be availability issues):
[link placeholder]

You can use this repository to run included project. It contains additional modules that can help to understand functionality better.

Testing of this module can be done via the "Examples" pages or "Unit tests" page. There are buttons in the header that can be used to add or remove sample data from your database.

## Mendix versions successfully tested on

- 8.18.1 (V 2.x)
- 8.18.27 (V 2.x)
- 9.18.0
- 9.24.1
- 9.24.13
- 10.6.0

## Dependencies

- No dependencies on other modules or libraries

## License

Apache 2
