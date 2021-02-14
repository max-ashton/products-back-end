FYI 
This code has no unit tests, that's not because I don't believe in unit tests but because I have other things to do that test code that will never enter production.
As far as the CSV import is concerned, if I was doing this in a real life app I would use liquibase or flyway, but this seemed quicker to import the data using a SpringBoot InitializingBean.
