package halfbyte.app;

public class Utils {
    public static byte hexStringToByte(String s) throws Exception {
        // input length check
        if (s.length() < 1 || s.length() > 2){
            throw new Exception("Invalid Input");
        }

        // make upper
        s = s.toUpperCase();

        // keep track of total
        int total = 0;

        // loop through the string characters
        for (int i = 0; i < s.length(); ++i){
            // move to the next place
            total *= 0x10;

            // get the character
            char c = s.charAt(i);

            // check if 0 - 9
            if (c >= '0' && c <= '9') {
                total += (c - 0x30);
            }

            // check if A - F
            else if (c >= 'A' && c <= 'F'){
                total += (c - 0x37);
            }

            // invalid
            else{
                throw new Exception("Invalid Input");
            }
        }

        // done
        return (byte) (total & 0xff);
    }

    public static String byteToHexString(byte b){
        return String.format("%02X", b);
    }
}
