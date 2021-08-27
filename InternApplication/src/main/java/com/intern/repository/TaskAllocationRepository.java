package com.intern.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.intern.model.TaskAllotment;

@Repository
public interface TaskAllocationRepository extends JpaRepository<TaskAllotment, Long>
{
	@Query(value = "select task_allotment_history.intern_name, task_allotment_history.testor_name, \r\n"
			+ "task_allotment_history.start_date, task_allotment_history.end_date, task_allotment.task_alloted\r\n"
			+ "from task_allotment inner join task_allotment_history on \r\n"
			+ "task_allotment.assigned_by=:mentorEmail and\r\n"
			+ "task_allotment.intern_id=task_allotment_history.intern_id",nativeQuery=true)
	ArrayList<Object> findAllInterns(@Param("mentorEmail") String mentorEmail);
}
