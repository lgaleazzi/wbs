# WBS Builder
WBS Builder is a web application to build [Work Breakdown Structures](https://en.wikipedia.org/wiki/Work_breakdown_structure). The user can login and add new projects. For each project he can then build a tree of work deliverables.

![tree](https://user-images.githubusercontent.com/22861111/30911000-32c580fa-a387-11e7-8c50-ab311ed0f1bb.PNG)
##  How to run the web application
WBS Builder is a Spring Boot app with embedded web server and runs on an H2 in-memory database by default. You will need Gradle to build the project. Gradle is integrated in IntelliJ Idea by default. If you use Eclipse, you can use the plugin Buildship to run the project. See here an article on how to install and use [Buildship](http://www.vogella.com/tutorials/EclipseGradle/article.html)
Open the web application at http://localhost:8090
Login with one of the two users generated at start up:
+ user1 / password
+ user2 / password
### Database
The embedded H2 database is wiped when the application is restarted. Alternatively you can use a Mariadb or MySQL database to persist data. After you have installed and started either MariaDB or MySQL:
+ Create a database named 'wbs_db'
+ In the DataConfig.java class, replace @PropertySource("application-h2.properties") with @PropertySource("application-mysql.properties")
+ In the application-mysql.properties file, replace the values for properties spring.datasource.url, spring.datasource.username and spring.datasource.password as needed
## Features
Project dashboard:

![projects](https://user-images.githubusercontent.com/22861111/30910996-3020b32e-a387-11e7-870f-ee2285eb7f12.PNG)

Adding a new element to the WBS:

![form](https://user-images.githubusercontent.com/22861111/30910988-2863d36e-a387-11e7-8b0d-939954286180.PNG)

Elements are either from type "Standard" or "Work Package". Work packages are always the lowest level in the WBS tree.
## Technologies
The application uses:
+ Back end: [Spring boot](https://projects.spring.io/spring-boot/) and [Spring security](https://projects.spring.io/spring-security/)
+ ORM: [Hibernate](http://hibernate.org/)
+ Database: [MariaDB](https://mariadb.org/) or [H2](http://www.h2database.com)
+ Template engine: [Thymeleaf](https://www.thymeleaf.org/)
+ CSS: [Bootstrap](http://getbootstrap.com/)
+ Build Tool: [Gradle](https://gradle.org/)
