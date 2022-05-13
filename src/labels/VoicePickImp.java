package labels;

public class VoicePickImp implements VoicePickCodeGenerator {

	@Override
	public String calculateVoicePickCode(String gtin, String lot) {
		String plainText = gtin + lot;
		int result = new CRC16Imp().crc16(plainText);
		String res = Integer.toString(result);
		return res.substring(res.length() -4);
	}
}
