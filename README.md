# Dashboard for the Warnings Next Generation Plugin
This project is depending on the [Jenkins Warnings Next Generation Plugin](https://github.com/jenkinsci/warnings-ng-plugin) and is used to display the results of static analysis tools.

## Preparation
- Start a Jenkins
- Install the [Warnings Next Generation Plugin](https://github.com/jenkinsci/warnings-ng-plugin)
- Open this project in IntelliJ
- Set the property **jenkins.api.rest-configuration.end-point** from the [application.properties](https://github.com/dmardin/warnings-ng-ui/blob/master/src/main/resources/application.properties) to the main Jenkins Endpoint. If Jenkins is running on http://localhost:8080 then the endpoint is:
  ```
  jenkins.api.rest-configuration.end-point=http://localhost:8080/jenkins/api/json
  ```
- Setup the MySQL database with Docker
    - navigate to [bin](https://github.com/dmardin/dashboard/tree/master/bin) with a terminal
    - Start the database by running the bash script [start-db.sh](https://github.com/dmardin/dashboard/tree/master/bin/start-db.sh)
- Stop and clean up the database
  - The database can stopped by running the bash script [stop-db.sh](https://github.com/dmardin/dashboard/tree/master/bin/stop-db.sh)
  - The database can be cleaned up by running the bash script [stop-and-clean-db.sh](https://github.com/dmardin/dashboard/tree/master/bin/stop-and-clean-db.sh)
  
## Run the Application
- Select the [DashboardApplication.java](https://github.com/dmardin/dashboard/blob/master/src/main/java/edu/hm/hafner/dashboard/DashboardApplication.java) in IntelliJ and execute the command ```Run DashboardApplication```
- When the info messages **Requested data saved to database** appears, open the url http://localhost:8181 in the Browser
