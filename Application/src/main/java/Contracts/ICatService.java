package Contracts;

import Models.Enums.CatColor;
import ResultTypes.TransactionResult;

import java.time.LocalDate;
import java.util.Collection;

public interface ICatService {
    TransactionResult addCatToContext(String catName, String catBreed, LocalDate catBirthday, CatColor catColor, String ownerName);
    TransactionResult mapCatToOwner(String ownerName, String catName);
    TransactionResult addFriendsToCat(String targetCatName, Collection<String> friendCatNames);
}

// TODO: maybe make UUID for each cat and owner to avoid issues with repeated names
