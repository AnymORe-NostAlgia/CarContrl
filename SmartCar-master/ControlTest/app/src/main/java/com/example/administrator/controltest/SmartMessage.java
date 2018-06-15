package com.example.administrator.controltest;

public class SmartMessage {

	public final static byte[] CMD_FORWARD = { 0x76, 0x00, 0x20, 0x00, 0x09,
			0x00, 0x00 };
	public final static byte[] CMD_LEFT = { 0x76, 0x00, 0x20, 0x00, 0x0A, 0x00,
			0x00 };
	public final static byte[] CMD_RIGHT = { 0x76, 0x00, 0x20, 0x00, 0x05,
			0x00, 0x00 };
	public final static byte[] CMD_BACKWARD = { 0x76, 0x00, 0x20, 0x00, 0x06,
			0x00, 0x00 };
	public final static byte[] CMD_FORWARD_LEFT = { 0x76, 0x00, 0x20, 0x00,
			0x08, 0x00, 0x00 };
	public final static byte[] CMD_FORWARD_RIGHT = { 0x76, 0x00, 0x20, 0x00,
			0x01, 0x00, 0x00 };
	public final static byte[] CMD_BACKWARD_LEFT = { 0x76, 0x00, 0x20, 0x00,
			0x04, 0x00, 0x00 };
	public final static byte[] CMD_BACKWARD_RIGHT = { 0x76, 0x00, 0x20, 0x00,
			0x02, 0x00, 0x00 };
	public final static byte[] CMD_STOP = { 0x76, 0x00, 0x20, 0x00, 0x00, 0x00,
			0x00 };
	public final static byte[] CMD_SENSOR = { 0x76, 0x00, 0x30, 0x00, 0x00,
			0x00, 0x00 };
	public static byte[] CMD_SENSOR_ON = { 0x76, 0x00, 0x30, 0x00, 0x01, 0x00,
			0x31 };
	public static byte[] CMD_SENSOR_OFF = { 0x76, 0x00, 0x30, 0x00, 0x00, 0x00,
			0x30 };

	public final static int TOP_LEFT = 0;
	public final static int TOP_CENTER = 1;
	public final static int TOP_RIGHT = 2;
	public final static int LEFT = 3;
	public final static int RIGHT = 4;
	public final static int BOTTOM_LEFT = 5;
	public final static int BOTTOM_CENTER = 6;
	public final static int BOTTOM_RIGHT = 7;
	public final static int CENTER = 8;

	public final static int AVOID = 1002;
	public final static int TOUCH = 0xA1;
	public final static int SPEED = 0xA2;
	public final static int DIRECT = 0xA3;
	public final static int SENSOR_ON = 0xA4;
	public final static int SENSOR_OFF = 0xA5;
	public final static int ULTRA_ON = 0xA6;
	public final static int ULTRA_OFF = 0xA7;
}
