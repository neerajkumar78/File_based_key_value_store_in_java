import java.util.Scanner;
import java.util.Properties;
import org.json.JSONObject;

public class Runner {
	static Scanner scan1=new Scanner(System.in);
	static Scanner scan2=new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		
		//Create Key-Value Database
		System.out.println("Do you want to give path or name to your database,if not we will create a file with default name as database.properties\n Enter 1 for yes 2 for no and any key for exit");
		int flag1=scan1.nextInt();
		try {
			DatabaseHandler keysDb;
		switch(flag1) {
		case 1:
			 System.out.println("Enter your path with file name with .properties ext");
			 String path=scan2.nextLine();
			 keysDb= new DatabaseHandler(path, false);
			 operations(keysDb);
			 break;
		case 2:
			keysDb = new DatabaseHandler("database.properties", false);
			operations(keysDb);
			break;
		default:
			System.exit(0);
			break;	
		}
		}
		catch(Exception e) {
			System.out.println("Application Terminated");
			System.exit(0);
		}
		
		
		
	}
	public static void operations(DatabaseHandler keysDb){
		try {
		Properties keys = keysDb.loadKeys();
		while(true) {
		System.out.println("Choose your operation 1.create 2.update 3.read 4.delete or enter any other key to exit");
		int flag2=scan1.nextInt();
		String key;
		switch(flag2) {
		case 1:
			System.out.println("Enter Key");
			key=scan2.nextLine();
			try {
				if(keys.getProperty(key)==null) {
				System.out.println("As per requirement JSON object which contain name,age,city of a person is required to store as value");
				System.out.println("Enter Name");
				String name=scan2.nextLine();
				System.out.println("Enter Age as int value");
				int age=scan1.nextInt();
				System.out.println("Enter city");
				String city=scan2.nextLine();
				JSONObject obj = new JSONObject();
				obj.put("name", name);
				obj.put("age", age);
				obj.put("city", city);
				keysDb.updateKey(key, obj);
			}
				else {
					System.out.println("Key already exist");
				}
			}
			catch(Exception e) {
				System.out.println("Operation is not performed successfully. Please try again.");
			}
			break;
		case 2:
			System.out.println("Enter Key");
			key=scan2.nextLine();
			try {
				if(keys.getProperty(key)!=null) {
				System.out.println("As per requirement JSON object which contain name,age,city of a person is required to store as value");
				System.out.println("Enter Name");
				String name=scan2.nextLine();
				System.out.println("Enter Age as int value");
				int age=scan1.nextInt();
				System.out.println("Enter city");
				String city=scan2.nextLine();
				JSONObject obj = new JSONObject();
				obj.put("name", name);
				obj.put("age", age);
				obj.put("city", city);
				keysDb.updateKey(key, obj);
			}
				else {
					System.out.println("Key does not exist");
				}
			}
			catch(Exception e) {
				System.out.println("Operation is not performed successfully. Please try again.");
			}
			break;
		case 3:
			System.out.println("Enter Key");
			key=scan2.nextLine();
			try {
				if(keys.getProperty(key)!=null) {
				System.out.println(keys.getProperty(key));
			}
				else {
					System.out.println("Key does not exist");
				}
			}
			catch(Exception e) {
				System.out.println("Operation is not performed successfully. Please try again.");
			}
			break;
		case 4:
			System.out.println("Enter Key");
			key=scan2.nextLine();
			try {
				if(keys.getProperty(key)!=null) {
				keysDb.deleteKey(key);
				System.out.println("key deleted successfully");
			}
				else {
					System.out.println("Key does not exist");
				}
			}
			catch(Exception e) {
				System.out.println("Operation is not performed successfully. Please try again.");
			}
			break;
		default:
			System.out.println("Application Terminated");
			System.exit(0);
			break;
		}
		}
		}
		catch(Exception e) {
			System.out.println("Application Terminated");
		}
	
	}
	
}
