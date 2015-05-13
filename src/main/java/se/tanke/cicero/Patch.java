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
	private MidiMessage message;

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

	public MidiMessage getMessage() {
		return message;
	}

	public void setMessage(final MidiMessage message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		final StringBuilder buf = new StringBuilder();
		buf.append("<patch ");
		buf.append(bank);
		buf.append("-");
		buf.append(Integer.toString(position));
		buf.append(" \"");
		buf.append(name);
		buf.append("\">");
		
		return buf.toString();
	}
}
