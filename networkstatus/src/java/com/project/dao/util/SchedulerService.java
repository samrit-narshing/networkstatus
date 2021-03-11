/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.util;

import com.project.model.util.SchedulerTaskResource;
import com.test.ChartDataTwoMain;

/**
 *
 * @author samri_g64pbd3
 */
public interface SchedulerService {

    public SchedulerTaskResource readSchedulerTask() throws Exception;

    public SchedulerTaskResource updateSchedulerTaskStatus(int status) throws Exception;

    public ChartDataTwoMain parseJSONURL() throws Exception;
}
