package com.matager.app.owner.settings;

import java.util.Optional;

public interface OwnerSettingService {

    void initOwnerSettings(Long ownerId);

    void setSettingValue(Long ownerId, OwnerSettings setting, String value);

    Optional<String> getSettingValue(Long ownerId, OwnerSettings setting);
}
