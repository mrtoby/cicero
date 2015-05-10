package se.tanke.cicero;

import java.util.List;
import java.util.Optional;

import se.tanke.cicero.midi.MidiMessage;

/**
 * This interface represent a device that supports some kind of patches that can
 * be handled using midi sysex messages.
 */
public interface DeviceType {

	/**
	 * Get a human-readable name of the device.
	 * @return The device name
	 */
	String getName();
	
	/**
	 * Get a unique identifier (within this application) for this device type.
	 * The id should only contain printable ascii characters except for
	 * whitespaces.
	 * @return The identifier
	 */
	String getId();

	/**
	 * Get the default configuration for the device.
	 * @return Default configuration
	 */
	DeviceConfiguration getDefaultConfiguration();
	
	/**
	 * Get a non-harmful midi sysex message that the device should respond to.
	 * This will be used when trying to detect if the device is present on some
	 * midi interface.
	 * @param config Configuration to use
	 * @return A midi sysex message
	 * @see #isDeviceDetectionResponse(MidiMessage)
	 */
	MidiMessage getDeviceDetectionMessage(DeviceConfiguration config);
	
	/**
	 * Check if a midi sysex message is the expected response for the device
	 * detection message.
	 * @param config Configuration to use
	 * @param reply Some received midi message
	 * @return <code>true</code> if this is the expected response
	 * @see #getDeviceDetectionMessage()
	 */
	boolean isDeviceDetectionResponse(DeviceConfiguration config, MidiMessage reply);
	
	/**
	 * Create messages needed to request all patches from the device.
	 * @param config Configuration to use
	 * @return A list of midi messages that can be used to obtain all patches from the device
	 */
	List<MidiMessage> getRequestAllPatchesMessage(DeviceConfiguration config);

	/**
	 * Check if this device use the provided manufacturer id in its midi sysex
	 * messages.
	 * @param manufacturerBytes One or three bytes representing the manufacturer
	 * @return <code>true</code> if this device be able to deal with messages from this manufacturer
	 */
	boolean isManufacturerApplicable(int[] manufacturerBytes);

	/**
	 * Decode the given midi message into a patch for this device. 
	 * @param config Configuration to use
	 * @param msg The message to decode
	 * @return The patch, if it could be decoded
	 */
	Optional<Patch> decodePatch(DeviceConfiguration config, MidiMessage msg);
	
	/**
	 * Encode a patch into a midi message again. If the information in the patch has
	 * changed this should be reflected in the resulting midi message. 
	 * @param config Configuration to use
	 * @param patch Patch to encode
	 * @return Midi message, if it could be encoded
	 */
	Optional<MidiMessage> encodePatch(DeviceConfiguration config, Patch patch);
}
