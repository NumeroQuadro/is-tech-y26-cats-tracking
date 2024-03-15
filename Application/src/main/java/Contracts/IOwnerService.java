package Contracts;

import ResultTypes.TransactionResult;

import java.time.LocalDate;

public interface IOwnerService {
    TransactionResult addOwnerToContext(LocalDate birthday, String ownerName);
    TransactionResult deleteOwnerFromContext(String ownerName);
}
