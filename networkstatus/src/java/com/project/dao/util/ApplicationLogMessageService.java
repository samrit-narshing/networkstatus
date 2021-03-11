package com.project.dao.util;

import com.project.model.util.ApplicationLogMessageListResource;
import com.project.model.util.ApplicationLogMessageResource;
import com.project.util.searchcriteria.ApplicationLogMessageSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface ApplicationLogMessageService {

    public ApplicationLogMessageResource findApplicationLogMessage(Long id) throws Exception;

    public ApplicationLogMessageResource createApplicationLogMessage(ApplicationLogMessageResource data) throws Exception;

    public ApplicationLogMessageListResource createApplicationLogMessages(ApplicationLogMessageListResource data) throws Exception;

    public ApplicationLogMessageListResource findApplicationLogMessageListBySearchCriteria(ApplicationLogMessageSearchCriteria searchCriteria) throws Exception;

    public ApplicationLogMessageResource deleteApplicationLogMessage(Long id) throws Exception;

    public int deleteAllApplicationLogMessage() throws Exception;

}
