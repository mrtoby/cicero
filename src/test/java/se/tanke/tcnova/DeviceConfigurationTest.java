package se.tanke.tcnova;

import org.junit.Assert;
import org.junit.Test;

import se.tanke.tcnova.DeviceConfiguration;

public class DeviceConfigurationTest {

    @Test
    public void getSysExId_defaults_to_zero() {
        DeviceConfiguration sut = new DeviceConfiguration();
        Assert.assertEquals(0x00, sut.getSysexId());
    }
    
}
