package org.poo.clients;

import org.poo.fileio.UserInput;

public class User {
    private String firstName;
    private String lastName;
    private String email;

    public User(final UserInput user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

    /**
     * Getter for the first name of the user
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the first name of the user
     * @param firstName
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the last name of the user
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the last name of the user
     * @param lastName
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the email of the user
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email of the user
     * @param email
     */
    public void setEmail(final String email) {
        this.email = email;
    }
}
