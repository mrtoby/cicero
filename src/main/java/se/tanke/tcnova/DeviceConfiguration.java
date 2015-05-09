package se.tanke.tcnova;

/**
 * Configuration for a specific TC Electronic Nova System device.
 */
public class DeviceConfiguration {

    private int sysexId = 0x00;

    public int getSysexId() {
        return sysexId;
    }

    /**
     * Set the sysex id to use. 
     * @param sysexId The sysex id
     */
    public void setSysexId(final int sysexId) {
        if (sysexId < 0 || sysexId > 0x7f) {
            throw new IllegalArgumentException("Sysex id is out of range: " + sysexId);
        }
        this.sysexId = sysexId;
    }
}
