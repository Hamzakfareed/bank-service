package com.whitebox.users.repositories;

import com.whitebox.users.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String > {
}
