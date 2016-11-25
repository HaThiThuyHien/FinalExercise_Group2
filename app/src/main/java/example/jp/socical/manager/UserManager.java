package example.jp.socical.manager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import example.jp.socical.bean.DataLoginBean;
import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

/**
 * Created by Hien_BRSE on 11/21/2016.
 */

public class UserManager {

    private static Gson gson = new Gson();

    public static final String USER_DATA = "USER_DATA";

    public static void saveCurrentUser(DataLoginBean userProfileBean) {
        String userData = gson.toJson(userProfileBean, DataLoginBean.class);
        SharedPrefUtils.putString(USER_DATA, userData);
    }

    public static DataLoginBean getCurrentUser() {
        String userData = SharedPrefUtils.getString(USER_DATA, null);
        if (StringUtil.checkStringValid(userData)) {
            try {
                return gson.fromJson(userData, DataLoginBean.class);
            } catch (JsonSyntaxException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void clearUserData() {
        SharedPrefUtils.removeKey(USER_DATA);
        SharedPrefUtils.removeKey("TOKEN");
    }
}
