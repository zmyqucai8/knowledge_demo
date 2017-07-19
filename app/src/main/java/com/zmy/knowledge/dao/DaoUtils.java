//package com.zmy.knowledge.dao;
//
//import android.content.Context;
//
///**
// * Created by win7 on 2017/7/11.
// */
//
//public class DaoUtils {
//    /**
//     * 数据库管理 及操作类
//     */
//    private static DaoMaster daoMaster;
//    private static DaoSession daoSession;
//
//    /**
//     * 取得DaoMaster
//     *
//     * @param context
//     * @return
//     */
//    public static DaoMaster getDaoMaster(Context context) {
//        if (daoMaster == null) {
//            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "CloudEn-db", null);
//            daoMaster = new DaoMaster(helper.getWritableDatabase());
//        }
//        return daoMaster;
//    }
//
//    /**
//     * 取得DaoSession
//     *
//     * @param context
//     * @return
//     */
//    public static DaoSession getDaoSession(Context context) {
//        if (daoSession == null) {
//            if (daoMaster == null) {
//                daoMaster = getDaoMaster(context);
//            }
//            daoSession = daoMaster.newSession();
//        }
//        return daoSession;
//    }
//}
