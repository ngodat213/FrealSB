package com.example.frealsb.Services;

import com.example.frealsb.Entities.Food;
import com.example.frealsb.Repositories.FoodRepository;
import com.example.frealsb.RequestEntities.RequestFood;
import com.example.frealsb.Services.Interface.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FoodService implements IFoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Food> getAllFoods(String s, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return foodRepository.findAllBy(s, pageable);
    }

    @Override
    public Food getFood(String id) {
        return foodRepository.findById(id).get();
    }

    @Override
    public Food addFood(RequestFood req) {
        return foodRepository.save(req.toAddData());
    }

    @Override
    public Food updateFood(RequestFood req) {
        return foodRepository.save(req.toUpdateData());
    }

    @Override
    public Food deleteFood(String id) {
        Food data = getFood(id);
        data.setUpdatedAt(new Date());
        return foodRepository.save(data);
    }
}
