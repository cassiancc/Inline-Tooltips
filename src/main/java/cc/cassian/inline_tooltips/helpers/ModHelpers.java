package cc.cassian.inline_tooltips.helpers;

public class ModHelpers {
    public static String format(double amount) {
        String format = String.format("%1.1f", amount);
        if (format.endsWith(".0")) {
            return String.valueOf(Math.round(amount));
        }
        return format;
    }

}
