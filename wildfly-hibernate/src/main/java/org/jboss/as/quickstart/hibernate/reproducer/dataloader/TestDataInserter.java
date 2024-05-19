package org.jboss.as.quickstart.hibernate.reproducer.dataloader;

import org.jboss.as.quickstart.hibernate.reproducer.entity.Four;
import org.jboss.as.quickstart.hibernate.reproducer.entity.One;
import org.jboss.as.quickstart.hibernate.reproducer.entity.Three;
import org.jboss.as.quickstart.hibernate.reproducer.entity.Two;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Singleton
@Startup
public class TestDataInserter
{
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void insertTestData()
    {
        final var one = new One();
        final var two = new Two();
        final var three = new Three();
        final var four = new Four();

        one.setTwo(two);
        two.getOnes().add(one);
        two.setThree(three);
        three.getTwos().add(two);
        three.setFour(four);
        four.getThrees().add(three);

        one.setOneConcreteProp("oneConcrete");
        one.setAbsOneStringProp("oneAbs");
        two.setAbsTwoStringProp("twoAbs");
        two.setTwoConcreteProp("twoConcrete");
        three.setAbsThreeStringProp("threeAbs");
        three.setThreeConcreteProp("threeConcrete");
        four.setFourConcreteProp("fourConcrete");

        em.persist(one);
    }
}
