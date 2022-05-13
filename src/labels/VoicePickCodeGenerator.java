package labels;

public interface VoicePickCodeGenerator {

	/**
	 * Calculate the Voice Pick code as defined by the specifications, by taking the last 4 digits of
	 * the CRC16 hash of the lot code appended to the GTIN
	 * @param gtin the GTIN to use for calculation
	 * @param lot the lot to use for calculation
	 * @return the last 4 digits of the CRC16 hash of the gtin and lot
	 */
	public String calculateVoicePickCode(String gtin, String lot);
}
