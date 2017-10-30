# Labrador
This is the Java version of boilerplate code for UI test framework using Selenium and TestNG, which contains support for the following.
- Chrome and Firefox browser
- Screenshot taking upon failure
- TestNG report and stacktrace logging
- Test execution on Selenium Grid, Browserstack, Sauce Labs
- Parallel test execution

Framework can be extended according to your needs. Other versions:
- [Husky](https://github.com/dhtlee/husky) - Javascript ES6 with WebdriverJS (Selenium)

## Resources
- [Selenium-Java](https://seleniumhq.github.io/selenium/docs/api/java/)
- [TestNG](http://testng.org/)

## Pre-requisite
- [Git](https://git-scm.com)
- [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads)
- [Maven 3.3.9+](https://maven.apache.org)

## Setting up environment
1. Clone this repo
<br>`git clone https://github.com/dhtlee/labrador.git`
1. Import project to your IDE of choice
1. Download latest drivers (Note: drivers will be downloaded to _<project_root_dir>/drivers_)
<br>`mvn clean compile -P download-drivers`
1. Create a local copy of **config.properties** from config-template.properties in _src/main/resources_
1. Update driver path and other configuration values in config.properties where needed

## Running the demo tests
- Execute demo TestNG **regression** test suite: Note: There will be 1 test that passes and 1 that fails
<br>`mvn clean test -DsuiteXmlFile=regression.xml` 
- To view test report, open _target/surefire-reports/index.html_ in a browser, navigate to 'Reporter Output'

## Parallel test execution
- Update **thread-count** value in the test suites xml

## License
Labrador is released under the [MIT License](https://opensource.org/licenses/MIT).