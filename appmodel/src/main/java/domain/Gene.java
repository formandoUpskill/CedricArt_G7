package domain;

/**
 * Represents a gene with attributes like id, name, and description.
 * This class is used for managing gene-related data.
 */
public class Gene {

    private String id;

    private String name;

    private String description;



    public String getId() {
       return id;
     }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;

    }

    public void setName(String name) {
        this.name = name;
    }


    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Gene{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

