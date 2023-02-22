package org.dev.frontend.CAP.store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.dev.api.CAP.enums.Range;
import org.dev.api.CAP.enums.Role;
import org.dev.api.CAP.model.CategoriesDTO;

import java.util.List;

@Getter
public class UsersControlStore {
    private static final UsersControlStore usersControlStore = new UsersControlStore();

    public static UsersControlStore getStore() {return usersControlStore;}

    private final StoreRestUtils storeRestUtils = StoreRestUtils.getInstance();

    @Getter
    private final ObservableList<String> categoriesList = FXCollections.observableArrayList();

    public void refreshStore(){
        categoriesList.clear();
    }

    public void populateAllCategories() {
        List<CategoriesDTO> categoriesDTOS = storeRestUtils.getAllCategories().orElseThrow(RuntimeException::new);
        for (CategoriesDTO categoriesDTO: categoriesDTOS) {
            if (storeRestUtils.getCurrentUser().getRole() == Role.PERSONAL) {
                if (categoriesDTO.getRange() == Range.ALL || categoriesDTO.getRange() == Range.PUBLIC_PERSONAL) {
                    categoriesList.add(categoriesDTO.getName());
                } else if (categoriesDTO.getRange() == Range.PRIVATE_PERSONAL && categoriesDTO.getUsername()
                        .equals(storeRestUtils.getCurrentUser().getUserName())){
                    categoriesList.add(categoriesDTO.getName());
                }
            }
        }
    }

    public String saveNewCategory(CategoriesDTO categoriesDTO) {
        storeRestUtils.saveCategory(categoriesDTO);
        return "";
    }
}
