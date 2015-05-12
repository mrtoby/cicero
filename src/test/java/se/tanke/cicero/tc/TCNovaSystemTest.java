package se.tanke.cicero.tc;

public class NovaSystemTest {

	public final static int[] patchSysexData = { 0xf0, 0x00, 0x20, 0x1f, 0x00,
		0x63, 0x20, 0x01, 0x1f, 0x00, 0x42, 0x4c, 0x41, 0x43, 0x4b, 0x20,
		0x48, 0x4f, 0x4c, 0x45, 0x52, 0x6f, 0x74, 0x6f, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x0a, 0x04, 0x00, 0x00, 0x6b, 0x00, 0x00, 0x00, 0x00,
		0x14, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x7f, 0x7f, 0x7f, 0x07, 0x00, 0x00,
		0x00, 0x00, 0x32, 0x00, 0x00, 0x00, 0x64, 0x00, 0x00, 0x00, 0x01,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00,
		0x00, 0x09, 0x00, 0x00, 0x00, 0x75, 0x7f, 0x7f, 0x07, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x0b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x06, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x72, 0x7f,
		0x7f, 0x07, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x29,
		0x01, 0x00, 0x00, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x2b, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x55, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x2d, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x14, 0x04, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x64, 0x00,
		0x00, 0x00, 0x28, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2a,
		0x00, 0x00, 0x00, 0x18, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x14, 0x00, 0x00, 0x00, 0x32,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00,
		0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00,
		0x00, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x76, 0x7f,
		0x7f, 0x07, 0x00, 0x00, 0x00, 0x00, 0x05, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x49, 0x7f, 0x7f, 0x07, 0x28, 0x00, 0x00,
		0x00, 0x64, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x37, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x03,
		0x01, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x07, 0x00, 0x00, 0x00,
		0x3c, 0x01, 0x00, 0x00, 0x75, 0x7f, 0x7f, 0x07, 0x0c, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00,
		0x00, 0x00, 0x23, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x1e, 0xf7 };

public final static int[] systemParametersSysexData = { 0xf0, 0x00, 0x20,
		0x1f, 0x00, 0x63, 0x20, 0x02, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x32,
		0x00, 0x00, 0x00, 0x64, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3c, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x55, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0a, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x02,
		0x00, 0x00, 0x00, 0x7e, 0x7f, 0x7f, 0x07, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x38, 0x03, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x4f, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x01,
		0x00, 0x00, 0x00, 0x0f, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x52, 0x04, 0x00, 0x00, 0x03, 0x04, 0x04,
		0x00, 0x06, 0x0a, 0x10, 0x00, 0x09, 0x10, 0x1c, 0x00, 0x0c, 0x16,
		0x28, 0x00, 0x0f, 0x1c, 0x34, 0x00, 0x12, 0x22, 0x40, 0x00, 0x15,
		0x28, 0x4c, 0x00, 0x18, 0x2e, 0x58, 0x00, 0x1b, 0x34, 0x64, 0x00,
		0x1e, 0x3a, 0x70, 0x00, 0x21, 0x40, 0x7c, 0x00, 0x24, 0x46, 0x08,
		0x01, 0x27, 0x4c, 0x14, 0x01, 0x2a, 0x52, 0x20, 0x01, 0x2d, 0x58,
		0x2c, 0x01, 0x30, 0x5e, 0x38, 0x01, 0x33, 0x64, 0x44, 0x01, 0x36,
		0x6a, 0x50, 0x01, 0x39, 0x70, 0x5c, 0x01, 0x3c, 0x76, 0x68, 0x01,
		0x3f, 0x7c, 0x74, 0x01, 0x42, 0x02, 0x01, 0x02, 0x45, 0x08, 0x0d,
		0x02, 0x48, 0x0e, 0x19, 0x02, 0x4b, 0x14, 0x25, 0x02, 0x4e, 0x1a,
		0x31, 0x02, 0x51, 0x20, 0x3d, 0x02, 0x54, 0x26, 0x49, 0x02, 0x57,
		0x2c, 0x55, 0x02, 0x5a, 0x32, 0x61, 0x02, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x03, 0x04, 0x04, 0x00, 0x06, 0x0a, 0x10,
		0x00, 0x09, 0x10, 0x1c, 0x00, 0x0c, 0x16, 0x28, 0x00, 0x0f, 0x1c,
		0x34, 0x00, 0x12, 0x22, 0x40, 0x00, 0x15, 0x28, 0x4c, 0x00, 0x18,
		0x2e, 0x58, 0x00, 0x1b, 0x34, 0x64, 0x00, 0x1e, 0x3a, 0x70, 0x00,
		0x21, 0x40, 0x7c, 0x00, 0x24, 0x46, 0x08, 0x01, 0x27, 0x4c, 0x14,
		0x01, 0x2a, 0x52, 0x20, 0x01, 0x2d, 0x58, 0x2c, 0x01, 0x30, 0x5e,
		0x38, 0x01, 0x33, 0x64, 0x44, 0x01, 0x36, 0x6a, 0x50, 0x01, 0x39,
		0x70, 0x5c, 0x01, 0x3c, 0x76, 0x68, 0x01, 0x00, 0x77, 0x02, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x47, 0xf7 };

}