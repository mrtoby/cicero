package se.tanke.novatool;

/**
 * Configuration for the TC Nova communication tool.
 */
public class Configuration {

    private int sysExId = 0x00;

    public int getSysExId() {
        return sysExId;
    }

    public byte getSysExIdByte() {
        return (byte) sysExId;
    }

    public void setSysExId(final int sysExId) {
        if (sysExId < 0 || sysExId > 0x7f) {
            throw new IllegalArgumentException("SysEx ID is out of range: " + sysExId);
        }
        this.sysExId = sysExId;
    }
}
