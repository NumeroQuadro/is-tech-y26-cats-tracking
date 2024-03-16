import Models.Owner;
import Repositories.CatRepository;
import Repositories.OwnerRepository;
import org.junit.jupiter.api.Test;
import jakarta.persistence.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddOwner_ValidOwner_OwnerPersisted {
    @Test
    public void addOwner_ValidOwner_OwnerPersisted() {
        EntityManagerFactory mockedEntityManagerFactory = mock(EntityManagerFactory.class);
        EntityManager mockedEntityManager = mock(EntityManager.class);
        EntityTransaction mockedTransaction = mock(EntityTransaction.class);
        when(mockedEntityManagerFactory.createEntityManager()).thenReturn(mockedEntityManager);
        when(mockedEntityManager.getTransaction()).thenReturn(mockedTransaction);

        String name = "John Doe";
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        // Repository initialization
        OwnerRepository ownerRepository = new OwnerRepository(mockedEntityManagerFactory);

        Owner resultOwner = ownerRepository.addOwner(birthday, name);

        verify(mockedTransaction).begin();
        verify(mockedEntityManager).persist(resultOwner);
        verify(mockedTransaction).commit();

        assertNotNull(resultOwner);
        assertEquals(name, resultOwner.getName());
        assertEquals(birthday, resultOwner.getBirthDate());
    }
}
