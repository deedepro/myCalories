package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.dto.EntityView;
import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.*;
import dev.mycalories.myCalories.repository.DiaryRepository;
import dev.mycalories.myCalories.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static dev.mycalories.myCalories.constants.Constants.ZERO;

@Service
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;
    private final ProductsService productsService;
    private final RecipeService recipeService;
    private final FoodService foodService;
    private final UserService userService;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, ProductsService productsService, RecipeService recipeService, FoodService foodService, UserService userService) {
        this.diaryRepository = diaryRepository;
        this.productsService = productsService;
        this.recipeService = recipeService;
        this.foodService = foodService;
        this.userService = userService;
    }

    @Override
    public Diary findDiary(long id) {
        return diaryRepository.findById(id).orElse(null);
    }

    @Override
    public void write(Food food) {

    }

    public void deleteEntity(long id) {
        Diary diary = findDiary(id);
        if (diary != null) {
            diaryRepository.delete(diary);
        }
    }

    @Override
    public void addEntity(long productId, BigInteger count, Date date, Mealtime mealtime) {
        Product product = productsService.findProduct(productId);
        Food food = foodService.findFood(product);
        User user = userService.getCurrentUser();
        Diary diary = new Diary(food, user, date, mealtime, count);
        diaryRepository.save(diary);
    }

    @Override
    public EntityView createEntityView(Diary diary) {
        EntityView entityView = new EntityView();
        entityView.setId(diary.getId());
        entityView.setWeight(diary.getWeight());
        entityView.setMealtime(diary.getMealtime().getName());
        entityView.setDate(diary.getDate());
        ProductView productView;
        if(diary.getFood().getProduct() != null){
            productView = productsService.createProductView(diary.getFood().getProduct(), diary.getWeight());
        } else {
            productView = recipeService.createRecipeView(diary.getFood().getRecipe(), diary.getWeight());
        }
        entityView.setProductView(productView);
        return entityView;
    }

    @Override
    public EntityView createTotalEntityView(List<EntityView> entities, String name) {
        BigInteger tWeight = BigInteger.ZERO;
        BigDecimal tProtein = ZERO, tFat = ZERO, tCarb = ZERO, tFibers = ZERO, tKcal = ZERO;
        for (EntityView entity : entities) {
            tWeight = tWeight.add(entity.getWeight());
            ProductView entityProductView = entity.getProductView();
            tProtein = tProtein.add(entityProductView.getProtein());
            tFat = tFat.add(entityProductView.getFat());
            tCarb = tCarb.add(entityProductView.getCarb());
            tFibers = tFibers.add(entityProductView.getFibers());
            tKcal = tKcal.add(entityProductView.getKcal());
        }
        EntityView entityView = new EntityView();
        entityView.setWeight(tWeight);
        ProductView productView = new ProductView();
        productView.setName(name);
        productView.setEnergyValues(tProtein, tFat, tCarb, tFibers, tKcal);
        entityView.setProductView(productView);
        return entityView;
    }

    @Override
    public List<EntityView> collectAllEntitiesByDate(Date date) {
        List<Diary> allByDate = findDayEntries(date);
        return createEntityViews(allByDate);
    }

    @Override
    public List<EntityView> collectAllEntitiesByDateAndMealtime(Date date, Mealtime mealtime) {
        User user = userService.getCurrentUser();
        List<Diary> allByDateAndMealtime = diaryRepository.findAllByUserAndDateAndMealtime(user, date, mealtime);
        return createEntityViews(allByDateAndMealtime);
    }

    @Override
    public void editEntry(long inputId, BigInteger inputWeight, Date inputDate, Mealtime mealtime) {
        Diary diary = findDiary(inputId);
        if (diary != null) {
            diary.setWeight(inputWeight);
            diary.setDate(inputDate);
            diary.setMealtime(mealtime);
            diaryRepository.save(diary);
        }
    }

    private List<EntityView> createEntityViews(List<Diary> allByDate) {
        List<EntityView> result = new ArrayList<>();
        for (Diary diary : allByDate) {
            EntityView entityView = createEntityView(diary);
            result.add(entityView);
        }
        return result;
    }

    public List<Diary> findDayEntries(Date date) {
        User user = userService.getCurrentUser();
        return diaryRepository.findAllByUserAndDate(user, date);
    }

    @Override
    public List<Diary> findMealtimeEntries(Date date, Mealtime mealtime) {
        User user = userService.getCurrentUser();
        return diaryRepository.findAllByUserAndDateAndMealtime(user, date, mealtime);
    }
}
