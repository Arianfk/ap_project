package logic;

import com.example.ap.Hash;
import com.example.ap.Theme;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    public static List<User> allUsers = new ArrayList<>();
    public static List<User> deletedUsers = new ArrayList<>();
    static Logger logger = LogManager.getLogger(User.class.getName());
    public transient Theme theme = new Theme();
    public transient Department department;
    public String username, name, email, imagePath, password, nationalCode, phoneNumber, enterYear;
    public LocalDate birthDay;
    public LocalDateTime lastLogIn;
    public boolean isDeleted = false;

    public User() {
        allUsers.add(this);
        lastLogIn = LocalDateTime.now();
    }

    public User(String username, String password) {
        allUsers.add(this);
        this.username = username;
        this.password = Hash.hash(password);
    }

    public static User getByUsername(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) return user;
        }
        return null;
    }

    public static User findByUsername(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) return user;
        }
        return null;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = Hash.hash(password);
        logger.info("User " + username + " changed password");
    }

    public void delete() {
        isDeleted = true;
        deletedUsers.add(this);
        allUsers.remove(this);
    }

    public void setEmail(String email) {
        if (email == null) {
            this.email = null;
            return;
        }

        if (email.equals(this.email)) return;

        this.email = email;
        logger.info("User " + this.username + " changed email");
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            this.phoneNumber = null;
            return;
        }
        if (phoneNumber.equals(this.phoneNumber)) return;

        this.phoneNumber = phoneNumber;
        logger.info("User " + this.username + " changed phone number");
    }
}
