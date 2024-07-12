package com.myapp.edu.repository;

import com.myapp.edu.domain.Course;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @EntityGraph(attributePaths = {"memberCourseList.member"})
    @Query("select c from Course c")
    Page<Course> findAllCourses(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Course> findAllByIdIn(Set<Long> ids);
}
