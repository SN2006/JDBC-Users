package com.example.utils;

import com.example.repository.ConfigDBRepository;

public class ConfigSetting {

    public static void doStartSetting(){
        ConfigDBRepository configDBRepository = new ConfigDBRepository();
        configDBRepository.createTable();
    }

}
