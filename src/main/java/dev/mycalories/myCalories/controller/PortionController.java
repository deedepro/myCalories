package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.entity.Portion;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.service.PortionService;
import dev.mycalories.myCalories.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PortionController {

    final PortionService portionService;
    final ProductsService productsService;

    @Autowired
    public PortionController(PortionService portionService, ProductsService productsService) {
        this.portionService = portionService;
        this.productsService = productsService;
    }

    @GetMapping("/products/{id}/portions")
    String makeProductPortionPage(Model model,
                                  @PathVariable(value = "id") long productId,
                                  @RequestParam(value = "selectedFilter", required = false) String selectedFilter) {
        Product product = productsService.findProduct(productId);
        model.addAttribute("product", product);
        List<Portion> portions = portionService.collectPortions(product);
        model.addAttribute("portions", portions);
        model.addAttribute("selectedFilter", selectedFilter);
        return "portion";
    }

    @PostMapping("/portions/add")
    String addPortion(Model model,
                      @RequestParam(name = "id") long id,
                      @RequestParam(name = "name") String name,
                      @RequestParam(name = "count") int count,
                      @RequestParam(value = "selectedFilter", required = false) String selectedFilter) {
        Product product = productsService.findProduct(id);
        portionService.createPortion(product, name, count);
        String urlParams = "/products/" + id
                + "/portions"
                + "?selectedFilter=" + selectedFilter;
        return "redirect:" + urlParams;
    }

    @PostMapping("/portions/del")
    String deletePortion(Model model,
                         @RequestParam(name = "id") long id,
                         @RequestParam(value = "selectedFilter", required = false) String selectedFilter) {
        Portion portion = portionService.findPortionById(id);
        portionService.deletePortion(portion);
        String urlParams = "/products/" + portion.getProduct().getId()
                + "/portions"
                + "?selectedFilter=" + selectedFilter;
        return "redirect:" + urlParams;
    }
}
