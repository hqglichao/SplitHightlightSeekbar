package base.splithighlightseekbar;

import android.os.Looper;
import android.util.Log;

/**
 * Created by beyond on 18-8-7.
 */

public class Utils {
    public static long parseLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        return 0;
    }


    public static boolean checkIsMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
