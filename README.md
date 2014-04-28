# Open Registry 2

This software is in the development status.

## Running the stand-alone jar

1. Download a release from https://github.com/UniconLabs/orville/releases
2. Run `java -jar openregistry-web-{version}.jar`. *Note:* M2 requires that the system be run with an external configuration file as describe in the [Configuration](#configuration) section.
3. post to http://localhost:8080/v1/sorPeople/test/

## Configuration

A sample configuration file is available at https://github.com/UniconLabs/orville/blob/master/web/src/main/resources/application.properties. To configure the application, download the file and modify as needed (change idMatch configuration, change database configuration, etc). To run the application with the new configuration, run
```
java -jar openregistry-web-{version}.jar --spring.config.location=file:/path/to/config/openregistry.properties
```

## Security

The HTTP Basic Auth security for Orville REST endpoints is enabled by default. Without providing a valid username:password base64 encoded value
in HTTP `Authorization` header while invoking Orvile HTTP resources, the server will return an HTTP `401` error code.

The configuration is controlled by the following properties
(either in the classpath `application.properties` or an externalized properties file as described above):

```bash
security.user.name=user
security.user.password=user
```

In the `application.properties` included in the classpath, username `user`, and password `user` is used.
Adjust these with more robust username/password values as necessary.

## Database backend

**Note:** Currently only the H2 database is supported
