public class Bit {
    public static void main(String args[]) {
        testBitCountZero();
        testBitCountSingleBit();
        testBitCountMultipleBits();
        testBitCountNegativeNumber();
        testBitCountMaxInteger();
        testLargeInput();
        testZeroInput();
        testNegativeInput();
    }

    // See: https://en.wikipedia.org/wiki/Hamming_weight
    public static int bit_adder(int num) {
        // clear the lower bit of each pair of bits
        num = num - ((num >>> 1)) & 0x55555555; // hex represents 0101... repeated 8 times

        // sum adjacent pairs of bits
        num = (num & 0x33333333) + ((num >>> 2) & 0x33333333);  // hex represents 0011.. repeated 8 times

        // Sum adjacent pairs of 4 bits
        num = (num + (num >>> 4)) & 0x0f0f0f0f; // 0x0f0f0f0f represents the binary pattern 00001111 repeated 8 times
        
        // Sum adjacent pairs of 8 bits
        num = num + (num >>> 8);
        
        // Sum adjacent pairs of 16 bits to get the final result
        num = num + (num >>> 16);

        // Mask to get the final 6-bit result 
        return num & 0x3f;  // 0x3f represents the binary pattern 00111111
    }

    public static void testBitCountZero() {
        int result = Bit.bit_adder(0);
        System.out.println("Bit count for 0: " + result);
    }

    public static void testBitCountSingleBit() {
        int result = Bit.bit_adder(16);
        System.out.println("Bit count for 16: " + result);

    }

    public static void testBitCountMultipleBits() {
        int result = Bit.bit_adder(23);
        System.out.println("Bit count for 23: " + result);

    }

    public static void testBitCountNegativeNumber() {
        int result = Bit.bit_adder(-5);
        System.out.println("Bit count for -5: " + result);

    }

    public static void testBitCountMaxInteger() {
        int result = Bit.bit_adder(2147483647);
        System.out.println("Bit count for 2147483647: " + result);

    }

    public static void testLargeInput() {
        int result = Bit.bit_adder(1000);
        System.out.println("Bit adder for 1000: " + result);
    }

    public static void testZeroInput() {
        int result = Bit.bit_adder(0);
        System.out.println("Bit adder for 0: " + result);
    }

    public static void testNegativeInput() {
        int result = Bit.bit_adder(-1000);
        System.out.println("Bit adder for -1000: " + result);
    }
}
