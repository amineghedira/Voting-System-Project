package com.WebProject.VotingSystem.services;

import com.WebProject.VotingSystem.domain.Admin;
import com.WebProject.VotingSystem.exceptions.EtAuthException;
import com.WebProject.VotingSystem.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Override
    public Admin validateAdmin(String adminName, String password) throws EtAuthException {
        return adminRepository.findByNameAndPassword(adminName, password);
    }

    @Override
    public Admin registerAdmin(String adminName, String password) throws EtAuthException {
        Integer count = adminRepository.getCountByName(adminName);
        if (count>0)
            throw new EtAuthException("adminName already used!");
        Integer adminID=adminRepository.create(adminName, password);
        return adminRepository.findByID(adminID );
    }
}
