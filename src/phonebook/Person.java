/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Krisztián
 */
public class Person {
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty email;
    
    public Person(){
        this.lastName = new SimpleStringProperty("");
        this.firstName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
    }
    
    public Person(String lName, String fName, String mail){
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(mail);
    }

    
    public void setLastName(String lName) {
        lastName.set(lName);
    }
    
    public void setFirstName(String fName) {
        firstName.set(fName);
    }
    
    public void setEmail(String mail){
        email.set(mail);
    }

    public String getFirstName() {
        return firstName.get();
    }
    
    public String getLastName(){
        return lastName.get();
    }
    
    public String getEmail(){
        return email.get();
    }
    
}
