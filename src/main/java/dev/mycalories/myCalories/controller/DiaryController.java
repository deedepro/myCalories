package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.dto.EntityView;
import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.dto.RecipeView;
import dev.mycalories.myCalories.entity.*;
import dev.mycalories.myCalories.service.DiaryService;
import dev.mycalories.myCalories.service.MealtimeService;
import dev.mycalories.myCalories.service.ProductsService;
import dev.mycalories.myCalories.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс отвечает за работу сайта по пути /diary
 */
@Controller
public class DiaryController {

    private final ProductsService productsService;
    private final RecipeService recipeService;
    private final DiaryService diaryService;
    private final MealtimeService mealtimeService;

    @Autowired
    public DiaryController(ProductsService productsService, RecipeService recipeService, DiaryService diaryService, MealtimeService mealtimeService) {
        this.recipeService = recipeService;
        this.productsService = productsService;
        this.diaryService = diaryService;
        this.mealtimeService = mealtimeService;
    }

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @GetMapping("/diary")
    String showDiaryPage(Model model,
                         @RequestParam(value = "mealtime", required = false) String mealtimeName,
                         @RequestParam(value = "date", required = false) Date date) {
        return showDiaryPage(model, mealtimeName, date, 0);
    }

    @GetMapping("/diary/{selectedId}")
    String showDiaryPage(Model model,
                         @RequestParam(value = "mealtime", required = false) String mealtimeName,
                         @RequestParam(value = "date", required = false) Date date,
                         @PathVariable(name = "selectedId") long selectedId) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        model.addAttribute("date", date);
        model.addAttribute("dateView", formatter.format(date));

        if (mealtimeName == null || mealtimeName.isEmpty()) {
            mealtimeName = "all";
        }
        model.addAttribute("mealtimeRusName", mealtimeService.translateName(mealtimeName));
        model.addAttribute("mealtime", mealtimeName);

        List<EntityView> entities = collectEntities(date, mealtimeName);
        model.addAttribute("entities", entities);

        EntityView selectedEntity;
        if (selectedId == 0) {
            selectedEntity = diaryService.createTotalEntityView(entities, mealtimeService.translateName(mealtimeName));
        } else {
            selectedEntity = findSelectedEntityView(entities, selectedId);
            selectedEntity.getProductView().setName(selectedEntity.getProductView().getName());
        }
        model.addAttribute("selectedEntity", selectedEntity);


        return "diary";
    }

    @GetMapping("/diary/menu")
    String showMenuPage(Model model,
                        @RequestParam(value = "date", required = false) Date date,
                        @RequestParam(value = "mealtime", required = false) String mealtimeName) {
        return showMenuPage(model, null, null, date, mealtimeName);
    }

    @GetMapping("/diary/menu/{filter}")
    String showMenuPage(Model model,
                        @PathVariable(value = "filter") String filter,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(value = "date", required = false) Date date,
                        @RequestParam(value = "mealtime", required = false) String mealtimeName) {
        return showMenuPage(model, filter, 0, search, date, mealtimeName);
    }

    @GetMapping("/diary/menu/{filter}/{id}")
    String showMenuPage(Model model,
                        @PathVariable(value = "filter") String filter,
                        @PathVariable(value = "id") long id,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(value = "date", required = false) Date date,
                        @RequestParam(value = "mealtime", required = false) String mealtimeName) {
        model.addAttribute("date", date);
        model.addAttribute("mealtime", mealtimeName);
        prepareModel(model, filter, id, search);
        return "diaryProductPage";
    }

    void prepareModel(Model model, String filter, long id, String search) {
        if(filter == null){
            filter = "all";
        }
        model.addAttribute("filter", filter);

        List<ProductView> items = new ArrayList<>();
        switch (filter) {
            case "all": {
                List<RecipeView> recipeViews = recipeService.collectAllRecipes();
                List<ProductView> productViews = productsService.collectAllProducts();
                items.addAll(productViews);
                items.addAll(recipeViews);
                break;
            }
            case "my": {
                List<RecipeView> recipeViews = recipeService.collectMyRecipes();
                List<ProductView> productViews = productsService.collectMyProducts();
                items.addAll(productViews);
                items.addAll(recipeViews);
                break;
            }
            case "favorites":
//            TODO: доделать "Избранное"
//            products = productsService.collectFavoriteProducts();
                break;
        }

        //search
        if (search != null && !search.isEmpty()) {
            items = productsService.searchProducts(items, search);
            model.addAttribute("search", search);
        }

        model.addAttribute("products", items);
        ProductView selectedProductView;
        if (id != 0) {
            selectedProductView = items.stream().filter(product -> product.getId().equals(id)).findAny().orElse(null);
        } else {
            selectedProductView = items.stream().findFirst().orElse(null);
        }
        model.addAttribute("selectedProduct", selectedProductView);
    }

    @GetMapping("/diary/add/{id}")
    String showAddPage(Model model,
                       @PathVariable(value = "id") long productId,
                       @RequestParam(value = "dateFilter", required = false) Date date,
                       @RequestParam(value = "mealtimeFilter", required = false) String mealtimeName) {
        //Параметры прошлой страницы
        model.addAttribute("date", date);
        model.addAttribute("mealtime", mealtimeName);

        ProductView productView;
        Product product = productsService.findProduct(productId);
        if(product != null){
            productView = productsService.createProductView(product);
        } else {
            Recipe recipe = recipeService.findRecipe(productId);
            productView = recipeService.createRecipeView(recipe);
        }

        model.addAttribute("product", productView);
        return "entity";
    }

    /**
     * Обработчик события открытия страницы "Дневник" в режиме изменений записи дневника
     *
     * @param entityId    идентификатор выбранной записи
     * @param model параметры страницы
     * @return открытие страницы "Дневник"
     */
    @GetMapping("/diary/edit/{id}")
    String showEditPage(Model model,
                        @PathVariable(value = "id") long entityId,
                        @RequestParam(value = "date", required = false) Date date,
                        @RequestParam(value = "mealtime", required = false) String mealtimeName) {
        //Параметры прошлой страницы
        model.addAttribute("date", date);
        model.addAttribute("mealtime", mealtimeName);
        //Параметры записи
        Diary entity = diaryService.findDiary(entityId);
        EntityView entityView = diaryService.createEntityView(entity);
        model.addAttribute("entity", entityView);
        //Параметры продукта/рецепта
        ProductView productView;
        Product product = entity.getFood().getProduct();
        if(product != null){
            productView = productsService.createProductView(product);
        } else {
            Long recipeId = entity.getFood().getRecipe().getId();
            Recipe recipe = recipeService.findRecipe(recipeId);
            productView = recipeService.createRecipeView(recipe);
        }
        model.addAttribute("product", productView);
        return "entity";
    }

    private List<EntityView> collectEntities(Date date, String mealtimeName) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        if (!"all".equals(mealtimeName)) {
            Mealtime mealtime = mealtimeService.findMealtimeByName(mealtimeName);
            return diaryService.collectAllEntitiesByDateAndMealtime(date, mealtime);
        } else {
            return diaryService.collectAllEntitiesByDate(date);
        }
    }

    private EntityView findSelectedEntityView(List<EntityView> entities, long id) {
        return entities.stream().filter(entity -> entity.getId().equals(id)).findAny().orElse(null);
    }

    /**
     * Добавить запись в дневник
     *
     * @param id           идентификатор выбранного продукта
     * @param mealtimeName наименование выбранного пользователем приема пищи
     * @param date         выбранная пользователем дата в формате строки
     * @param inputWeight  указанный пользователем вес
     * @param model        параметры страницы
     * @return обновление страницы дневника
     */
    @PostMapping("/diary/add/{id}")
    String addFoodToDiary(@PathVariable(value = "id") long id,
                          @RequestParam(value = "entityMealtime") String mealtimeName,
                          @RequestParam(value = "entityDate") Date date,
                          @RequestParam(value = "count") BigInteger inputWeight,
                          Model model) {
        Mealtime mealtime = mealtimeService.findMealtimeByName(mealtimeName);
        diaryService.addEntity(id, inputWeight, date, mealtime);
        String params = "?date=" + date + "&mealtime=" + mealtimeName;
        return "redirect:/diary" + params;
    }

    /**
     * Обработчик события изменения записи дневника в системе
     *
     * @param id           идентификатор выбранного продукта
     * @param mealtimeName наименование выбранного пользователем приема пищи
     * @param date         выбранная пользователем дата в формате строки
     * @param count        указанный пользователем вес
     * @param model        параметры страницы
     * @return переадресация на страницу "Дневника"
     */
    @PostMapping("/diary/edit/{id}")
    String editEntity(@PathVariable(value = "id") long id,
                      @RequestParam(value = "mealtime") String mealtimeName,
                      @RequestParam(value = "date") Date date,
                      @RequestParam(value = "entityMealtime") String entityMealtimeName,
                      @RequestParam(value = "entityDate") Date entityDate,
                      @RequestParam(value = "count") BigInteger count,
                      Model model) {
        Mealtime entityMealtime = mealtimeService.findMealtimeByName(entityMealtimeName);
        diaryService.editEntry(id, count, entityDate, entityMealtime);
        String params = "?date=" + date + "&mealtime=" + mealtimeName;
        return "redirect:/diary" + params;
    }

    /**
     * Удалить запись из дневника
     *
     * @param id    идентификатор удаляемой записи
     * @param model параметры страницы
     * @return обновление страницы дневника
     */
    @PostMapping("/diary/del/{id}")
    String deleteEntity(@PathVariable(value = "id") long id,
                        @RequestParam(value = "date", required = false) Date date,
                        @RequestParam(value = "mealtime", required = false) String mealtimeName,
                        Model model) {
        diaryService.deleteEntity(id);
        String params = "?date=" + date + "&mealtime=" + mealtimeName;
        return "redirect:/diary" + params;
    }
}
