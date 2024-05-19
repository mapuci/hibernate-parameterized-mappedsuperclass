package org.jboss.as.quickstart.hibernate.reproducer.repo;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.as.quickstart.hibernate.reproducer.entity.One;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ReproducerRepo
{
    @PersistenceContext
    EntityManager em;

    @Inject
    Logger logger;

    @Transactional
    public void thisFailsOnQuarkus()
    {
        try
        {
            final var one = em.createQuery("select one from One one", One.class).getResultList().stream().findFirst().orElseThrow();
            final var two = one.getTwo(); //this does not invoke the $$_hibernate_getInterceptor / $$_hibernate_read_two methods for some reason. That's why we have bug here.
            final var three = two.getThree();
            final var four = three.getFour();
            logger.log(Level.INFO, "It did not fail!" + four.getFourConcreteProp());
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "ERROR is replicated", e);
        }
    }

    @Transactional
    public void thisWillNotFailBecauseWeUseNonParameterizedAbstractGetterToInitLazyEntity()
    {
        try
        {
        final var one = em.createQuery("select one from One one", One.class).getResultList().stream().findFirst().orElseThrow();
        final var absOneStringProp = one.getAbsOneStringProp();
        final var two = one.getTwo();
        final var absTwoStringProp = two.getAbsTwoStringProp();
        final var three = two.getThree();
        final var absThreeStringProp = three.getAbsThreeStringProp();
        final var four = three.getFour();
        logger.log(Level.INFO, "It did not fail!" + four.getFourConcreteProp());

        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "This did not happen in quarkus", e);
        }
    }
}
