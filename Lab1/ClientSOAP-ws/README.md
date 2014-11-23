# SOAP Client (web application)
This project contains a demonstration of a web service build with [JAX-WS](http://docs.oracle.com/javaee/6/tutorial/doc/bnayl.html). 

Deploy your code to a [Jetty](http://www.eclipse.org/jetty/) server with ```gradle jettyEclipseRun```. Then, open the page at [http://localhost:8080/](http://localhost:8080/). The SOAP server must be running at [http://localhost:8081/](http://localhost:8081/)

The user can add, remove or list the tasks that the SOAP server manege.

The service endpoint is at [http://localhost:8080/index.html](http://localhost:8080/index.html) (see [WEB-INF/sun-jaxws.xml](src/main/webapp/WEB-INF/sun-jaxws.xml) for the endpoint mapping). 


