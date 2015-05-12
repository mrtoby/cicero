package se.tanke.cicero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import se.tanke.cicero.midi.MidiMessage;

/**
 * Registry of device types.
 */
public class DeviceTypeRegistry {

	private List<DeviceType> deviceTypes = new ArrayList<>();
	private Map<String, DeviceType> deviceTypeById = new HashMap<>();
	
	/**
	 * Register a device type.
	 * @param deviceType Device type to register
	 */
	public void register(final DeviceType deviceType) {
		if (deviceTypeById.containsKey(deviceType.getId())) {
			throw new IllegalArgumentException("Device type already added: " + deviceType);
		}
		deviceTypes.add(deviceType);
		deviceTypeById.put(deviceType.getId(), deviceType);
	}
	
	/**
	 * Get device type by its id.
	 * @param deviceTypeId Device type id
	 * @return The device type, if present
	 */
	public Optional<DeviceType> getById(final String deviceTypeId) {
		return Optional.ofNullable(deviceTypeById.get(deviceTypeId));
	}

	/**
	 * Find device types that can handle a specific patch sysex message.
	 * @param msg The message to find a device type based on
	 * @return Device types that can convert the message into a patch
	 */
	public List<DeviceType> findByPatchMessage(final MidiMessage msg) {
		final List<DeviceType> result = new ArrayList<>();
		for (DeviceType deviceType : deviceTypes) {
			final Optional<Patch> patch = deviceType.decodePatch(msg);
			if (patch.isPresent()) {
				result.add(deviceType);
			}
		}
		return result;
	}
}
