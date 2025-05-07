package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.repository.IRepository;
import vn.online.shop.onlineshop.repository.IUserRepository;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User> {
    private final IUserRepository userRepository;

    @Override
    public IRepository<User> getRepository() {
        return userRepository;
    }


}
