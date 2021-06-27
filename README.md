# DIT Interview Project


This project has been integrated with Swagger UI, once this project is running you should be able to view Swagger UI for API documentation and running the REST APIs from the browser. 

There are Postman project files attached too, if you prefer postman.

To build it, you will need to download and unpack the latest (or recent) version of Maven (https://maven.apache.org/download.cgi)
and put the `mvn` command on your path.
Then, you will need to install a Java 1.8 JDK, and make sure you can run `java` from the command line.

Now you can clone this project and run `mvn clean install` and Maven will compile the project, run test cases, and generate the jar file in the `target` directory. 

The successful output will look like this:

[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ dit-interview-project ---

[INFO] Installing /home/anavulla/MY_DATA/Work/sts_workspace/dit-interview-project/target/dit-interview-project-0.0.1-SNAPSHOT.jar to /home/anavulla/.m2/repository/com/dit/dit-interview-project/0.0.1-SNAPSHOT/dit-interview-project-0.0.1-SNAPSHOT.jar

[INFO] Installing /home/anavulla/MY_DATA/Work/sts_workspace/dit-interview-project/pom.xml to /home/anavulla/.m2/repository/com/dit/dit-interview-project/0.0.1-SNAPSHOT/dit-interview-project-0.0.1-SNAPSHOT.pom

[INFO] ------------------------------------------------------------------------

[INFO] BUILD SUCCESS

[INFO] ------------------------------------------------------------------------

[INFO] Total time:  7.299 s


You can now go to target folder to launch the app using command line
> java -jar dit-interview-project-0.0.1-SNAPSHOT.jar

How you run this code is up to you, but usually you would start by using an IDE like [Eclipse](https://eclipse.org/ide/). The .classpath and .settings are available to run this on IDE.

Once the app is running in your local, you can visit http://localhost:8080 (assuming 8080 is default port) and navigate to [SwaggerUI](http://localhost:8080/swagger-ui.html) for API documentation and running them in the browser.


## Database Setup

The mysql script files are available under  `/src/main/java/resources` 
* `dabatabse.sql`

This script needs to be executed under `root` user to setup app user, database, along with privileges.

* `schema.sql`

This script can be executed as part of the application run, update the flag **mysql.schema.file.execute** to ***true*** in `application.prperties`.

Please make sure that **spring.datasource.username** & **spring.datasource.password** matches with values from `database.sql` if you want to customize them.

***I prefer table names in singular.***

>> **Note**: If you set to true, this will drop the table and re-create it on the app launch, this is intended for development use only.

## Configuration

The configurations are available in `/src/main/java/resources/application.properties`

You can customize logging level and mysql database settings using avilable variables.

* `cookie.security`

This needs to be turned on in production environment to create secure cookie.

* `token.issuer`

This can be updated to modify the issuer name in JWT token.

* `token.ttlMillis`

This can be updated to set the validity of JWT token, this in ***milli seconds***.

* `base64.apikey`

This is the api key for JWT token and is in ***base64*** format. 

>> **Note**: This JWT token generation is using HS256 algorithm, you can use complex alogirthms to set public key private key cert based signing.


## APIs

You can import the Postman files located under `/src/main/java/resources/postman`, or use command line to run the APIs.

* `/hello`

Request
```
curl -X 'GET' \
  'http://localhost:8080/hello' \
  -H 'accept: application/json'

```
Response body
```
{
  "succes": true,
  "data": "Hello, World!",
  "error": null
}
```

* `/create`

Request
```
curl -X 'POST' \
  'http://localhost:8080/create' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "firstname": "John",
    "lastname": "Joe",
    "username": "jjoe123",
    "password": "test@123",
    "email": "john@joe.com"
}'
```
	
Response body
```
{
  "succes": true,
  "data": {
    "id": 1
  },
  "error": null
}
```

* `/login`

Request
```
curl -X 'POST' \
  'http://localhost:8080/login' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "username": "jjoe123",
    "password": "test@123"
}'
```

Response body
```
{
  "succes": true,
  "data": null,
  "error": null
}
```
Response headers
```
HTTP/1.1 200
Set-Cookie: default=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MTBiMjI4OC0zNzg5LTQ0OTktYjlkNy1kMDUyZTNiYTYzODYiLCJpYXQiOjE2MjQ4MTI1MDcsInN1YiI6Impqb2UxMjMiLCJpc3MiOiJkaXQtYXBpIiwiZXhwIjoxNjI0ODEyNTE3fQ.Da7cJtJjsst4dsFAnsh-KGBTuwfKweXqI9SsjVoPd48; Max-Age=604800; Path=/; HttpOnly
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 27 Jun 2021 16:48:27 GMT
Keep-Alive: timeout=60
Connection: keep-alive
```

>> You can read the cookie in postman and set `authtoken` environment variable in ***Tests***, we will use this as request header for `/authenticate` API. I personally do not prefer using cookies, the JWT token can actually be passed in one of the response headers of `/login` API.
```
var cookie =  pm.cookies.toString()
var jwt=cookie.split("=").pop();
pm.environment.set("authtoken", jwt);
```

* `/authenticate`

Request

```
curl --location --request GET 'http://localhost:8080/authenticate' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkMjJlOWQ5OS02ODY2LTRkOTMtYWMxZS02ZmRmYTU3M2YwMzUiLCJpYXQiOjE2MjQ4MTQ0MDQsInN1YiI6Impqb2UxMjMiLCJpc3MiOiJkaXQtYXBpIiwiZXhwIjoxNjI0ODE0NDE0fQ.7t7gRxTdTZYMhO6QJA6UZP9KRsPGJv_5v2qQZJSNAyg' \
--header 'Cookie: default=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJkMjJlOWQ5OS02ODY2LTRkOTMtYWMxZS02ZmRmYTU3M2YwMzUiLCJpYXQiOjE2MjQ4MTQ0MDQsInN1YiI6Impqb2UxMjMiLCJpc3MiOiJkaXQtYXBpIiwiZXhwIjoxNjI0ODE0NDE0fQ.7t7gRxTdTZYMhO6QJA6UZP9KRsPGJv_5v2qQZJSNAyg'
```

Response body

```
{
    "succes": true,
    "data": {
        "authorized": true
    },
    "error": null
}
```












