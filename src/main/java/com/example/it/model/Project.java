package com.example.it.model;


import java.io.Serializable;

public class Project implements Serializable {

        private int id;
        private String name;
        private String customer;
        private String cost;
        private String deadline;
        private String level;

        public Project( String name,   // когда клиент присылает информацию
                String customer,
                String cost,
                String deadline,
                String level){
            this.name = name;
            this.customer = customer;
            this.cost = cost;
            this.deadline = deadline;
            this.level = level;

        }


        public Project( int id,// когда читаем БД
                        String name,
                        String customer,
                        String cost,
                        String deadline,
                        String level){

            this.id = id;
            this.name = name;
            this.customer = customer;
            this.cost = cost;
            this.deadline = deadline;
            this.level = level;
        }



        public int getId() {
        return id;
    }

        public String getName() {
        return name;
    }

        public String getCustomer() {
        return customer;
    }

        public String getCost() {
        return cost;
    }

        public String getDeadline() {
        return deadline;
    }

        public String getLevel() {
        return level;
    }


        public void setName(String name) {
        this.name = name;
    }

        public void setCustomer(String customer) {
        this.customer = customer;
    }

        public void setCost(String cost) {
        this.cost = cost;
    }

        public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

        public void setLevel(String level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customer=" + customer +
                ", cost=" + cost +
                ", deadline='" + deadline +
                ", level='" + level +
                '}';
    }

}
