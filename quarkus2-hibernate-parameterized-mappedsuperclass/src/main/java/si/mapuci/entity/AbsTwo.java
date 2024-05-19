package si.mapuci.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class AbsTwo<ONE extends AbsOne<?>, THREE extends AbsThree<?, ?>>
{
    @Id
    @GeneratedValue
    private Long id;

    private String absTwoStringProp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "three_id")
    private THREE three;

    @OneToMany(mappedBy = "two", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ONE> ones = new HashSet<>();

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbsTwo<?, ?> absOne = (AbsTwo<?, ?>) o;
        return Objects.equals(id, absOne.id);
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

    public String getAbsTwoStringProp()
    {
        return this.absTwoStringProp;
    }

    public THREE getThree()
    {
        return this.three;
    }

    public Set<ONE> getOnes()
    {
        return this.ones;
    }

    public void setAbsTwoStringProp(String absTwoStringProp)
    {
        this.absTwoStringProp = absTwoStringProp;
    }

    public void setThree(THREE three)
    {
        this.three = three;
    }

    public void setOnes(Set<ONE> ones)
    {
        this.ones = ones;
    }

    public String toString()
    {
        return "AbsTwo(id=" + this.getId() + ")";
    }

    void setId(Long id)
    {
        this.id = id;
    }
}
