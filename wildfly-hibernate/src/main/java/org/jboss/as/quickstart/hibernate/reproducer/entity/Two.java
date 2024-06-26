package org.jboss.as.quickstart.hibernate.reproducer.entity;

import jakarta.persistence.Entity;

@Entity
public class Two extends AbsTwo<One, Three> {
    private String twoConcreteProp;

    public String getTwoConcreteProp() {
        return this.twoConcreteProp;
    }

    public void setTwoConcreteProp(String twoConcreteProp) {
        this.twoConcreteProp = twoConcreteProp;
    }
}
