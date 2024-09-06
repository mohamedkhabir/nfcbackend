package com.camelsoft.scano.client.services.Auth;


import com.camelsoft.scano.client.Repository.Auth.UserDeviceRepository;
import com.camelsoft.scano.client.models.Auth.UserDevice;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.tools.exception.NotFoundException;
import com.camelsoft.scano.tools.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserDeviceService {

    // Autowire Repositories
    @Autowired
    private UserDeviceRepository repository;
    @Autowired
    private TokenUtil tokenUtil;

    public UserDevice findbytoken(String token) {
        try {
            return this.repository.findByToken(token).get();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public List<UserDevice> findbyuser(users user) {
        try {
            return this.repository.findAllByUser(user);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public List<UserDevice> findbyuserdevice(users user) {
        try {
            List<UserDevice> result= new ArrayList<>();
            result = this.repository.findAllByUserOrderByTimestmpDesc(user) ;
                return result;

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public UserDevice save(UserDevice model) {
        try {
            return this.repository.save(model);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public void deletebyid(Long id) {
        try {
            this.repository.deleteById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public UserDevice update(UserDevice model) {
        try {
            return this.repository.save(model);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public void deleteexpirationtoken(List<UserDevice> deviceInfoList, UserDetails userDetails) {
        for (UserDevice deviceInfo : deviceInfoList) {
            if (deviceInfo.getToken() == null)
                continue;
            if (!this.tokenUtil.isTokenValid(deviceInfo.getToken(), userDetails)) {
                try {
                    this.repository.deleteById(deviceInfo.getId());
                } catch (NoSuchElementException ex) {
                    throw new NotFoundException(String.format("No data found"));
                }
            }
        }

    }

}
