package si.mapuci;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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

    @Startup
    void insertSomeDataToDb()
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
        three.setAbsTreeStringProp("threeAbs");
        three.setThreeConcreteProp("threeConcrete");
        four.setFourConcreteProp("fourConcrete");

        oneRepo.persist(one);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/this-will-fail")
    public String thisWillFail()
    {
        final var one = oneRepo.listAll().stream().findFirst().orElseThrow();
        final var two = one.getTwo(); //this does not invoke the $$_hibernate_getInterceptor / $$_hibernate_read_two methods for some reason. That's why we have bug here.
        final var tree = two.getThree();
        final var four = tree.getFour();
        return four.getFourConcreteProp();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/this-will-fail-because-we-use-non-parameterized-abstract-getter-to-init-lazy-entity")
    public String thisWillNotFailBecauseWeUseNonParameterizedAbstractGetterToInitLazyEntity()
    {
        final var one = oneRepo.listAll().stream().findFirst().orElseThrow();
        final var absOneStringProp = one.getAbsOneStringProp();
        final var two = one.getTwo();
        final var absTwoStringProp = two.getAbsTwoStringProp();
        final var tree = two.getThree();
        final var absTreeStringProp = tree.getAbsTreeStringProp();
        final var four = tree.getFour();
        return four.getFourConcreteProp();
    }

    @ApplicationScoped
    static class OneRepo implements PanacheRepository<One>
    {
    }

}
