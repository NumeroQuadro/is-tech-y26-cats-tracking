package Models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CatColorConverter implements AttributeConverter<CatColor, String> {

    @Override
    public String convertToDatabaseColumn(CatColor attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name(); // This will convert the enum to its name string
    }

    @Override
    public CatColor convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return CatColor.valueOf(dbData.toLowerCase()); // This will convert the string back to the enum
    }
}
