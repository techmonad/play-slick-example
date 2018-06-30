# --- !Ups
CREATE TABLE "employee"("id" SERIAL PRIMARY KEY ,"name" varchar(200) , "email" varchar(200)  ,"company_name" varchar,"position" varchar);
INSERT INTO "employee" values (1,'Bob', 'bob@xyz.com','ABC Solution','Tech Lead');
INSERT INTO "employee" values (2,'Rob', 'rob@abc.com','ABC Solution','Consultant');
INSERT INTO "employee" values (3,'Joe', 'joe@xyz.com','ABC Solution ','Senior Consultant');


# --- !Downs

DROP TABLE "employee";
