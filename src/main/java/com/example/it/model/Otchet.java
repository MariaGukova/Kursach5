package com.example.it.model;

import java.io.Serializable;

public class Otchet implements Serializable {

    private String name;
    private String risks;
    private String participants;
    private String deadline;

    public Otchet( String name,   // когда клиент присылает информацию
                    String risks,
                    String participants,
                    String deadline){
        this.name = name;
        this.risks = risks;
        this.participants = participants;
        this.deadline = deadline;

    }


    public Otchet(){

        this.name = name;
        this.risks = risks;
        this.participants = participants;
        this.deadline = deadline;

    }


    public String getName() {
        return name;
    }

    public String getRisks() {
        return risks;
    }

    public String getParticipants() {
        return participants;
    }

    public String getDeadline() {
        return deadline;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setRisks(String risks) {
        this.risks = risks;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }



    @Override
    public String toString() {
        return "Project{" +
                ", name='" + name + '\'' +
                ", risks=" + risks +
                ", participants=" + participants +
                ", deadline='" + deadline +
                '}';
    }

}
