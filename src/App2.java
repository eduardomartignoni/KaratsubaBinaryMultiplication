/* read README */
public class App2 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid number of arguments. Please provide two binary numbers to multiply.");
            return;
        }

        String num1 = args[0];
        String num2 = args[1];

        String result = multiplyKaratsuba(num1, num2);
        System.out.println(result);
    }

    private static int makeEqualLength(StringBuilder num1, StringBuilder num2) {
        int length1 = num1.length();
        int length2 = num2.length();

        if (length1 < length2) {
            for (int i = 0; i < length2 - length1; i++) {
                num1.insert(0, '0');
            }
            return length2;
        } else if (length1 > length2) {
            for (int i = 0; i < length1 - length2; i++) {
                num2.insert(0, '0');
            }
        }
        return length1;
    }

    private static StringBuilder firstHalf(StringBuilder num) {
        int length = num.length();
        return new StringBuilder(num.substring(0, (int) Math.floor(length / 2)));
    }

    private static StringBuilder secondHalf(StringBuilder num) {
        int length = num.length();
        return new StringBuilder(num.substring((int) Math.ceil(length / 2)));
    }

    private static String shiftLeft(String num, int shift) {
        StringBuilder result = new StringBuilder(num);
        for (int i = 0; i < shift; i++) {
            result.append('0');
        }
        return result.toString();
    }

    private static String[] sumBits(String bit1, String bit2, String carry) {
        String sum = "0";
        String newCarry = "0";

        if (bit1.equals("1") && bit2.equals("1")) {
            if (carry.equals("1")) {
                sum = "1";
                newCarry = "1";
            } else {
                sum = "0";
                newCarry = "1";
            }
        } else if (bit1.equals("1") || bit2.equals("1")) {
            if (carry.equals("1")) {
                sum = "0";
                newCarry = "1";
            } else {
                sum = "1";
                newCarry = "0";
            }
        } else {
            if (carry.equals("1")) {
                sum = "1";
                newCarry = "0";
            } else {
                sum = "0";
                newCarry = "0";
            }
        }

        return new String[]{sum, newCarry};
    }

    private static String sumStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        String carry = "0";
        int length = makeEqualLength(new StringBuilder(num1), new StringBuilder(num2));

        num1 = String.format("%" + length + "s", num1).replace(' ', '0');
        num2 = String.format("%" + length + "s", num2).replace(' ', '0');

        for (int i = length - 1; i >= 0; i--) {
            String bit1 = num1.substring(i, i + 1);
            String bit2 = num2.substring(i, i + 1);

            String[] addResults = sumBits(bit1, bit2, carry);
            result.insert(0, addResults[0]);
            carry = addResults[1];
        }

        if (carry.equals("1")) {
            result.insert(0, carry);
        }

        return result.toString();
    }
    private static String subtractStrings(String num1, String num2) {
        int length = Math.max(num1.length(), num2.length());
        num1 = String.format("%" + length + "s", num1).replace(' ', '0');
        num2 = String.format("%" + length + "s", num2).replace(' ', '0');
        
        StringBuilder result = new StringBuilder();
        String borrow = "0";
        
        for (int i = length - 1; i >= 0; i--) {
            String bit1 = num1.substring(i, i + 1); 
            String bit2 = num2.substring(i, i + 1); 
            
            String[] subtractResults = subtractBits(bit1, bit2, borrow);
            result.insert(0, subtractResults[0]); 
            borrow = subtractResults[1]; 
        }
        
        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }
        
        return result.toString();
    }
    
    private static String[] subtractBits(String bit1, String bit2, String borrow) {
        String diff = "0"; 
        String newBorrow = "0"; 
        
        if (bit1.equals("0")) {
            if (bit2.equals("0")) {
                if (borrow.equals("1")) {
                    diff = "1"; 
                    newBorrow = "1";
                } else {
                    diff = "0"; 
                    newBorrow = "0";
                }
            } else { 
                if (borrow.equals("1")) {
                    diff = "0";
                    newBorrow = "1";
                } else {
                    diff = "1"; 
                    newBorrow = "1";
                }
            }
        } else { 
            if (bit2.equals("0")) {
                if (borrow.equals("1")) {
                    diff = "0"; 
                    newBorrow = "0";
                } else {
                    diff = "1"; 
                    newBorrow = "0";
                }
            } else { 
                if (borrow.equals("1")) {
                    diff = "1"; 
                    newBorrow = "1";
                } else {
                    diff = "0"; 
                    newBorrow = "0";
                }
            }
        }
        
        return new String[]{diff, newBorrow};
    }
    
    private static String multiplyStrings(StringBuilder num1, StringBuilder num2) {
        String result = "0";
        int length = makeEqualLength(num1, num2);

        for (int i = length - 1; i >= 0; i--) {
            if (num2.charAt(i) == '1') {
                String shiftedNum1 = shiftLeft(num1.toString(), length - 1 - i);
                result = sumStrings(result, shiftedNum1);
            }
        }

        return result;
    }

    private static String multiplyKaratsuba(String num1Str, String num2Str) {
        StringBuilder num1 = new StringBuilder(num1Str);
        StringBuilder num2 = new StringBuilder(num2Str);

        int length = makeEqualLength(num1, num2);

        if (length == 0) {
            return "0";
        } else if (length == 1) {
            return (num1.charAt(0) == '1' && num2.charAt(0) == '1') ? "1" : "0";
        }

        StringBuilder xl = firstHalf(num1);
        StringBuilder xr = secondHalf(num1);
        StringBuilder yl = firstHalf(num2);
        StringBuilder yr = secondHalf(num2);

        String R1 = multiplyStrings(xl, yl);
        String R2 = multiplyStrings(xr, yr);
        String R3 = multiplyStrings(new StringBuilder(sumStrings(xl.toString(), xr.toString())), 
                                    new StringBuilder(sumStrings(yl.toString(), yr.toString())));

        String P1 = shiftLeft(R1, 2 * (int) Math.ceil(length / 2.0)); 
        String P2 = shiftLeft(subtractStrings(subtractStrings(R3, R1), R2), (int) Math.ceil(length / 2.0));
        String result = sumStrings(sumStrings(P1, P2), R2);

        return result;
    }
}
