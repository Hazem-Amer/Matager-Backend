package com.matager.app.owner.settings;

import com.matager.app.owner.Owner;
import com.matager.app.owner.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerSettingServiceImpl implements OwnerSettingService {

    private final OwnerRepository ownerRepository;
    private final OwnerSettingRepository ownerSettingRepository;

    @Override
    public void initOwnerSettings(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Owner does not exist."));

        for (OwnerSettings setting : OwnerSettings.values()) {
            if (!ownerSettingRepository.existsByOwnerIdAndName(ownerId, setting.name())) {
                OwnerSetting newOwnerSetting = new OwnerSetting();
                newOwnerSetting.setOwner(owner);
                newOwnerSetting.setName(setting.name());
                newOwnerSetting.setValue(OwnerSettings.getDefaultValue(setting));
                ownerSettingRepository.saveAndFlush(newOwnerSetting);
            }
        }
    }

    @Override
    public void setSettingValue(Long ownerId, OwnerSettings setting, String value) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Owner does not exist."));

        Optional<OwnerSetting> ownerSetting = ownerSettingRepository.findByOwnerIdAndName(ownerId, setting.name());
        if (ownerSetting.isPresent()) {
            ownerSetting.get().setValue(value);
            ownerSettingRepository.saveAndFlush(ownerSetting.get());
        } else {
            OwnerSetting newOwnerSetting = new OwnerSetting();
            newOwnerSetting.setOwner(owner);
            newOwnerSetting.setName(setting.name());
            newOwnerSetting.setValue(value);
            ownerSettingRepository.saveAndFlush(newOwnerSetting);
        }
    }

    @Override
    public Optional<String> getSettingValue(Long ownerId, OwnerSettings setting) {
        return ownerSettingRepository.getValueByOwnerIdAndSetting(ownerId, setting.name());
    }
}
