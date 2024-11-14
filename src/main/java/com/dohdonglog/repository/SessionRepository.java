package com.dohdonglog.repository;

import com.dohdonglog.domain.Session;
import com.dohdonglog.domain.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {


}
