package com.epam.esm.module3.controller.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TagDto extends RepresentationModel<TagDto> {

    private Long id;

    @NotNull
    @Size(min = 2,max = 45)
    private String name;

    public TagDto() {}

    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagDto)) return false;
        if (!super.equals(o)) return false;

        TagDto tagDto = (TagDto) o;

        if (!Objects.equals(id, tagDto.id)) return false;
        return Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
