
package todoserversoap.app;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the todoserversoap.app package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RemoveToDoResponse_QNAME = new QName("http://app.toDoServerSOAP/", "removeToDoResponse");
    private final static QName _RemoveToDo_QNAME = new QName("http://app.toDoServerSOAP/", "removeToDo");
    private final static QName _AddToDo_QNAME = new QName("http://app.toDoServerSOAP/", "addToDo");
    private final static QName _AddToDoResponse_QNAME = new QName("http://app.toDoServerSOAP/", "addToDoResponse");
    private final static QName _ListToDos_QNAME = new QName("http://app.toDoServerSOAP/", "listToDos");
    private final static QName _ListToDosResponse_QNAME = new QName("http://app.toDoServerSOAP/", "listToDosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: todoserversoap.app
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RemoveToDo }
     * 
     */
    public RemoveToDo createRemoveToDo() {
        return new RemoveToDo();
    }

    /**
     * Create an instance of {@link ListToDos }
     * 
     */
    public ListToDos createListToDos() {
        return new ListToDos();
    }

    /**
     * Create an instance of {@link AddToDoResponse }
     * 
     */
    public AddToDoResponse createAddToDoResponse() {
        return new AddToDoResponse();
    }

    /**
     * Create an instance of {@link AddToDo }
     * 
     */
    public AddToDo createAddToDo() {
        return new AddToDo();
    }

    /**
     * Create an instance of {@link RemoveToDoResponse }
     * 
     */
    public RemoveToDoResponse createRemoveToDoResponse() {
        return new RemoveToDoResponse();
    }

    /**
     * Create an instance of {@link ListToDosResponse }
     * 
     */
    public ListToDosResponse createListToDosResponse() {
        return new ListToDosResponse();
    }

    /**
     * Create an instance of {@link ToDo }
     * 
     */
    public ToDo createToDo() {
        return new ToDo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveToDoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://app.toDoServerSOAP/", name = "removeToDoResponse")
    public JAXBElement<RemoveToDoResponse> createRemoveToDoResponse(RemoveToDoResponse value) {
        return new JAXBElement<RemoveToDoResponse>(_RemoveToDoResponse_QNAME, RemoveToDoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveToDo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://app.toDoServerSOAP/", name = "removeToDo")
    public JAXBElement<RemoveToDo> createRemoveToDo(RemoveToDo value) {
        return new JAXBElement<RemoveToDo>(_RemoveToDo_QNAME, RemoveToDo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToDo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://app.toDoServerSOAP/", name = "addToDo")
    public JAXBElement<AddToDo> createAddToDo(AddToDo value) {
        return new JAXBElement<AddToDo>(_AddToDo_QNAME, AddToDo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToDoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://app.toDoServerSOAP/", name = "addToDoResponse")
    public JAXBElement<AddToDoResponse> createAddToDoResponse(AddToDoResponse value) {
        return new JAXBElement<AddToDoResponse>(_AddToDoResponse_QNAME, AddToDoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListToDos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://app.toDoServerSOAP/", name = "listToDos")
    public JAXBElement<ListToDos> createListToDos(ListToDos value) {
        return new JAXBElement<ListToDos>(_ListToDos_QNAME, ListToDos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListToDosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://app.toDoServerSOAP/", name = "listToDosResponse")
    public JAXBElement<ListToDosResponse> createListToDosResponse(ListToDosResponse value) {
        return new JAXBElement<ListToDosResponse>(_ListToDosResponse_QNAME, ListToDosResponse.class, null, value);
    }

}
