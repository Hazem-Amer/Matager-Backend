package com.matager.app.setting;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.user.User;

import java.util.List;

public interface SettingService {
    List<Setting> syncSettings(User user, SyncSettingModel syncSettingModel);

    Setting saveSetting(Owner owner, User user, Store store, SettingModel settingModel);

    Setting updateSetting(Owner owner, User user, Store store, SettingModel settingModel);

    String getSettingValue(Long storeId, String name);

}
