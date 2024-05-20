package si.mapuci;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.graph.GraphParser;
import org.hibernate.jpa.AvailableHints;
import si.mapuci.entity.Four;
import si.mapuci.entity.One;
import si.mapuci.entity.One_;
import si.mapuci.entity.Three;
import si.mapuci.entity.Three_;
import si.mapuci.entity.Two;
import si.mapuci.entity.Two_;

@Path("/test")
@Transactional
public class ReproducerEndpoint
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
        three.setAbsThreeStringProp("threeAbs");
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
        final var three = two.getThree();
        final var four = three.getFour();
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
        final var three = two.getThree();
        final var absThreeStringProp = three.getAbsThreeStringProp();
        final var four = three.getFour();
        return four.getFourConcreteProp();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/stringBasedFetchGraphErrorShowcase")
    public String stringBasedFetchGraphErrorShowcase()
    {
        final var oneId = oneRepo.getEntityManager().createQuery("select one.id from One one", Long.class).getSingleResult();
        final var fetchGraph = "%s(%s(%s))".formatted(One_.TWO, Two_.THREE, Three_.FOUR);
        final var fetchedOne = oneRepo.find(One_.ID, oneId)
                .withHint(AvailableHints.HINT_SPEC_FETCH_GRAPH, GraphParser.parse(One.class, fetchGraph, oneRepo.getEntityManager().unwrap(SessionImplementor.class)))
                .singleResult();
        return QuarkusTransaction.suspendingExisting().call(() -> {
            //this does not allow lazy loading, so we will produce exception
            fetchedOne.getAbsOneStringProp();
            final var two = fetchedOne.getTwo();
            two.getAbsTwoStringProp();
            final var three = two.getThree();
            final var threeStringProp = three.getAbsThreeStringProp();
            final var four = three.getFour();
            return four.getFourConcreteProp();
        });
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/programmaticallyCreatedGraphWorks")
    public String programmaticallyCreatedGraphWorks()
    {
        final var oneId = oneRepo.getEntityManager().createQuery("select one.id from One one", Long.class).getSingleResult();

        final var oneGraph = oneRepo.getEntityManager().createEntityGraph(One.class);
        final var twoSubgraph = oneGraph.addSubgraph(One_.TWO, Two.class);
        final var threeSubgraph = twoSubgraph.addSubgraph(Two_.THREE, Three.class);
        threeSubgraph.addSubgraph(Three_.FOUR);

        final var fetchedOne = oneRepo.find(One_.ID, oneId)
                .withHint(AvailableHints.HINT_SPEC_FETCH_GRAPH, oneGraph)
                .singleResult();

        return QuarkusTransaction.suspendingExisting().call(() -> {
            //this does not allow lazy loading, so we will produce exception
            fetchedOne.getAbsOneStringProp();
            final var two = fetchedOne.getTwo();
            two.getAbsTwoStringProp();
            final var three = two.getThree();
            final var threeStringProp = three.getAbsThreeStringProp();
            final var four = three.getFour();
            return four.getFourConcreteProp();
        });
    }

    @ApplicationScoped
    static class OneRepo implements PanacheRepository<One>
    {
    }

}
