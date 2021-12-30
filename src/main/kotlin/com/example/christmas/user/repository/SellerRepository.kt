package com.example.christmas.user.repository

import com.example.christmas.user.model.domain.SellerDomain
import org.springframework.data.repository.CrudRepository

interface SellerRepository : CrudRepository<SellerDomain, Long>