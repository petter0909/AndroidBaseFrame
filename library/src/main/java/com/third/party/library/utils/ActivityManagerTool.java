package com.third.party.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

/**
 * activity管理类
 *
 * @author Jason
 */
public class ActivityManagerTool {

    public static boolean isUseActivityManager = true;

    private final List<Activity> activities = new LinkedList<Activity>();

    private static ActivityManagerTool manager;

    /*
     * activity exist
     */
    private boolean isExist = false;

    public static Class<?> indexActivity;

    public static List<Class<?>> bottomActivities = new LinkedList<Class<?>>();

    /**
     * @return
     */
    public static ActivityManagerTool getActivityManager() {
        if (null == manager) {
            manager = new ActivityManagerTool();
        }
        return manager;
    }

    /**
     * @param activity
     * @return
     */
    public boolean add(final Activity activity) {

        int position = 0;

        if (isUseActivityManager) {

            if (isBottomActivity(activity)) {
                for (int i = 0; i < activities.size() - 1; i++) {
                    if (!isBottomActivity(activities.get(i))) {
                        popActivity(activities.get(i));
                        i--;
                    }
                    if (i > 0) {
                        if (activities.get(i).getClass()
                                .equals(activity.getClass())) {
                            isExist = true;
                            position = i;
                        }
                    }
                }

            }
        }

        if (!activities.add(activity)) {
            return false;
        }

        if (isExist) {
            isExist = false;
            activities.remove(position);
        }
        return true;
    }

    /**
     * @param activity
     */
    public void finish(final Activity activity) {
        for (Activity iterable : activities) {
            if (activity != iterable) {
                iterable.finish();
            }
        }
    }

    /**
     */
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.out.println("程序退出");
        System.exit(0);
    }

    /**
     * 清除所有activity
     */
    public void clearActivitys() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 清除所有activity 除了noclearActivity
     */
    public void clearActivitys(Class<?> loginActivityClass) {
        for (Activity activity : activities) {
            if (activity != null && activity.getClass() != loginActivityClass) {
                activity.finish();
            }
        }
    }

    /**
     * @param activity
     */
    private void popActivity(final Activity activity) {

        if (activity != null) {
            activity.finish();
            activities.remove(activity);
        }

    }

    /**
     *
     * @param targetclazz
     * @param sourceActivity
     */
    public void removeTemporaryActivities(final Class<Activity> targetclazz,
                                          final Activity sourceActivity) {
        if (targetclazz == null || sourceActivity == null) {
            return;
        }

        int begin = -1;
        int end = -1;
        Activity activity;

        for (int i = activities.size() - 1; i >= 0; i--) {
            activity = activities.get(i);
            if (activity.getClass() == targetclazz && end == -1) {
                end = i;
            }
            if (sourceActivity == activity && begin == -1) {
                begin = i;
            }
            if (begin != -1 && end != -1) {
                break;
            }
        }

        if (end != -1 && begin > end) {
            for (int i = begin; i > end; i--) {
                activity = activities.get(i);
                popActivity(activity);
            }
        }
    }

    /**
     * @return
     */
    @SuppressWarnings("unused")
    private Activity currentActivity() {
        Activity activity = activities.get(activities.size() - 1);

        return activity;
    }

    /**
     * @return
     */
    public boolean isBottomActivity(final Activity activity) {

        for (int i = 0; i < bottomActivities.size(); i++) {
            if (activity.getClass() == bottomActivities.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     */
    public void backIndex(final Context context) {

        if (activities.size() <= 0) {
            return;
        }

        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (isBottomActivity(activity)) {
                Intent intent = new Intent();
                intent.setClass(context, indexActivity);
                context.startActivity(intent);
            } else {
                popActivity(activity);
            }
        }
    }

    /**
     * @param clazz
     */
    public <E extends Activity> boolean backToActivity(final Class<E> clazz) {
        boolean flag = false;
        if (activities.size() <= 0) {
            return flag;
        }

        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (activity.getClass() == clazz) {
                flag = true;
                break;
            }
        }
        if (flag) {
            for (int i = activities.size() - 1; i >= 0; i--) {
                Activity activity = activities.get(i);
                if (activity.getClass() != clazz) {
                    popActivity(activity);
                } else {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * @param activity
     */
    public void removeActivity(final Activity activity) {

        if (activity != null) {
            activities.remove(activity);
        }
    }

    /**
     * @param activityClass
     */
    public void setBottomActivities(final Class<?> activityClass) {
        if (activityClass != null) {
            bottomActivities.add(activityClass);
        }
    }

    public List<Activity> getActivities() {
        return activities;
    }
}