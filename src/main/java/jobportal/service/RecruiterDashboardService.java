package jobportal.service;

import jobportal.dto.response.RecruiterDashboardResponse;

public interface RecruiterDashboardService {

    RecruiterDashboardResponse getDashboard(String recruiterEmail);

}
