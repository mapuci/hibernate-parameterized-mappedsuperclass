package org.jboss.as.quickstart.hibernate.reproducer.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
public abstract class AbsTwo<ONE extends AbsOne<?>, THREE extends AbsThree<?, ?>> {
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbsTwo<?, ?> absOne = (AbsTwo<?, ?>) o;
        return Objects.equals(id, absOne.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return this.id;
    }

    public String getAbsTwoStringProp() {
        return this.absTwoStringProp;
    }

    public THREE getThree() {
        return this.three;
    }

    public Set<ONE> getOnes() {
        return this.ones;
    }

    public void setAbsTwoStringProp(String absTwoStringProp) {
        this.absTwoStringProp = absTwoStringProp;
    }

    public void setThree(THREE three) {
        this.three = three;
    }

    public void setOnes(Set<ONE> ones) {
        this.ones = ones;
    }

    public String toString() {
        return "AbsTwo(id=" + this.getId() + ")";
    }

    void setId(Long id) {
        this.id = id;
    }
}
