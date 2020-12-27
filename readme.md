# Operacion Fuego de Quasar


## Required installations

- Jdk 15 is needed to run the program. You can download from here 
```
https://openjdk.java.net/
```
- Make sure that JAVA_HOME path is set in environment variables as follows:

    JAVA_HOME: C:\Program Files\Java\jdk15

  Add path in environment variable 'Path' as follows: %JAVA_HOME%\bin

To run the program cd to the project folder and execute the following command:
```
mvnw.cmd spring-boot:run
```

## Services
Three rest services are exposed:
```
POST - http://localhost:8080/topsecret
POST - http://localhost:8080/topsecret_split
GET - http://localhost:8080/topsecret_split
```