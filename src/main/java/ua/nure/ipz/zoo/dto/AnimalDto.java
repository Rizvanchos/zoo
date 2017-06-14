package ua.nure.ipz.zoo.dto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AnimalDto extends DomainEntityDto<AnimalDto> {
    private UUID aviaryId;
    private String name;
    private String type;
    private String imageUrl;
    private String description;

    public AnimalDto(UUID domainId, UUID aviaryId, String name, String type, String imageUrl, String description) {
        super(domainId);
        this.aviaryId = aviaryId;
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public UUID getAviaryId() {
        return aviaryId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        return Arrays.asList(getDomainId(), getAviaryId(), getName(), getType(), getImageUrl(), getDescription());
    }

    @Override
    public String toString() {
        return String.format("ID = %s\nAviaryId = %s\nName = %s\nType = %s\nImageUrl = %s\nDescription = %s", getDomainId(),
                aviaryId, name, type, imageUrl, description);
    }
}
