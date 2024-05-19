package si.mapuci.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Four
{
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "four", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Three> threes = new HashSet<>();
    ;

    private String fourConcreteProp;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Four four = (Four) o;
        return Objects.equals(id, four.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id);
    }

    public Long getId()
    {
        return this.id;
    }

    public Set<Three> getThrees()
    {
        return this.threes;
    }

    public String getFourConcreteProp()
    {
        return this.fourConcreteProp;
    }

    public void setThrees(Set<Three> threes)
    {
        this.threes = threes;
    }

    public void setFourConcreteProp(String fourConcreteProp)
    {
        this.fourConcreteProp = fourConcreteProp;
    }

    void setId(Long id)
    {
        this.id = id;
    }
}
