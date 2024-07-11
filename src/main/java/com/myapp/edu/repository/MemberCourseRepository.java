package com.myapp.edu.repository;

import com.myapp.edu.domain.MemberCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCourseRepository extends JpaRepository<MemberCourse, Long> {
}