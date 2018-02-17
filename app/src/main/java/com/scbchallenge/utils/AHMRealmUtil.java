package com.scbchallenge.utils;

import android.content.Context;

import com.scbchallenge.models.MobileList;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class AHMRealmUtil {

    private static AHMRealmUtil ahmRealmUtil;
    private Context context;
    private RealmConfiguration realmConfiguration;

    /***
     ** Initialization of the Realm Object.
     ** @param mContext
     **/
    private AHMRealmUtil(Context mContext) {
        this.context = mContext;
        Realm.init(context);
        realmConfiguration = new RealmConfiguration.Builder().name("SCB.realm").schemaVersion(0).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static AHMRealmUtil getInstance(Context context) {
        if (ahmRealmUtil == null) {
            ahmRealmUtil = new AHMRealmUtil(context);
            return ahmRealmUtil;
        }
        return ahmRealmUtil;
    }

    public void addMobileList(final List<MobileList> mobileList) {
        clearDB();
        Realm.getInstance(realmConfiguration).beginTransaction();
        Realm.getInstance(realmConfiguration).copyToRealmOrUpdate(mobileList);
        Realm.getInstance(realmConfiguration).commitTransaction();
    }

    private void clearDB() {
        Realm.getInstance(realmConfiguration).executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(MobileList.class);
            }
        });
    }

    public void updateFavouriteMobileList(boolean value, String mobileId) {
        RealmResults<MobileList> results = Realm.getInstance(realmConfiguration).where(MobileList.class).findAll();
        results.load();
        Realm.getInstance(realmConfiguration).beginTransaction();
        if (!results.isEmpty()) {
            for (MobileList response : results) {
                if (response.getId().equalsIgnoreCase(mobileId)) {
                    response.setFavourite(value);
                }
            }
        }
        Realm.getInstance(realmConfiguration).commitTransaction();
    }

    public RealmList<MobileList> getFavouriteList() {
        RealmResults<MobileList> results = Realm.getInstance(realmConfiguration).where(MobileList.class).findAll();
        results.load();
        RealmList<MobileList> getFavouriteList = new RealmList<>();
        if (!results.isEmpty()) {
            for (MobileList response : results) {
                if (response.isFavourite()) {
                    getFavouriteList.add(response);
                }
            }
        }
        return getFavouriteList;
    }

    public RealmList<MobileList> sortListBy(String key, int id, boolean favourite) {
        String[] fieldNames = {key};
        Sort[] sort;
        if (id == AppConstants.LOW_TO_HIGH) {
            sort = new Sort[]{Sort.ASCENDING};
        } else {
            sort = new Sort[]{Sort.DESCENDING};
        }
        RealmResults<MobileList> results = Realm.getInstance(realmConfiguration).where(MobileList.class).findAllSorted(fieldNames, sort);
        results.load();
        RealmList<MobileList> getFavouriteList = new RealmList<>();
        if (!results.isEmpty()) {
            if (!favourite) {
                for (MobileList response : results) {
                    MobileList mobileList = new MobileList();
                    mobileList.setFavourite(response.isFavourite());
                    mobileList.setName(response.getName());
                    mobileList.setBrand(response.getBrand());
                    mobileList.setDescription(response.getDescription());
                    mobileList.setThumbImageURL(response.getThumbImageURL());
                    mobileList.setPrice(response.getPrice());
                    mobileList.setRating(response.getRating());
                    mobileList.setId(response.getId());
                    getFavouriteList.add(mobileList);
                }
            } else {
                for (MobileList response : results) {
                    if (response.isFavourite()) {
                        getFavouriteList.add(response);
                    }
                }

            }
        }
        return getFavouriteList;
    }

    public RealmList<MobileList> getMobileList() {
        RealmResults<MobileList> results = Realm.getInstance(realmConfiguration).where(MobileList.class).findAll();
        results.load();
        RealmList<MobileList> getFavouriteList = new RealmList<>();
        if (!results.isEmpty()) {
            for (MobileList response : results) {
                MobileList mobileList = new MobileList();
                mobileList.setFavourite(response.isFavourite());
                mobileList.setName(response.getName());
                mobileList.setBrand(response.getBrand());
                mobileList.setDescription(response.getDescription());
                mobileList.setThumbImageURL(response.getThumbImageURL());
                mobileList.setPrice(response.getPrice());
                mobileList.setRating(response.getRating());
                mobileList.setId(response.getId());
                getFavouriteList.add(mobileList);
            }

        }
        return getFavouriteList;
    }

    public void deleteFavouriteListItem(String mobileId) {
        RealmResults<MobileList> results = Realm.getInstance(realmConfiguration).where(MobileList.class).findAll();
        results.load();
        Realm.getInstance(realmConfiguration).beginTransaction();
        if (!results.isEmpty()) {
            for (MobileList response : results) {
                if (response.getId().equalsIgnoreCase(mobileId)) {
                    response.setFavourite(false);
                }
            }
        }
        Realm.getInstance(realmConfiguration).commitTransaction();
    }
}
