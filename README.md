# Warnings Next Generation UI
This project is depending on the [Jenkins Warnings Next Generation Plugin](https://github.com/jenkinsci/warnings-ng-plugin) and is used to display the results of static analysis tools.

## Preparation
- Start a Jenkins
- Import the projekt to InelliJ
- Set the property **jenkins.api.rest-configuration.end-point** from the [application.properties](https://github.com/dmardin/warnings-ng-ui/blob/master/src/main/resources/application.properties) to the main Jenkins Endpoint. If Jenkins is runningn on http://localhost:8080 then the endpoint is
  ```
  jenkins.api.rest-configuration.end-point=http://localhost:8080/jenkins/api/json
  ```
- Setup Database **(use h2 or MySQL)**
  - Setup h2 Database
    - The h2 database is already setup in the [application.properties](https://github.com/dmardin/warnings-ng-ui/blob/master/src/main/resources/application.properties).
  - Setup MySQL Database with Docker
    - If you want to use MySQL with Docker comment out the h2 section in the [application.properties](https://github.com/dmardin/warnings-ng-ui/blob/master/src/main/resources/application.properties). Then comment in the MySQL section in the [application.properties](https://github.com/dmardin/warnings-ng-ui/blob/master/src/main/resources/application.properties). 
    
## Run the Application
- Select the [WarningsNgUiApplication.java](https://github.com/dmardin/warnings-ng-ui/blob/master/src/main/java/edu/hm/hafner/warningsngui/WarningsNgUiApplication.java) in IntelliJ and execute the command ```Run WarningsNgUiApplication```
- Open the url http://localhost:8181 in the Browser
