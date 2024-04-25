package com.example;

import com.example.utils.AppStarter;
import com.example.utils.ConfigSetting;

public class App
{
    public static void main( String[] args )
    {
        ConfigSetting.doStartSetting();
        AppStarter.startApp();
    }
}
