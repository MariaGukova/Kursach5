package com.example.it;


import java.io.Serializable;

public class Project implements Serializable {

        private int id;
        private String name;
        private String customer;
        private String cost;
        private String deadline;

        public Project( String name,   // когда клиент присылает информацию
                String customer,
                String cost,
                String deadline){
            this.name = name;
            this.customer = customer;
            this.cost = cost;
            this.deadline = deadline;

        }


        public Project( int id,// когда читаем БД
                        String name,
                        String customer,
                        String cost,
                        String deadline){

            this.id = id;
            this.name = name;
            this.customer = customer;
            this.cost = cost;
            this.deadline = deadline;
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


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customer=" + customer +
                ", cost=" + cost +
                ", deadline='" + deadline +
                '}';
    }

}
