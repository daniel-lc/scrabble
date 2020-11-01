# scrabble-club

Server application which exposing api for accessing information about scrabble club members. 
It's written in Kotlin language and with the functional "flavour" using the Spring webflux framework.

App is running on the http://localhost:7070

You can use the Postman service or prepared http requests in the repo.

Prerequisities:
Docker
Java 11

Steps to run the application:

* Download repo to yourself: git clone https://github.com/daniel-lc/scrabble.git
* Go to project folder: cd scrabble
* Run app: ./run.sh


*TODO*

* finish two endpoints:
    * for getting worst and best member
    * for listing members with the top 10 average scores
* optimize db queries, mainly for missing endpoints
* add more test data
* add the pagination to REST requests in order to decrease length of time to fetch the data.
* dockerize app (at the moment only db is running in the docker)
* add Unit tests
