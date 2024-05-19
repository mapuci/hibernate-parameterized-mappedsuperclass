package si.mapuci;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.runtime.Startup;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.common.annotation.Blocking;
import si.mapuci.entity.Four;
import si.mapuci.entity.One;
import si.mapuci.entity.Three;
import si.mapuci.entity.Two;

@Path("/test")
@Transactional
public class TestResource
{
    @Inject
    OneRepo oneRepo;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/this-will-fail")
    @Blocking
    public String thisWillFail()
    {
        final var one = oneRepo.listAll().stream().findFirst().orElseThrow();
        final var two = one.getTwo(); //this does not invoke the $$_hibernate_getInterceptor / $$_hibernate_read_two methods for some reason. That's why we have bug here.
        final var three = two.getThree();
        final var four = three.getFour();
        return four.getFourConcreteProp();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/this-will-fail-because-we-use-non-parameterized-abstract-getter-to-init-lazy-entity")
    @Blocking
    public String thisWillNotFailBecauseWeUseNonParameterizedAbstractGetterToInitLazyEntity()
    {
        final var one = oneRepo.listAll().stream().findFirst().orElseThrow();
        final var absOneStringProp = one.getAbsOneStringProp();
        final var two = one.getTwo();
        final var absTwoStringProp = two.getAbsTwoStringProp();
        final var three = two.getThree();
        final var absThreeStringProp = three.getAbsThreeStringProp();
        final var four = three.getFour();
        return four.getFourConcreteProp();
    }

    @ApplicationScoped
    static class OneRepo implements PanacheRepository<One>
    {
    }

    @ApplicationScoped
    static class TestDataInserter
    {
        @Inject
        OneRepo oneRepo;

        @Transactional
        void onStart(@Observes StartupEvent event)
        {
            {
                oneRepo.deleteAll();

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

                oneRepo.persist(one);
            }
        }
    }
}
