package ua.nure.ipz.zoo.dto;

import java.util.List;
import java.util.UUID;

public abstract class DomainEntityDto<T> {
    private final UUID domainId;

    protected DomainEntityDto(UUID domainId) {
        this.domainId = domainId;
    }

    public UUID getDomainId() {
        return domainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        }

        List<Object> set1 = this.getAttributesToIncludeInEqualityCheck();
        List<Object> set2 = this.getAttributesToIncludeInEqualityCheck();
        return set1.equals(set2);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        for (Object attr : this.getAttributesToIncludeInEqualityCheck()) {
            hash = hash * 31 + ((attr == null) ? 0 : attr.hashCode());
        }
        return hash;
    }

    protected abstract List<Object> getAttributesToIncludeInEqualityCheck();
}
