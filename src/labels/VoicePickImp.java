/**
 * Implements VoiceiIckCodeGenerator. 
 */

package labels;

public class VoicePickImp implements VoicePickCodeGenerator {

	@Override
	public String calculateVoicePickCode(String gtin, String lot) {
		gtin = prefixZeros(gtin);
		String plainText = gtin + lot;
		int result = new CRC16Imp().crc16(plainText);
		String res = Integer.toString(result);
		while (res.length() < 4) {
			res = "0" + res;
		}
		return res.substring(res.length() -4);
	}
	
	private String prefixZeros(String gtin) {
		while (gtin.length() < 14) {
			gtin = "0" + gtin;
		}
		return gtin;
	}
}
