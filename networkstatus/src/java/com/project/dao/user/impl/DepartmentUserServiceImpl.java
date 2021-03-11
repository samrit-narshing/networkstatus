/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.dao.user.impl;

import com.project.config.PropertiesConfig;
import com.project.dao.rest.RestResponseManagerService;
import com.project.dao.user.DepartmentUserService;
import com.project.exception.rest.BadRequestException;
import com.project.exception.rest.InternalServerException;
import com.project.exception.rest.UnAuthorizedException;
import com.project.model.rest.RestResponse;
import com.project.model.user.DepartmentUserListResource;
import com.project.model.user.DepartmentUserResource;
import com.project.model.user.RoleResource;
import com.project.model.user.RoletListResource;
import com.project.model.util.FileInfo;
import com.project.model.util.UploadedFileContent;
import com.project.util.searchcriteria.DepartmentUserSearchCriteria;
import javax.annotation.Resource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author SAM
 */
//@Component
@Service("departmentUserService")
public class DepartmentUserServiceImpl implements DepartmentUserService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String query = null;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private RestResponseManagerService restResponseManagerService;

    private final String UNIQUE_WEBSERVICE_APPLICATION_ID = "LALITPUR@MANGALBAZAR#5536666";

    @Override
    public DepartmentUserResource createDepartmentUser(DepartmentUserResource professorResource) throws Exception {
        String rolesListPath = "/rest/web/departmentuser/create";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(professorResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource responseProfessorResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return responseProfessorResource;
            case 401:
                throw new UnAuthorizedException();
            case 404:
                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }

    }

    @Override
    public RoletListResource readRoles() throws Exception {
        String rolesListPath = "/rest/web/departmentuser/role/list";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                RoletListResource roletListResource = mapper.readValue(responseString, RoletListResource.class);
                return roletListResource;
            case 401:
                throw new UnAuthorizedException();
            case 404:
                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }

    }

    @Override
    public RoleResource readRolesByID(Long id) throws Exception {
        String path = "/rest/web/departmentuser/role/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                RoleResource roleResource = mapper.readValue(responseString, RoleResource.class);
                return roleResource;
            case 401:
                throw new UnAuthorizedException();
            case 404:
                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }

    }

    @Override
    public DepartmentUserResource readDepartmentUserByID(Long id) throws Exception {
        String path = "/rest/web/departmentuser/find/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource userResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return userResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public DepartmentUserResource readDepartmentUserByUsername(String userName) throws Exception {
        String path = "/rest/web/departmentuser/find/username/" + userName;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        System.out.println("cccccccccccccccc" + restResponse);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource userResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return userResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public DepartmentUserResource readDepartmentUserByUsername_ONLY_FOR_REST_LOGIN(String userName) throws Exception {

        String path = "/rest/web/departmentuser/find/authcheck/encrypted";
        String postString = "username=" + userName + "&password=" + UNIQUE_WEBSERVICE_APPLICATION_ID;

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostPLAINData(path, postString);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("cccccccccccccccc" + restResponse);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource professorResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return professorResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public DepartmentUserResource readDepartmentUserByUsernameAndPassword(String userName, String password) throws Exception {
        String path = "/rest/web/departmentuser/find/username/" + userName + "/password/" + password;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        System.out.println("cccccccccccccccc" + restResponse);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource professorResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return professorResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public DepartmentUserListResource readDepartmentUsersBySearchCriteria(DepartmentUserSearchCriteria searchCriteria) throws Exception {

//        testingCrosssite();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(searchCriteria);
        String path = "/rest/web/departmentuser/list";
        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserListResource departmentUserListResource = mapper.readValue(responseString, DepartmentUserListResource.class);
                return departmentUserListResource;
            case 401:
                throw new UnAuthorizedException();
            case 404:
                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }

    }

    @Override
    public DepartmentUserResource updateDepartmentUser(Long id, DepartmentUserResource userResource) throws Exception {
        String path = "/rest/web/departmentuser/update/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource responseProfessorResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return responseProfessorResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public DepartmentUserResource updateDepartmentUserDetails(Long id, DepartmentUserResource userResource) throws Exception {
        String path = "/rest/web/departmentuser/update/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource responseProfessorResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return responseProfessorResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public DepartmentUserResource updateDepartmentUserPassword(Long id, DepartmentUserResource userResource) throws Exception {
        String path = "/rest/web/departmentuser/update/password/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userResource);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource responseProfessorResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return responseProfessorResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public DepartmentUserResource deleteDepartmentUserByID(Long id) throws Exception {
        String path = "/rest/web/departmentuser/delete/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource professorResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return professorResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

    @Override
    public FileInfo uploadDepartmentUserProfileImageByObject(UploadedFileContent uploadedFileContent) throws Exception {
        String rolesListPath = "/rest/web/filehandler/upload/image/departmentuser/profile";
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(uploadedFileContent);
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(rolesListPath, jsonInString);

        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                FileInfo fileInfo = mapper.readValue(responseString, FileInfo.class);
                return fileInfo;
            case 401:
                throw new UnAuthorizedException();
            case 404:
                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }

    }

    @Override
    public DepartmentUserResource updateDepartmentUserEnabledStatus(Long id) throws Exception {
        String path = "/rest/web/departmentuser/update/changeenabledstatus/" + id;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString("");
        System.out.println(jsonInString);

        RestResponse restResponse = restResponseManagerService.getResponseStringFromWebServerWithPostJSONDataAndAuthDetails(path, jsonInString);
        switch (restResponse.getResponseCode()) {
            case 200:
            case 201:
                String responseString = restResponse.getResponseString();
                DepartmentUserResource responseResource = mapper.readValue(responseString, DepartmentUserResource.class);
                return responseResource;
            case 404:
                return null;
            case 401:
                throw new UnAuthorizedException();
//            case 404:
//                throw new BadRequestException();
            case 500:
                throw new InternalServerException();
            default:
                throw new Exception("Response code : " + restResponse.getResponseCode());
        }
    }

}
