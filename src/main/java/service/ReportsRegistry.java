package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReportsRegistry {
    private static ReportsRegistry instance = new ReportsRegistry();

    protected Map<Integer, Long> reportsMap;

    public static ReportsRegistry getInstance() {
        return instance;
    }
    
    private ReportsRegistry() {
        reportsMap = new HashMap<>();
    }

    public void registerReport(int reportId, long userId) {
        reportsMap.put(reportId, userId);
    }

    public Optional<Long> getAuthorOfReport(int reportId) {
        return Optional.ofNullable(reportsMap.get(reportId));
    }
}
