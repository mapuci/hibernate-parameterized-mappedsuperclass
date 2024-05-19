package si.mapuci.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbsOne<TWO extends AbsTwo<?, ?>>
{
    @Id
    @GeneratedValue
    private Long id;

    private String absOneStringProp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "two_id")
    private TWO two;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbsOne<?> absOne = (AbsOne<?>) o;
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

    public String getAbsOneStringProp()
    {
        return this.absOneStringProp;
    }

    public TWO getTwo()
    {
        return this.two;
    }

    public void setAbsOneStringProp(String absOneStringProp)
    {
        this.absOneStringProp = absOneStringProp;
    }

    public void setTwo(TWO two)
    {
        this.two = two;
    }

    public String toString()
    {
        return "AbsOne(id=" + this.getId() + ")";
    }

    void setId(Long id)
    {
        this.id = id;
    }
}
