package si.mapuci.entity;

import javax.persistence.Entity;

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
