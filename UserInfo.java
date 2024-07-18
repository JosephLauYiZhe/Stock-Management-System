
public class UserInfo {
    private String name;
    private String userID;
       
    // Constructor
    public UserInfo() {
        this.name = "";
        this.userID = "guest";
    }

    public void setName(String name) {
    	if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
            this.userID = generateUserID(); // Generate userID based on the provided name
        }
    }
    
    
    //Get name of the user. Prompts the user to enter first name and surname.
    public String getName() {
    	return name;
    }

    // Check if the name contains space(s)
    public boolean hasSpaces() {
        return name.contains(" ");
    }

    //Generate a user ID for user if a valid name is entered
    public String generateUserID() {
        if (hasSpaces()) {
            String[] fullname = name.split(" ");
            return fullname[0].substring(0,1).concat(fullname[fullname.length - 1]);
        } 
        else {
            return "guest";
        }
    }

	public String getUserID() {
		return userID;
	}

}
