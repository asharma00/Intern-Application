package com.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intern.model.TaskAllotmentHistory;
import com.intern.model.TaskAllotmentHistoryId;

@Repository
public interface TaskAllocationHistoryRepo extends JpaRepository<TaskAllotmentHistory, TaskAllotmentHistoryId>
{

}
