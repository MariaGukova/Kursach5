package com.Server.ExstractProjects;

import com.example.it.model.Project;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProjectProperty {

    private IntegerProperty id;
    private StringProperty name;
    private  StringProperty customer;
    private StringProperty cost;
    private StringProperty deadline;
    private StringProperty level;


    public ProjectProperty(Project project) {
        id = new SimpleIntegerProperty(project.getId());
        name = new SimpleStringProperty(project.getName());
        customer = new SimpleStringProperty(project.getCustomer());
        cost = new SimpleStringProperty(project.getCost());
        deadline = new SimpleStringProperty(project.getDeadline());
        level = new SimpleStringProperty(project.getLevel());

    }



    public Project toProject() {
        return new Project(id.intValue(),
                name.getValue(),
                customer.getValue(),
                cost.getValue(),
                deadline.getValue(),
                level.getValue());
    }

    public int getId() {
        return id.get();
    }
    // они используются неявно
    public IntegerProperty idProperty() {
        return id;
    }


    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }

    public String getCustomer() {
        return customer.get();
    }

    public StringProperty customerProperty() {
        return customer;
    }


    public String getCost() {
        return cost.get();
    }

    public StringProperty costProperty() {
        return cost;
    }


    public String getDeadline() {
        return deadline.get();
    }

    public StringProperty deadlineProperty() {
        return deadline;
    }

    public String getLevel() {
        return level.get();
    }

    public StringProperty levelProperty() {
        return level;
    }



    public void setName(String name) {
        this.name.set(name);
    }
    public void setCustomer(String customer) {
        this.customer.set(customer);
    }
    public void setCost(String cost) {
        this.cost.set(cost);
    }
    public void setDeadline(String deadline) {
        this.deadline.set(deadline);
    }
    public void setLevel(String level) {
        this.level.set(level);
    }


}
