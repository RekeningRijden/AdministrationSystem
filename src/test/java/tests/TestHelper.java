package tests;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Sam
 */
final class TestHelper {

    private TestHelper() {
        //Utility class constructor cannot be called
    }

    /**
     * @return a jar with all the classes used by Arquillian.
     */
    static Archive<?> createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "main", "org.netbeans.rest.application.config", "resources", "web")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(war.toString(true));
        return war;
    }
}
