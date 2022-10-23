# UP42-backend-challenge

## Built With


* 	[JDK](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/what-is-corretto-11.html) - Java™ Platform, Standard Edition Development Kit
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications

## Assumptions
- File data does not change regularly
- The file always contains valid json
- The file size is acceptable

## To be enhanced
- Add more unit tests and coverages
- API documentation
- Pagination
- Store the file in reliable storage
- Maybe store the quicklook field in a separate key,value datasource
- Exception handling with more descriptive messages
- More logs

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.up42.codingchallenge.BackendCodingChallengeApplication` class from your IDE.

Or from terminal

```cmd
./gradlew bootRun
```