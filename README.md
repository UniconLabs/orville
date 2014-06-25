# Open Registry 2

Curent version is `2.0.0-RC2`

## Running the stand-alone war and making a sample RESTful request

A sample configuration file is available at https://github.com/UniconLabs/orville/blob/master/web/src/main/resources/application.properties. To configure the application, download the file and modify as needed (change idMatch configuration, change database configuration, etc) and put this file into an external file system location e.g. `/path/to/config/openregistry.properties` 

1. Download the latest release from https://github.com/UniconLabs/orville/releases
3. Run `java -jar orville-{version}.war --spring.config.location=file:/path/to/config/openregistry.properties`. 
4. Make an HTTP POST request to `http://localhost:8080/v1/sorPeople/test/` with the following test payload:

```json
{
  "sorAttributes": {
    "names": [
      {
        "type": "official",
        "given": "John",
        "family": "Doe"
      }
    ],
    "emailAddresses": [
      {
        "address": "john.doe@gmail.com",
        "type": "personal"
      }
    ],
    "identifiers": [
      {
        "identifier": "jd76",
        "type": "network"
      }
    ],
    "sponsor": {
      "identifier": "12345",
      "type": "enterprise"
    },
    "roleEnds": "2014-08-04T12:00:00Z"
  }
}
```

## Security

The HTTP Basic Auth security for Orville REST endpoints is enabled by default. Without providing a valid username:password base64 encoded value
in HTTP `Authorization` header while invoking Orville HTTP resources, the server will return an HTTP `401` error code.

The configuration is controlled by the following properties
(either in the classpath `application.properties` or an externalized properties file as described above):

```bash
security.user.name=user
security.user.password=user
```

In the `application.properties` included in the classpath, username `user`, and password `user` is used.
Adjust these with more robust username/password values as necessary.

## External servlet container deployment

Orville could be built and deployed to external Servlet containers supporting Servlet v3 spec. e.g. Tomcat7+ as a regular `war` deployment artifact.
In order to use this deployment model, first set the `SPRING_CONFIG_LOCATION` OS environment variable pointing to the orville's externalized properties file
e.g. `export SPRING_CONFIG_LOCATION=file:/path/to/config/openregistry.properties`

To build the war file, from the root of the project just run `./gradlew clean build` The resultant war file will be built and available in
`web/build/libs/` subdirectory. Once it's built, simply deploy it to any Servlet v3 container of choice.

> NOTE: Orville external war deployment has been tested in Tomcat 7.0.42
