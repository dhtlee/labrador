# Labrador
UI test framework template with Selenium and TestNG. Framework contains support for the following:
- Chrome and Firefox browser
- Screenshot taking upon failure
- TestNG report and stacktrace logging
- Test execution on Selenium Grid, Browserstack, Sauce Labs
- Parallel test execution

### Pre-requisite
- Maven 3.3.9+ installed
- JDK 1.8+ installed

### Setting up environment
1. Clone this repo
1. Import project to your IDE of choice
1. Download latest drivers (Note: drivers will be downloaded to _<project_root_dir>/drivers_)
   <br>`mvn clean compile -P download-drivers`
1. Create a local copy of **config.properties** from config-template.properties in _src/main/resources_
1. Update configuration values in config.properties where needed

### Running the demo tests
- To execute entire TestNG regression test suite: `mvn clean test -DsuiteXmlFile=regression.xml` Note: There will be 1 test that passes and 1 that fails
- To view test report, open _target/surefire-reports/index.html_ in a browser, navigate to 'Reporter Output'

### Parallel test execution
- Update **thread-count** value in the test suites xml