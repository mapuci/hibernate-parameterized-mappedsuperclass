package si.mapuci.entity;

import javax.persistence.Entity;

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
