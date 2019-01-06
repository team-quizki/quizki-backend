# Quizki Backend

Quizki is a question and answer database which generates exams. This is the backend of the project. For the frontend go to [team-quizki/quizki-frontend](https://github.com/team-quizki/quizki-frontend).

This project uses Spring Boot v2.0.3.

## Getting Started
Here is what you need to do to get the project up and running on your local machine for development and testing purposes.

### Dependencies
To run the backend you will need the following tools
* Oracle [Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) Use JDK8 as JDK11 currently does not work with quizki-backend.
* Apache [Maven](https://maven.apache.org/) Check if you have it by typing `mvn -v`
* Oracle [MySQL](https://www.mysql.com/)

Other tools you will need if you are new to quizki-backend or to backend coding:
* [GitHub account](https://github.com/)
* [Git apps](https://git-scm.com/downloads)
* [Postman API Developer](https://www.getpostman.com/)
* [Eclipse IDE](https://www.eclipse.org/)

#### Linux
Install the dependencies (above) per the instructions for the given software package.

#### Windows
Install the dependencies (above) per the instructions for the given software package.

#### Mac
These dependencies can be installed using the [Homebrew](https://brew.sh/) package manager for macOS.
* Maven `$ brew install maven`
* MySQL `$ brew install mysql`

### Installation
You will need to fork the [team-quizki/quizki-backend repository](https://github.com/team-quizki/quizki-backend), then clone your new fork to your local computer, and configure the cloned copy on your computer. You can follow these [instruction on Github](https://help.github.com/articles/fork-a-repo/). To summarize this instruction: 

- On GitHub fork the [team-quizki/quizki-backend](https://github.com/team-quizki/quizki-backend) repository.

- Using Git Bash or Git CMD clone your newly forked repository to your local computer:
    `$ git clone https://github.com/<<UseYourGitHubHandleHere>>/quizki-backend.git`

- Change to the quizki-backend directory. 
    `$ cd quizki-backend`
    
- Configure your local quizki-backend to use remote upstream. 
    `$ git remote add upstream hhttps://github.com/team-quizki/quizki-backend`  
    
- Check remote origin and upstream looks similar to the following.
    `$ git remote -v`
    
> origin  https://github.com/FrauDeborah/quizki-backend.git (fetch)

> origin  https://github.com/FrauDeborah/quizki-backend.git (push)

> upstream  https://github.com/team-quizki/quizki-backend.git (fetch)

> upstream  https://github.com/team-quizki/quizki-backend.git (push)
   
    
- Configure your user email to prepare for commit
    `$ git config --global user.email "yourEmail@example.com"` 
    
- Configure your name email to prepare for commit
    `$ git config --global user.name "Your Name"` 
  

### Initializing the Database
You will only need to do this the first time you run the backend.
  1. Initialize a user. `$ mysql -u root -p < ./src/main/resources/META-INF/sql/init_quizki2_user.sql`
  2. Populate the database. `$ mysql -u quizki2 -p < ./src/main/resources/META-INF/sql/populate_quizki2_db_20180619.sql` The password for the `quizki2` user in mysql is `quizki2`.

You can check that it is working by entering the database. `$ mysql -u quizki2 -p quizki2_db`

## Development Server
To get the backend running on your local machine, you will need a MySQL daemon running and you will need to compile and run the application. Follow these steps each time you want to run the backend.
  1. Run a MySQL daemon in the background. `$ mysqld`
  2. Compile and run the application by using the Spring Boot Maven plugin. `$ mvn spring-boot:run`

## Running the Tests

Type `$ mvn test`.

## Deployment

The project is not yet ready for deployment. We will add additional notes about how to deploy this on a live system when it is ready.

## Built With
* Oracle [Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) We've done light testing up through JDK9.
* Apache [Maven](https://maven.apache.org/) Check if you have it with `$ mvn -v`
* Oracle [MySQL](https://www.mysql.com/)

## Getting Involved

This is a project of the Denver Mock Programming Job group. Find us at our [Meetup page](https://www.meetup.com/Denver-Mock-Programming-Job-Meetup/).

### Contributing
Please read [CONTRIBUTING.md] for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

This project is in alpha version.

## Authors

* Johnathan
* Jim
* Deborah
* Jeremiah
* Nabil

## License

GPL v3.0 all day.



