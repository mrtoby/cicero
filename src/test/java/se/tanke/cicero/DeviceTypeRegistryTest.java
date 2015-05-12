package se.tanke.cicero;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import se.tanke.cicero.midi.MidiMessage;

public class DeviceTypeRegistryTest {

	@Mock private DeviceType deviceTypeA;
	@Mock private DeviceType deviceTypeB;
	private DeviceTypeRegistry sut;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(deviceTypeA.getId()).thenReturn("A");
		when(deviceTypeB.getId()).thenReturn("B");
		sut = new DeviceTypeRegistry();
		sut.register(deviceTypeA);
		sut.register(deviceTypeB);
	}
	
	@Test
	public void getById_returns_existing_device_type() {
		Optional<DeviceType> result = sut.getById("A");
		assertTrue(result.isPresent());
		assertEquals(deviceTypeA, result.get());
	}
	
	@Test
	public void getById_returns_empty_for_missing_device_type() {
		Optional<DeviceType> result = sut.getById("X");
		assertFalse(result.isPresent());
	}
	
	@Test
	public void findByPatchMessage_returns_empty_if_no_type_can_handle_messages() {
		MidiMessage knownMessage = mock(MidiMessage.class);
		when(deviceTypeA.decodePatch(Mockito.eq(knownMessage))).thenReturn(Optional.empty());
		when(deviceTypeB.decodePatch(Mockito.eq(knownMessage))).thenReturn(Optional.empty());
		
		List<DeviceType> result = sut.findByPatchMessage(knownMessage);
		
		assertTrue(result.isEmpty());
	}

	@Test
	public void findByPatchMessage_can_find_single_device_type_by_message() {
		MidiMessage knownMessage = mock(MidiMessage.class);
		when(deviceTypeA.decodePatch(Mockito.eq(knownMessage))).thenReturn(Optional.of(new Patch()));
		when(deviceTypeB.decodePatch(Mockito.eq(knownMessage))).thenReturn(Optional.empty());
		
		List<DeviceType> result = sut.findByPatchMessage(knownMessage);
		
		assertEquals(1, result.size());
		assertEquals(deviceTypeA, result.get(0));
	}

	@Test
	public void findByPatchMessage_can_find_multiple_device_types_by_message() {
		MidiMessage knownMessage = mock(MidiMessage.class);
		when(deviceTypeA.decodePatch(Mockito.eq(knownMessage))).thenReturn(Optional.of(new Patch()));
		when(deviceTypeB.decodePatch(Mockito.eq(knownMessage))).thenReturn(Optional.of(new Patch()));
		
		List<DeviceType> result = sut.findByPatchMessage(knownMessage);
		
		assertEquals(2, result.size());
		assertEquals(deviceTypeA, result.get(0));
		assertEquals(deviceTypeB, result.get(1));
	}
}
