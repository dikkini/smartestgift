package com.smartestgift.service;

import com.smartestgift.controller.model.GiftPage;
import com.smartestgift.dao.GiftCategoryDAO;
import com.smartestgift.dao.GiftDAO;
import com.smartestgift.dao.SmartUserDAO;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;
import com.smartestgift.dao.model.SmartUser;
import com.smartestgift.dao.model.SmartUserGift;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by dikkini on 28.02.14.
 * Email: dikkini@gmail.com
 */
@Service
@Transactional
public class ShopServiceImpl implements ShopService {
}
