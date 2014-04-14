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

1. Download https://github.com/UniconLabs/orville/releases/download/2.0-M1/openregistry-web-2.0.0-M1.jar
2. Run `java -jar openregistry-web-2.0.0-M1.jar`
3. post to http://localhost:8080/v1/sorPeople/test/
