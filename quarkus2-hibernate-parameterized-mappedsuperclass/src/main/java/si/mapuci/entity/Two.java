package si.mapuci.entity;

import javax.persistence.Entity;

@Entity
public class Two extends AbsTwo<One, Three>
{
    private String twoConcreteProp;

    public String getTwoConcreteProp()
    {
        return this.twoConcreteProp;
    }

    public void setTwoConcreteProp(String twoConcreteProp)
    {
        this.twoConcreteProp = twoConcreteProp;
    }
}
