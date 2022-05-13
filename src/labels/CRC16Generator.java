package labels;

public interface CRC16Generator {
	/**
	 * Return the cyclic redundancy check for the given plaintext using a polynomial of degree 16
	 * @param plainText the text to return the crc16 for
	 * @return CRC16 of the plaintext
	 */
	public int crc16(String plainText);
}
