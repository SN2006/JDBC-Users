package com.example.utils;

import com.example.controller.AppController;
import com.example.repository.ConfigDBRepository;
import com.example.service.AppService;
import com.example.view.AppView;

public class AppStarter {

    public static void startApp(){
        AppService service = new AppService();
        AppView view = new AppView();
        AppController controller = new AppController(view, service);
        controller.runApp();
    }

}
