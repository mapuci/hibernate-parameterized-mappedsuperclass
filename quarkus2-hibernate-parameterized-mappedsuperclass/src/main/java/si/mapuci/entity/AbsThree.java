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
public abstract class AbsThree<TWO extends AbsTwo<?, ?>, FOUR>
{
    @Id
    @GeneratedValue
    private Long id;

    private String absThreeStringProp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "four_id")
    private FOUR four;

    @OneToMany(mappedBy = "three", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TWO> twos = new HashSet<>();
    ;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbsThree<?, ?> absThree = (AbsThree<?, ?>) o;
        return Objects.equals(id, absThree.id);
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

    public String getAbsThreeStringProp()
    {
        return this.absThreeStringProp;
    }

    public FOUR getFour()
    {
        return this.four;
    }

    public Set<TWO> getTwos()
    {
        return this.twos;
    }

    public void setAbsThreeStringProp(String absThreeStringProp)
    {
        this.absThreeStringProp = absThreeStringProp;
    }

    public void setFour(FOUR four)
    {
        this.four = four;
    }

    public void setTwos(Set<TWO> twos)
    {
        this.twos = twos;
    }

    void setId(Long id)
    {
        this.id = id;
    }
}
