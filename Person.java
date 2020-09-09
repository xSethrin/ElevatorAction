//package hw2;
/**
 * this class creates a Person.  A person has a name and a get name method that returns her or his name
 * @author Nikolo
 *
 */
public class Person{
	
	String name;
	int floor;
	
	/**
	 * method to add name to a person
	 * @param n is the string passed to be set as the persons name
	 */
	public void addName(String n) {
		name = n;
	}
	
	/**
	 * method to return the name of the person
	 * @return returns the name of the person
	 */
	public String getName() {
		return name;
	}
}