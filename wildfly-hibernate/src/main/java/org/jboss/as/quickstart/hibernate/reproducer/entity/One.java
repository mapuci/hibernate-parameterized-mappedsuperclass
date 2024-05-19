package org.jboss.as.quickstart.hibernate.reproducer.entity;

import jakarta.persistence.Entity;

@Entity
public class One extends AbsOne<Two>
{
    private String oneConcreteProp;

    public String getOneConcreteProp()
    {
        return this.oneConcreteProp;
    }

    public void setOneConcreteProp(String oneConcreteProp)
    {
        this.oneConcreteProp = oneConcreteProp;
    }
}
