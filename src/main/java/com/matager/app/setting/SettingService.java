package com.matager.app.setting;

import at.orderking.bossApp.owner.Owner;
import at.orderking.bossApp.store.Store;
import at.orderking.bossApp.user.User;

import java.util.List;

public interface SettingService {
    List<Setting> syncSettings(User user, SyncSettingModel syncSettingModel);

    Setting saveSetting(Owner owner, User user, Store store, SettingModel settingModel);

    Setting updateSetting(Owner owner, User user, Store store, SettingModel settingModel);

    String getSettingValue(Long storeId, String name);

}
