package base.splithighlightseekbar;

import android.app.Application;

/**
 * Created by beyond on 18-8-7.
 */

public class MyApplication extends Application {
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getInstance() {
        return application;
    }
}
