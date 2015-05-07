package rx.facebook.app;

import android.app.ActivityManager;
import android.app.Application;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

import com.facebook.SessionDefaultAudience;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.facebook.drawee.backends.pipeline.Fresco;

public class RxFacebookApplication extends Application {
    //public static final String APP_NAMESPACE = "sromkuapp_vtwo"; // credit sromku
    //public static final String APP_NAMESPACE = "rxfacebook";
    public static final String APP_NAMESPACE = "rxfacebook_test";

    @Override
    public void onCreate() {
        super.onCreate();

        //Timber.plant(new Timber.DebugTree());

        Fresco.initialize(this);

        //Permission.EMAIL,
        //Permission.PUBLISH_ACTION,
        //Permission.PUBLIC_PROFILE,
        //Permission.READ_STREAM,
        //Permission.USER_BIRTHDAY,
        //Permission.USER_FRIENDS,
        //Permission.USER_LOCATION,
        //Permission.USER_PHOTOS,
        //Permission.USER_STATUS,
        //Permission.USER_VIDEOS,
        Permission[] permissions = new Permission[] {
            Permission.READ_STREAM, // test
            Permission.USER_PHOTOS, // test
            Permission.PUBLIC_PROFILE,
            Permission.PUBLISH_ACTION
        };

        // @string/app_id
        String facebookAppId = getApplicationMetaData(this, com.facebook.Settings.APPLICATION_ID_PROPERTY);

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
            .setAppId(facebookAppId)
            .setNamespace(APP_NAMESPACE)
            .setPermissions(permissions)
            .setDefaultAudience(SessionDefaultAudience.FRIENDS)
            .setAskForAllPermissionsAtOnce(false)
            .build();

        SimpleFacebook.setConfiguration(configuration);

        //com.facebook.Settings.setPlatformCompatibilityEnabled(false);
    }

    public static String getApplicationMetaData(Context context, String key) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (info.metaData != null) {
                return info.metaData.getString(key);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }
}
