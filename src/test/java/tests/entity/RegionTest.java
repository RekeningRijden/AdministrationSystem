package tests.entity;

import org.junit.Test;

import java.math.BigDecimal;

import main.domain.Region;

import static org.junit.Assert.assertEquals;

/**
 * @author Sam
 */
public class RegionTest {

    @Test
    public void testGettersSetters(){
        Region region = new Region();
        region.setId(1L);
        region.setLatitudeSouth(10.00d);
        region.setName("Limburg");
        region.setRoadTaxPerKm(BigDecimal.TEN);

        assertEquals(new Long(1L), region.getId());
        assertEquals(10.00d, region.getLatitudeSouth(), 0);
        assertEquals("Limburg", region.getName());
        assertEquals(BigDecimal.TEN, region.getRoadTaxPerKm());

        region.setRoadTaxPerKmString("1,00");

        assertEquals("1,00", region.getRoadTaxPerKmString());
        assertEquals(BigDecimal.ONE.setScale(2, BigDecimal.ROUND_HALF_UP), region.getRoadTaxPerKm());

    }
}
