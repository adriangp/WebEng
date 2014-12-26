# WebSockets Client (web application)
This project contains a demonstration of a web service build with WebSockets. 

Deploy your code to a [Jetty](http://www.eclipse.org/jetty/) server with ```gradle jettyEclipseRun```. Then, open the page at [http://localhost:8080/](http://localhost:8080/). The WebSocket server must be running at [http://localhost:8025/](http://localhost:8025/)

The user can add, list and delete the tasks that the WebSocket server manage.

The service endpoint is at [http://localhost:8080/index.html](http://localhost:8080/index.html) (see [WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml) for the endpoint mapping). 


