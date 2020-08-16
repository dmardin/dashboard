# Warnings Next Generation UI
This project is depending on the [Jenkins Warnings Next Generation Plugin](https://github.com/jenkinsci/warnings-ng-plugin) and is used to display the results of static analysis tools.

## Preparation
- Start a Jenkins
- Install the Jenkins Plugin [Warnings Next Generation Plugin]https://github.com/jenkinsci/warnings-ng-plugin
- Import this project to InelliJ
- Set the property **jenkins.api.rest-configuration.end-point** from the [application.properties](https://github.com/dmardin/warnings-ng-ui/blob/master/src/main/resources/application.properties) to the main Jenkins Endpoint. If Jenkins is runningn on http://localhost:8080 then the endpoint is
  ```
  jenkins.api.rest-configuration.end-point=http://localhost:8080/jenkins/api/json
  ```
- Setup database
  - Setup MySQL database with Docker
    - navigate to [resources](https://github.com/dmardin/warnings-ng-ui/tree/master/src/main/resources) with a terminal
    - start docker by running the following command
    ```
    docker-compose -f docker-compose-mysql.yml up -d
    ```
  - the database can stopped by running the following command
    ```
    docker-compose -f docker-compose-mysql.yml down
    ```
  - the database can be cleaned up by running following command
    ```
    docker volume prune
    ```
  
    
## Run the Application
- Select the [WarningsNgUiApplication.java](https://github.com/dmardin/warnings-ng-ui/blob/master/src/main/java/edu/hm/hafner/warningsngui/WarningsNgUiApplication.java) in IntelliJ and execute the command ```Run WarningsNgUiApplication```
- When the info messages **Requested data saved to database** appears, open the url http://localhost:8181 in the Browser
