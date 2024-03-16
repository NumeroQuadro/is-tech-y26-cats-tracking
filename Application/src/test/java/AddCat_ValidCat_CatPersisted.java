import Models.CatsMainInfo;
import Models.Enums.CatColor;
import Models.Owner;
import Repositories.CatRepository;
import Repositories.OwnerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class AddCat_ValidCat_CatPersisted {
    @Test
    public void addCat_ValidCat_CatPersisted() {
        EntityManagerFactory mockedEntityManagerFactory = mock(EntityManagerFactory.class);
        EntityManager mockedEntityManager = mock(EntityManager.class);
        EntityTransaction mockedTransaction = mock(EntityTransaction.class);
        when(mockedEntityManagerFactory.createEntityManager()).thenReturn(mockedEntityManager);
        when(mockedEntityManager.getTransaction()).thenReturn(mockedTransaction);

        String name = "Kitty";
        String catBreed = "Siamese";
        LocalDate birthday = LocalDate.of(2019, 1, 1);
        CatColor catColor = CatColor.semi_color;

        CatRepository catRepository = new CatRepository(mockedEntityManagerFactory);
        CatsMainInfo cat = catRepository.addCatToMainInfo("Kitty", "Siamese", LocalDate.of(2019, 1, 1), CatColor.semi_color);


        verify(mockedTransaction).begin();
        verify(mockedEntityManager).persist(cat);
        verify(mockedTransaction).commit();

        assertNotNull(cat);
        assertEquals(name, cat.getName());
        assertEquals(catBreed, cat.getBreed());
        assertEquals(birthday, cat.getBirthDate());
        assertEquals(catColor, cat.getColor());
    }
}
