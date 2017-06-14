package ua.nure.ipz.zoo.dto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProvisionDto extends DomainEntityDto<ProductDto> {
    private List<RationDto> rations;

    public ProvisionDto(UUID domainId, List<RationDto> rations) {
        super(domainId);
        this.rations = rations;
    }

    public List<RationDto> getRations() {
        return rations;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        List<Object> attributes = Arrays.asList(getDomainId(), rations);
        return attributes;
    }

    @Override
    public String toString() {
        String rationsView = rations.stream().map(ration -> "\n" + ration + "\n").reduce(String::concat).get().trim();
        return String.format("ID = %s\nRations: \n\n%s", getDomainId(), rationsView);
    }
}
