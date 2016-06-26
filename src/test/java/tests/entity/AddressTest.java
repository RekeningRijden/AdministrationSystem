package tests.entity;

import org.junit.Test;

import main.domain.Address;

import static org.junit.Assert.assertEquals;

/**
 * @author Sam
 */
public class AddressTest {

    @Test
    public void testGettersSetters(){
        Address address = new Address();
        address.setCity("Amsterdam");
        address.setCountry("Nederland");
        address.setId(1L);
        address.setStreet("Regenbooglaan");
        address.setStreetNr("14");
        address.setZipCode("4493FF");

        assertEquals("Amsterdam", address.getCity());
        assertEquals("Nederland", address.getCountry());
        assertEquals(new Long(1L), address.getId());
        assertEquals("Regenbooglaan", address.getStreet());
        assertEquals("Regenbooglaan 14", address.getStreetAndNr());
        assertEquals("14", address.getStreetNr());
        assertEquals("4493FF", address.getZipCode());
    }
}
