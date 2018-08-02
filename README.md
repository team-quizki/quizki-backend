# quizki-backend

Quizki is a question and answer database which generates exams. This is the backend of the project. For the frontend go to [team-quizki/frontend](https://github.com/team-quizki/quizki-frontend).

This project was generated with  version 6.0.1.

## Getting Started
Here is what you need to do to get the project up and running on your local machine for development and testing purposes.

### Dependencies
To run the backend you will need the following tools
* Apache [Maven](https://maven.apache.org/) Check if you have it with `$ mvn -v`
* Apache [Tomcat](http://tomcat.apache.org/)
* Oracle [MySQL](https://www.mysql.com/)

#### Mac
These dependencies can be installed using the [Homebrew](https://brew.sh/) package manager for macOS.
* Maven `$ brew install maven`
* Tomcat `$ brew install tomcat`
* MySQL `$ brew install mysql`

### Installation
To run the backend on your local machine, you need to clone this repository.
  1. Clone this repository. `$ git clone https://github.com/team-quizki/quizki-backend.git`
  2. Change to the quizki-backend directory. `$ cd quizki-backend`
  3. Configure a remote for your fork. [GitHub Help](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)

## Development Server
To get the backend running on your local machine, you will need a MySQL daemon running and you will need to compile and run the application. Follow these steps each time you want to run the backend.
  1. Run a MySQL daemon in the background. `$ mysqld`
  2. Compile and run the application by using the Spring Boot Maven plugin. `$ mvn sping-boot:run`

### Initializing the Database
You will only need to do this the first time you run the backend.
  1. Initialize a user. `$ mysql -u root -p < ./src/main/resources/META-INF/sql/init_quizki2_user.sql`
  2. Populate the database. `$ mysql -u root -p < ./src/main/resources/META-INF/sql/populate_quizki2_db_02180619.sql`

You can check that it is working by entering the database. `$ mysql -u quizki2 -p quizki2_db`

## Running the Tests

We do not currently have tests built for the backend

## Deployment

The project is not yet ready for deployment. We will add additional notes about how to deploy this on a live system when it is ready.

## Built With
* Apache [Maven](https://maven.apache.org/) Check if you have it with `$ mvn -v`
* Apache [Tomcat](http://tomcat.apache.org/)
* Oracle [MySQL](https://www.mysql.com/)

## Getting Involved

This is a project of the Denver Mock Programming Job group. Find us at our [Meetup page](https://www.meetup.com/Denver-Mock-Programming-Job-Meetup/)

### Contributing
Please read [CONTRIBUTING.md] for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

This project is in alpha version.

## Authors

* Jonathan
* Jim
* Deborah
* Jeremiah
* Nabil
* 

## License



## Acknowledgements

We would like to thank our sponsors:
