
package todoserversoap.app;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.1
 * 
 */
@WebService(name = "ToDoServer", targetNamespace = "http://app.toDoServerSOAP/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ToDoServer {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "removeToDo", targetNamespace = "http://app.toDoServerSOAP/", className = "todoserversoap.app.RemoveToDo")
    @ResponseWrapper(localName = "removeToDoResponse", targetNamespace = "http://app.toDoServerSOAP/", className = "todoserversoap.app.RemoveToDoResponse")
    public String removeToDo(
        @WebParam(name = "arg0", targetNamespace = "")
        ToDo arg0);

    /**
     * 
     * @return
     *     returns java.util.List<todoserversoap.app.ToDo>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listToDos", targetNamespace = "http://app.toDoServerSOAP/", className = "todoserversoap.app.ListToDos")
    @ResponseWrapper(localName = "listToDosResponse", targetNamespace = "http://app.toDoServerSOAP/", className = "todoserversoap.app.ListToDosResponse")
    public List<ToDo> listToDos();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addToDo", targetNamespace = "http://app.toDoServerSOAP/", className = "todoserversoap.app.AddToDo")
    @ResponseWrapper(localName = "addToDoResponse", targetNamespace = "http://app.toDoServerSOAP/", className = "todoserversoap.app.AddToDoResponse")
    public String addToDo(
        @WebParam(name = "arg0", targetNamespace = "")
        ToDo arg0);

}
