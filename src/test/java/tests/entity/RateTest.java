package tests.entity;

import org.junit.Test;

import java.math.BigDecimal;

import main.domain.Rate;

import static org.junit.Assert.assertEquals;

/**
 * @author Sam
 */
public class RateTest {

    @Test
    public void testGettersSetters(){
        Rate rate = new Rate();
        rate.setId(1L);
        rate.setName("A");
        rate.setValue(BigDecimal.ONE);

        assertEquals(new Long(1L), rate.getId());
        assertEquals("A", rate.getName());
        assertEquals(BigDecimal.ONE, rate.getValue());

        rate.setValueString("10,00");

        assertEquals("10,00", rate.getValueString());
        assertEquals(BigDecimal.TEN.setScale(2, BigDecimal.ROUND_HALF_UP), rate.getValue());
    }
}
