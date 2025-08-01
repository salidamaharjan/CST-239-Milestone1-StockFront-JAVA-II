/**
 * 
 */
/**
 * 
 */
module Milestone1 {
	requires com.fasterxml.jackson.databind;
	requires junit;

	// https://stackoverflow.com/questions/67310161/java-jackson-failed-to-construct-beanserializer
	// app was not accessed to jackson.databind because of Java 9 module system
	opens app to com.fasterxml.jackson.databind;

//	exports app;

}