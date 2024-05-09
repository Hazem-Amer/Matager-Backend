package com.matager.app.setting;

import com.matager.app.owner.Owner;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final StoreRepository storeRepository;
    private final SettingRepository settingRepository;


    @Override
    public List<Setting> syncSettings(User user, SyncSettingModel syncSettingsModel) {
        Owner owner = user.getOwner();
        Store store;

        if (syncSettingsModel.getStoreUuid() != null) {
            store = storeRepository.findByOwnerIdAndUuid(owner.getId(), syncSettingsModel.getStoreUuid()).orElseThrow(() -> new RuntimeException("Store not found."));
        } else {
            if (user.getDefaultStore() == null)
                throw new RuntimeException("No default store found, please specify store uuid.");
            store = user.getDefaultStore();
        }

        List<Setting> settings = new ArrayList<>();

        for (SettingModel settingModel : syncSettingsModel.getSettings()) {
            if (settingRepository.existsByStoreIdAndName(store.getId(), settingModel.getName())) {
                settings.add(updateSetting(owner, user, store, settingModel));
            } else {
                settings.add(saveSetting(owner, user, store, settingModel));
            }
        }

        return settings;
    }

    @Override
    public Setting saveSetting(Owner owner, User user, Store store, SettingModel settingModel) {
        Setting setting = new Setting();

        setting.setOwner(owner);
        setting.setStore(store);
        setting.setName(settingModel.getName());
        setting.setValue(settingModel.getValue());

        return settingRepository.saveAndFlush(setting);
    }

    @Override
    public Setting updateSetting(Owner owner, User user, Store store, SettingModel settingModel) {
        Setting setting = settingRepository.findByStoreIdAndName(store.getId(), settingModel.getName()).orElseThrow(() -> new RuntimeException("Setting not found."));

        if (settingModel.getName() != null) setting.setName(settingModel.getName());
        if (settingModel.getValue() != null) setting.setValue(settingModel.getValue());

        return settingRepository.saveAndFlush(setting);
    }

    @Override
    public String getSettingValue(Long storeId, String name) {
        return settingRepository.getValueByStoreIdAndName(storeId, name).orElseThrow(() -> new RuntimeException("Setting not found."));
    }
}
