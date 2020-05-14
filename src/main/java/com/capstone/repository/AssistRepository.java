package com.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.entity.Assist;

@Repository
public interface AssistRepository extends JpaRepository<Assist, Long> {

	List<Assist> findAllByAssistMemberCommunityCode(String communityCode);
}
