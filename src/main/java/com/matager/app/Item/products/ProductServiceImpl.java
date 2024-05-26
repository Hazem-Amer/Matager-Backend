package com.matager.app.Item.products;

import com.matager.app.Item.Item;
import com.matager.app.Item.ItemService;
import com.matager.app.Item.products.userclicks.UserProductClicks;
import com.matager.app.Item.products.userclicks.UserProductClicksRepository;
import com.matager.app.auth.AuthenticationFacade;
import com.matager.app.store.Store;
import com.matager.app.store.StoreRepository;
import com.matager.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ItemService itemService;
    private final AuthenticationFacade authenticationFacade;
    private final UserProductClicksRepository userProductClicksRepository;
    private final StoreRepository storeRepository;

    @Override
    public List<Item> getRecommendedProducts(Long storeId) {
        User user = authenticationFacade.getAuthenticatedUser();
        return itemService.getRecommendedProducts(storeId, user.getId());
    }

    @Override
    public Page<Item> getProductsFilteredAndSorted(Long storeId, String name, Long categoryId, Long subCategoryId, Boolean isVisible, String sort, int page, int size) {
        return itemService.getItemsFilteredAndSorted(storeId, name, categoryId, subCategoryId, isVisible, sort, page, size);
    }

    @Override
    public Item getProduct(Long storeId,Long itemId) {
        User user = authenticationFacade.getAuthenticatedUser();
        Item item = itemService.getItem(itemId);
        if (user != null) {
            Optional<UserProductClicks> optionalUserProductClicks =
                    userProductClicksRepository.findByUserIdAndItemId(user.getId(), itemId);
            if (optionalUserProductClicks.isPresent()) {
                optionalUserProductClicks.get().setClickCount(optionalUserProductClicks.get().getClickCount() + 1);
                userProductClicksRepository.saveAndFlush(optionalUserProductClicks.get());
            } else {
                Store store = storeRepository.findById(storeId).orElseThrow(()->new RuntimeException("Store not found"));
                userProductClicksRepository.saveAndFlush(new UserProductClicks(store,item,user,1));
            }
        }
        return item;
    }
}
