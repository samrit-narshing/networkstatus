/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.system;

import com.project.model.system.ControlPanel;

/**
 *
 * @author Samrit
 */
public interface ControlPanelConfigService {

    public abstract int callGarbageCollector();

    public abstract ControlPanel readControlPanel() throws Exception;

    public abstract int callGarbageCollectorForWebServer() throws Exception;

    public abstract int resetAllDataFromWebServer() throws Exception;

}
