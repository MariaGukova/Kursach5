package com.Server.ExstractOtchet;


import com.example.it.model.Otchet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OtchetProperty {

    private StringProperty name;
    private  StringProperty risks;
    private StringProperty participants;
    private StringProperty deadline;


    public OtchetProperty(Otchet otchet) {

        name = new SimpleStringProperty(otchet.getName());
        risks = new SimpleStringProperty(otchet.getRisks());
        participants = new SimpleStringProperty(otchet.getParticipants());
        deadline = new SimpleStringProperty(otchet.getDeadline());
    }



  /*  public Otchet toOtchet() {
        return new Ochet(
                name.getValue(),
                risks.getValue(),
                participants.getValue(),
                deadline.getValue();
    }

   */

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }

    public String getRisks() {
        return risks.get();
    }

    public StringProperty risksProperty() {
        return risks;
    }


    public String getParticipants() {
        return participants.get();
    }

    public StringProperty participantsProperty() {
        return participants;
    }


    public String getDeadline() {
        return deadline.get();
    }

    public StringProperty deadlineProperty() {
        return deadline;
    }



    public void setName(String name) {
        this.name.set(name);
    }
    public void setRisks(String risks) {
        this.risks.set(risks);
    }
    public void setParticipants(String participants) {
        this.participants.set(participants);
    }
    public void setDeadline(String deadline) {
        this.deadline.set(deadline);
    }

}
