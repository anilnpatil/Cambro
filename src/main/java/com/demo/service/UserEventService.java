package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.UserEvent;
import com.demo.repository.UserEventRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserEventService {

    @Autowired
    private UserEventRepository userEventRepository;

     @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveLoginEvent(String username) throws DataAccessException {
        try {
            UserEvent userEvent = new UserEvent(username);
            userEvent.setLoginTime(LocalDateTime.now()); // Set login time
            userEventRepository.save(userEvent);
        } catch (DataAccessException e) {
            // Handle database access exception
            throw new DataAccessException("Failed to save login event", e) {};
        }
    }
    //to update the SchedulId in userEvent table
    public void updateWithScheduleId(String scheduleId) throws DataAccessException {
        try {
            // Retrieve the last UserEvent from the database based on login time
            UserEvent latestUserEvent = userEventRepository.findFirstByOrderByLoginTimeDesc();
            
            if (latestUserEvent != null) {
                if (latestUserEvent.getScheduleId() == null) {
                    // If last loginTime does not have a scheduleId, update it
                    latestUserEvent.setScheduleId(scheduleId);
                    latestUserEvent.setScheduleIdTime(LocalDateTime.now());
                    userEventRepository.save(latestUserEvent);
                } else {
                    // If last loginTime has a scheduleId, save a new UserEvent with the provided scheduleId and login time
                    UserEvent newUserEvent = new UserEvent();
                    newUserEvent.setUsername(latestUserEvent.getUsername());
                    newUserEvent.setLoginTime(latestUserEvent.getLoginTime());
                    newUserEvent.setScheduleId(scheduleId);
                    newUserEvent.setScheduleIdTime(LocalDateTime.now());
                    userEventRepository.save(newUserEvent);
                }
            } else {
                // Handle user event not found
                throw new DataAccessResourceFailureException("User event not found for username");
            }
        } catch (DataAccessException e) {
            // Handle database access exception
            throw new DataAccessResourceFailureException("Failed to update with schedule ID", e);
        }
    }
   
    //to update the BatchEndTime In userEvent table
    @Transactional
    public void updateBatchEndTime() throws DataAccessException {
        try {
            // Retrieve the last UserEvent from the database based on login time
            UserEvent latestUserEvent = userEventRepository.findFirstByOrderByLoginTimeDesc();
            if (latestUserEvent != null) {
                // Set BatchEndTime based on the latest LoginTime
                latestUserEvent.setBatchEndTime(LocalDateTime.now());
                
                // Save the updated UserEvent
                userEventRepository.save(latestUserEvent);
            } else {
                // Handle case where no UserEvent is found
                throw new DataAccessResourceFailureException("No UserEvent found in the database");
            }
        } catch (DataAccessException e) {
            // Handle database access exception
            throw new DataAccessResourceFailureException("Failed to update BatchEndTime", e);
        }
    }


    //it updates the loguttime in userEvent
   public void updateLogoutTime() throws DataAccessException {
        try {
            UserEvent userEvent = userEventRepository.findFirstByOrderByLoginTimeDesc();
            if (userEvent != null) {
                // Only update logout time if it's not already set
                if (userEvent.getLogoutTime() == null) {
                    userEvent.setLogoutTime(LocalDateTime.now());
                    userEventRepository.save(userEvent);
                }
            } else {
                // Handle user event not found
                throw new DataAccessResourceFailureException("User event not found for username ");
            }
        } catch (DataAccessException e) {
            // Handle database access exception
            throw new DataAccessResourceFailureException("Failed to update logout time", e);
        }
    }
   
    // to update the logout time if not already updated
    // public String getBatchStatusAndScheduleIdByLastLoginTime() throws DataAccessException {
    //     try {
    //         UserEvent latestLoginEvent = userEventRepository.findFirstByOrderByLoginTimeDesc();
    //         if (latestLoginEvent != null) {
    //             if (latestLoginEvent.getLogoutTime() == null) {
    //                 if (latestLoginEvent.getScheduleId() != null) {
    //                     return "Batch is running\n" +      
    //                     "Schedule ID: " + latestLoginEvent.getScheduleId() + "\n" +
    //                     "Username: " + latestLoginEvent.getUsername();
    //                 } else {
    //                     return "Batch is not running";
    //                 }
    //             }
    //         }
    //         return "Batch is not running";
    //     } catch (DataAccessException e) {
    //         throw new DataAccessException("Failed to fetch batch status and schedule ID by last login time", e) {};
    //     }
    // }

    // Method to get the start time, end time, and schedule ID
    public Map<String, Object> getStartTimeEndTimeScheduleID() {
        Map<String, Object> result = new HashMap<>();
    
        try {
            String sqlQuery = "SELECT CASE " +
                                    "WHEN endTime IS NULL THEN 1 " +
                                    "ELSE 0 " +
                               "END AS isBatchRunning, " +
                               "CASE WHEN endTime IS NULL THEN scheduleOlsnReleaseBarcode ELSE NULL END AS scheduleId " +
                               "FROM simuka " +
                               "ORDER BY startTime DESC " +
                               "LIMIT 1";
    
            result = jdbcTemplate.queryForMap(sqlQuery);
            // Convert the isBatchRunning value to boolean
            Long isBatchRunningValue = (Long) result.get("isBatchRunning");
            boolean isBatchRunning = (isBatchRunningValue == 1) ? true : false;
            result.put("isBatchRunning", isBatchRunning);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "Unable to fetch batch status");
        }
    
        return result;
    }
    
}