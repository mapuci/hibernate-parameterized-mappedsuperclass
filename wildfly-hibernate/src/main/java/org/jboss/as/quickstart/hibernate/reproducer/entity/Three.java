package org.jboss.as.quickstart.hibernate.reproducer.entity;

import jakarta.persistence.Entity;

@Entity
public class Three extends AbsThree<Two, Four>
{
    private String threeConcreteProp;

    public String getThreeConcreteProp()
    {
        return this.threeConcreteProp;
    }

    public void setThreeConcreteProp(String threeConcreteProp)
    {
        this.threeConcreteProp = threeConcreteProp;
    }
}
