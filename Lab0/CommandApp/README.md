# Tasks list with GSON
This project contains a demonstration of [GSON](https://code.google.com/p/google-gson/). Gson is a Java library that can be used to convert arbitrary Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.

The following examples are included:
* __AddToDo__ (```gradle -q addToDo```) tests first it the file ```todos_list.json``` exists. If it exists, the example unmarshalls its content into Java objects. Example code expects that the file contains an ```ListTasks``` instance. Then, code asks to the user the details of a ```ToDo``` and then the task is added to the list of tasks. Finally, the list of tasks is marshalled into a JSON file with name ```todos_list.json```.
* __ListTasks__ (```gradle -q listToDo```) unmarshalls ```todos_list.json``` into Java objects and then dump the objects to the console.
