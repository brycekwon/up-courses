public class bitExample {

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

    public static void testBitCountZero() {
        int result = Integer.bitCount(0);
        System.out.println("Bit count for 0: " + result);
        int result2 = Bit.bit_adder(0);
        System.out.println("Bit count for 0: " + result2);
    }

    public static void testBitCountSingleBit() {
        int result = Integer.bitCount(16);
        System.out.println("Bit count for 16: " + result);
        int result2 = Bit.bit_adder(16);
        System.out.println("Bit count for 16: " + result2);

    }

    public static void testBitCountMultipleBits() {
        int result = Integer.bitCount(23);
        System.out.println("Bit count for 23: " + result);
        int result2 = Bit.bit_adder(23);
        System.out.println("Bit count for 23: " + result2);

    }

    public static void testBitCountNegativeNumber() {
        int result = Integer.bitCount(-5);
        System.out.println("Bit count for -5: " + result);
        int result2 = Bit.bit_adder(-5);
        System.out.println("Bit count for -5: " + result2);

    }

    public static void testBitCountMaxInteger() {
        int result = Integer.bitCount(2147483647);
        System.out.println("Bit count for 2147483647: " + result);
        int result2 = Bit.bit_adder(2147483647);
        System.out.println("Bit count for 2147483647: " + result2);

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
