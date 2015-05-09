package se.tanke.novatool;

import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void getSysExId_defaults_to_zero() {
        Configuration sut = new Configuration();
        Assert.assertEquals(0x00, sut.getSysExId());
    }
    
}
