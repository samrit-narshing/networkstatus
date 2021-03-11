package com.project.dao.user;

import com.project.model.user.DeviceUserResource;
import com.project.model.user.DeviceUsersListResource;
import com.project.util.searchcriteria.DeviceUserSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface DeviceUserService {

    public DeviceUserResource createOrUpdateDeviceUser(DeviceUserResource data) throws Exception;

    public DeviceUserResource findDeviceUser(Long id) throws Exception;

    public DeviceUserResource findDeviceUserByUsername(String username) throws Exception;

    public DeviceUsersListResource findDeviceUsersBySearchCriteria(DeviceUserSearchCriteria searchCriteria) throws Exception;

    public DeviceUserResource deleteDeviceUser(Long id) throws Exception;
    
    public int deleteAllDeviceUserAndSignOut() throws Exception;

    public int deleteDeviceUserByUsername(String username) throws Exception;

}
