#!/bin/bash
docker-compose -f ../src/main/resources/docker-compose-mysql.yml down
docker volume rm resources_dashboard_mysql_data