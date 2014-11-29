# REST Client (web application)
This project contains a demonstration of a web service build with [JAX-RS](http://docs.oracle.com/javaee/6/tutorial/doc/giepu.html). 

Deploy your code to a [Jetty](http://www.eclipse.org/jetty/) server with ```gradle jettyEclipseRun```. Then, open the page at [http://localhost:8080/](http://localhost:8080/). The REST server must be running at [http://localhost:8081/](http://localhost:8081/)

The user can create, read, update and delete (CRUD) the tasks that the REST server manage.

The service endpoint is at [http://localhost:8080/index.html](http://localhost:8080/index.html) (see [WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml) for the endpoint mapping). 


