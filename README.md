play-slick-example [ ![Codeship Status for techmonad/play-slick-app](https://app.codeship.com/projects/8a9b68a0-4e15-0136-d88f-42007895f430/status?branch=master)](https://app.codeship.com/projects/293442)
=======

-----------------------------------------------------------------------
Instructions :-
-----------------------------------------------------------------------
Clone and run the app(default database is H2):

     $ git clone git@github.com:techmonad/play-slick-app.git
     $ cd play-slick-app
     $ sbt run
    
 Run the all unit test:

     $ sbt test
    
Run the app using Postgres database:

     $ sbt 'run   -Dconfig.file=conf/postgres.conf'
    
-----------------------------------------------------------------------
All the Screens :-
-----------------------------------------------------------------------
### Home Page

![alt tag](/public/images/home.png)

### Add an Employee

![alt tag](/public/images/addEmployee.png)

### Edit an Employee

![alt tag](/public/images/editEmployee.png)

### Delete an Employee

![alt tag](/public/images/deleteEmployee.png)

-----------------------------------------------------------------------
###References :-
-----------------------------------------------------------------------

* [Play 2.6.x](http://www.playframework.com)
* [WebJars](http://www.webjars.org/)
* [Bootstrap](http://getbootstrap.com/css/)
* [Slick](http://slick.typesafe.com/)

