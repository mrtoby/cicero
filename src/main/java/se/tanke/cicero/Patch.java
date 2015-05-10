package se.tanke.cicero;

import se.tanke.cicero.midi.MidiMessage;

/**
 * This class represent a patch, described as a sysex-midi-message.
 */
public class Patch {

	private String deviceId;
	private String bank;
	private int position;
	private String name;
	private MidiMessage msg;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(final String bank) {
		this.bank = bank;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public MidiMessage getMsg() {
		return msg;
	}

	public void setMsg(final MidiMessage msg) {
		this.msg = msg;
	}

	@Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
    	builder.append("<patch ");
    	builder.append(bank);
    	builder.append("-");
    	builder.append(Integer.toString(position));
    	builder.append(" ");
    	builder.append(name);
    	builder.append(">");
    	return builder.toString();
    }
}
