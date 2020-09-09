//package hw2;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
/**
 * This code takes in two text files and reads them
 * the first text file must be a list of people and there respective floors
 * the second text file must be a list of commands for the people and elevator
 * the code will then read through these files and act based off of the commands in the file
 * @author Nikolo
 *
 */

public class ElevatorAction{
	
	static SLList <Person> floor0 = new SLList <Person>();
	static SLList <Person> floor1 = new SLList <Person>();
	static SLList <Person> floor2 = new SLList <Person>();
	static SLList <Person> floor3 = new SLList <Person>();
	
	/**
	 * the main method of the code
	 * calls on the make people method and comand method passing the two aguments to them
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main (String[] args) throws FileNotFoundException {
		QueueList<String> waiting0 = new QueueList<String>();
		QueueList<String> waiting1 = new QueueList<String>();
		QueueList<String> waiting2 = new QueueList<String>();
		QueueList<String> waiting3 = new QueueList<String>();
		StackList<String> elevator = new StackList<String>();
		File f = new File(args[0]);
		File t = new File(args[1]);
		makePeople(f); //this method creates people
		commands(t, waiting0, waiting1, waiting2, waiting3, elevator);//this method reads the command file
	}
	
	/**
	 * this method takes arg[0] (the people file) and creates people using the People.class
	 * @param f is the file that contains the names of people to be made
	 * @throws FileNotFoundException
	 */
	public static void makePeople(File f) throws FileNotFoundException {
		String name = "";
		int floor = 0;
		Scanner peeps = new Scanner(f);
		while (peeps.hasNextLine()) {
			Scanner line = new Scanner(peeps.nextLine());
			while (line.hasNext()) {
				name = line.next();//finds the persons name
				floor = Integer.parseInt(line.next());//finds a persons floor
				Person person = new Person();//creates a person
				person.addName(name);//gives the new person a name
				//the following code adds the people to the appropriate floor
				if(floor == 0) {
					floor0.add(person);
				}
				else if (floor == 1) {
					floor1.add(person);
				}
				else if (floor == 2) {
					floor2.add(person);
				}
				else if (floor == 3) {
					floor3.add(person);

				}
			}
		}
	}
	
	/**
	 * this method reads the command file and then calls on other methods accordingly (I am really happy eclipse has spell check)
	 * @param t is args[1] the command text file (get it? t is for text)
	 * @param waiting0 a queue of people on floor 0
	 * @param waiting1 a queue of people on floor 1
	 * @param waiting2 a queue of people on floor 2
	 * @param waiting3 a queue of people on floor 3
	 * @param elevator a stack for the elevator
	 * @throws FileNotFoundException
	 */
	public static void commands(File t, QueueList<String> waiting0, QueueList<String> waiting1, QueueList<String> waiting2, QueueList<String> waiting3,  StackList<String> elevator) throws FileNotFoundException {
		int floor = 0;
		int n = 0;
		String name = "";
		Scanner c = new Scanner(t);//c is for commands
		while (c.hasNextLine()) {
			Scanner line = new Scanner(c.nextLine());
			while (line.hasNext()) {
				String token = line.next().toUpperCase();
				if (token.equals("WAIT")){
					floor = Integer.parseInt(line.next());	
					name = line.next();
					if (floor == 0) {
						wait(floor, name, waiting0);//wait command method
					}
					else if (floor == 1) {
						wait(floor, name, waiting1);//wait command method
					}
					else if (floor == 2) {
						wait(floor, name, waiting2);//wait command method
					}
					else if (floor == 3) {
						wait(floor, name, waiting3);//wait command method
					}
					
				}
				else if (token.equals("PICK_UP")) {
					floor = Integer.parseInt(line.next());	
					if (floor == 0) {
						pickUp(floor, waiting0, elevator);//pick up command method
					}
					else if (floor == 1) {
						pickUp(floor, waiting1, elevator);//pickup command method
					}
					else if (floor == 2) {
						pickUp(floor, waiting2, elevator);//pickup command method
					}
					else if (floor == 3) {
						pickUp(floor, waiting3, elevator);
					}
					
				}
				else if (token.equals("DROP_OFF")) {
					floor = Integer.parseInt(line.next());
					n = Integer.parseInt(line.next());
					dropOff(floor, n, elevator, waiting0, waiting1, waiting2, waiting3);//drop off method
				}
				else if (token.equals("INSPECTION")) {
					inspection(waiting0, waiting1, waiting2, waiting3, elevator);//inspection method
				}
			}
		}
	}
	/**
	 * the following method is used when the command is wait it adds people to the wait queue from the given floors
	 * @param floor the number of the floor
	 * @param name the name of the person to be queued
	 * @param waiting the queue of people waiting on said floor
	 */
	public static void wait(int floor, String name, QueueList<String> waiting) {
		int check = waiting.size();
		if (floor == 0 ) {
			for(int i = 0; i < floor0.length(); i++) {
				if (floor0.getValue(i).getName().equals(name)){
					waiting.enqueue(floor0.getValue(i).getName());
					removeName(name, floor);
					
					
				}
			}
			if (check == waiting.size()) {
				System.out.println(name + " is not on floor 0");
			}
		}
		else if (floor == 1 ) {
			for(int i = 0; i < floor1.length(); i++) {
				if (floor1.getValue(i).getName().equals(name)){
					waiting.enqueue(floor1.getValue(i).getName());
					removeName(name, floor);

					
				}
			}
			if (check == waiting.size()) {
				System.out.println(name + " is not on floor 1");
			}
		}
		else if (floor == 2 ) {
			for(int i = 0; i < floor2.length(); i++) {
				if (floor2.getValue(i).getName().equals(name)){
					waiting.enqueue(floor2.getValue(i).getName());
					removeName(name, floor);

					
				}
			}
			if (check == waiting.size()) {
				System.out.println(name + " is not on floor 2");
			}
		}
		else if (floor == 3 ) {
			for(int i = 0; i < floor3.length(); i++) {
				if (floor3.getValue(i).getName().equals(name)){
					waiting.enqueue(floor3.getValue(i).getName());
					removeName(name, floor);

					
				}
			}
			if (check == waiting.size()) {
				System.out.println(name + " is not on floor 3");
			}
		}
	}

	/**
	 * this method picks up as many people that can fit from a given floor
	 * @param floor the floor the elevator picks people up on
	 * @param waiting the queue people are waiting in
	 * @param elevator a stack creating a digital elevator
	 */
	public static void pickUp(int floor, QueueList<String> waiting, StackList<String> elevator) {
		String temp = "";
		int cap = 7 - elevator.size();
		if (floor == 0) {
			for(int i = 0; i < cap; i++) {
				if(!waiting.isEmpty()) {
					temp = waiting.dequeue();
					elevator.push(temp);
					/**for(int k = 0; k < floor0.length(); k++) {
						if (floor0.getValue(k).getName().equals(temp)){
							floor0.remove(k);
							}
						}
						*/
					}
				}
			}
		if (floor == 1) {
			for(int i = 0; i < cap; i++) {
				if(!waiting.isEmpty()) {
					temp = waiting.dequeue();
					elevator.push(temp);
					/**for(int k = 0; k < floor1.length(); k++) {
						if (floor1.getValue(k).getName().equals(temp)){
							floor1.remove(k);
						}
					}
					*/
				}
			}
		}
		if (floor == 2) {
			for(int i = 0; i < cap; i++) {
				if(!waiting.isEmpty()) {
					temp = waiting.dequeue();
					elevator.push(temp);
					/**for(int k = 0; k < floor2.length(); k++) {
						if (floor2.getValue(k).getName().equals(temp)){
							floor2.remove(k);
						}
					}
					*/
				}
			}
		}
		if (floor == 3) {
			for(int i = 0; i < cap; i++) {
				if(!waiting.isEmpty()) {
					temp = waiting.dequeue();
					elevator.push(temp);
					/**for(int k = 0; k < floor3.length(); k++) {
						if (floor3.getValue(k).getName().equals(temp)){
							floor3.remove(k);
						}
					}
					*/
				}
			}
		}
	}
 
	/**
	 * this method drops off people from the (stack) elevator onto the floor passed via the text file
	 * @param floor the floor number
	 * @param n the number of people getting off the elevator
	 * @param elevator a stack making a cool digital elevator
	 * @param waiting0 a queue of people waiting to get on the elevator
	 * @param waiting1 a queue of people waiting to get on the elevator
	 * @param waiting2 a queue of people waiting to get on the elevator
	 * @param waiting3 a queue of people waiting to get on the elevator
	 */
	public static void dropOff(int floor, int n, StackList<String> elevator, QueueList<String> waiting0, QueueList<String> waiting1, QueueList<String> waiting2, QueueList<String> waiting3) {
		String temp = "";
		

		if (n >= elevator.size()) {
			if (floor == 0) {
				while(elevator.size()!=0) {
					temp = elevator.pop();
					Person person = new Person();
					person.addName(temp);
					floor0.add(person);
				}
				pickUp(floor, waiting0, elevator);
			}
			else if (floor == 1) {
				while(elevator.size()!=0) {
					temp = elevator.pop();
					Person person = new Person();
					person.addName(temp);
					floor1.add(person);	
				}
				pickUp(floor, waiting1, elevator);
			}
			else if (floor == 2) {
				while(elevator.size()!=0) {
					temp = elevator.pop();
					Person person = new Person();
					person.addName(temp);
					floor2.add(person);	
				}
				pickUp(floor, waiting2, elevator);
			}
			else if (floor == 3) {
				while(elevator.size()!=0) {
					temp = elevator.pop();
					Person person = new Person();
					person.addName(temp);
					floor3.add(person);
				}
				pickUp(floor, waiting3, elevator);
			}
		}
		else if (n < elevator.size()) {
			if (floor == 0) {
				for(int i = 0; i < n; i++) {
					temp = elevator.pop();
					Person person = new Person();
					person.addName(temp);
					floor0.add(person);	
				}
				pickUp(floor, waiting0, elevator);
			}
			else if (floor == 1) {
				for(int i = 0; i < n; i++) {
					temp = elevator.pop();
					Person person = new Person();
					person.addName(temp);
					floor1.add(person);
				}
				pickUp(floor, waiting1, elevator);
			}
			else if (floor == 2) {
				for(int i = 0; i < n; i++) {
					temp = elevator.pop();
					Person person = new Person();
					person.addName(temp);
					floor2.add(person);	
				}
				pickUp(floor, waiting2, elevator);
			}
			else if (floor == 3) {
				for(int i = 0; i < n; i++) {
					temp = elevator.pop();
					Person person = new Person();
					person.addName(temp);
					floor3.add(person);	
				}
				pickUp(floor, waiting3, elevator);
			}
		}
	}
	
	/**
	 * this method executes the inspection method.  This method will print up who is next in line to get on and off the elevator
	 * @param waiting0 queue of people waiting on floor 0
	 * @param waiting1 queue of people waiting on floor 1
	 * @param waiting2 queue of people waiting on floor 2
	 * @param waiting3 queue of people waiting on floor 3
	 * @param elevator
	 */
	public static void inspection(QueueList<String> waiting0, QueueList<String> waiting1, QueueList<String> waiting2, QueueList<String> waiting3, StackList<String> elevator) {
		int cap = 7 - elevator.size();
		String temp = "";
		System.out.println("Elevator status:");
		if(elevator.size() == 0) {
			System.out.println("The elevator is empty.  There are 7 empty spots");
			System.out.println("No one is in the elevator");
		}
		else {
			System.out.println("The elevator is not empty.  There are " + cap + " empty spots." );
			temp = elevator.pop();
			System.out.println(temp + " will be the next person to leave the elevator.");
			elevator.push(temp);
		}
		if (waiting0.size() == 0) {
			System.out.println("There are no people waiting to get on the elevator on floor 0");
		}
		else {
			temp = waiting0.getFront();
			System.out.println(temp + " will be the next person to get on the elevator from floor 0.");
		}
		if (waiting1.size() == 0) {
			System.out.println("There are no people waiting to get on the elevator on floor 1");
		}
		else {
			temp = waiting1.getFront();
			System.out.println(temp + " will be the next person to get on the elevator from floor 1.");
		}
		if (waiting2.size() == 0) {
			System.out.println("There are no people waiting to get on the elevator on floor 2");
		}
		else {
			temp = waiting2.getFront();
			System.out.println(temp + " will be the next person to get on the elevator from floor 2.");
		}
		if (waiting3.size() == 0) {
			System.out.println("There are no people waiting to get on the elevator on floor 3");
		}
		else {
			temp = waiting3.getFront();
			System.out.println(temp + " will be the next person to get on the elevator from floor 3.");
		}
	}
	
	/**
	 * this method is a helper method that removes people from the floor list after they get in queue for the elevator
	 * @param name
	 * @param floor
	 */
	public static void removeName(String name, int floor) {
		if(floor == 0) {
			for(int i = 0; i < floor0.length(); i++) {
				if(floor0.getValue(i).getName().equals(name)) {
					floor0.remove(i);
					i = floor0.length();
				}
			}
		}
		else if(floor == 1) {
			for(int i = 0; i < floor1.length(); i++) {
				if(floor1.getValue(i).getName().equals(name)) {
					floor1.remove(i);
					i = floor1.length();
				}
			}
		}
		else if(floor == 2) {
			for(int i = 0; i < floor2.length(); i++) {
				if(floor2.getValue(i).getName().equals(name)) {
					floor2.remove(i);
					i = floor2.length();
				}
			}
		}
		if(floor == 3) {
			for(int i = 0; i < floor3.length(); i++) {
				if(floor3.getValue(i).getName().equals(name)) {
					floor3.remove(i);
					i = floor3.length();
				}
			}
		}
	}

	/**
	 * this method is never used. I used it to help me debug
	 * If you are reading this Heather I am guessing you are either checking to see if I wrote comments or you were able to break my code
	 * I hope you are just looking for comments
	 * 
	 * P.S. I hope you have a great day.
	 */
	public static void printFloors() {
		String name0 = "";
		String name1 = "";
		String name2 = "";
		String name3 = "";
		for(int i = 0; i < floor0.length(); i++) {
			name0 = name0 +" " + floor0.getValue(i).getName();
		}
		for(int i = 0; i < floor1.length(); i++) {
			name1 = name1 + " " +  floor1.getValue(i).getName();
		}
		for(int i = 0; i < floor2.length(); i++) {
			name2 = name2 +" "+ floor2.getValue(i).getName();
		}
		for(int i = 0; i < floor3.length(); i++) {
			name3 = name3 +" "+ floor3.getValue(i).getName();
		}
		System.out.println("Floor0: " + name0);
		System.out.println("Floor1: " + name1);
		System.out.println("Floor2: " + name2);
		System.out.println("Floor3: " + name3);
	}
}