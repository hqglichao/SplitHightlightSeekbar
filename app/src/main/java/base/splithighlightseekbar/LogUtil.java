package base.splithighlightseekbar;

import android.util.Log;

/**
 * Created by beyond on 18-8-7.
 */

public class LogUtil {
    private static boolean isDebug = true;
    private final static String tag = "LogUtil v1";

    public static void d(String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }
}
