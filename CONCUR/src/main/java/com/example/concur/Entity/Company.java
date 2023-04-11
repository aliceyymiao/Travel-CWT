package com.example.concur.Entity;
import jakarta.persistence.*;
@Embeddable
public class Company {

    private String name;
    private String catchphrase;
    private String bs;


    public String toString(){
        return "Name: "+this.name+"; "+
                "CatchPhrase: "+this.catchphrase+"; "+
                "Bs: "+this.bs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchphrase;
    }

    public void setCatchPhrase(String catchphrase) {
        this.catchphrase = catchphrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }
}
