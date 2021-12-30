package com.example.christmas.user.repository

import com.example.christmas.user.model.domain.UserDomain
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserDomain, Long>