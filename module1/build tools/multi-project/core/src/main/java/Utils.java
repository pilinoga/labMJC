import com.epam.task1.StringUtils;
import java.util.Arrays;

public class Utils {
    public static boolean isAllPositiveNumbers(String... str) {
        return str != null && Arrays.stream(str).allMatch(StringUtils::isPositiveNumber);
    }
}
