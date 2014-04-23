# Open Registry 2

This software is in the early development status. During development the simplest way to run on the console (which will have the REST API implementation)
is to do the following after checking out the project:

## To run on the command line during development

* Create /etc/openregistry/config/openregistry.properties
* Add appropriate configuration properties there

```bash
> ./ORDevRun
```

## Running the stand-alone jar

1. Download a release from https://github.com/UniconLabs/orville/releases
2. Run `java -jar openregistry-web-{version}.jar`
3. post to http://localhost:8080/v1/sorPeople/test/

## Configuration

A sample configuration file is available at https://github.com/UniconLabs/orville/blob/master/web/src/main/resources/application.properties. To configure the application, download the file and modify as needed (change idMatch configuration, change database configuration, etc). To run the application with the new configuration, run
```
java -jar openregistry-web-{version}.jar --spring.config.location=file:/path/to/config/openregistry.properties
```

**Note:** Currently only the H2 database is supported
